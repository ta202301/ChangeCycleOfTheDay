package com.gtihub.ta.qiitasample;
import org.bukkit.plugin.java.JavaPlugin;

public class QiitaSample extends JavaPlugin{
    //↓ onEnableはロードされた時に実行されるメソッド
    @Override
    public void onEnable() {
        // ↓ サーバー上にログを残す
        getLogger().info("Hello, Qiita!");
    }
}
