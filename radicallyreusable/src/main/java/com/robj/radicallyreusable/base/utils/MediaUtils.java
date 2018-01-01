package com.robj.radicallyreusable.base.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.robj.radicallyreusable.base.components.Optional;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jj on 21/06/17.
 */

public class MediaUtils {

    private static final String TAG = MediaUtils.class.getSimpleName();

    public static Bitmap getThumbnailFromUri(Context context, Uri uri, String mimeType, int desiredWidth, int desiredHeight) {
        if(!hasUriPermissions(context, uri))
            return null;
        if(!TextUtils.isEmpty(mimeType)) {
            if (mimeType.startsWith("image"))
                return getImageFromUri(context, uri, desiredWidth, desiredHeight);
            else if (mimeType.startsWith("video"))
                return getVideoThumbnailFromUri(context, uri);
        }
        return null;
    }

    private static Bitmap getVideoThumbnailFromUri(Context context, Uri uri) {
        if(!hasUriPermissions(context, uri))
            return null;
        Bitmap bitmap = null;
        try {
            if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
                Log.d(TAG, "Trying to get thumbnail from media store uri id..");
                long fileId = MediaStoreUtils.getFileId(context, uri);
                bitmap = MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(), fileId, MediaStore.Video.Thumbnails.MICRO_KIND, null);

//                Cursor thumbCursor = null;
//                try {
//
//                    thumbCursor = context.managedQuery(
//                            MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
//                            thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID + " = "
//                                    + fileId, null, null);
//
//                    if (thumbCursor.moveToFirst()) {
//                        String thumbPath = thumbCursor.getString(thumbCursor
//                                .getColumnIndex(MediaStore.Video.Thumbnails.DATA));
//
//                        return thumbPath;
//                    }
//
//                } finally {
//                }

//                String lastSegment = uri.getLastPathSegment();
//                if (lastSegment.contains(":")) {
//                    String[] segments = lastSegment.split(":");
//                    lastSegment = segments[segments.length - 1];
//                }
//                int mediaStoreId = Integer.parseInt(lastSegment);
//                return MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(), mediaStoreId, MediaStore.Images.Thumbnails.MICRO_KIND, null);
                if(bitmap == null) {
                    Log.d(TAG, "Trying to get thumbnail from media store uri path..");
                    String path;
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
                    if (cursor != null) {
                        if (cursor.moveToFirst()) {
                            int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                            if (columnIndex > -1) {
                                path = cursor.getString(columnIndex);
                                bitmap = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MICRO_KIND);
                            }
                        }
                        cursor.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if(bitmap == null) {
                Log.d(TAG, "Trying to get thumbnail from uri path..");
                bitmap = ThumbnailUtils.createVideoThumbnail(uri.getPath(), MediaStore.Images.Thumbnails.MICRO_KIND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(bitmap == null) {
            Log.d(TAG, "Trying to get thumbnail from metadata receiver..");
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(context, uri);
            return retriever.getFrameAtTime();
        }
        return bitmap;
    }

    public static Observable<Optional<Bitmap>> getVideoThumbnailFromUriObservable(Context context, Uri uri, int desiredWidth, int desiredHeight) {
        return Observable.<Optional<Bitmap>>create(e -> {
            try {
                Bitmap bitmap = getVideoThumbnailFromUri(context, uri);
                e.onNext(new Optional(bitmap));
            } catch (Exception ex) {
                ex.printStackTrace();
                e.onError(ex);
            }
            e.onComplete();
        }).subscribeOn(Schedulers.io());
    }

    private static Bitmap getImageFromUri(Context context, Uri uri, int desiredWidth, int desiredHeight) {
        if(!hasUriPermissions(context, uri))
            return null;
        Bitmap bmp = null;
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 64*1024);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            bufferedInputStream.mark(64*1024);
            bmp = BitmapFactory.decodeStream(bufferedInputStream, null, options);
            try {
                bufferedInputStream.reset();
            } catch (Exception e) {
                Log.e(TAG, "Mark not supported..");
                bufferedInputStream.close();
                inputStream = context.getContentResolver().openInputStream(uri);
                bufferedInputStream = new BufferedInputStream(inputStream);
            }
            options.inSampleSize = ImageUtils.calculateInSampleSize(options, desiredWidth, desiredHeight);
            if(bmp != null)
                bmp.recycle();
            options.inJustDecodeBounds = false;
            bmp = BitmapFactory.decodeStream(bufferedInputStream, null, options);
            bufferedInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }

    public static long getContentLengthFromUri(Context context, Uri uri) {
        if(!hasUriPermissions(context, uri))
            return -1;
        long contentLength = 0;
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 32*1024);
            contentLength = getContentLength(bufferedInputStream);
            bufferedInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentLength;
    }

    public static long getContentLength(Context context, Uri mediaUri) {
        ContentResolver cr = context.getContentResolver();
        long contentLength = -1;
        try {
            Cursor c = cr.query(mediaUri, new String[]{ MediaStore.Audio.Media.SIZE }, null, null, null);
            if(c != null) {
                if (c.moveToFirst())
                    contentLength = c.getLong(c.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                c.close();
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(contentLength == -1) {
            contentLength = getContentLengthFromUri(context, mediaUri);
            
        }
        return contentLength;
    }

    private static long getContentLength(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        long size = 0;
        while ((len = inputStream.read(buffer, 0, bufferSize)) != -1) {
            byteBuffer.write(buffer, 0, len);
            size += len;
        }
        return size;
    }

    public static String getMimeType(Context context, Uri uri) {
        String mimeType = null;
        if(hasUriPermissions(context, uri)) {
            if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
                ContentResolver cr = context.getContentResolver();
                mimeType = cr.getType(uri);
            }
            if (TextUtils.isEmpty(mimeType)) {
                String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
                mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
            }
        }
        return mimeType;
    }

    public static String getFileExtension(Context context, Uri uri) {
        if(!hasUriPermissions(context, uri))
            return null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else
            return MimeTypeMap.getFileExtensionFromUrl(uri.toString());
    }

    public static String getFileName(Context context, Uri uri, boolean withExtension) {
        if(!hasUriPermissions(context, uri))
            return null;
        String filename = null;
        if (uri.getScheme() != null && uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if(cursor != null) {
                if(cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if(columnIndex > -1)
                        filename = cursor.getString(columnIndex);
                }
                cursor.close();
            }
        } else
            filename = uri.getLastPathSegment();
        if(!TextUtils.isEmpty(filename)) {
            if(!withExtension) {
                int index = filename.lastIndexOf('.');
                if (index > 0)
                    filename = filename.substring(0, index); //Attempt to remove file extension
            }
        } else
            filename = String.valueOf(System.currentTimeMillis());
        return filename;
    }

    private static boolean hasUriPermissions(Context context, Uri uri) {
        if(uri == null) {
            Log.d(TAG, "Uri iss null..");
            return false;
        }
//        if(uri.toString().contains(BuildConfig.APPLICATION_ID))
//            return true;
//        int permission = context.checkUriPermission(uri , Binder.getCallingPid(), Binder.getCallingUid(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        if (permission == PackageManager.PERMISSION_DENIED) {
//            Log.d(TAG, "No permission to read file at uri " + uri.toString() + "..");
//            return false;
//        }
        return true;
    }

}
