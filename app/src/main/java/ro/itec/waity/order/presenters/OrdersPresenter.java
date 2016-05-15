package ro.itec.waity.order.presenters;

import java.util.List;

import ro.itec.waity.api.model.BillResponse;
import ro.itec.waity.api.model.OrderDeliverResponse;
import ro.itec.waity.bl.persistence.order.Order2;
import ro.itec.waity.bl.persistence.order.OrderMgr;
import ro.itec.waity.bl.persistence.order.OrderState;
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
        view.showLoader();
        subscriptions.add(model.getOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Order2>>() {
                    @Override
                    public void onCompleted() {
                        view.hideLoader();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Order2> orders) {
                        view.addOrders(orders);
                    }
                }));
    }

    @Override
    public void makeBill() {
        view.showLoader();
        subscriptions.add(model.makeBill()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BillResponse>() {
                    @Override
                    public void onCompleted() {
                        view.hideLoader();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BillResponse billResponse) {
                        view.showBillDialog(billResponse.getPrice());
                    }
                }));
    }

    @Override
    public void onOrderClick(final Order2 order, final int position) {
        view.showLoader();
        subscriptions.add(model.deliverOrder(order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OrderDeliverResponse>() {
                    @Override
                    public void onCompleted() {
                        view.hideLoader();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideLoader();
                    }

                    @Override
                    public void onNext(OrderDeliverResponse deliverResponse) {
                        processResponse(deliverResponse, order, position);
                    }
                }));
    }

    private void processResponse(OrderDeliverResponse deliverResponse, Order2 order, int position) {
        if (deliverResponse.getStatus().equals("ok")) {
            order.orderState = OrderState.STATE_DELIVERED;
            OrderMgr.INSTANCE.modifyOrderState(order.orderId, OrderState.STATE_DELIVERED);
            view.updateOrder(position);
        }
    }
}
