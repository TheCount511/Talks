package sugarsinitiative.talks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class FeedsLoader extends AsyncTaskLoader<List<Feeds>> {
    private static final String LOG_TAG = MainActivity.class.getName();
    List<Feeds> result;
    private String urls;

    public FeedsLoader(Context context, String url) {
        super(context);
        urls = url;
    }

    @Override
    protected void onStartLoading() {
        android.util.Log.i(LOG_TAG, "TEST: onStartLoading called...");
        forceLoad();
    }

    @Override
    public List<Feeds> loadInBackground() {
        android.util.Log.i(LOG_TAG, "TEST: loadInBackground() called...");
        if (urls == null) {
            return null;
        }
        result = QueryUtils.fetchFeedData(urls);
        android.util.Log.i(LOG_TAG, "TEST: loadInBackground() called..." + urls);

        return result;
    }
}
