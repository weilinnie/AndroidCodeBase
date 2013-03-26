package info.winiex.androidbasement.image.type;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HttpImage extends BaseImage {

	private String mUriStr;

	public HttpImage(int reqWidth, int reqHeight, String uriStr) {
		mReqWidth = reqWidth;
		mReqHeight = reqHeight;
		mUriStr = uriStr;
	}

	public String getUriStr() {
		return mUriStr;
	}

	@Override
	public String getMemCacheKey() {
		return getDiskCacheKey() + "#" + "width" + String.valueOf(mReqWidth)
				+ "height" + String.valueOf(mReqHeight);
	}

	@Override
	public String getDiskCacheKey() {
		String cacheKey = "";
		if (mUriStr != null) {
			try {
				cacheKey = URLEncoder.encode(mUriStr.replace("*", ""), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return cacheKey;
	}

	@Override
	public boolean equals(Object o) {
		boolean isEqual = false;
		if (o != null && o instanceof HttpImage && mUriStr != null) {
			isEqual = mUriStr.equals(((HttpImage) o).getUriStr());
		}
		return isEqual;
	}

}
