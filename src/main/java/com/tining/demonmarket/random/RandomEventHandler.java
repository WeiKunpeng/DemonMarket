package com.tining.demonmarket.random;

import com.tining.demonmarket.data.MarketEvent;

public interface RandomEventHandler {
    public void run(MarketEvent event);
}
