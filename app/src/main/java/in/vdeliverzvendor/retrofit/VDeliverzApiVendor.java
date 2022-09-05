package in.vdeliverzvendor.retrofit;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import in.vdeliverzvendor.BuildConfig;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VDeliverzApiVendor {
    private static Retrofit retrofit = null;
    public static final String BASE_URL = "http://production.gdigitaldelivery.com/api/vendor/";
    //public static final String BASE_URL = "http://suvai.deliverymnx.net/api/vendor/";
    static String TAG = VDeliverzApiVendor.class.getSimpleName();

    public static VDeliverzApiinterfacevendor getClient() {
        Log.d(TAG, "getClient: 0");
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {
            interceptor.level(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.level(HttpLoggingInterceptor.Level.NONE);
        }

        httpClient.addInterceptor(interceptor);

        httpClient.addInterceptor(new Interceptor() {

            @NotNull
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();

                Log.d(TAG, "intercept: ");

                String token = null;
                if (MnxPreferenceManager.getString(MnxConstant.TOKEN, null) != null) {
                    token = MnxPreferenceManager.getString(MnxConstant.TOKEN, null);
                }
                if (token != null) {
                    Log.d("RetrofitClient", "intercept: " + "Bearer" + token);
                    requestBuilder.addHeader("Authorization", "Bearer" + " " + token);
                }
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        VDeliverzApiinterfacevendor api = retrofit.create(VDeliverzApiinterfacevendor.class);
        return api;
    }


}
