package org.mf.keepinventoryitem.commands;

import org.bukkit.command.CommandSender;
import org.mf.keepinventoryitem.utils.Util;

public class HelpCommand {
    final String header="&7--------------------- [&a 掉落保护 &7] ---------------------\n";
    final String content= """
            &7- &a/kii help &f- &7展示这个帮助菜单
            &7- &a/kii check &f- &7列出当前在保护名单上的物品
            &7- &a/kii debug &f- &7为op开启掉落物显示功能
            &7- &a/kii add &f- &7将主手上的物品添加到掉落保护名单.
            &7- &a/kii del <index> &f- &7从保护名单中删除指定索引值的物品
            """;
    public HelpCommand(CommandSender sender){
        if(!Util.hasPermission(sender,"kii.help")) {
            Util.sendMsg(sender,"你无权这样做");
            return;
        }
        sender.sendMessage(Util.strFormatter(header+content));
    }
}
