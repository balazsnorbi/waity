package ro.itec.waity.order.model;

import java.util.List;

import ro.itec.waity.bl.persistence.order.Order;
import ro.itec.waity.bl.persistence.order.OrderMgr;
import ro.itec.waity.order.OrdersMVP;
import rx.Observable;
import rx.Subscriber;

public class OrderModel implements OrdersMVP.ProvidedModelOps {
    @Override
    public Observable<List<Order>> getOrders() {
        return Observable.create(new Observable.OnSubscribe<List<Order>>() {
            @Override
            public void call(Subscriber<? super List<Order>> subscriber) {
                subscriber.onNext(OrderMgr.INSTANCE.getOrdersList());
                subscriber.onCompleted();
            }
        });
    }
}
