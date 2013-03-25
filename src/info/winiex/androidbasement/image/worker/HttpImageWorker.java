package info.winiex.androidbasement.image.worker;

import info.winiex.androidbasement.image.utils.ImageUtils;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class HttpImageWorker extends ImageWorker {

	private String mUriStr;

	public HttpImageWorker(String uriStr, ImageView imageView) {
		this.mUriStr = uriStr;
		mImageReference = new WeakReference<ImageView>(imageView);
	}

	@Override
	protected Bitmap doInBackground(Integer... params) {
		super.doInBackground(params);

		return ImageUtils.decodeBitmapFromHttp(mUriStr, mReqWidth, mReqHeight);
	}

	public String getUriStr() {
		return mUriStr;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
	}

	@Override
	protected boolean isThisWorkerMySelf(ImageWorker thisWorker) {
		if (thisWorker instanceof HttpImageWorker) {
			HttpImageWorker thisHttpImageWorker = (HttpImageWorker) thisWorker;
			if (thisHttpImageWorker.getUriStr().equals(mUriStr)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	protected void loadBitmap(ImageView imageView, String uriStr, int reqWidth,
			int reqHeight) {
		if (cancelPotentialWork(imageView)) {
			final ImageWorker imageWorker = new HttpImageWorker(uriStr,
					imageView);
			final Bitmap defaultBitmap = ImageUtils.decodeBitmapFromHttp(
					uriStr, reqWidth, reqHeight);
			final ImageWorker.AsyncDrawable asyncDrawable = new ImageWorker.AsyncDrawable(
					defaultBitmap, imageWorker);
			imageView.setImageDrawable(asyncDrawable);
			imageWorker.execute(reqWidth, reqHeight);
		}
	}

}
