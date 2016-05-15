package ro.itec.waity.order.model;

import java.util.List;

import ro.itec.waity.api.ApiServiceManager;
import ro.itec.waity.api.model.BillResponse;
import ro.itec.waity.api.model.OrderDeliverResponse;
import ro.itec.waity.bl.persistence.order.Order2;
import ro.itec.waity.bl.persistence.order.OrderMgr;
import ro.itec.waity.bl.shared_preferences.KeyList;
import ro.itec.waity.bl.shared_preferences.PreferencesMgr;
import ro.itec.waity.order.OrdersMVP;
import rx.Observable;
import rx.Subscriber;

public class OrderModel implements OrdersMVP.ProvidedModelOps {
    @Override
    public Observable<List<Order2>> getOrders() {
        return Observable.create(new Observable.OnSubscribe<List<Order2>>() {
            @Override
            public void call(Subscriber<? super List<Order2>> subscriber) {
                subscriber.onNext(OrderMgr.INSTANCE.getOrdersList());
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<BillResponse> makeBill() {
        // FIXME: remove
        OrderMgr.INSTANCE.clear();
        return ApiServiceManager.getWaityApiService().getBill(
                PreferencesMgr.INSTANCE.readInt(KeyList.KEY_DESK_ID));
    }

    @Override
    public Observable<OrderDeliverResponse> deliverOrder(final Order2 order) {
        return ApiServiceManager.getWaityApiService().orderDeliver(order.orderId);
    }
}
