package org.mf.keepinventoryitem.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class Util {
    public static boolean isNullOrAir(final ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }
    //以插件名称前缀向玩家/控制台发信息
    public static void sendMsg(CommandSender sender,String msg){
        sender.sendMessage(ChatColor.BLUE + "[掉落保护]" + ChatColor.DARK_GREEN+msg);
    }
    //解析字符串中的'&'颜色代码
    public static String strFormatter(String ori){
        return ChatColor.translateAlternateColorCodes('&',ori);
    }
    //检查俩物品类型是否相等
    public static boolean isTypeEqual(ItemStack item1,ItemStack item2){
        return item1.getType().getKey().getNamespace()
                .equalsIgnoreCase(item2.getType().getKey().getNamespace())
                &&
                item1.getType().getKey().getKey()
                        .equalsIgnoreCase(item2.getType().getKey().getKey());
    }
    //检查玩家是否有对应权限，控制台则直接返回true
    public static boolean hasPermission(CommandSender sender,String permission){
        return (sender.hasPermission("kii.admin")||sender.hasPermission(permission) || sender.isOp()||sender instanceof org.bukkit.command.ConsoleCommandSender||sender instanceof org.bukkit.command.RemoteConsoleCommandSender);
    }
}
