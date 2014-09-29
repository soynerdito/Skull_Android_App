package com.soynerdito.skullmaster;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onClickLeft(View view ){
		new PostBlink().execute(new Command(Command.BLINK_LEFT));
	}

	public void onClickRight(View view ){
		new PostBlink().execute(new Command(Command.BLINK_RIGHT));
	}
	
	
	private class PostBlink extends AsyncTask<Command, Integer, Integer> {
	     
		protected Integer doInBackground(Command... cmds) {
	         int count = 0;
	         for( Command cmd : cmds){
	        	 postClick( cmd);
	         }
	         return count;
	     }

	     protected void onProgressUpdate(Integer... progress) {
	     }

	 }
	
	private boolean postClick(Command cmd) {
		boolean result = false;
		HttpClient hc = new DefaultHttpClient();
		String message;
		String url = "http://remote-alert.herokuapp.com/skull";
		
		HttpPost p = new HttpPost(url);
		JSONObject object = new JSONObject();
		try {
			object = cmd.toJSon();
		} catch (Exception ex) {

		}

		try {
			message = object.toString();

			p.setHeader("Content-type", "application/json");
			p.setHeader("Accept", "application/json");
			p.setEntity(new StringEntity(message, HTTP.ASCII) );
			//p.setHeader("Content-type", "application/json");
			HttpResponse resp = hc.execute(p);
			if (resp != null) {
				if (resp.getStatusLine().getStatusCode() == 204)
					result = true;
			}

			Log.d("Status line", "" + resp.getStatusLine().getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();

		}

		return result;
	}
	 
}
