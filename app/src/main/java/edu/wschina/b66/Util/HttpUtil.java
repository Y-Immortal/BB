package edu.wschina.b66.Util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
	private static final String ip = "http://whisperx.cn:9100/api";
	public static <V> void get(Context context, String url, Map<String, Object> params, boolean hasToken, Class<V> clazz, Callback<V> callback) {
		OkHttpClient okHttpClient = new OkHttpClient();
		Request.Builder builder = new Request.Builder()
				.url(ip + url);
		if (hasToken) {
			builder.header("Authorization", "Bearer " + SPUtil.getString(context, "token", ""));
		}
		Request request = builder.build();
		Handler handler = new Handler(Looper.getMainLooper());
		okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
			@Override
			public void onFailure(@NonNull Call call, @NonNull IOException e) {

			}
			@Override
			public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
				if (response.isSuccessful()) {
					assert response.body() != null;
					String json = response.body().string();
					Gson gson = new Gson();
					V data = gson.fromJson(json, clazz);
					handler.post(() -> {
						callback.success(data);
					});
				} else {
					handler.post(() -> {
						Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
					});
				}
			}
		});
	}
	
	public static <V> void postFormData(Context context, String url, Map<String, Object> formData, boolean hasToken, Class<V> clazz, Callback<V> callback) {
		OkHttpClient client = new OkHttpClient();
		Gson gson = new Gson();
		final MultipartBody.Builder formBodyBuilder = new MultipartBody.Builder();
		formData.forEach((k, v) -> {
			if (v instanceof File) {
				File file = (File) v;
				formBodyBuilder.addFormDataPart(k, file.getName(), RequestBody.create(file, MediaType.parse("image/jpeg")));
			}else {
				formBodyBuilder.addFormDataPart(k, String.valueOf(v));
			}

		});
		final MultipartBody formBody = formBodyBuilder.build();
		Request.Builder builder = new Request.Builder()
				.url(ip + url)
				.post(formBody);
		if (hasToken) {
			builder.header("Authorization", "Bearer " + SPUtil.getString(context, "token", ""));
		}
		Request request = builder.build();
		Handler handler = new Handler(Looper.getMainLooper());
		client.newCall(request).enqueue(new okhttp3.Callback() {
			@Override
			public void onFailure(@NonNull Call call, @NonNull IOException e) {

			}
			
			@Override
			public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
				if (response.isSuccessful()) {
					assert response.body() != null;
					String json = response.body().string();
					V data = gson.fromJson(json, clazz);
					handler.post(() -> {
						callback.success(data);
					});
				} else {
					handler.post(() -> {
						Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
					});
				}
			}
		});
	}
	
	public static <T, V> void postObject(Context context, String url, T data, boolean hasToken, Class<V> clazz, Callback<V> callback) {
		OkHttpClient client = new OkHttpClient();
		Gson gson = new Gson();
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(data));
		Request.Builder builder = new Request.Builder()
				.url(ip + url)
				.post(requestBody);
		if (hasToken) {
			builder.header("Authorization", SPUtil.getString(context, "token", ""));
		}
		Request request = builder.build();
		Handler handler = new Handler(Looper.getMainLooper());
		client.newCall(request).enqueue(new okhttp3.Callback() {
			@Override
			public void onFailure(@NonNull Call call, @NonNull IOException e) {

			}
			
			@Override
			public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
				if (response.isSuccessful()) {
					assert response.body() != null;
					String json = response.body().string();
					V data = gson.fromJson(json, clazz);
					handler.post(() -> {
						callback.success(data);
					});
				} else {
					handler.post(() -> {
						Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
					});
				}
			}
		});
	}
	
	public interface Callback<V> {
		void success(V data);
	}
	
}
