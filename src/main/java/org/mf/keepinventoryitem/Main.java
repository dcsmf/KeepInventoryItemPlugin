package org.mf.keepinventoryitem;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    public static JavaPlugin instance;
    public static boolean debugMode=false;

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        instance=this;
        getLogger().info("特定物品死亡保存插件已启动");
        //注册死亡事件处理器
        Bukkit.getPluginManager().registerEvents(new EventProcessor(),this);
        //注册命令处理器
        Objects.requireNonNull(Bukkit.getPluginCommand("keepinventoryitem")).setExecutor(new CommandProcessor());
        //注册Tab补全
        Objects.requireNonNull(Bukkit.getPluginCommand("keepinventoryitem")).setTabCompleter(new CommandProcessor());
    }

    @Override
    public void onDisable() {
        saveConfig();
    }
}
