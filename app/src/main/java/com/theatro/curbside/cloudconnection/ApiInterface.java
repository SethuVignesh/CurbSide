//package com.theatro.curbside.cloudconnection;
//
//import com.theatro.curbside.BeansReponse.OrderResponse;
//
//import org.json.JSONObject;
//import org.json.JSONStringer;
//
//import retrofit2.Call;
//import retrofit2.http.Body;
//import retrofit2.http.GET;
//import retrofit2.http.POST;
//import retrofit2.http.Path;
//import retrofit2.http.Query;
//
//
//public interface ApiInterface {
//    @GET("theatrodemo/order")
//    Call<OrderResponse> getTopRatedMovies();
//
//    @GET("movie/{id}")
//    Call<OrderResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
//
//    @POST("theatrodemo/order")
//    Call<OrderResponse> CHECKIN(@Body JSONObject data);
//}