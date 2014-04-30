package com.codepath.apps.mytwitterapp;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
				
				//create handle to our listview
				ListView lvTweets = (ListView)findViewById(R.id.lvTweets);
				TweetsAdapter adapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);
				
				
				
				Log.d("DEBUG", jsonTweets.toString());
			}
		});
		

		//if (savedInstanceState == null) {
			//getSupportFragmentManager().beginTransaction()
					//.add(R.id.container, new PlaceholderFragment()).commit();
		//}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
 private final int REQUEST_CODE = 20;
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//this method/fcn returns a boolean..e.g. returns true
		//code that will be called when user clicks compose on action bar
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId(); // tells you which item on action bar was clicked
		
		if (id == R.id.action_compose) { //if user clicks id=compose then launch compose activity
			Intent i = new Intent(this, ComposeActivity.class); //declared a new intent
			startActivityForResult(i, REQUEST_CODE);
			
			
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent i) {
		if (resultCode != ComposeActivity.RESULT_CANCELED ) {
		// there is data in this intent, piece of text.. save as variable here
		String string_tweet_text = i.getExtras().getString("composeText");
		MyTwitterApp.getRestClient().postTweetTwitterServer(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets){
				
			}		
	
		}, string_tweet_text);
	}
		
		MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
				
				//create handle to our listview
				ListView lvTweets = (ListView)findViewById(R.id.lvTweets);
				TweetsAdapter adapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);
				
				
				
				Log.d("DEBUG", jsonTweets.toString());
			}
		});
		
		
	}
	

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_timeline,
					container, false);
			return rootView;
		}
	}

}
