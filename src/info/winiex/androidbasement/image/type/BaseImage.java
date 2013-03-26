package info.winiex.androidbasement.image.type;

public abstract class BaseImage implements IImage {

	protected int mReqWidth;

	protected int mReqHeight;

	@Override
	public void setReqWidth(int reqWidth) {
		mReqWidth = reqWidth;
	}

	@Override
	public void setReqHeight(int reqHeight) {
		mReqHeight = reqHeight;
	}

	@Override
	public int getReqWidth() {
		return mReqWidth;
	}

	@Override
	public int getReqHeight() {
		return mReqHeight;
	}

	
}
