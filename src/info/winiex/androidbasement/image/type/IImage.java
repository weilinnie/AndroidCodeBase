package info.winiex.androidbasement.image.type;

public interface IImage {

	String getMemoryCacheKey();

	String getDiskCacheKey();

	int getWidth();

	int getLength();

}
