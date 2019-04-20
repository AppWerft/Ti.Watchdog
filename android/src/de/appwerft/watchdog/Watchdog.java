package de.appwerft.watchdog;

import org.appcelerator.kroll.common.Log;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

// https://stackoverflow.com/questions/4459058/alarm-manager-example
public class Watchdog extends BroadcastReceiver {
	private boolean debug;
	private int interval;
	private static final String LCAT = WatchdogModule.LCAT;

	private void L(String txt) {
		if (debug)
			Log.d(LCAT, txt);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		L("onAlarmReceived");
		PowerManager.WakeLock wl = ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
		wl.acquire();
		wl.release();
	}

	public void start(Context context) {
		start(context, true, 60 * 1000 * 10);
	}

	public void start(Context ctx, boolean debug, int interval) {
		this.debug = debug;
		this.interval = interval;
		((AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE)).setInexactRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), this.interval,
				PendingIntent.getBroadcast(ctx, 0, new Intent(ctx, Watchdog.class), 0));
		L("started");
	}

	public void stopp(Context context) {
		L("stopped");
		Intent intent = new Intent(context, Watchdog.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}
}