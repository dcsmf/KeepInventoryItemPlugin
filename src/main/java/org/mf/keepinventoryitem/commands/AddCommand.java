package org.mf.keepinventoryitem.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.mf.keepinventoryitem.ConfigReader;
import org.mf.keepinventoryitem.utils.Util;

public class AddCommand {
    public AddCommand(CommandSender sender){
        //只有玩家可以执行
        if(sender instanceof Player){
            //判断有无对应权限
            if(!Util.hasPermission(sender,"kii.add")) {
                Util.sendMsg(sender,"你无权这样做");
                return;
            }
            //得到主手物品
            ItemStack item=((Player) sender).getInventory().getItemInMainHand();
            //写入配置文件
            if(new ConfigReader().addItem(item)){
                Util.sendMsg(sender, "已经添加成功");
            }else {
                Util.sendMsg(sender,"添加失败");
            }
        }
    }
}
