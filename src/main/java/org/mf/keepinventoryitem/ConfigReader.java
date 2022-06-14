package org.mf.keepinventoryitem;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 这个类用来从config.yml读取不会掉落的方块的信息
 */
public class ConfigReader {
    private final FileConfiguration config;
    private final File configFile;

    public ConfigReader(){
        //读取配置文件
        config=Main.instance.getConfig();
        configFile=new File(Main.instance.getDataFolder(),"config.yml");
    }
    private boolean saveConfigFile(){
        try {
            config.save(configFile);
            return true;
        } catch (IOException e) {
            Main.instance.getLogger().warning(e.getMessage());
            return false;
        }
    }
    public boolean addItem(ItemStack item){
        //得到物品名称前后缀，如minecraft和stone
        var prefix=item.getType().getKey().getNamespace();
        var itemName=item.getType().getKey().getKey();
        //从配置文件读取物品名单
        var definedItems = readItems();
        //把新增的物品添加到名单上，并保存到配置文件
        definedItems.add(prefix+":"+itemName);
        config.set("items",definedItems);
        return saveConfigFile();
    }

    public List<String> readItems(){
        //从配置文件读取物品名称
        return config.getStringList("items").stream().distinct().map(String::toLowerCase).collect(Collectors.toList());
    }
    public List<String> readItemsWithIndex(){
        var itemsList=readItems();
        for(int i=0;i<itemsList.size();i++){
            itemsList.set(i, ChatColor.DARK_GREEN + String.valueOf(i) + ChatColor.WHITE + " -> " + "{" + ChatColor.GOLD + itemsList.get(i) + ChatColor.WHITE + "} ");
        }
        return itemsList;
    }

    public Boolean delItem(int index, CommandSender sender){
        //读取物品
        List<String> itemsList =readItems();
        //如果超索引则失败
        if(index<0 || index>=itemsList.size())
            return false;
        //开始移除并保存
        String itemName=itemsList.remove(index);
        config.set("items",itemsList);
        //文件保存成功则返回真
        if(saveConfigFile()){
            sender.sendMessage(itemName+"已经删除成功！");
            return true;
        }
        return false;
    }
}
