package volley;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by greenhat on 26/8/17.
 */

public class Cache extends LruCache<String,Bitmap> implements ImageLoader.ImageCache {



    public static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        return cacheSize;
    }
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */


    public Cache(int maxSize) {
        super(maxSize);
    }
    public Cache() {

        this(getDefaultLruCacheSize());
    }



    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }
    @Override
    public Bitmap getBitmap(String s) {


        return get(s);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {

        put(s,bitmap);
    }
}
