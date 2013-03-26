package info.winiex.androidbasement.image.worker;

import info.winiex.androidbasement.image.type.DiskImage;
import info.winiex.androidbasement.image.utils.ImageUtils;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class DiskImageWorker extends ImageWorker {

	private DiskImage mDiskImage;

	public DiskImageWorker(DiskImage diskImage, ImageView imageView) {
		mDiskImage = diskImage;
		mImageReference = new WeakReference<ImageView>(imageView);
	}

	@Override
	protected Bitmap doInBackground(Integer... params) {
		super.doInBackground(params);

		return ImageUtils.decodeBitmapFromDisk(mDiskImage.getImageFilePath(),
				mReqWidth, mReqHeight);
	}

	private String getImageFilePath() {
		return mDiskImage.getImageFilePath();
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
			if (thisDiskImageWorker.getImageFilePath().equals(
					mDiskImage.getImageFilePath())) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public void loadBitmap(DiskImage diskImage, ImageView imageView) {
		if (cancelPotentialWork(imageView)) {
			final ImageWorker imageWorker = new DiskImageWorker(diskImage,
					imageView);
			final ImageWorker.AsyncDrawable asyncDrawable = new ImageWorker.AsyncDrawable(
					ImageUtils.defaultBitmap, imageWorker);
			imageView.setImageDrawable(asyncDrawable);
			imageWorker.execute(diskImage.getReqWidth(),
					diskImage.getReqHeight());
		}
	}

}
