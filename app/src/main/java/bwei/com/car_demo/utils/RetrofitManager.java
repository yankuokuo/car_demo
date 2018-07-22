package bwei.com.car_demo.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.internal.schedulers.NewThreadWorker;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static String BASE_URL="https://www.zhaoapi.cn/";
    private final Retrofit retrofit;

    private static class SingleHoder{
        private static final RetrofitManager MANAGE=new RetrofitManager(BASE_URL);
    }
    public static RetrofitManager getDefault(){
        return SingleHoder.MANAGE;
    }
    private RetrofitManager(String baseUrl){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    private OkHttpClient okHttpClient(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .readTimeout(2000, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }
    public <T> T Create(Class<T> Clazz) {
        return retrofit.create(Clazz);
    }
}
