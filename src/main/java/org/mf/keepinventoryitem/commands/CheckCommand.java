package org.mf.keepinventoryitem.commands;

import org.bukkit.command.CommandSender;
import org.mf.keepinventoryitem.ConfigReader;
import org.mf.keepinventoryitem.utils.Util;

public class CheckCommand {
    public CheckCommand(CommandSender sender){
        if(!Util.hasPermission(sender,"kii.check")) {
            Util.sendMsg(sender,"你无权这样做");
            return;
        }
        sender.sendMessage(new ConfigReader().readItemsWithIndex().toString());
    }
}
