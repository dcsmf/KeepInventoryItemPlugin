package org.mf.keepinventoryitem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.mf.keepinventoryitem.commands.*;
import org.mf.keepinventoryitem.utils.Util;

import java.util.*;
import java.util.stream.IntStream;

public class CommandProcessor implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        //如果有参数
        if (args.length >= 1) {
            switch (args[0].toLowerCase()) {
                case "check" -> new CheckCommand(sender);
                case "debug" -> new DebugCommand(sender);
                case "add" -> new AddCommand(sender);
                case "del" -> new DelCommand(sender, args);
                case "help" -> new HelpCommand(sender);
            }
            return true;
        }
        new HelpCommand(sender);
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        final String[] COMMANDS = new String[] {"check","help","add","del","debug"};

            List<String> completions = new ArrayList<>();
            if (args.length == 1) {
                //字符串匹配
                for(String c:COMMANDS){
                    if(c.contains(args[0])&&Util.hasPermission(sender,"kii."+c)){
                        completions.add(c);
                    }
                }
            } else if ("del".equals(args[0]) && args.length == 2) {
                //生成索引值List
                completions=IntStream.range(0,new ConfigReader().readItems().size()).mapToObj(String::valueOf).toList();
            }
            return completions;
    }
}