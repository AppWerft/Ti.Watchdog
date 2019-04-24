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
	private static final String LCAT = WatchdogModule.LCAT;

	private void L(String txt) {	
		if (debug)
			Log.d(LCAT, txt);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		L("onreceive");
		PowerManager.WakeLock wakeLock = ((PowerManager) context.getSystemService(Context.POWER_SERVICE))
				.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
		wakeLock.acquire();
		wakeLock.release();
		L("acquire…released");
	}

	public void start(Context context) {
		start(context, true, 60 * 1000 );
	
	}

	public void start(Context ctx, boolean debug, int interval) {
		this.debug = debug;
		this.interval = interval;
		AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
		am.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), this.interval,
				PendingIntent.getBroadcast(ctx, 0, new Intent(ctx, Watchdog.class), 0));
		L("AlarmManager started");
	}

	public void stop(Context context) {
		L("stopped");
		Intent intent = new Intent(context, Watchdog.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}
}