package info.winiex.androidbasement.image.type;

public class DiskImage extends BaseImage {

	private String mImageFilePath;

	public DiskImage(int reqWidth, int reqHeight, String imageFilePath) {
		mReqWidth = reqWidth;
		mImageFilePath = imageFilePath;
		mReqHeight = reqHeight;
	}

	public String getImageFilePath() {
		return mImageFilePath;
	}

	@Override
	public String getMemCacheKey() {
		return mImageFilePath + "#" + "width" + String.valueOf(mReqWidth)
				+ "height" + String.valueOf(mReqHeight);
	}

	@Override
	public String getDiskCacheKey() {
		return mImageFilePath.replaceAll("[.:/,%?&=]", "+").replaceAll("[+]+",
				"+")
				+ "#"
				+ "width"
				+ String.valueOf(mReqWidth)
				+ "height"
				+ String.valueOf(mReqHeight);
	}

	@Override
	public boolean equals(Object o) {
		boolean isEqual = false;
		if (o != null && o instanceof DiskImage) {
			isEqual = mImageFilePath.equals(((DiskImage) o).getImageFilePath());
		}
		return isEqual;
	}

}
