package info.kingpes.retrofitnodejs;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


/**
 * Created by Chau Huynh on 4/8/2017.
 */

public interface ServiceApi {
    @GET("/demoGet/")
    Call<List<User>> demoGet(@QueryMap Map<String, Object> params, @Header("secret")String auth, @Header("secret1")String auth1, @Header("secret2")String auth2);

    @POST("/demoPost")
    Call<User> demoPost(@Body Person params, @Header("auth")String token);
}
