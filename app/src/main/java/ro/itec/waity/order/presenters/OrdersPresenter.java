package ro.itec.waity.order.presenters;

import java.util.List;

import ro.itec.waity.bl.persistence.order.Order;
import ro.itec.waity.order.OrdersMVP;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class OrdersPresenter implements OrdersMVP.ProvidedPresenterOps {

    private CompositeSubscription subscriptions = new CompositeSubscription();

    private OrdersMVP.RequiredViewOps view;
    private OrdersMVP.ProvidedModelOps model;

    public OrdersPresenter(OrdersMVP.RequiredViewOps view, OrdersMVP.ProvidedModelOps model) {
        this.view = view;
        this.model = model;
    }

    public void fetchOrders() {
        subscriptions.add(model.getOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Order>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Order> orders) {
                        view.addOrders(orders);
                    }
                }));
    }
}
