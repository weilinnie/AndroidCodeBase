package info.winiex.androidbasement.image.worker;

import java.io.File;

import android.graphics.Bitmap;

public class DiskImageWorker extends BaseImageWorker<File, Void, Bitmap> {

	@Override
	protected Bitmap doInBackground(File... params) {
		return super.doInBackground(params);
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
	}

}
