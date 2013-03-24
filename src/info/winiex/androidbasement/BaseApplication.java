package info.winiex.androidbasement;

import info.winiex.androidbasement.utils.AppData;
import android.app.Application;

public class BaseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		AppData.init(getApplicationContext());
	}

}
