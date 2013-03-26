package info.winiex.androidbasement.image.type;

public class ResourceImage extends BaseImage {

	private int mResId;

	public ResourceImage(int reqWidth, int reqHeight, int resId) {
		mReqWidth = reqWidth;
		mReqHeight = reqHeight;
		mResId = resId;
	}

	public int getResId() {
		return mResId;
	}

	@Override
	public String getMemCacheKey() {
		return String.valueOf(mResId) + "#" + "width"
				+ String.valueOf(mReqWidth) + "height"
				+ String.valueOf(mReqHeight);
	}

	@Deprecated
	@Override
	public String getDiskCacheKey() {
		return String.valueOf(mResId) + "#" + "width"
				+ String.valueOf(mReqWidth) + "height"
				+ String.valueOf(mReqHeight);
	}

	@Override
	public boolean equals(Object o) {
		boolean isEqual = false;
		if (o != null && o instanceof ResourceImage) {
			isEqual = mResId == ((ResourceImage) o).getResId();
		}
		return isEqual;
	}

}
