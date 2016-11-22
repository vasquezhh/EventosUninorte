package com.events.uninorte.Services;

import com.events.uninorte.Models.EventMainModel;
import com.events.uninorte.Models.EventModel;
import com.events.uninorte.Models.LoginModel;
import com.events.uninorte.Models.NewsModel;
import com.events.uninorte.Models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by hainerv on 9/10/16.
 */

public interface Service {

    @GET("events/day/{day}")
    Call<List<EventModel>> getEvents(@Path("day") String day);

    @GET("event/show")
    Call<EventModel> getEvent(@Query("url") String url);

    @GET("events/main")
    Call<List<EventMainModel>> getMain();

    @GET("news/main/{page}")
    Call<List<NewsModel>> getNews(@Path("page") String page);

    @FormUrlEncoded
    @POST("user/login")
    Call<LoginModel> login(@Field("email") String email, @Field("password") String password);

    @POST("user/register")
    Call<LoginModel> register(@Body UserModel user);
}
