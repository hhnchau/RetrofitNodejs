package info.kingpes.retrofitnodejs;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;


/**
 * Created by Chau Huynh on 4/8/2017.
 */

public interface ServiceApi {
    @GET("/demoGet")
    Call<User> demoGet(@QueryMap Map<String, Object> params, @Header("secret")String auth, @Header("secret1")String auth1, @Header("secret2")String auth2);

    @POST("/demoPost")
    Call<User> demoPost(@Body Person params, @Header("auth")String token);

    @PUT("/demoPut")
    Call<User> demoPut(@Body Person params, @Header("secret")String auth, @Header("secret1")String auth1, @Header("secret2")String auth2);

    @DELETE("/demoDelete")
    Call<User> demoDelete(@QueryMap Map<String, Object> params, @Header("auth")String token);
}
