package com.tining.demonmarket.storage;

import com.tining.demonmarket.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class ConfigReader {

    public static FileConfiguration config = Main.instance.getConfig();

    public static String getMysqlConfig(String mysqlConfigTag) {
        return config.getString(mysqlConfigTag);
    }

    public static double getRate() {
        return config.getDouble("TaxRate");
    }

    public static int getTimeOut(String timeOutTag) {
        return config.getInt("TimeOut." + timeOutTag);
    }

    public static boolean getEnable(String enableTag) {
        return config.getBoolean("Enable." + enableTag);
    }

    public static double getTaxRate() {
        return config.getDouble("TaxRate");
    }

    public static Map<String, Double> getWorth(){
        Map<String, Double> value = new HashMap<>();
        Map<String, Object> data = config.getConfigurationSection("worth").getValues(false);
        for (String obj  : data.keySet()) {
            value.put(obj, Double.parseDouble(data.get(obj).toString()));
        }
        return value;
    }

}