package com.tining.demonmarket.command;

import com.tining.demonmarket.data.MarketItem;
import com.tining.demonmarket.economy.MarketData;
import com.tining.demonmarket.economy.MarketTrade;
import com.tining.demonmarket.player.Inventory;
import com.tining.demonmarket.storage.ConfigReader;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.logging.Logger;

//import javax.annotation.ParametersAreNonnullByDefault;

public class UserCommand implements CommandExecutor {

    Logger logger = Logger.getLogger("command");

    public static Map<String,Double> worth = ConfigReader.getWorth();

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
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "sell": {
                Material itemToSell = player.getInventory().getItemInMainHand().getType();
                if(Objects.isNull(itemToSell) || itemToSell.name().equals("AIR")){
                    sender.sendMessage(ChatColor.YELLOW + "[Market]你手里的物品无法交易");
                    return true;
                }
                //检测此种物品是否可交易
                //MarketItem marketItem = MarketData.getMarketItem(itemToSell);
                if (!worth.containsKey(itemToSell.name())) {
                    sender.sendMessage(ChatColor.YELLOW + "[Market]你输入的物品当前不可交易");
                    return true;
                }

                double value = worth.get(itemToSell.name());
                if (value == 0){
                    sender.sendMessage(ChatColor.YELLOW + "[Market]你输入的物品当前一文不值");
                    return true;
                }
                int amountInInventory = Inventory.calcInventory(player, itemToSell);
                int sellAmount = 0;
                if(args.length == 1){
                    sellAmount = player.getInventory().getItemInMainHand().getAmount();
                    MarketTrade.trade(player, itemToSell ,value, sellAmount, MarketTrade.type.SELL);
                    break;
                }
                if(!"all".equals(args[1].toLowerCase(Locale.ROOT))){
                    return false;
                }

                sellAmount = amountInInventory;
                MarketTrade.trade(player, itemToSell, value, sellAmount, MarketTrade.type.SELL);
                break;

            } case "test":{


            }
            default:break;
        }
        return true;
    }
}