package com.ender.globalmarket.economy;

import com.ender.globalmarket.common.MathUtil;
import com.ender.globalmarket.data.MarketItem;
import com.ender.globalmarket.storage.ConfigReader;
import org.bukkit.Material;

import static org.bukkit.Bukkit.getLogger;

public class MarketEconomy {

    /**
     * 规范化数字，目前不做处理
     * @param money
     * @return
     */
    public static double formatMoney(double money) {
        return money;
    }

    public static double getBuyingPrice(MarketItem item, int count) {
        double price = 0.0;

        while (count > 0) {
            count--;
            item.x--;
            price += calculate(item);
        }
        return formatMoney(price);
    }

    /**
     * 计算出售价格
     * @param item 物品
     * @param count 数量
     * @param money 玩家资产
     * @return 结算价格
     */
    public static double getSellingPrice(MarketItem item, int count,double money) {
        double price = 0.0;
        double onePrice = 0.0;

        while (count > 0) {
            count--;
            onePrice += MathUtil.priceDownByProperty(item.x,money);
            price += onePrice;
            money += onePrice;
        }
        return formatMoney(price);
    }

    public static double getTax(double price) {
        return formatMoney(price * ConfigReader.getTaxRate());
    }
    //价格计算函数式:f(x)=k/(x+1)^(1/b)
    public static double calculate(MarketItem item) {
        double price = item.k / Math.pow((double) item.x + 1.0,  1.0 / (double) item.b);
        return price;
    }



}
