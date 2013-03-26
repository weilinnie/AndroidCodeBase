package info.winiex.androidbasement.image.worker;

import info.winiex.androidbasement.image.utils.ImageUtils;
import info.winiex.androidbasement.image.utils.MediaStoreImageUtils;
import info.winiex.androidbasement.image.worker.ImageWorker;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.ImageView;

public class MediaStoreImageWorker extends ImageWorker {

	private int mImageId;

	private int mLoadImageType;

	private int mLoadImageLocation;

	public static int THUMBNAIL_MINI = MediaStore.Images.Thumbnails.MINI_KIND;

	public static int THUMBNAIL_MICRO = MediaStore.Images.Thumbnails.MICRO_KIND;

	public static int THUMBNAIL_FULL_SCREEN = MediaStore.Images.Thumbnails.FULL_SCREEN_KIND;

	private static int LOAD_THUMBNAIL = 1;

	private static int LOAD_ORIGIN = 2;

	private static int LOAD_INTERNAL = 1;

	private static int LOAD_EXTERNAL = 2;

	private static int DEFAULT_REQ_WIDTH = 100;

	private static int DEFAULT_REQ_HEIGHT = 100;

	public MediaStoreImageWorker(int imageId, ImageView imageView) {
		mImageId = imageId;
		mImageReference = new WeakReference<ImageView>(imageView);
	}

	public int getImageId() {
		return mImageId;
	}

	@Override
	protected Bitmap doInBackground(Integer... params) {
		super.doInBackground(params);
		mLoadImageType = params[2];
		mLoadImageLocation = params[3];

		Bitmap bitmapResult = null;

		if (mLoadImageLocation == LOAD_INTERNAL) {

			if (mLoadImageType == LOAD_THUMBNAIL) {
				int thumbnailType = params[4];
				bitmapResult = MediaStoreImageUtils
						.decodeBitmapThumbnailFromMediaStoreInternal(mImageId,
								thumbnailType);
			} else if (mLoadImageType == LOAD_ORIGIN) {
				bitmapResult = MediaStoreImageUtils
						.decodeBitmapOriginFromMediaStoreInternal(mImageId,
								mReqWidth, mReqHeight);
			}

		} else if (mLoadImageLocation == LOAD_EXTERNAL) {

			if (mLoadImageType == LOAD_THUMBNAIL) {
				int thumbnailType = params[4];
				bitmapResult = MediaStoreImageUtils
						.decodeBitmapThumbnailFromMediaStoreExternal(mImageId,
								thumbnailType);
			} else if (mLoadImageType == LOAD_ORIGIN) {
				bitmapResult = MediaStoreImageUtils
						.decodeBitmapOriginFromMediaStoreExternal(mImageId,
								mReqWidth, mReqHeight);
			}

		}

		return bitmapResult;
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
		if (thisWorker instanceof MediaStoreImageWorker) {
			MediaStoreImageWorker thisMediaStoreImageWorker = (MediaStoreImageWorker) thisWorker;
			if (thisMediaStoreImageWorker.getImageId() == mImageId) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	public void loadBitmapThumbnailInternal(ImageView imageView, int imageId,
			int thumbnailType) {
		loadBitmap(imageView, imageId, DEFAULT_REQ_WIDTH, DEFAULT_REQ_HEIGHT,
				true, true, thumbnailType);
	}

	public void loadBitmapThumbnailExternal(ImageView imageView, int imageId,
			int thumbnailType) {
		loadBitmap(imageView, imageId, DEFAULT_REQ_WIDTH, DEFAULT_REQ_HEIGHT,
				false, true, thumbnailType);
	}

	public void loadBitmapOriginInternal(ImageView imageView, int imageId,
			int reqWidth, int reqHeight) {
		loadBitmap(imageView, imageId, reqWidth, reqHeight, true, false, 0);
	}

	public void loadBitmapOriginExternal(ImageView imageView, int imageId,
			int reqWidth, int reqHeight) {
		loadBitmap(imageView, imageId, reqWidth, reqHeight, false, false, 0);
	}

	private void loadBitmap(ImageView imageView, int imageId, int reqWidth,
			int reqHeight, boolean loadInternal, boolean loadThumbnail,
			int thumbnailType) {

		if (cancelPotentialWork(imageView)) {
			final ImageWorker imageWorker = new MediaStoreImageWorker(imageId,
					imageView);
			final ImageWorker.AsyncDrawable asyncDrawable = new ImageWorker.AsyncDrawable(
					ImageUtils.defaultBitmap, imageWorker);
			imageView.setImageDrawable(asyncDrawable);

			if (loadThumbnail) {
				if (loadInternal) {
					imageWorker.execute(reqWidth, reqHeight, LOAD_THUMBNAIL,
							LOAD_INTERNAL, thumbnailType);
				} else {
					imageWorker.execute(reqWidth, reqHeight, LOAD_THUMBNAIL,
							LOAD_EXTERNAL, thumbnailType);
				}

			} else {
				if (loadInternal) {
					imageWorker.execute(reqWidth, reqHeight, LOAD_ORIGIN,
							LOAD_INTERNAL);
				} else {
					imageWorker.execute(reqWidth, reqHeight, LOAD_ORIGIN,
							LOAD_EXTERNAL);
				}
			}
		}
	}
}
