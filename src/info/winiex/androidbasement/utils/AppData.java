package info.winiex.androidbasement.utils;

import android.content.Context;

public class AppData {

	private static Context mContext;

	public static void init(Context context) {
		mContext = context;
	}

	public static Context getContext() {
		return mContext;
	}

}
