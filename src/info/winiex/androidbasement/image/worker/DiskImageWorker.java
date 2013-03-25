package info.winiex.androidbasement.image.worker;

import info.winiex.androidbasement.image.utils.ImageUtils;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class DiskImageWorker extends ImageWorker {

	private String mImageFilePath;

	public DiskImageWorker(String imageFilePath, ImageView imageView) {
		mImageFilePath = imageFilePath;
		mImageReference = new WeakReference<ImageView>(imageView);
	}

	public String getImageFilePath() {
		return mImageFilePath;
	}

	@Override
	protected Bitmap doInBackground(Integer... params) {
		super.doInBackground(params);

		return ImageUtils.decodeBitmapFromDisk(mImageFilePath, mReqWidth,
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
		if (thisWorker instanceof DiskImageWorker) {
			DiskImageWorker thisDiskImageWorker = (DiskImageWorker) thisWorker;
			if (thisDiskImageWorker.getImageFilePath().equals(mImageFilePath)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public void loadBitmap(ImageView imageView, String imageFilePath,
			int reqWidth, int reqHeight) {
		if (cancelPotentialWork(imageView)) {
			final ImageWorker imageWoker = new DiskImageWorker(
					imageFilePath, imageView);
			final Bitmap defaultBitmap = ImageUtils.decodeBitmapFromDisk(
					imageFilePath, reqWidth, reqHeight);
			final ImageWorker.AsyncDrawable asyncDrawable = new ImageWorker.AsyncDrawable(
					defaultBitmap, imageWoker);
			imageView.setImageDrawable(asyncDrawable);
			imageWoker.execute(reqWidth, reqHeight);
		}
	}

}
