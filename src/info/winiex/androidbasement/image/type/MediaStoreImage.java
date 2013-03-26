package info.winiex.androidbasement.image.type;

import android.provider.MediaStore;

public class MediaStoreImage extends BaseImage {

	public final static int THUMBNAIL_MINI = MediaStore.Images.Thumbnails.MINI_KIND;

	public final static int THUMBNAIL_MICRO = MediaStore.Images.Thumbnails.MICRO_KIND;

	public final static int THUMBNAIL_FULL_SCREEN = MediaStore.Images.Thumbnails.FULL_SCREEN_KIND;

	public final static int DEFAULT_WIDTH = 100;

	public final static int DEFAULT_HEIGHT = 100;

	protected int mImageId;

	protected boolean mIsInternal;

	protected boolean mIsThumbnail;

	protected int mThumbnailType;

	private MediaStoreImage(int reqWidth, int reqHeight, int imageId,
			boolean isInternal, boolean isThumbnail, int thumbnailType) {
		mReqWidth = reqWidth;
		mReqHeight = reqHeight;
		mImageId = imageId;
		mIsInternal = isInternal;
		mIsThumbnail = isThumbnail;
		mThumbnailType = thumbnailType;
	}

	public static MediaStoreImage newThumbnailInsternal(int reqWidth,
			int reqHeight, int imageId, int thumbnailType) {
		return new MediaStoreImage(reqWidth, reqHeight, imageId, true, true,
				thumbnailType);
	}

	public static MediaStoreImage newThumbnailExternal(int reqWidth,
			int reqHeight, int imageId, int thumbnailType) {
		return new MediaStoreImage(reqWidth, reqHeight, imageId, false, true,
				thumbnailType);
	}

	public static MediaStoreImage newOriginInternal(int reqWidth,
			int reqHeight, int imageId) {
		return new MediaStoreImage(reqWidth, reqHeight, imageId, true, false, 0);
	}

	public static MediaStoreImage newOriginExternal(int reqWidth,
			int reqHeight, int imageId) {
		return new MediaStoreImage(reqWidth, reqHeight, imageId, false, false,
				0);
	}

	public int getImageId() {
		return mImageId;
	}

	public boolean isInternal() {
		return mIsInternal;
	}

	public boolean isThumbnail() {
		return mIsThumbnail;
	}

	public int getThumbnailType() {
		return mThumbnailType;
	}

	@Override
	public String getMemCacheKey() {
		return String.valueOf(mImageId) + "#" + "width"
				+ String.valueOf(mReqWidth) + "height"
				+ String.valueOf(mReqHeight);
	}

	@Deprecated
	@Override
	public String getDiskCacheKey() {
		return String.valueOf(mImageId) + "#" + "width"
				+ String.valueOf(mReqWidth) + "height"
				+ String.valueOf(mReqHeight);
	}

}
