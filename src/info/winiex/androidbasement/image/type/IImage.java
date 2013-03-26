package info.winiex.androidbasement.image.type;

public interface IImage {

	String getMemCacheKey();

	String getDiskCacheKey();

	void setReqWidth(int reqWidth);

	int getReqWidth();

	void setReqHeight(int reqHeight);

	int getReqHeight();

}
