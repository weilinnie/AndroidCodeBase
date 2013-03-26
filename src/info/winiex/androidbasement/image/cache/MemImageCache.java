package info.winiex.androidbasement.image.cache;

import info.winiex.androidbasement.image.utils.ImageUtils;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class MemImageCache {

	private LruCache<String, Bitmap> mLruCache;

	public MemImageCache() {
		initLruCache();
	}

	private void initLruCache() {
		// Get max available VM memory, exceeding this amount will throw an
		// OutOfMemory exception. Stored in kilobytes as LruCache takes an
		// int in its constructor.
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

		// Use 1/8th of the available memory for this memory cache.
		final int cacheSize = maxMemory / 8;

		mLruCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// The cache size will be measured in kilobytes rather than
				// number of items.
				return (int) ImageUtils.getBitmapSizeInBytes(bitmap) / 1024;
			}
		};
	}

}
