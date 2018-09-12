package sugarsinitiative.talks;

import android.os.Parcel;
import android.os.Parcelable;

public class Feeds implements Parcelable {
    public static final Creator<Feeds> CREATOR = new Creator<Feeds>() {
        @Override
        public Feeds createFromParcel(Parcel in) {
            return new Feeds(in);
        }

        @Override
        public Feeds[] newArray(int size) {
            return new Feeds[size];
        }
    };
    /**
     * This holds the text to be displayed on each feed
     */
    private String mFeedText;
    /**
     * This holds the web link to each feed
     */
    private String mFeedLink;
    /**
     * This holds the ection name  for each feed
     */
    private String mFeedSection;
    /**
     * This holds the date of publication  for each feed
     */
    private String mPublicationDate;

    /**
     *
     */
    public Feeds(String feedText, String feedLink, String feedSection, String publicationDate) {
        mFeedText = feedText;
        mFeedLink = feedLink;
        mPublicationDate = publicationDate;
        mFeedSection = feedSection;
    }

    protected Feeds(Parcel in) {
        mFeedText = in.readString();
        mFeedLink = in.readString();
        mFeedSection = in.readString();
        mPublicationDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mFeedText);
        parcel.writeString(mFeedLink);
        parcel.writeString(mFeedSection);
        parcel.writeString(mPublicationDate);

    }

    //this method is used to get the text
    public String getmFeedText() {
        return mFeedText;
    }

    //this method is used to get the link
    public String getmFeedLink() {
        return mFeedLink;
    }

    //this method is used to get the section name
    public String getmFeedSection() {
        return mFeedSection;
    }

    //this method is used to get the date of publication
    public String getmPublicationDate() {
        return mPublicationDate;
    }
}
