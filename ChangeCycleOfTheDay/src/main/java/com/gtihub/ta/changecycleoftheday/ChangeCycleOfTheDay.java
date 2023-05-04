package com.gtihub.ta.changecycleoftheday;

import org.bukkit.plugin.java.JavaPlugin;

public class ChangeCycleOfTheDay extends JavaPlugin{
    //↓ onEnableはロードされた時に実行されるメソッド
    @Override
    public void onEnable() {
        // ↓ サーバー上にログを残す
        getLogger().info("Hello, Qiita!");
    }
	

}
