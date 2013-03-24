package info.winiex.androidbasement.image.worker;

import android.graphics.Bitmap;
import android.net.Uri;

public class HttpImageWorker extends BaseImageWorker<Uri, Void, Bitmap> {

	@Override
	protected Bitmap doInBackground(Uri... params) {
		return super.doInBackground(params);
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
	}

}
