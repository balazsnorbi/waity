package ro.itec.waity.order.model;

import java.util.LinkedList;
import java.util.List;

import ro.itec.waity.api.ApiServiceManager;
import ro.itec.waity.api.model.Category;
import ro.itec.waity.api.model.CategoryResponse;
import ro.itec.waity.api.model.OrderBody;
import ro.itec.waity.api.model.PlaceOrderResponse;
import ro.itec.waity.api.model.ProductsResponse;
import ro.itec.waity.api.model.Produse;
import ro.itec.waity.bl.persistence.temporary_order.TemporaryOrderMgr;
import ro.itec.waity.bl.persistence.temporary_order.TemporaryProduct;
import ro.itec.waity.bl.shared_preferences.KeyList;
import ro.itec.waity.bl.shared_preferences.PreferencesMgr;
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
                TemporaryOrderMgr.INSTANCE.addProductToOrder(product.getId(), product.getDescription(), quantity, extra);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<PlaceOrderResponse> checkoutTempOrder() {
        List<OrderBody> orderBodies = new LinkedList<>();
        List<TemporaryProduct> temporaryProducts
                = TemporaryOrderMgr.INSTANCE.getTemporaryProductsList();
        for (TemporaryProduct product : temporaryProducts) {
            orderBodies.add(new OrderBody(product.productId, product.quantity));
        }
        return ApiServiceManager.getWaityApiService().addOrder(
                "application/json",
                PreferencesMgr.INSTANCE.readInt(KeyList.KEY_USER_ID),
                PreferencesMgr.INSTANCE.readInt(KeyList.KEY_DESK_ID),
                orderBodies);
    }
}
