package com.example.strawberry.Interfaces;

import com.example.strawberry.Model.Image;
import com.example.strawberry.Model.Data;
import com.example.strawberry.Model.Password;
import com.example.strawberry.Model.Reaction;
import com.example.strawberry.Model.ReactionDTO;
import com.example.strawberry.Model.UserDTO;
import com.example.strawberry.Model.ResponseObject;
import com.example.strawberry.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://strawberryy.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("/api/v1/users")
    Call <ResponseObject<List<User>>> getAllusers();
    // login
    @POST("/api/v1/auth/login")
    Call <ResponseObject<Data>> checkLogin(@Body UserDTO userDTO);

    @GET("/api/v1/users/forget-password")
    Call <ResponseObject<User>> forgetPassword(@Query("email") String email);

    // create account
    @POST("/api/v1/users/register")
    Call <ResponseObject<User>> createAccount(@Body User user);

    // re-sendcode
    @GET("/api/v1/users/register/{id}/resend-code")
    Call <ResponseObject<User>> sendCode(@Path("id") int id);

    //acctive
    @GET("/api/v1/users/register/{id}/active")
    Call <ResponseObject<User>> activeUser(@Path("id") int id,@Query("code") String code);
    // get react
    @GET("/api/v1/reactions/{idPost}/get-count")
    Call <ResponseObject<Reaction>> getReact(@Path("idPost") int idPost);

    // get image
    @GET("/api/v1/posts/{idPost}/images")
    Call <ResponseObject<List<Image>>> getImage(@Path("idPost") int idPost);

    // get public post

    @GET("/api/v1/posts")
    Call <ResponseObject<List<Data>>> getAllPublicPost();

    //
    @POST("/api/v1/reactions/set-reaction")
    Call <ResponseObject<Data>> setReact(@Body ReactionDTO reactionDTO);

    //
    @GET("/api/v1/users/{id}/posts")
    Call <ResponseObject<List<Data>>> getAllPostUser(@Path("id") Integer id);

    @GET("/api/v1/users/{id}/images")
    Call <ResponseObject<List<Image>>> getAllImageUser(@Path("id") Integer id);

    // up post user
    @Multipart
    @POST("/api/v1/posts/{idUser}/create-post")
    Call <RequestBody> upPost(@Path("idUser") Integer idUser,
                              @Part MultipartBody.Part fileImages);

    @POST("/api/v1/users/{id}/change-password")
    Call<ResponseObject<Password>> changePassword(@Path("id") Integer idUser, @Body Password password);
}
