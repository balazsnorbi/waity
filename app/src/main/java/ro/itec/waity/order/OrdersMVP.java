package ro.itec.waity.order;

import java.util.List;

import ro.itec.waity.api.model.OrderDeliverResponse;
import ro.itec.waity.bl.persistence.order.Order2;
import rx.Observable;

public interface OrdersMVP {

    /**
     * Required View methods available to Presenter.
     * A passive layer, responsible to show data
     * and receive user interactions
     */
    interface RequiredViewOps {

        void addOrders(List<Order2> orders);

        void showLoader();

        void hideLoader();

        void showBillDialog();

        void updateOrder(int position);

    }

    /**
     * Operations offered to View to communicate with Presenter.
     * Processes user interactions, sends data requests to Model, etc.
     */
    interface ProvidedPresenterOps {

        void fetchOrders();

        void makeBill();

        void onOrderClick(Order2 order, int position);
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

        Observable<List<Order2>> getOrders();

        Observable<Integer> makeBill();

        Observable<OrderDeliverResponse> deliverOrder(Order2 order);
    }

}
