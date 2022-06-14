package org.mf.keepinventoryitem.commands;

import org.bukkit.command.CommandSender;
import org.mf.keepinventoryitem.ConfigReader;
import org.mf.keepinventoryitem.utils.Util;

import java.util.regex.Pattern;

public class DelCommand {
    public DelCommand(CommandSender sender,String[] args){
        if(!Util.hasPermission(sender,"kii.del")) {
            Util.sendMsg(sender,"你无权这样做");
            return;
        }
        if(args.length>1 && Pattern.matches("^\\d+$",args[1])){
            if(!new ConfigReader().delItem(Integer.parseInt(args[1]),sender)){
                sender.sendMessage("删除失败，请输入要删除的数据的索引值："+new ConfigReader().readItemsWithIndex().toString());
            }
        }
    }
}
