package com.codepath.apps.mytwitterapp;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet> {

	public TweetsAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    View view = convertView;
	    if (view == null) {
	    	LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	view = inflater.inflate(R.layout.tweet_item, null);
	    }
	     
        Tweet tweet = getItem(position);
        
        ImageView imageView = (ImageView) view.findViewById(R.id.ivProfile);
        ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), imageView);
        
        TextView nameView = (TextView) view.findViewById(R.id.tvName);
        String formattedName = "<b>" + tweet.getUser().getName() + "</b>" + " <small><font color='#777777'>@" +
                tweet.getUser().getScreenName() + "</font></small>";
        nameView.setText(Html.fromHtml(formattedName));
        TextView timeView = (TextView)view.findViewById(R.id.timeView); // handle to my textView
        String string = tweet.getJSONString();
        int posi = string.indexOf("created_at"); // here's the position of the instance "created_at" appears in current tweet
        
        String timestamp = string.substring(posi +13, posi + 43);
        
        
        timeView.setText(timestamp); // f(x,y)= void
        Log.d("DEBUG", tweet.getJSONString());
        
        TextView bodyView = (TextView) view.findViewById(R.id.tvBody);
        bodyView.setText(Html.fromHtml(tweet.getBody()));
        
        return view;
	}
	
}
