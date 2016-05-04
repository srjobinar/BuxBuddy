package com.example.chandana.buxbuddy;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ScriptRunner extends AsyncTask<String, Void, String> {
	
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;

	public static final MediaType JSON
			= MediaType.parse("application/json; charset=utf-8");
	
	private String objectString;
	private boolean isDone = false;
	private int code;
	private ScriptFinishListener listener;
	private JSONObject obj;
	public ScriptRunner(JSONObject obj,ScriptFinishListener listener) {

		this.listener = listener;
		this.obj = obj;
	}
	
	@Override
	protected String doInBackground(String... s) {

		try {

			OkHttpClient client = new OkHttpClient();
			Log.d("tag",obj.toString());
			RequestBody body = RequestBody.create(JSON, obj.toString());
			Request request = new Request.Builder()
					.url("http://athena.nitc.ac.in/chandana_b130826cs/Buxbuddy_Server/login.php")
					.post(body)
					.build();
			Response response = client.newCall(request).execute();
			isDone = true;
			return response.body().string();


		} catch (Exception e) {
			e.printStackTrace();
		}

		isDone = false;
		return "";
	}

	public boolean getIsDone(){

		return isDone;
	}
	@Override
    protected void onPostExecute(String result) {
		Log.d("tag",result);
		if(isDone){
			listener.finish(result,SUCCESS);
		}else{
			listener.finish(result, FAIL);
		}
		
    }
	
	
	public interface ScriptFinishListener{
		public void finish(String result, int resultCode);
	}
}
