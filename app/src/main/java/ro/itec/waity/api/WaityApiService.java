package ro.itec.waity.api;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

public interface WaityApiService {

    @FormUrlEncoded
    @POST("user")
    Observable<UserResponse> getUser(@Field("email") String email,
                                        @Field("password") String password);

}
