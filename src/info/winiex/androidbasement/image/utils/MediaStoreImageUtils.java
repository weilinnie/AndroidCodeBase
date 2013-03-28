package info.winiex.androidbasement.image.utils;

import info.winiex.androidbasement.utils.AppData;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

public class MediaStoreImageUtils {

	public final static int DECODE_INTERNAL = 0;

	public final static int DECODE_EXTERNAL = 1;

	public final static int DECODE_THUMBNAIL = 2;

	public final static int DECODE_ORIGIN = 3;

	private final static int DEFAULT_REQ_WIDTH = 100;

	private final static int DEFAULT_REQ_HEIGHT = 100;

	public static Bitmap decodeThumbnailInternalBitmapFromMediaStore(
			int imageId, int thumbnailType) {
		return decodeBitmapFromMediaStore(imageId, DEFAULT_REQ_WIDTH,
				DEFAULT_REQ_HEIGHT, DECODE_INTERNAL, DECODE_THUMBNAIL,
				thumbnailType);
	}

	public static Bitmap decodeThumbnailExternalBitmapFromMediaStore(
			int imageId, int thumbnailType) {
		return decodeBitmapFromMediaStore(imageId, DEFAULT_REQ_WIDTH,
				DEFAULT_REQ_HEIGHT, DECODE_EXTERNAL, DECODE_THUMBNAIL,
				thumbnailType);
	}

	public static Bitmap decodeOriginInternalBitmapFromMediaStore(int imageId,
			int reqWidth, int reqHeight) {
		return decodeBitmapFromMediaStore(imageId, reqWidth, reqHeight,
				DECODE_INTERNAL, DECODE_ORIGIN, 0);
	}

	public static Bitmap decodeOriginExternalBitmapFromMediaStore(int imageId,
			int reqWidth, int reqHeight) {
		return decodeBitmapFromMediaStore(imageId, reqWidth, reqHeight,
				DECODE_EXTERNAL, DECODE_ORIGIN, 0);
	}

	private static Bitmap decodeBitmapFromMediaStore(int imageId, int reqWidth,
			int reqHeight, int internalOrExternal, int thumbnailOrOrigin,
			int thumbnailType) {

		Uri imageToDecodeUri = getImageUri(imageId, internalOrExternal,
				thumbnailOrOrigin, thumbnailType);

		if (thumbnailOrOrigin == DECODE_THUMBNAIL) {
			return loadImageThumbnail(imageToDecodeUri, thumbnailType);
		} else {
			return loadImageFull(imageToDecodeUri, reqWidth, reqHeight);
		}
	}

	public static Uri getThumbnailInternalImageUri(int imageId,
			int thumbnailType) {
		return getImageUri(imageId, DECODE_INTERNAL, DECODE_THUMBNAIL,
				thumbnailType);
	}

	public static Uri getThumbnailExternalImageUri(int imageId,
			int thumbnailType) {
		return getImageUri(imageId, DECODE_EXTERNAL, DECODE_THUMBNAIL,
				thumbnailType);
	}

	public static Uri getOriginInternalImageUri(int imageId) {
		return getImageUri(imageId, DECODE_INTERNAL, DECODE_ORIGIN, 0);
	}

	public static Uri getOriginExternalImageUri(int imageId) {
		return getImageUri(imageId, DECODE_EXTERNAL, DECODE_ORIGIN, 0);
	}

	private static Uri getImageUri(int imageId, int internalOrExternal,
			int thumbnailOrOrigin, int thumbnailType) {
		Uri baseUri = null;
		Uri imageToDecodeUri = null;

		if (internalOrExternal == DECODE_INTERNAL) {

			if (thumbnailOrOrigin == DECODE_THUMBNAIL) {
				baseUri = MediaStore.Images.Thumbnails.INTERNAL_CONTENT_URI;
			} else if (thumbnailOrOrigin == DECODE_ORIGIN) {
				baseUri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
			}

		} else if (internalOrExternal == DECODE_EXTERNAL) {

			if (thumbnailOrOrigin == DECODE_THUMBNAIL) {
				baseUri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
			} else if (thumbnailOrOrigin == DECODE_ORIGIN) {
				baseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
			}
		}

		imageToDecodeUri = Uri.withAppendedPath(baseUri,
				String.valueOf(imageId));

		return imageToDecodeUri;
	}

	public static String getThumbnailInternalImageFilePath(int imageId,
			int thumbnailType) {
		return getImageFilePath(imageId, DECODE_INTERNAL, DECODE_THUMBNAIL,
				thumbnailType);
	}

