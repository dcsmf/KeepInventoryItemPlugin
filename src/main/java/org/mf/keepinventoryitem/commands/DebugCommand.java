package org.mf.keepinventoryitem.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.mf.keepinventoryitem.Main;
import org.mf.keepinventoryitem.utils.Util;

public class DebugCommand {
    public DebugCommand(CommandSender sender){
        if(!Util.hasPermission(sender,"kii.debug")) {
            Util.sendMsg(sender,"你无权这样做");
            return;
        }
        if(Main.debugMode){
            Util.sendMsg(sender,"debug模式已关闭");
            Main.debugMode=false;
        }else {
            Util.sendMsg(sender,"debug模式已开启，将为op显示掉落物详情");
            Util.sendMsg(sender, ChatColor.DARK_RED+"请注意：物品不要过多，否则聊天框会显示不全");
            Main.debugMode=true;
        }
    }
}
