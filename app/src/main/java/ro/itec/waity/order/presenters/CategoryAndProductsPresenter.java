package ro.itec.waity.order.presenters;

import android.util.Log;

import java.util.List;
import java.util.Map;

import ro.itec.waity.api.model.Category;
import ro.itec.waity.api.model.CategoryResponse;
import ro.itec.waity.api.model.PlaceOrderResponse;
import ro.itec.waity.api.model.ProductsResponse;
import ro.itec.waity.api.model.Produse;
import ro.itec.waity.bl.persistence.order.OrderMgr;
import ro.itec.waity.bl.persistence.order.OrderState;
import ro.itec.waity.bl.persistence.temporary_order.TemporaryProduct;
import ro.itec.waity.order.ProductsMVP;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class CategoryAndProductsPresenter implements ProductsMVP.ProvidedPresenterOps {
    private static final String TAG = CategoryAndProductsPresenter.class.getSimpleName();

    private final ProductsMVP.ProvidedModelOps model;

    private final CompositeSubscription subscriptions = new CompositeSubscription();
    private final ProductsMVP.RequiredViewOps view;

    public CategoryAndProductsPresenter(ProductsMVP.RequiredViewOps view, ProductsMVP.ProvidedModelOps model) {
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
                .map(new Func1<CategoryResponse, List<Category>>() {
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

    @Override
    public void fetchProductsForCategory(Category category) {
        view.showProgressBar();
        view.switchToProductsPerspective();
        subscriptions.add(model.getProductsForCategory(category)
                .filter(new Func1<ProductsResponse, Boolean>() {
                    @Override
                    public Boolean call(ProductsResponse productsResponse) {
                        if (productsResponse.getStatus().equals("ok")) {
                            return true;
                        }
                        return false;
                    }
                })
                .map(new Func1<ProductsResponse, List<Produse>>() {
                    @Override
                    public List<Produse> call(ProductsResponse productsResponse) {
                        return productsResponse.getProduse();
                    }
                })
                .flatMapIterable(new Func1<List<Produse>, Iterable<Produse>>() {
                    @Override
                    public Iterable<Produse> call(List<Produse> produses) {
                        return produses;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Produse>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: ");
                        view.hideProgressBar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        view.hideProgressBar();
                    }

                    @Override
                    public void onNext(Produse product) {
                        Log.d(TAG, "onNext: " + product.toString());
                        view.addProduct(product);
                    }
                }));
    }

    @Override
    public void addTempProductOrder(Produse product, Integer quantity, String extra) {
        view.showProgressBar();
        subscriptions.add(model.addTempProductOrder(product, quantity, extra)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: ");
                        view.hideProgressBar();
                        view.showFloatingCheckout();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        view.hideProgressBar();
                    }

                    @Override
                    public void onNext(Void Void) {
                        Log.d(TAG, "onNext: ");
                    }
                }));
    }

    @Override
    public void checkoutTempOrder() {
        view.showProgressBar();
        subscriptions.add(model.checkoutTempOrder()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<TemporaryProduct>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted: ");
                        view.hideProgressBar();
                        view.hideFloatingCheckout();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        view.hideProgressBar();
                    }

                    @Override
                    public void onNext(List<TemporaryProduct> products) {
                        Log.d(TAG, "onNext: ");
                    }
                }));
    }
}
