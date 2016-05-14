package ro.itec.waity.order.presenters;

import java.util.List;

import ro.itec.waity.api.model.Category;
import ro.itec.waity.api.model.CategoryResponse;
import ro.itec.waity.order.OrderMVP;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
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
        view.showProgressBar();
        subscriptions.add(model.getCategories()
                .filter(new Func1<CategoryResponse, Boolean>() {
                    @Override
                    public Boolean call(CategoryResponse categoryResponse) {
                        if (categoryResponse.getStatus().equals("ok")) {
                            return true;
                        }
                        return false;
                    }
                })
                .map(new Func1<CategoryResponse,List<Category>>() {
                    @Override
                    public List<Category> call(CategoryResponse categoryResponse) {
                        return categoryResponse.getCategories();
                    }
                })
                .flatMapIterable(new Func1<List<Category>, Iterable<Category>>() {
                    @Override
                    public Iterable<Category> call(List<Category> categories) {
                        return categories;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Category>() {
                    @Override
                    public void onCompleted() {
                        view.hideProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideProgressBar();
                    }

                    @Override
                    public void onNext(Category category) {
                        view.addCategory(category);
                    }
                }));
    }
}
