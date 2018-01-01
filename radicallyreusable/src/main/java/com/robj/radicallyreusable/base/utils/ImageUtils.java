package com.robj.radicallyreusable.base.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.robj.radicallyreusable.base.components.Optional;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jj on 21/06/17.
 */

public class ImageUtils {

    public static final int PNG_QUALITY = 100;

    private static final String TAG = ImageUtils.class.getSimpleName();

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static float getRotationToApply(Context context, Uri mediaUri) {
        int orientation = getOrientation(context, mediaUri);
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return 90f;
            case ExifInterface.ORIENTATION_ROTATE_180:
                return 180f;
            case ExifInterface.ORIENTATION_ROTATE_270:
                return 270f;
            default:
                return 0f;
        }
    }

    public static int getOrientation(Context context, Uri imageUri) {
        String filePath;
        int orientation = ExifInterface.ORIENTATION_NORMAL;
        if (imageUri.getScheme().equals(ContentResolver.SCHEME_CONTENT))
            filePath = MediaStoreUtils.getPath(context, imageUri);
        else
            filePath = imageUri.getPath();
        try {
            ExifInterface exif = new ExifInterface(filePath);
            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            Log.d(TAG, "Orientation is: " + orientation + "..");
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return orientation;
    }

    public static Bitmap getRotatedBitmap(Context context, Uri imageUri, float orientation) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap original = BitmapFactory.decodeStream(inputStream);
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);
            Bitmap bmp = Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
            original.recycle();
            inputStream.close();
            return bmp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Observable<Optional<Bitmap>> getResizedImageFromUriObservable(Context context, Uri uri, int desiredWidth, int desiredHeight) {
        return Observable.<Optional<Bitmap>>create(e -> {
            try {
                Bitmap bitmap = getResizedImageFromUri(context, uri, desiredWidth, desiredHeight);
                e.onNext(new Optional(bitmap));
            } catch (Exception ex) {
                ex.printStackTrace();
                e.onError(ex);
            }
            e.onComplete();
        }).subscribeOn(Schedulers.io());
    }

    public static Bitmap getResizedImageFromUri(Context context, Uri uri, int desiredWidth, int desiredHeight) {
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
            options.inSampleSize = calculateInSampleSize(options, desiredWidth, desiredHeight);
            if(bmp != null)
                bmp.recycle();
            options.inJustDecodeBounds = false;
            bmp = BitmapFactory.decodeStream(bufferedInputStream, null, options);
            bufferedInputStream.close();
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }

    @BindingAdapter("srcImagePreview")
    public static void srcImagePreview(final View v, final Uri imageUri) {
        if (v instanceof ImageView) {
            ImageView iv = (ImageView) v;
            int width = iv.getWidth(), height = iv.getHeight();
            if(width == 0)
                width = iv.getMaxWidth();
            if(height == 0)
                height = iv.getMaxHeight();
            if(height == 0 || width == 0) {
                //TODO: Listen for layout then try again
                return;
            }
//            Bitmap bitmap = getResizedImageFromUri(v.getContext(), imageUri, width, height);
//            if(bitmap != null)
//                iv.setImageBitmap(bitmap);
            getResizedImageFromUriObservable(v.getContext(), imageUri, width, height)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(bitmapOptional -> {
                        if(!bitmapOptional.isEmpty())
                            iv.setImageBitmap(bitmapOptional.get());
                        else
                            ; //TODO
                    }, throwable -> throwable.printStackTrace()); //TODO
        }
    }

}
