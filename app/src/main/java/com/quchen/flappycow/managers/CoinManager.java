package com.quchen.flappycow.managers;

public class CoinManager {

    public CoinManager() {
    }

    public int coin = 0;


    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public void increaseCoin() {
        coin++;
    }
}
