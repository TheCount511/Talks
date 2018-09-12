package sugarsinitiative.talks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FeedsDataAdapter extends ArrayAdapter<Feeds> {

    public FeedsDataAdapter(Context context, ArrayList<Feeds> feeds) {
        super(context, 0, feeds);
    }

    //This method converts the received date into a more readable format
    private String publicationDate(String dateOfPublication) {
        String firstPart;
        String secondPart;

        String separator = "T";

        String[] separated = dateOfPublication.split(separator);
        firstPart = separated[0];
        secondPart = separated[1].replace("Z", "");

        String dateText = "Published on " + firstPart + " | " + secondPart;

        return dateText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
        }


        //get the data item for this position
        Feeds currentItem = getItem(position);

        TextView feedHeadlineText = convertView.findViewById(R.id.headline);
        TextView feedSection = convertView.findViewById(R.id.section);
        TextView feedPublicationDate = convertView.findViewById(R.id.date);

        String headlineText;
        String section;
        String formattedPublicationDate;

        headlineText = currentItem.getmFeedText();
        feedHeadlineText.setText(headlineText);

        section = currentItem.getmFeedSection();
        feedSection.setText(section);

        formattedPublicationDate = publicationDate(currentItem.getmPublicationDate());
        feedPublicationDate.setText(formattedPublicationDate);

        return convertView;
    }
}
