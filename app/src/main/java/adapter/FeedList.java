package adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.project.greenhat.tap_t.FeedView;
import com.project.greenhat.tap_t.R;

import java.util.List;

import control.acontrol;
import data.Feeditem;

/**
 * Created by greenhat on 26/8/17.
 */

public class FeedList extends BaseAdapter{


    private Activity activity;
    private Context c;
    private LayoutInflater inflater;
    private List<Feeditem> feedItems;
    private List<Feeditem> feeditemsd;
    ImageLoader imageLoader = acontrol.getInstance().getImageLoader();

    public FeedList(Context c, List<Feeditem> feedItems) {
        this.c = c;
        this.feedItems = feedItems;
        this.feeditemsd = feedItems;
    }

    @Override
    public int getCount() {

        return feedItems.size();
    }

    @Override
    public Object getItem(int position) {


        return feedItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (imageLoader == null)
            imageLoader = acontrol.getInstance().getImageLoader();
        if (convertView == null)
            convertView = inflater.inflate(R.layout.feed_item, null);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView timestamp = (TextView) convertView.findViewById(R.id.timestamp);
        TextView statusMsg = (TextView) convertView.findViewById(R.id.txtStatusMsg);
        TextView url = (TextView) convertView.findViewById(R.id.txtUrl);
        NetworkImageView profilePic = (NetworkImageView) convertView.findViewById(R.id.profilePic);
        //  FeedView feedImageView = (FeedView) convertView.findViewById(R.id.feedImage1);
        Feeditem item = feedItems.get(position);

        name.setText(item.getName());

        // Converting timestamp into x ago format
        // CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(Long.parseLong(item.getTimeStamp()),System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        timestamp.setText(item.getTimeStamp());

        // Chcek for empty status message
        if (!TextUtils.isEmpty(item.getStatus())) {
            statusMsg.setText(item.getStatus());
            statusMsg.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            statusMsg.setVisibility(View.GONE);
        }

        // Checking for null feed url
        if (item.getUrl() != null) {
            url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">" + item.getUrl() + "</a> "));

            // Making url clickable
            url.setMovementMethod(LinkMovementMethod.getInstance());
            url.setVisibility(View.VISIBLE);
        } else {
            // url is null, remove from the view
            url.setVisibility(View.GONE);
        }

        // user profile pic
        profilePic.setImageUrl(item.getProfilePic(), imageLoader);

        // Feed image


        return convertView;
    }
}
  /*  @Override
    public Filter getFilter() {


        return new Filter() {

            @SuppressWarnings("unchecked")

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values


                if (feedItems == null) {
                    feedItems = new ArrayList<Feeditem>(feeditemsd); // saves the original data in mOriginalValues
                }

                *//********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********//*
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = feedItems.size();
                    results.values = feedItems;
                } else {
                    ArrayList<Feeditem> FilteredArrList = new ArrayList<Feeditem>();
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < feedItems.size(); i++) {
                        String data = feedItems.get(i).getTimeStamp();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new Feeditem(0,feedItems.get(i).getName(),"","",feedItems.get(i).getProfilePic(),feedItems.get(i).getTimeStamp(),feedItems.get(i).getUrl()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                feeditemsd = (ArrayList<Feeditem>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

        };

    }
}
*/