	public static String getThumbnailExternalImageFilePath(int imageId,
			int thumbnailType) {
		return getImageFilePath(imageId, DECODE_EXTERNAL, DECODE_THUMBNAIL,
				thumbnailType);
	}

	public static String getOriginInternalImageFilePath(int imageId) {
		return getImageFilePath(imageId, DECODE_INTERNAL, DECODE_ORIGIN, 0);
	}

	public static String getOriginExternalImageFilePath(int imageId) {
		return getImageFilePath(imageId, DECODE_EXTERNAL, DECODE_ORIGIN, 0);
	}

	private static String getImageFilePath(int imageId, int internalOrExternal,
			int thumbnailOrOrigin, int thumbnailType) {
		Cursor imageCursor = null;
		Uri imageToDecodeUri = getImageUri(imageId, internalOrExternal,
				thumbnailOrOrigin, thumbnailType);

		try {
			String[] projection = { MediaStore.Images.Media.DATA };
			imageCursor = AppData.getContentResolver().query(imageToDecodeUri,
					projection, null, null, null);
			if (imageCursor != null && imageCursor.getCount() == 1) {
				imageCursor.moveToFirst();
				String imageFilePath = imageCursor.getString(imageCursor
						.getColumnIndex(MediaStore.Images.Media.DATA));

				return imageFilePath;
			}
		} finally {
			if (imageCursor != null) {
				imageCursor.close();
			}
		}

		return null;
	}

	public static int[] getThumbnailInternalImageWidthHeight(int imageId,
			int thumbnailType) {
		return getImageWidthHeight(imageId, DECODE_INTERNAL, DECODE_THUMBNAIL,
				thumbnailType);
	}

	public static int[] getThumbnailExternalImageWidthHeight(int imageId,
			int thumbnailType) {
		return getImageWidthHeight(imageId, DECODE_EXTERNAL, DECODE_THUMBNAIL,
				thumbnailType);
	}

	public static int[] getOriginInternalImageWidthHeight(int imageId) {
		return getImageWidthHeight(imageId, DECODE_INTERNAL, DECODE_ORIGIN, 0);
	}

	public static int[] getOriginExternalImageWidthHeight(int imageId) {
		return getImageWidthHeight(imageId, DECODE_EXTERNAL, DECODE_ORIGIN, 0);
	}

	private static int[] getImageWidthHeight(int imageId,
			int internalOrExternal, int thumbnailOrOrigin, int thumbnailType) {
		int[] result = new int[2];
		String imageFilePath = null;

		if (internalOrExternal == DECODE_INTERNAL) {

			if (thumbnailOrOrigin == DECODE_THUMBNAIL) {
				imageFilePath = getThumbnailInternalImageFilePath(imageId,
						thumbnailType);
			} else {
				imageFilePath = getOriginInternalImageFilePath(imageId);
			}

		} else {
			if (thumbnailOrOrigin == DECODE_THUMBNAIL) {
				imageFilePath = getThumbnailExternalImageFilePath(imageId,
						thumbnailType);
			} else {
				imageFilePath = getOriginExternalImageFilePath(imageId);
			}
		}

		BitmapFactory.Options options = ImageUtils
				.getDiskImageOptions(imageFilePath);

		result[0] = options.outWidth;
		result[1] = options.outHeight;

		return result;
	}

	private static Bitmap loadImageThumbnail(Uri imageUri, int thumbnailType) {
		String uriStr = imageUri.toString();

		int originalImageId = Integer.parseInt(uriStr.substring(
				uriStr.lastIndexOf("/") + 1, uriStr.length()));

		return MediaStore.Images.Thumbnails.getThumbnail(
				AppData.getContentResolver(), originalImageId, thumbnailType,
				null);
	}

	private static Bitmap loadImageFull(Uri imageUri, int reqWidth,
			int reqHeight) {
		Cursor imageCursor = null;

		try {
			String[] projection = { MediaStore.Images.Media.DATA };
			imageCursor = AppData.getContentResolver().query(imageUri,
					projection, null, null, null);
			if (imageCursor != null && imageCursor.getCount() == 1) {
				imageCursor.moveToFirst();
				String imageFilePath = imageCursor.getString(imageCursor
						.getColumnIndex(MediaStore.Images.Media.DATA));

				return ImageUtils.decodeSampleBitmapFromDisk(imageFilePath,
						reqWidth, reqHeight);
			}
		} finally {
			if (imageCursor != null) {
				imageCursor.close();
			}
		}

		return null;
	}
}
