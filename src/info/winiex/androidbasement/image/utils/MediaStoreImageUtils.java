package info.winiex.androidbasement.image.utils;

import info.winiex.androidbasement.utils.AppData;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

public class MediaStoreImageUtils {

	public final static int DECODE_INTERNAL = 0;

	public final static int DECODE_EXTERNAL = 1;

	public final static int DECODE_THUMBNAIL = 2;

	public final static int DECODE_ORIGIN = 3;

	public final static int THUMBANIL_MINI = MediaStore.Images.Thumbnails.MINI_KIND;

	public final static int THUMBNAIL_MICRO = MediaStore.Images.Thumbnails.MICRO_KIND;

	public final static int THUMBNAIL_FULL_SCREEN = MediaStore.Images.Thumbnails.FULL_SCREEN_KIND;

	private final static int DEFAULT_REQ_WIDTH = 100;

	private final static int DEFAULT_REQ_HEIGHT = 100;

	public static Bitmap decodeBitmapThumbnailFromMediaStoreInternal(
			int imageId, int thumbnailType) {
		return decodeBitmapFromMediaStore(imageId, DEFAULT_REQ_WIDTH,
				DEFAULT_REQ_HEIGHT, DECODE_INTERNAL, DECODE_THUMBNAIL,
				thumbnailType);
	}

	public static Bitmap decodeBitmapThumbnailFromMediaStoreExternal(
			int imageId, int thumbnailType) {
		return decodeBitmapFromMediaStore(imageId, DEFAULT_REQ_WIDTH,
				DEFAULT_REQ_HEIGHT, DECODE_EXTERNAL, DECODE_THUMBNAIL,
				thumbnailType);
	}

	public static Bitmap decodeBitmapOriginFromMediaStoreInternal(int imageId,
			int reqWidth, int reqHeight) {
		return decodeBitmapFromMediaStore(imageId, reqWidth, reqHeight,
				DECODE_INTERNAL, DECODE_ORIGIN, THUMBNAIL_MICRO);
	}

	public static Bitmap decodeBitmapOriginFromMediaStoreExternal(int imageId,
			int reqWidth, int reqHeight) {
		return decodeBitmapFromMediaStore(imageId, reqWidth, reqHeight,
				DECODE_EXTERNAL, DECODE_ORIGIN, THUMBNAIL_MICRO);
	}

	private static Bitmap decodeBitmapFromMediaStore(int imageId, int reqWidth,
			int reqHeight, int internalOrExternal, int thumbnailOrOrigin,
			int thumbnailType) {
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

		if (thumbnailOrOrigin == DECODE_THUMBNAIL) {
			return loadImageThumbnail(imageToDecodeUri, thumbnailType);
		} else {
			return loadImageFull(imageToDecodeUri, reqWidth, reqHeight);
		}
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

				return ImageUtils.decodeBitmapFromDisk(imageFilePath, reqWidth,
						reqHeight);
			}
		} finally {
			if (imageCursor != null) {
				imageCursor.close();
			}
		}

		return null;
	}
}
