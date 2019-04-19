package de.appwerft.watchdog;

import org.appcelerator.kroll.common.Log;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;


public class Watchdog extends BroadcastReceiver {
	private boolean debug;
	private int interval;
	private static final String LCAT = WatchdogModule.LCAT;
	
	private void L(String txt) {
		if (debug) Log.d(LCAT,txt);
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		L("onAlarmReceived");
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
		wl.acquire();
		wl.release();
	}

	public void start(Context context) {
		start(context,true,60*1000*10);
	}
	public void start(Context context,boolean debug,int interval) {
		this.debug = debug;
		this.interval = interval;
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, Watchdog.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), this.interval, pi); // Millisec * Second *
		L("started");// Minute
	}

	public void stopp(Context context) {
		L("stopped");
		Intent intent = new Intent(context, Watchdog.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}
}