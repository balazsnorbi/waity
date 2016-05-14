package ro.itec.waity.order.presenters;

import ro.itec.waity.api.model.CategoryResponse;
import ro.itec.waity.order.OrderMVP;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class OrderCategoryPresenter implements OrderMVP.ProvidedPresenterOps {

    private final OrderMVP.ProvidedModelOps model;

    private final CompositeSubscription subscriptions = new CompositeSubscription();
    private final OrderMVP.RequiredViewOps view;

    public OrderCategoryPresenter(OrderMVP.RequiredViewOps view, OrderMVP.ProvidedModelOps model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void fetchCategories() {
        subscriptions.add(model.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CategoryResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CategoryResponse categoryResponse) {
                        view.addCategories(categoryResponse.getCategories());
                    }
                }));
    }
}
