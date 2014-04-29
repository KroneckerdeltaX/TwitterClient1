package com.codepath.apps.mytwitterapp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
   
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;// Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "d56xcEuu3ZwkobEQKF7ActNFs";       // Change this
    public static final String REST_CONSUMER_SECRET = "kRjZhoCFqDcxfQRT4mE6wm49YP5yy6L7VtNrUXkOjUZfUR9caN"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://mytwitterapp"; // Change this (here and in manifest)
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getHomeTimeline(AsyncHttpResponseHandler handler) {
    	String url = getApiUrl("statuses/home_timeline.json");
    	client.get(url, null, handler); //these are the commands that interact with the external twitter server
    
    }
    public void postTweetTwitterServer(AsyncHttpResponseHandler handler, String composedText){ // this method also needs additional argument
    	//https://api.twitter.com/1.1/statuses/update.json
    	String url = getApiUrl("statuses/update.json"); 
    	try {
    	composedText = URLEncoder.encode(composedText,"UTF-8");
    
    	
    	} catch (UnsupportedEncodingException e ) { //no idea what this does.. ask someone if there is a character in there that maybe 
    												//cannot be encoded? does it map the string with spaces to something 
    												//that is allowed?
    		e.printStackTrace();
    		
    		
    	}
    	
    	RequestParams params = new RequestParams(); //Initializes it now attach to composed tweet below
    	params.put("status" ,composedText);
    	client.post(url, params, handler); //call params for all of the options.. but in our case it is a container for our composedtweet/status
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //after to post tweet you are supposed to jump to timeline to view new tweet but if jump back then 
		//twitter server hasn't updated yet so you don't see new tweet so implement a 2 sec delay hee
    	
    }
    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here
    public void getInterestingnessList(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("format", "json");
        client.get(apiUrl, params, handler);
    }
    
    
    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
}