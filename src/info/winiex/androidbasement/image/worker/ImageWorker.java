package info.winiex.androidbasement.image.worker;

import info.winiex.androidbasement.utils.AppData;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

public abstract class ImageWorker extends AsyncTask<Integer, Void, Bitmap> {

	protected WeakReference<ImageView> mImageReference;

	protected Bitmap mBitmapResult;

	protected int mReqWidth;

	protected int mReqHeight;
	
	protected int mDefaultBitmapResId;

	public Bitmap getBitmapResult() {
		return mBitmapResult;
	}

	public int getReqWidth() {
		return mReqWidth;
	}

	public int getReqHeight() {
		return mReqHeight;
	}
	
	public void setDefaultBitmapResId(int resId) {
		mDefaultBitmapResId = resId;
	}

	@Override
	protected Bitmap doInBackground(Integer... params) {
		mReqWidth = params[0];
		mReqHeight = params[1];
		return null;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		mBitmapResult = result;
	}

	/**
	 * 绑定了 BaseImageWorker 的 Drawable 对象
	 * 
	 * @author winiex
	 * 
	 */
	static class AsyncDrawable extends BitmapDrawable {
		private final WeakReference<ImageWorker> bitmapWorkerTaskReference;

		public AsyncDrawable(Bitmap bitmap, ImageWorker bitmapWorkerTask) {
			super(AppData.getResources(), bitmap);
			bitmapWorkerTaskReference = new WeakReference<ImageWorker>(
					bitmapWorkerTask);
		}

		public ImageWorker getBitmapWorkerTask() {
			return bitmapWorkerTaskReference.get();
		}
	}

	protected final boolean cancelPotentialWork(ImageView imageView) {
		final ImageWorker bitmapWorkerTask = getBitmapWorkerTask(imageView);

		if (bitmapWorkerTask != null) {
			if (!isThisWorkerMySelf(bitmapWorkerTask)) {
				// Cancel previous task
				bitmapWorkerTask.cancel(true);
			} else {
				// The same work is already in progress
				return false;
			}
		}
		// No task associated with the ImageView, or an existing task was
		// cancelled
		return true;
	}

	/**
	 * 根据 worker 本身的唯一标志（譬如resId）来判断是否是自身
	 * 
	 * @param thisWorker
	 * @return
	 */
	abstract protected boolean isThisWorkerMySelf(ImageWorker thisWorker);

	/**
	 * 获取绑定在某个 ImageView 上的 BaseImageWorker
	 * 
	 * @param imageView
	 * @return
	 */
	protected final ImageWorker getBitmapWorkerTask(ImageView imageView) {
		if (imageView != null) {
			final Drawable drawable = imageView.getDrawable();
			if (drawable instanceof AsyncDrawable) {
				final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
				return asyncDrawable.getBitmapWorkerTask();
			}
		}
		return null;
	}

}
