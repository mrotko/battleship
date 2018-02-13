package pl.rotkomichal.battleship;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import pl.rotkomichal.battleship.utils.LruBitmapCache;

/**
 * Created by michal on 25.12.17.
 */

public class AppController extends Application {
    public final static String TAG = AppController.class.getSimpleName();

    private RequestQueue requestQueue;

    private ImageLoader imageLoader;

    private static AppController instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized AppController getInstance() {
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        if(imageLoader == null) {
            imageLoader = new ImageLoader(getRequestQueue(), new LruBitmapCache());
        }
        return imageLoader;
    }

    public <T> void addRequestToQueue(Request <T> request, String tag) {
        request.setTag(tag);
        getRequestQueue().add(request);
    }

    public <T> void addRequestToQueue(Request <T> request) {
        addRequestToQueue(request, TAG);
    }

    public void cancelPendingRequest(Object tag) {
        if(requestQueue != null) {
            requestQueue.cancelAll(tag);
        }

    }

}
