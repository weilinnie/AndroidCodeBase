package info.winiex.androidbasement.image.worker;

import info.winiex.androidbasement.image.utils.ImageUtils;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ResourceImageWorker extends ImageWorker {

	private int mResId;

	public ResourceImageWorker(int resId, ImageView imageView) {
		mResId = resId;
		mImageReference = new WeakReference<ImageView>(imageView);
	}

	public int getResId() {
		return mResId;
	}

	@Override
	protected Bitmap doInBackground(Integer... params) {
		super.doInBackground(params);

		return ImageUtils.decodeBitmapFromResource(mResId, mReqWidth,
				mReqHeight);
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		super.onPostExecute(bitmap);

		if (mImageReference != null && bitmap != null) {
			ImageView imageView = mImageReference.get();
			if (imageView != null) {
				imageView.setImageBitmap(bitmap);
			}
		}
	}

	@Override
	protected boolean isThisWorkerMySelf(ImageWorker thisWorker) {
		if (thisWorker instanceof ResourceImageWorker) {
			ResourceImageWorker thisResourceImageWorker = (ResourceImageWorker) thisWorker;
			if (thisResourceImageWorker.getResId() == mResId) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public void loadBitmap(ImageView imageView, int resId, int reqWidth,
			int reqHeight) {
		if (cancelPotentialWork(imageView)) {
			final ResourceImageWorker imageWorker = new ResourceImageWorker(
					resId, imageView);
			final ImageWorker.AsyncDrawable asyncDrawable = new ImageWorker.AsyncDrawable(
					ImageUtils.defaultBitmap, imageWorker);
			imageView.setImageDrawable(asyncDrawable);
			imageWorker.execute(reqWidth, reqHeight);
		}
	}
}
