package ro.itec.waity.order;

import ro.itec.waity.api.ProductsResponse;
import ro.itec.waity.api.Produse;
import ro.itec.waity.api.model.Category;
import ro.itec.waity.api.model.CategoryResponse;
import rx.Observable;
import rx.Subscription;

public interface OrderMVP {

    /**
     * Required View methods available to Presenter.
     * A passive layer, responsible to show data
     * and receive user interactions
     */
    interface RequiredViewOps {

        void showProgressBar();

        void hideProgressBar();

        void addCategory(Category category);

        void addProduct(Produse product);

        void switchToProductsPerspective();

        void showFloatingCheckout();

    }

    /**
     * Operations offered to View to communicate with Presenter.
     * Processes user interactions, sends data requests to Model, etc.
     */
    interface ProvidedPresenterOps {

        void fetchCategories();

        void fetchProductsForCategory(Category category);

        void addTempProductOrder(Produse product, Integer quantity, String extra);

    }

    /**
     * Required Presenter methods available to Model.
     */
    interface RequiredPresenterOps {
    }

    /**
     * Operations offered to Model to communicate with Presenter
     * Handles all data business logic.
     */
    interface ProvidedModelOps {

        Observable<CategoryResponse> getCategories();

        Observable<ProductsResponse> getProductsForCategory(Category category);

        Observable<Void> addTempProductOrder(Produse product, Integer quantity, String extra);

    }

}
