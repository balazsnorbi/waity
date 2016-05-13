package ro.itec.waity.api;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import okio.Buffer;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import ro.itec.waity.BuildConfig;

public class ApiServiceManager {
    private static final String WAITY_BASE_URL = "http://mobile.itec.ligaac.ro/";

    private static WaityApiService waityApiService;

    public static WaityApiService getWaityApiService() {
        if (waityApiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(WAITY_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            /** Handles Log */
            if (BuildConfig.DEBUG)
                retrofit.client().interceptors().add(new LoggingInterceptor());

            waityApiService = retrofit.create(WaityApiService.class);


        }

        return waityApiService;
    }

    public static class LoggingInterceptor implements Interceptor {
        @Override
        public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
            Log.i("LoggingInterceptor", "inside intercept callback");
            Request request = chain.request();
            long t1 = System.nanoTime();
            String requestLog = String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers());
            if (request.method().compareToIgnoreCase("post") == 0) {
                requestLog = "\n" + requestLog + "\n" + bodyToString(request);
            }
            Log.d("TAG", "request" + "\n" + requestLog);
            com.squareup.okhttp.Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            String responseLog = String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers());

            String bodyString = response.body().string();

            Log.d("TAG", "response only" + "\n" + bodyString);

            Log.d("TAG", "response" + "\n" + responseLog + "\n" + bodyString);

            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), bodyString))
                    .build();

        }

        public static String bodyToString(final Request request) {
            try {
                final Request copy = request.newBuilder().build();
                final Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                return buffer.readUtf8();
            } catch (final IOException e) {
                return "did not work";
            }
        }
    }
}
