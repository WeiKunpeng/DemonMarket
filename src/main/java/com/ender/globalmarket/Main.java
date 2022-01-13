package com.ender.globalmarket;

import com.ender.globalmarket.command.AdminCommand;
import com.ender.globalmarket.command.UserCommand;
import com.ender.globalmarket.data.MarketItem;
import com.ender.globalmarket.money.Vault;
import com.ender.globalmarket.storage.Mysql;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin {
    public static JavaPlugin instance;
    private static final Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onDisable() {
        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        getLogger().info(ChatColor.GREEN + "欢迎使用Ender's Global Market插件，加载成功");

        if (Bukkit.getPluginCommand("globalmarket") != null) {
            Bukkit.getPluginCommand("globalmarket").setExecutor(new UserCommand());
        }
        if (Bukkit.getPluginCommand("globalmarketadmin") != null) {
            Bukkit.getPluginCommand("globalmarketadmin").setExecutor(new AdminCommand());
        }

        Vault.vaultSetup();

        Mysql m = new Mysql();

        if (!m.mysqlInit()) {
            getLogger().warning(ChatColor.RED + "Failed to connect to mysql. Check your config.yml to fix this. Plugin is shutting down.");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            getLogger().info(ChatColor.GREEN + "Mysql connected.");
        }
    }
}