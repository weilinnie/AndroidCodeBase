package info.winiex.androidbasement.image.worker;

import android.graphics.Bitmap;

public class ResourceImageWorker extends BaseImageWorker<Integer, Void, Bitmap> {

	@Override
	protected Bitmap doInBackground(Integer... params) {
		return super.doInBackground(params);
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
	}

}
