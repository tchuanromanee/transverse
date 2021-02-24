package com.transverse.transverse;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class SelfHelpAdapter extends ArrayAdapter<SelfHelp> {
    private final int newsItemLayoutResource;

    public SelfHelpAdapter(final SelfHelpFragment context, final int newsItemLayoutResource) {
        super(context.getActivity(), 0);
        this.newsItemLayoutResource = newsItemLayoutResource;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        // We need to get the best view (re-used if possible) and then
        // retrieve its corresponding ViewHolder, which optimizes lookup efficiency
        final View view = getWorkingView(convertView);
        final SelfHelpAdapter.ViewHolder viewHolder = getViewHolder(view);
        final SelfHelp method = getItem(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.self_help_list_item, null);
        }

        final ImageView imageView = (ImageView)convertView.findViewById(R.id.self_help_icon);

        // 4
        //imageView.setImageResource(book.getImageResource());
        //imageView.setImageResource(R.drawable.ic_heart);

        // Setting the title view is straightforward
        viewHolder.titleView.setText(method.getName());

        String uri = "@drawable/ic_" + method.getID().toLowerCase();  // where myresource (without the extension) is the file

        int imageResource = getContext().getResources().getIdentifier(uri, null, getContext().getPackageName());

        //imageview = (ImageView)findViewById(R.id.imageView);
        Drawable res = getContext().getResources().getDrawable(imageResource);
        viewHolder.imageView.setImageDrawable(res);

        //viewHolder.imageView.setImageResource(R.drawable.ic_shhobby);
        // Setting image view is also simple
        //Get image ID, depending on the mood
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_heart);
       // String mDrawableName = "@drawable/ic_mood" + entry.getMood().getMoodLevel();
       // int resID = getContext().getResources().getIdentifier(mDrawableName , null, getContext().getPackageName());
      //  viewHolder.imageView.setImageResource(resID);
       // viewHolder.imageView.setImageResource(R.drawable.ic_heart);


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

    private SelfHelpAdapter.ViewHolder getViewHolder(final View workingView) {
        // The viewHolder allows us to avoid re-looking up view references
        // Since views are recycled, these references will never change
        final Object tag = workingView.getTag();
        SelfHelpAdapter.ViewHolder viewHolder = null;


        if(null == tag || !(tag instanceof SelfHelpAdapter.ViewHolder)) {
            viewHolder = new SelfHelpAdapter.ViewHolder();

            viewHolder.titleView = (TextView) workingView.findViewById(R.id.self_help_title);
            //viewHolder.subTitleView = (TextView) workingView.findViewById(R.id.user_entry_subtitle);
            viewHolder.imageView = (ImageView) workingView.findViewById(R.id.self_help_icon);

            workingView.setTag(viewHolder);

        } else {
            viewHolder = (SelfHelpAdapter.ViewHolder) tag;
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

