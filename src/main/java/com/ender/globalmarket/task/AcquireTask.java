package com.ender.globalmarket.task;

import com.ender.globalmarket.Main;
import com.ender.globalmarket.common.AppConst;
import com.ender.globalmarket.common.BukkitUtil;
import com.ender.globalmarket.data.MarketItem;
import com.ender.globalmarket.economy.MarketData;
import com.ender.globalmarket.economy.MarketTrade;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * 随机收购事件
 */
public class AcquireTask  implements Runnable{
    Logger log = Logger.getLogger("测试");
    private final Main plugin;

    public AcquireTask(Main plugin) {
        this.plugin = plugin;
    }

    /**
     * 进行随机收购事件
     */
    @Override
    public void run(){
        MarketTrade.randomAcquire = getRandomItem();
        MarketTrade.randomRestCount = new AtomicInteger(getRandomCount());
        Material item = MarketTrade.randomAcquire.item;
        //广播全服
        TranslatableComponent name = Component.translatable(item.name());

        StringBuilder stringBuilder = new StringBuilder();
        //Bukkit.broadcast(ChatColor.DARK_GREEN + "[Market]","globalmarket.broadcast");
    }

    /**
     * 获取一个随机出售物品
     * @return
     */
    public MarketItem getRandomItem(){
        List<MarketItem> list = MarketData.getMarketItem();
        //取出剩下的
        list = list.stream().filter(e -> e.x <= AppConst.ACQUIRE_MAX_PRICE).collect(Collectors.toList());

        Random r = new Random();
        int index = r.nextInt(list.size());

        return list.get(index);
    }

    /**
     * 获取一个随机存量
     * @return 随机存量
     */
    public int getRandomCount(){
        Random r = new Random();
        return AppConst.ACQUIRE_BASE + r.nextInt(AppConst.ACQUIRE_GAP);
    }
}
