package info.winiex.androidbasement.image.worker;

import info.winiex.androidbasement.image.type.HttpImage;
import info.winiex.androidbasement.image.utils.ImageUtils;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class HttpImageWorker extends ImageWorker {

	HttpImage mHttpImage;

	public HttpImageWorker(HttpImage httpImage, ImageView imageView) {
		mHttpImage = httpImage;
		mImageReference = new WeakReference<ImageView>(imageView);
	}

	@Override
	protected Bitmap doInBackground(Integer... params) {
		super.doInBackground(params);

		return ImageUtils.decodeBitmapFromHttp(mHttpImage.getUriStr(),
				mReqWidth, mReqHeight);
	}

	public String getUriStr() {
		return mHttpImage.getUriStr();
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
	}

	@Override
	protected boolean isThisWorkerMySelf(ImageWorker thisWorker) {
		if (thisWorker instanceof HttpImageWorker) {
			HttpImageWorker thisHttpImageWorker = (HttpImageWorker) thisWorker;
			if (thisHttpImageWorker.getUriStr().equals(mHttpImage.getUriStr())) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	protected void loadBitmap(ImageView imageView) {
		if (cancelPotentialWork(imageView)) {
			final ImageWorker imageWorker = new HttpImageWorker(mHttpImage,
					imageView);
			final ImageWorker.AsyncDrawable asyncDrawable = new ImageWorker.AsyncDrawable(
					ImageUtils.defaultBitmap, imageWorker);
			imageView.setImageDrawable(asyncDrawable);
			imageWorker.execute(mHttpImage.getReqWidth(),
					mHttpImage.getReqHeight());
		}
	}

}
