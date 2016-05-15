package ro.itec.waity.api;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import ro.itec.waity.api.model.CategoryResponse;
import ro.itec.waity.api.model.OrderBody;
import ro.itec.waity.api.model.OrderDeliverResponse;
import ro.itec.waity.api.model.PlaceOrderResponse;
import ro.itec.waity.api.model.ProductsResponse;
import ro.itec.waity.api.model.TableResponse;
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

    @POST("order/{userId}/{tableId}")
    Observable<PlaceOrderResponse> addOrder(@Header("Content-Type") String contentType,
                                            @Path("userId") Integer userId,
                                            @Path("tableId") Integer tableId,
                                            @Body List<OrderBody> orders);

    @FormUrlEncoded
    @POST("table")
    Observable<TableResponse> getTableIdFromQr(@Field("qr") String id);

    @FormUrlEncoded
    @POST("table")
    Observable<TableResponse> getTableIdFromNfc(@Field("nfc") String id);

    @GET("deliver/{orderId}")
    Observable<OrderDeliverResponse> orderDeliver(@Path("orderId") Integer orderId);

    @GET("bill/{tableId}")
    Observable<Integer> getBill(@Path("tableId") Integer tableId);

}
