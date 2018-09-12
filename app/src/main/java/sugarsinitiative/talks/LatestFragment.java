package sugarsinitiative.talks;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LatestFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Feeds>> {
    public static final String LOG_TAG = BusinessFragment.class.getName();
    private static final String GUARDIAN_API_URL = "https://content.guardianapis.com/business?api-key=29461360-794a-4dd6-9000-31da4159c370";
    ListView feedsListView;
    private FeedsDataAdapter adapter;
    private ProgressBar loading;
    private TextView emptyView;

    public LatestFragment() {
        //Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_business, container, false);

        loading = rootView.findViewById(R.id.loading_spinner);
        emptyView = rootView.findViewById(R.id.emptyView);

        adapter = new FeedsDataAdapter(getActivity(), new ArrayList<Feeds>());
        feedsListView = rootView.findViewById(R.id.list);
        feedsListView.setAdapter(adapter);
        feedsListView.setEmptyView(emptyView);

        //the emptyView is set to replace the list view if the array is empty
        emptyView.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        feedsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get the string value of the feedLink uri
                Feeds currentItem = adapter.getItem(position);
                Uri feedLink = Uri.parse(currentItem.getmFeedLink());

                //parse the string as a url in the internet
                Intent intent = new Intent(Intent.ACTION_VIEW, feedLink);
                startActivity(intent);
            }
        });

        //this initializes the network status method
        // and proceeds with running the app if there is a network else it handles it
        // by giving the user an appropriate feedback
        initialTestForNetwork();

        return rootView;
    }

    public void initialTestForNetwork() {
        /* This apps running and functionality starts with this method
         * and as explained in the onCreate method it checks if there is an active method
         * and proceeds with running the app as appropriate depending of the network status
         */

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        //Get details on the currently active default data network
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        //If there is a network connection, fetch data
        if (activeNetwork != null && activeNetwork.isConnected()) {

            // Get a reference to the LoaderManager, in order to interact with loaders.
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid// because this activity implements the LoaderCallbacks interface).
            Log.i(LOG_TAG, "TEST: calling initLoader()...");
            getLoaderManager().initLoader(1, null, this).forceLoad();

        } else {
            //Otherwise, display error
            //First, hide loading indicator so error message will be visible
            loading.setVisibility(View.GONE);

            //Update emptystate with no connection error message
            emptyView.setText(R.string.no_connection);
        }
    }

    @Override
    public Loader<List<Feeds>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG, "TEST: onCreateLoader() called...");
        return new FeedsLoader(getContext(), GUARDIAN_API_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Feeds>> loader, List<Feeds> data) {
        Log.i(LOG_TAG, "TEST: onLoadFinished() called...");
        //set the visibility of the loading progress bar to gone on load completion
        loading.setVisibility(View.GONE);
        adapter.clear();

        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
        } else {
            //set the emptyView text to the string resource which indicates that
            // no feeds were delivered on that topic
            emptyView.setText(R.string.empty_adapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Feeds>> loader) {
        //clear the adapter of the previous earthquake data
        Log.i(LOG_TAG, "TEST: onLoaderReset() called...");
        adapter.clear();
    }
}


