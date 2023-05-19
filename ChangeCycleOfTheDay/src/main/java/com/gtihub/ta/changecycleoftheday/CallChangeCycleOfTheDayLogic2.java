package com.gtihub.ta.changecycleoftheday;

import org.bukkit.scheduler.BukkitRunnable;

public class CallChangeCycleOfTheDayLogic2 extends BukkitRunnable {

	@Override
	public void run() {
		ChangeCycleOfTheDay logic = new ChangeCycleOfTheDay();
		logic.setTickMorning();
		
	}

}
