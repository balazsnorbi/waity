package ro.itec.waity.order;

import java.util.List;

import ro.itec.waity.api.model.Category;
import ro.itec.waity.api.model.CategoryResponse;
import rx.Observable;

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

    }

    /**
     * Operations offered to View to communicate with Presenter.
     * Processes user interactions, sends data requests to Model, etc.
     */
    interface ProvidedPresenterOps {
        void fetchCategories();
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
    }

}
