package info.winiex.androidbasement.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;

public class AppData {

	private static Context sContext;

	private static Resources sResources;
	
	private static ContentResolver sContentResolver;

	public static void init(Context context) {
		sContext = context;
		sResources = context.getResources();
		sContentResolver = context.getContentResolver();
	}

	public static Context getContext() {
		return sContext;
	}

	public static Resources getResources() {
		return sResources;
	}
	
	public static ContentResolver getContentResolver() {
		return sContentResolver;
	}
}
