package com.ender.globalmarket;

import com.ender.globalmarket.command.AdminCommand;
import com.ender.globalmarket.command.UserCommand;
import com.ender.globalmarket.data.MarketItem;
import com.ender.globalmarket.money.Vault;
import com.ender.globalmarket.storage.Mysql;
import com.ender.globalmarket.storage.MysqlInit;
import com.ender.globalmarket.task.AcquireTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import com.tripleying.qwq.LocaleLanguageAPI.*;

import java.util.logging.Logger;

public class Main extends JavaPlugin {
    public static JavaPlugin instance;
    private static final Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();


        if (Bukkit.getPluginCommand("market") != null) {
            Bukkit.getPluginCommand("market").setExecutor(new UserCommand());
        }

        Vault.vaultSetup();

        Mysql m = new Mysql();


        if (!m.mysqlInit()) {
            getLogger().warning(ChatColor.RED + "Failed to connect to mysql. Check your config.yml to fix this. Plugin is shutting down.");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            //初始化数据库
            if (!MysqlInit.checkTable("market_log")) {
                MysqlInit.init_market_log();
            }
            if (!MysqlInit.checkTable("market_item_data")) {
                MysqlInit.init_market_item_data();
            }

            getLogger().info(ChatColor.GREEN + "Mysql connected.");
        }
        getLogger().info(ChatColor.GREEN + "欢迎使用Ender's Global Market插件，加载成功");
        getLogger().info(ChatColor.GREEN + "本插件赠予开源社区，源代码地址：https://github.com/EnderTheCoder/GlobalMarket");

        //注册定时
        //两分钟查一次最大人数
        Bukkit.getScheduler().runTaskTimerAsynchronously
                (this, new AcquireTask(this), 0L, 20 * 60L * 2);
    }
}