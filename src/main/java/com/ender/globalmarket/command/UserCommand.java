package com.ender.globalmarket.command;

import com.ender.globalmarket.Main;
import com.ender.globalmarket.data.MarketItem;
import com.ender.globalmarket.data.Trade;
import com.ender.globalmarket.economy.MarketData;
import com.ender.globalmarket.economy.MarketEconomy;
import com.ender.globalmarket.economy.MarketTrade;
import com.ender.globalmarket.money.Vault;
import com.ender.globalmarket.player.Inventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

//import javax.annotation.ParametersAreNonnullByDefault;

public class UserCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }
        if (args.length == 0) {
            // 参数数量太少，拒绝处理
            return false;
        }

        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "sell": {
                Material itemToSell = player.getInventory().getItemInMainHand().getType();
                if(Objects.isNull(itemToSell) || itemToSell.name().equals("AIR")){
                    sender.sendMessage(ChatColor.YELLOW + "[Market]你手里的物品无法交易");
                    return true;
                }
                //检测此种物品是否可交易
                MarketItem marketItem = MarketData.getMarketItem(itemToSell);
                if (marketItem == null) {
                    sender.sendMessage(ChatColor.YELLOW + "[Market]你输入的物品当前不可交易");
                    return true;
                }
                if (marketItem.x == 0){
                    sender.sendMessage(ChatColor.YELLOW + "[Market]你输入的物品当前一文不值");
                    return true;
                }
                int amountInInventory = Inventory.calcInventory(player, itemToSell);
                int sellAmount = 0;
                if(args.length == 1){
                    sellAmount = player.getInventory().getItemInMainHand().getAmount();
                    MarketTrade.trade(player, marketItem, sellAmount, MarketTrade.type.SELL);
                    break;
                }
                if(!"all".equals(args[1].toLowerCase(Locale.ROOT))){
                    return true;
                }

                sellAmount = amountInInventory;
                MarketTrade.trade(player, marketItem, sellAmount, MarketTrade.type.SELL);
                break;

            }
            default:break;
        }
        return true;
    }
}