package ro.itec.waity.order.model;

import java.util.List;

import ro.itec.waity.bl.persistence.order.Order2;
import ro.itec.waity.bl.persistence.order.OrderMgr;
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
}
