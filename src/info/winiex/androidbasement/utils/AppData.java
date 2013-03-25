package info.winiex.androidbasement.utils;

import android.content.Context;
import android.content.res.Resources;

public class AppData {

	private static Context sContext;

	private static Resources sResources;

	public static void init(Context context) {
		sContext = context;
		sResources = context.getResources();
	}

	public static Context getContext() {
		return sContext;
	}

	public static Resources getResources() {
		return sResources;
	}
}
