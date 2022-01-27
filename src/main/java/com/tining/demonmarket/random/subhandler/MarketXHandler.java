package com.tining.demonmarket.random.subhandler;

import com.tining.demonmarket.data.MarketEvent;
import com.tining.demonmarket.data.MarketItem;
import com.tining.demonmarket.economy.MarketData;
import com.tining.demonmarket.random.RandomEventHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class MarketXHandler implements RandomEventHandler {

    @Override
    public void run(MarketEvent event) {
        MarketItem marketItem = MarketData.getMarketItem(event.material);

        if (marketItem == null) {
            return;
        }
        //广播全服
        Bukkit.broadcast(ChatColor.DARK_GREEN + "[DemonMarket]" + event.ban, "DemonMarket.broadcast");

        double temp = marketItem.x;
        temp *= 1 + (event.function ? event.percent : - event.percent);
        marketItem.x = (int) temp;
        MarketData.updateMarketItem(marketItem);
    }

}
