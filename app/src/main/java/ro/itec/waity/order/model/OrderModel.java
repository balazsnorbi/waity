package ro.itec.waity.order.model;

import ro.itec.waity.api.ApiServiceManager;
import ro.itec.waity.api.model.CategoryResponse;
import ro.itec.waity.order.OrderMVP;
import rx.Observable;

public class OrderModel implements OrderMVP.ProvidedModelOps {

    @Override
    public Observable<CategoryResponse> getCategories() {
        return ApiServiceManager.getWaityApiService().getCategories();
    }
}
