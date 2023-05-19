package com.gtihub.ta.changecycleoftheday;

import java.util.Objects;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 昼の長さが約50分になるプラグイン
 * 
 * A plug-in that makes the day length about 50 minutes
 * 
 * @author T
 *
 */
public class ChangeCycleOfTheDay extends JavaPlugin {
	
	/** BukkitのTaskTimerを呼ぶためのインスタンス */
	/** Instance for calling Bukkit's TaskTimer */
	private static Plugin plugin = null;

	/**  稼働しているサーバー */
	/** Running server  */
	private Server server = null;

	/**  昼の時間を変更したいWorld */
	/** World that wants to change the time of day */
	private World thisWorld = null;

	/**  tickを巻き戻した回数 */
	/**  Number of ticks rewound */
	private int setMorningCnt = 0;

	/**  trueの場合、tickを巻き戻さずに夜を来させる */
	/**  If true, let the night come without rewinding the tick */
	private boolean IsNight = false;

	@Override
	public void onEnable() {
		// サーバーが起動されると有効化される
		// Enabled when the server is started
		
		plugin = this;
		getLogger().info("ChangeCycleOfTheDay is running");

		setTickMorning();

		long beforeStartTick = 0;
		long repeatTick = 500;
		new CallChangeCycleOfTheDayLogic().runTaskTimer(getPlugin(), beforeStartTick, repeatTick);

	}
	
	/**
	 * tickを朝(1000)に巻き戻す
	 * 
	 * Rewind tick to morning (1000)
	 * 
	 */
	public void setTickMorning() {

		// 稼働しているサーバーとワールドの情報を取得する
		// Get running servers and worlds
		server = getServer();
		// 引数はワールドが生成されているディレクトリの名称になる。たぶん。
		// The argument is the name of the directory in which the world is generated. perhaps.
		thisWorld = server.getWorld("world");

		// 念のためワールドが取得できているか確認
		// check if the world has been acquired
		if (!Objects.equals(null, thisWorld)) {

			// 無事取得できていたら、ログを出力して巻き戻しを実施
			// If successfully acquired, output the log and rewind
			getLogger().info("Set Tick Morning");
			thisWorld.setTime(1000);

		} else {
			getLogger().info("Cannot get World");
		}
	}

	/**
	 * Bukkitのタイマー実行用にプラグインインスタンスを返すだけのクラス
	 * 
	 * A class that simply returns a plugin instance for Bukkit timer execution.
	 * 
	 * @return Plugin
	 */
	public static Plugin getPlugin() {
		return plugin;
	}

	/**
	 * 
	 * タイマーで呼び出されるクラス。
	 * 夜を来させるかどうか判定し、falseだった場合はtickの巻き戻し処理を呼び出す
	 * 
	 * A class called by a timer. 
	 * Determine whether to let the night come, and if it is false, call the rewind process of tick.
	 * 
	 */
	public class CallChangeCycleOfTheDayLogic extends BukkitRunnable {

		@Override
		public void run() {

			// ワールドのtickをチェック
			// Check world tick
			long time = thisWorld.getTime();

			if (!IsNight) {
				// 夜を来させない場合
				// If you don't let the night come
				if (11000 <= time) {
					// tickが11000以上だった場合、朝に巻き戻す
					// If tick is 11000 or more, rewind to morning
					setTickMorning();
					// 巻き戻しカウントをインクリメント
					// Increment rewind count
					setMorningCnt++;
					if (setMorningCnt == 4) {
						// 4回巻き戻しを実行後、夜を来させるフラグをtrueに設定する
						// Set the nightfall flag to true after rewinding 4 times
						IsNight = true;
						// 巻き戻しカウントをリセット
						// reset rewind count
						setMorningCnt = 0;
						getLogger().info("Set NightFlag true");
					}
				}
			} else {
				// 夜を来させる場合
				// if you let the night come
				// Just in case, put a count reset here as well
				setMorningCnt = 0;
				if (0<= time && time< 1000) {
					// tickが0を回っていたら、夜を来させるフラグにfalseを設定
					// If the tick is around 0, set the flag to bring the night to false
					IsNight = false;
					getLogger().info("Set NightFlag false");
				}
			}
		}
	}
}
