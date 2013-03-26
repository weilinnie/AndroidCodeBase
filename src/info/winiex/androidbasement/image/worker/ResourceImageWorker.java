package info.winiex.androidbasement.image.worker;

import info.winiex.androidbasement.image.type.ResourceImage;
import info.winiex.androidbasement.image.utils.ImageUtils;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ResourceImageWorker extends ImageWorker {

	private ResourceImage mResourceImage;

	public ResourceImageWorker(ResourceImage resourceImage, ImageView imageView) {
		mResourceImage = resourceImage;
		mImageReference = new WeakReference<ImageView>(imageView);
	}

	public int getResId() {
		return mResourceImage.getResId();
	}

	@Override
	protected Bitmap doInBackground(Integer... params) {
		super.doInBackground(params);

		return ImageUtils.decodeBitmapFromResource(mResourceImage.getResId(),
				mReqWidth, mReqHeight);
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
			if (thisResourceImageWorker.getResId() == mResourceImage.getResId()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public void loadBitmap(ResourceImage resourceImage, ImageView imageView) {
		if (cancelPotentialWork(imageView)) {
			final ResourceImageWorker imageWorker = new ResourceImageWorker(
					resourceImage, imageView);
			final ImageWorker.AsyncDrawable asyncDrawable = new ImageWorker.AsyncDrawable(
					ImageUtils.defaultBitmap, imageWorker);
			imageView.setImageDrawable(asyncDrawable);
			imageWorker.execute(resourceImage.getReqWidth(),
					resourceImage.getReqHeight());
		}
	}
}
