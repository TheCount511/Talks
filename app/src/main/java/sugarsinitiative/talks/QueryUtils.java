package sugarsinitiative.talks;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Return a list of {@link Feeds } objects that has been built up from
     * parsing a JSON response
     */

    private static List<Feeds> extractFeeds(String feedsJSON) {
        if (TextUtils.isEmpty(feedsJSON)) {
            return null;
        }
        List<Feeds> feeds = new ArrayList<>();

        /*Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
         is formatted, a JSONException exception object will be thrown.
          Catch the exception so the app doesn't crash, and print the error message to the logs.
         */
        try {

            // this traverses the json response to its root folder
            JSONObject jsonRootObject = new JSONObject(feedsJSON);

            //this traverses to the child object in the array called "response"";
            JSONObject mainObject = jsonRootObject.getJSONObject("response");

            //this traverses to an array in the child object of the json response called "results'
            JSONArray feedsArray = mainObject.optJSONArray("results");

            //this iterates for the number of items in the array called results
            for (int i = 0; i < feedsArray.length(); i++) {

               /* the object current feed is used to represent each item corresponding
                to position 'i' represents at that time
                */
                JSONObject currentFeed = feedsArray.getJSONObject(i);

                //this gets the value stored in the item 'sectionName'
                // from the current feed object
                String sectionId = currentFeed.getString("sectionName");

                //this gets the value stored in the item 'webPublicationDate'
                // from the current feed object
                String publicationDate = currentFeed.getString("webPublicationDate");

                //this gets the value stored in the item 'webTitle'
                // from the current feed object
                String feedTitle = currentFeed.getString("webTitle");

                //this gets the value stored in the item 'webTitle'
                // from the current feed object
                String weblink = currentFeed.getString("webUrl");

                Feeds feed = new Feeds(feedTitle, weblink, sectionId, publicationDate);
                feeds.add(feed);
                Log.i("QueryUtils", "showing the titles" + feedTitle);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the
            // "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception
            e.printStackTrace();
            Log.e("QueryUtils", "Problem parsing the feed JSON results", e);
        }

        //Return the feeds
        return feeds;
    }

    /**
     * Query the guardian api and return an {@link List<Feeds>} object
     * to represent a single feed
     */

    public static List<Feeds> fetchFeedData(String requestUrl) {
        android.util.Log.i(LOG_TAG, "TEST: fetchFeedData called...");

        try {
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Return the {@link Feeds}
        return extractFeeds(jsonResponse);
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Error with creating URL", e);
            return null;
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        if (url != null) {

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();

                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                }
                Log.e(LOG_TAG, "error response code:" + urlConnection.getResponseCode());
            } catch (IOException e) {


                Log.e(LOG_TAG, "problem receiving the feed JSON results:", e);

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {

                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
