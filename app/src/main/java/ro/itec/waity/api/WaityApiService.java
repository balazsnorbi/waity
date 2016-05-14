package ro.itec.waity.api;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import ro.itec.waity.api.model.CategoryResponse;
import ro.itec.waity.api.model.UserResponse;
import rx.Observable;

public interface WaityApiService {

    @FormUrlEncoded
    @POST("user")
    Observable<UserResponse> getUser(@Field("email") String email,
                                     @Field("password") String password);

    @GET("categories")
    Observable<CategoryResponse> getCategories();

    @GET("products/{id}")
    Observable<ProductsResponse> getProductsForCategory(@Path("id") Integer id);

}
