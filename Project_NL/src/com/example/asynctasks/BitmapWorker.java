package com.example.asynctasks;

import java.lang.ref.WeakReference;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Asynctask to handle decoding bitmap samples
 * Changed to create bitmap samples from byte array
 * 
 * @author Challenge.IT
 * @source http://developer.android.com/training/displaying-bitmaps/process-bitmap.html
 */
public class BitmapWorker extends AsyncTask<byte[], Void, Bitmap> {
    
        private final WeakReference<ImageView> imageViewReference;
        private final int MAX_WIDTH = 100;
        private final int MAX_HEIGHT = 100;
        
    public BitmapWorker(ImageView imageView) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(byte[]... params) {
            // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeByteArray(params[0], 0, params[0].length, options);
            
            return Bitmap.createScaledBitmap(bitmap, MAX_WIDTH, MAX_HEIGHT, false);
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
    
    /**
     * @source http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return sample size
     */
    private int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;
        
            if (height > reqHeight || width > reqWidth) {
        
                final int halfHeight = height / 2;
                final int halfWidth = width / 2;
        
                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) > reqHeight
                        && (halfWidth / inSampleSize) > reqWidth) {
                    inSampleSize *= 2;
                }
            }
            return inSampleSize;
        }
}