package ro.itec.waity.order.model;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ro.itec.waity.api.ApiServiceManager;
import ro.itec.waity.api.model.Category;
import ro.itec.waity.api.model.CategoryResponse;
import ro.itec.waity.api.model.OrderBody;
import ro.itec.waity.api.model.PlaceOrderResponse;
import ro.itec.waity.api.model.ProductsResponse;
import ro.itec.waity.api.model.Produse;
import ro.itec.waity.bl.persistence.order.OrderMgr;
import ro.itec.waity.bl.persistence.order.OrderState;
import ro.itec.waity.bl.persistence.temporary_order.TemporaryOrderMgr;
import ro.itec.waity.bl.persistence.temporary_order.TemporaryProduct;
import ro.itec.waity.bl.shared_preferences.KeyList;
import ro.itec.waity.bl.shared_preferences.PreferencesMgr;
import ro.itec.waity.order.ProductsMVP;
import rx.Observable;
import rx.Subscriber;

public class ProductModel implements ProductsMVP.ProvidedModelOps {

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
                TemporaryOrderMgr.INSTANCE.addProductToOrder(product.getId(), product.getDescription(), quantity, extra, product.getPrice());
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<TemporaryProduct>> checkoutTempOrder() {
        return Observable.create(new Observable.OnSubscribe<List<TemporaryProduct>>() {
            @Override
            public void call(Subscriber<? super List<TemporaryProduct>> subscriber) {
                final List<OrderBody> orderBodies = new LinkedList<>();
                final List<TemporaryProduct> temporaryProducts
                        = TemporaryOrderMgr.INSTANCE.getTemporaryProductsList();
                for (TemporaryProduct product : temporaryProducts) {
                    orderBodies.add(new OrderBody(product.productId, product.quantity));
                }

                TemporaryOrderMgr.INSTANCE.clearTemporaryOrder();

                PlaceOrderResponse placeOrderResponse = ApiServiceManager.getWaityApiService()
                        .addOrder("application/json",
                                PreferencesMgr.INSTANCE.readInt(KeyList.KEY_USER_ID),
                                PreferencesMgr.INSTANCE.readInt(KeyList.KEY_DESK_ID),
                                orderBodies).toBlocking().first();
                List<Integer> orderIds = placeOrderResponse.getOrdersIds();
                for (int i = 0; i < orderIds.size(); i++) {
                    TemporaryProduct tmpProduct = temporaryProducts.get(i);
                    OrderMgr.INSTANCE.addOrder(
                            orderIds.get(i),
                            tmpProduct.productId,
                            tmpProduct.quantity,
                            tmpProduct.extra.trim(),
                            tmpProduct.description.trim(),
                            tmpProduct.price.trim(),
                            OrderState.STATE_WAITING);
                }

                subscriber.onNext(temporaryProducts);
                subscriber.onCompleted();
            }
        });
    }
}
