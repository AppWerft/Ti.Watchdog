package de.appwerft.watchdog;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.TiC;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

@Kroll.module(name = "Watchdog", id = "de.appwerft.watchdog")
public class WatchdogModule extends KrollModule {

	public WatchdogModule() {
		super();
		Log.d(LCAT,"WatchdogModule created");
	}

	private static Context ctx;
	private Watchdog watchdog = null;
	private int interval = 60000;
	static final String LCAT = "🐕TiWatchdog";
	private boolean debug = true;

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {
		ctx = app.getApplicationContext();
	}

	public void onDestroy(Activity activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			watchdog.stop(ctx);
		}
	}

	@Kroll.method
	public void start(@Kroll.argument(optional = true) KrollDict opts) {
		if (opts != null) {
			if (opts.containsKeyAndNotNull(TiC.PROPERTY_INTERVAL)) {
				interval = opts.getInt(TiC.PROPERTY_INTERVAL);
			}
			if (opts.containsKeyAndNotNull("debug")) {
				debug = opts.getBoolean("debug");
			}
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			Log.d(LCAT,"started with interval " + Math.round(interval/1000) + " sec.");
			watchdog = new Watchdog();
			watchdog.start(ctx, debug, interval);
		} else
			Log.d(LCAT, "API < Oreo, we don't need the watchdog");
	}

	@Kroll.method
	public void stop() {
		if (watchdog != null && ctx !=null)
			watchdog.stop(ctx);
	}
}
