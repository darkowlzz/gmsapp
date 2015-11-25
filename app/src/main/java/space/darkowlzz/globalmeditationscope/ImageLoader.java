package space.darkowlzz.globalmeditationscope;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by sunny on 24/11/15.
 */
public class ImageLoader {

    private LruCache memoryCache;
    private Map imageViews = Collections.synchronizedMap(new WeakHashMap());
    private Context appContext;

    private Drawable mStubDrawable;

    public ImageLoader(Context context) {
        appContext = context;
        final int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE))
                                .getMemoryClass();
        final int cacheSize = 1024 * 1024 * memClass / 8;
        memoryCache = new LruCache(cacheSize);

        mStubDrawable = ContextCompat.getDrawable(context, R.drawable.gms_logo);
    }

    public void displayImage(String fileName, ImageView imageView) {
        imageViews.put(imageView, fileName);
        Bitmap bitmap = null;

        if (fileName != null && fileName.length() > 0) {
            bitmap = (Bitmap) memoryCache.get(fileName);
        }

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageDrawable(mStubDrawable);
            if (fileName != null && fileName.length() > 0) {
                queuePhoto(fileName, imageView);
            }
        }
    }

    private void queuePhoto(String fileName, ImageView imageView) {
        new LoadBitmapTask().execute(fileName, imageView);
    }

    public void updateImage(String fileName) {
        Bitmap bmp = getBitmap(fileName);
        if (bmp != null) {
            memoryCache.put(fileName, bmp);
        }
    }

    private Bitmap getBitmap(String fileName) {
        Bitmap ret = null;

        int targetW = 100;
        int targetH = 100;

        int photoW, photoH;

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        String filePath = null;
        BitmapFactory.decodeFile(filePath, bmOptions);
        photoW = bmOptions.outWidth;
        photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        ret = BitmapFactory.decodeFile(filePath, bmOptions);

        return ret;
    }

    private class PhotoToLoad {
        public String fileName;
        public ImageView imageView;

        public PhotoToLoad(String name, ImageView image) {
            fileName = name;
            imageView = image;
        }
    }

    private boolean imageViewReused(PhotoToLoad photoToLoad) {
        Object tag = imageViews.get(photoToLoad.imageView);
        if (tag == null || !tag.equals(photoToLoad.fileName)) {
            return true;
        }
        return false;
    }

    class LoadBitmapTask extends AsyncTask {
        private PhotoToLoad mPhoto;

        @Override
        protected Object doInBackground(Object[] params) {
            mPhoto = new PhotoToLoad((String) params[0], (ImageView) params[1]);

            if (imageViewReused(mPhoto)) {
                return null;
            }

            Bitmap bmp = getBitmap(mPhoto.fileName);
            if (bmp == null) {
                return null;
            }
            memoryCache.put(mPhoto.fileName, bmp);

            return bmp;
        }

        @Override
        protected void onPostExecute(Object o) {
            Bitmap bmp = (Bitmap) o;
            if (imageViewReused(mPhoto)) {
                return;
            }
            if (bmp != null) {
                mPhoto.imageView.setImageBitmap(bmp);
            } else {
                mPhoto.imageView.setImageDrawable(mStubDrawable);
            }
        }
    }
}