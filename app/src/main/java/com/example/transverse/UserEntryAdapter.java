package com.example.transverse;
import java.text.DateFormat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.transverse.UserEntry;

/**
 * Adapts NewsEntry objects onto views for lists
 */
public final class UserEntryAdapter extends ArrayAdapter<UserEntry> {

    private final int newsItemLayoutResource;

    public UserEntryAdapter(final StatisticsFragment context, final int newsItemLayoutResource) {
        super(context.getActivity(), 0);
        this.newsItemLayoutResource = newsItemLayoutResource;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        // We need to get the best view (re-used if possible) and then
        // retrieve its corresponding ViewHolder, which optimizes lookup efficiency
        final View view = getWorkingView(convertView);
        final ViewHolder viewHolder = getViewHolder(view);
        final UserEntry entry = getItem(position);

        // Setting the title view is straightforward
        viewHolder.titleView.setText(entry.getMood().getJournal());

        // Setting the subTitle view requires a tiny bit of formatting
        final String formattedSubTitle = String.format("On %s mood %s",
                entry.getDate(),
                entry.getMood().getMoodLevel()
        );

        viewHolder.subTitleView.setText(formattedSubTitle);

        // Setting image view is also simple
        //Get image ID
       //String mDrawableName = "ic_mood" + entry.getMood().getMoodLevel();
        //int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
        //viewHolder.imageView.setImageResource(entry.getMood().getMoodLevel());

        return view;
    }

    private View getWorkingView(final View convertView) {
        // The workingView is basically just the convertView re-used if possible
        // or inflated new if not possible
        View workingView = null;

        if(null == convertView) {
            final Context context = getContext();
            final LayoutInflater inflater = (LayoutInflater)context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);

            workingView = inflater.inflate(newsItemLayoutResource, null);
        } else {
            workingView = convertView;
        }

        return workingView;
    }

    private ViewHolder getViewHolder(final View workingView) {
        // The viewHolder allows us to avoid re-looking up view references
        // Since views are recycled, these references will never change
        final Object tag = workingView.getTag();
        ViewHolder viewHolder = null;


        if(null == tag || !(tag instanceof ViewHolder)) {
            viewHolder = new ViewHolder();

            viewHolder.titleView = (TextView) workingView.findViewById(R.id.user_entry_title);
            viewHolder.subTitleView = (TextView) workingView.findViewById(R.id.user_entry_subtitle);
            viewHolder.imageView = (ImageView) workingView.findViewById(R.id.user_entry_icon);

            workingView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) tag;
        }

        return viewHolder;
    }

    /**
     * ViewHolder allows us to avoid re-looking up view references
     * Since views are recycled, these references will never change
     */
    private static class ViewHolder {
        public TextView titleView;
        public TextView subTitleView;
        public ImageView imageView;
    }


}