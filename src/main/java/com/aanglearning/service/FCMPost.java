package com.aanglearning.service;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FCMPost {

	String url = "https://gcm-http.googleapis.com/gcm/send";

	MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	OkHttpClient client = new OkHttpClient();

	public void post(String json, String key) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder()
				.url(url)
				.header("Authorization", "key=" + key)
				.post(body).build();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(Call call, final Response response) throws IOException {
				try {
					if (!response.isSuccessful()) {
						throw new IOException("Unexpected code " + response);
					} else {
						System.out.println(response.toString());
					}
				} finally {
					response.body().close();
				}
			}
		});
	}

}
