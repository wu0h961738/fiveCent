package com.venom.backend.utils.net;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * api caller by using retrofit
 * */
public class AuthService {

    private static Map retrofitSet = new HashMap<>();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    /**logging interceptor*/
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    /**
     * error-handling interceptor for retrofit
     */
    private static Interceptor errorHandling = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            if(response.code() >= 400){ //錯誤回應
                return new Response.Builder()
                        .code(response.code())
                        .body(response.body())
                        .build();
            }
            return response;
        }
    };

    public static <S> S createService(Class<S> serviceClass, String baseUrl){
        if(retrofitSet.get(serviceClass) != null){
            return (S) retrofitSet.get(serviceClass);
        }
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(errorHandling);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build());
        Retrofit retrofit = builder.build();
        S s = retrofit.create(serviceClass);
        retrofitSet.put(serviceClass, s);
        return s;
    }

    private AuthService(){}
}
