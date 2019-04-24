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
	private boolean debug = true;
	private int interval;
	private static final int REQUESTCODE = 123456;
	private static final String LCAT = WatchdogModule.LCAT;

	private void L(String txt) {
		if (debug)
			Log.d(LCAT, txt);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		PowerManager.WakeLock wakeLock = ((PowerManager) context.getSystemService(Context.POWER_SERVICE))
				.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
		wakeLock.acquire();
		wakeLock.release();
		L("acquire…released");
	}

	public void start(Context context) {
		start(context, true, 60 * 1000, false);

	}

	public void start(Context ctx, boolean debug, int interval, boolean exact) {
		this.debug = debug;
		this.interval = interval;
		AlarmManager cronjob = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
		long now = System.currentTimeMillis();
		Intent intent =  new Intent(ctx, Watchdog.class);
		PendingIntent operation = PendingIntent.getBroadcast(ctx, REQUESTCODE,intent, 0);
		// RTC_WAKEUP—Wakes up the device to fire the pending intent at the specified time.
		if (exact == true)
			cronjob.setRepeating(AlarmManager.RTC_WAKEUP, now, this.interval, operation);
		else
			cronjob.setInexactRepeating(AlarmManager.RTC_WAKEUP, now, this.interval, operation);
		L("AlarmManager started, exact=" + exact);
	}

	public void stop(Context context) {
		L("stopped");
		Intent intent = new Intent(context, Watchdog.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, REQUESTCODE, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}
}