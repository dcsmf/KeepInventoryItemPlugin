package org.mf.keepinventoryitem;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.mf.keepinventoryitem.utils.Util;

import java.util.ArrayList;
import java.util.List;

class EventProcessor implements Listener {
    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent e) {
        if(!e.getKeepInventory()){
            //如果在debug模式且有权限，则发送信息
            if(Main.debugMode&&Util.hasPermission(e.getEntity(),"kii.debug"))
                Util.sendMsg(e.getEntity(),"未开启死亡不掉落，插件无法为玩家保存背包");
            return;
        }
        //玩家位置
        final Location location = e.getEntity().getLocation();
        //玩家所有的背包物品，物品可能为null
        final ItemStack[] contents = e.getEntity().getInventory().getContents();
        //配置文件中的物品名称列表
        final List<String> whiteList = new ConfigReader().readItems();
        //不掉落的物品
        final List<ItemStack> keeps= new ArrayList<>();
        //会掉落的物品
        final List<ItemStack> drops=new ArrayList<>();
        //将背包物品与白名单比较，进行分类，分为掉落和不掉落的，分别放到drops和keeps里
        for(ItemStack item:contents){
            if(null==item)
                continue;
            if(whiteList.contains(item.getType().getKey().getNamespace().toLowerCase() + ":" + item.getType().getKey().getKey().toLowerCase())){
                keeps.add(item);
            }else {
                drops.add(item);
            }
        }
        //开启线程进行背包的清理和掉落物的生成
        (new BukkitRunnable(){
            @Override
            public void run() {
                //遍历背包所有物品
                for (ItemStack content : contents) {
                    if (null == content)
                        continue;
                    //遍历需要丢的
                    for (ItemStack drop : drops) {
                        if (Util.isTypeEqual(content, drop)) {
                            //比较成功则丢到世界上然后把背包对应物品清零
                            e.getEntity().getWorld().dropItemNaturally(location, content);
                            content.setAmount(0);
                        }
                    }
                }
                //开启debug模式则向玩家发送调试信息
                if(Main.debugMode && e.getEntity().hasPermission("kii.debug")){
                    Util.sendMsg(e.getEntity(),"需要保存的物品->" + ChatColor.WHITE + keeps.stream().map(i -> "{ " + ChatColor.GOLD + i.getType().getKey().getKey() + ChatColor.WHITE + " } ").toList());
                    Util.sendMsg(e.getEntity(),"将会掉落的物品->" + ChatColor.WHITE + drops.stream().map(i -> "{ " + ChatColor.GOLD + i.getType().getKey().getKey() + ChatColor.WHITE + " } ").toList());
                }
            }
        }).runTask(Main.instance);

        //region 清空背包然后再给物品的写法
//        e.setKeepInventory(false);
//        //手动接管了掉落哪些物品，背包会被清空
//        (new BukkitRunnable(){
//            @Override
//            public void run() {
//                int dropItemAmount = 0;
//                final HashMap<Integer,ItemStack> overflow=e.getEntity().getInventory().addItem(keeps.toArray(ItemStack[]::new));
//                if(!overflow.isEmpty()){
//                    drops.addAll(overflow.values());
//                    Util.sendMsg(e.getEntity(),"由于背包已满，多出的物品也强制掉落了！");
//                }
//                for (ItemStack item : drops) {
//                    dropItemAmount++;
//                    e.getEntity().getWorld().dropItemNaturally(location,item);
//                }
//                Util.sendMsg(e.getEntity(),"丢了"+dropItemAmount+"个物品");
//            }
//        }).runTask(Main.instance);
        //endregion

        //region 最老版本写法
//        //得到掉落物
//        var contents = e.getDrops();
//        //不掉落则不处理
//        if (contents.isEmpty())
//            return;
//        //设置死亡不掉落但清空背包防止刷物品
//        e.setKeepInventory(true);
//        e.getEntity().getInventory().clear();
//        //将物品进行分类，掉的和不掉的
//        var sortedItems = contents.stream()
//                .collect(Collectors.groupingBy(i -> new ConfigReader().readItems().contains(i.getType().getKey().getNamespace() + ":" + i.getType().getKey().getKey())));
//        //需要掉落的
//        List<ItemStack> drops;
//        if (null != sortedItems.get(false)) {
//            drops = sortedItems.get(false);
//        } else {
//            drops = new ArrayList<>();
//        }
//        //需要保存的
//        if (null != sortedItems.get(true)) {
//            var keeps = sortedItems.get(true);
//            var keepsItems = keeps.toArray(ItemStack[]::new);
//            //向玩家背包填充物品，溢出的物品则放到Map里
//            HashMap<Integer, ItemStack> overflow = e.getEntity().getInventory().addItem(keepsItems);
//            //把溢出的物品也放到要掉的里面
//            if (!overflow.isEmpty()) {
//                drops.addAll(overflow.values());
//                e.getEntity().sendMessage(Util.getPrefix() + "由于背包已满，多出的物品也强制掉落了！");
//            }
//            if (Main.debugMode && e.getEntity().isOp()) {
//                e.getEntity().sendMessage(Util.getPrefix() + "需要保存的物品->" + ChatColor.WHITE + keeps.stream().map(i -> "{ " + ChatColor.GOLD + i.getType().getKey().getKey() + ChatColor.WHITE + " } ").toList());
//            }
//        }
//        //设置丢出的掉落物
//        if (!drops.isEmpty()) {
//            if (Main.debugMode && e.getEntity().isOp()) {
//                e.getEntity().sendMessage(Util.getPrefix() + "将会掉落的物品->" + ChatColor.WHITE + drops.stream().map(i -> "{ " + ChatColor.GOLD + i.getType().getKey().getKey() + ChatColor.WHITE + " } ").toList());
//            }
//            //向世界丢出掉的物品
//            for (ItemStack item : drops) {
//                e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), item);
//            }
//        }
//        e.getDrops().clear();
        //endregion
    }
}