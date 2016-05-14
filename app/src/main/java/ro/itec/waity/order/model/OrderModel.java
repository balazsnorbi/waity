package ro.itec.waity.order.model;

import ro.itec.waity.api.ApiServiceManager;
import ro.itec.waity.api.ProductsResponse;
import ro.itec.waity.api.Produse;
import ro.itec.waity.api.model.Category;
import ro.itec.waity.api.model.CategoryResponse;
import ro.itec.waity.bl.persistence.temporary_order.TemporaryOrderMgr;
import ro.itec.waity.order.OrderMVP;
import rx.Observable;
import rx.Subscriber;

public class OrderModel implements OrderMVP.ProvidedModelOps {

    @Override
    public Observable<CategoryResponse> getCategories() {
        return ApiServiceManager.getWaityApiService().getCategories();
    }

    @Override
    public Observable<ProductsResponse> getProductsForCategory(Category category) {
        return ApiServiceManager.getWaityApiService().getProductsForCategory(category.getId());
    }

    @Override
    public Observable<Void> addTempProductOrder(final Produse product, final Integer quantity,
                                                final String extra) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                TemporaryOrderMgr.INSTANCE.addProductToOrder(product.getId(), quantity, extra);
            }
        });
    }
}
