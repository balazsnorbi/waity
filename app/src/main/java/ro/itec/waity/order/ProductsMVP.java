package ro.itec.waity.order;

import java.util.List;

import ro.itec.waity.api.model.Category;
import ro.itec.waity.api.model.CategoryResponse;
import ro.itec.waity.api.model.ProductsResponse;
import ro.itec.waity.api.model.Produse;
import ro.itec.waity.bl.persistence.temporary_order.TemporaryProduct;
import rx.Observable;

public interface ProductsMVP {

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

        void hideFloatingCheckout();
    }

    /**
     * Operations offered to View to communicate with Presenter.
     * Processes user interactions, sends data requests to Model, etc.
     */
    interface ProvidedPresenterOps {

        void fetchCategories();

        void fetchProductsForCategory(Category category);

        void addTempProductOrder(Produse product, Integer quantity, String extra);

        void checkoutTempOrder();

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

        Observable<List<TemporaryProduct>> checkoutTempOrder();

    }

}
