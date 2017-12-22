//package com.theatro.curbside.cloudconnection;
//
//import com.theatro.curbside.MainActivity;
//import com.theatro.curbside.Utility;
//
//import java.io.IOException;
//
//import okhttp3.Interceptor;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//
//public class ApiClient {
//
//    public static final String BASE_URL = MainActivity.logOnUrl;
//    private static Retrofit retrofit = null;
//
//
//    public static Retrofit getClient() {
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Interceptor.Chain chain) throws IOException {
//                Request original = chain.request();
//
//                // Request customization: add request headers
//                Request.Builder requestBuilder = original.newBuilder()
//                        .addHeader("Content-Type", "application/json");
////                        .addHeader("x-access-token", "eyJhbGci");
//
//                Request request = requestBuilder.build();
//                return chain.proceed(request);
//            }
//        });
//
//        OkHttpClient client = httpClient.build();
//
//
//
//        return new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }
//}