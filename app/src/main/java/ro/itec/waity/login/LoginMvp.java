package ro.itec.waity.login;

import ro.itec.waity.api.model.UserResponse;
import rx.Observable;

public interface LoginMvp {

    /**
     * Required View methods available to Presenter.
     * A passive layer, responsible to show data
     * and receive user interactions
     */
    interface RequiredViewOps {

        void showUsernameNotBlankError();

        void showPasswordNotBlankError();

        void showProgressBar();

        void hideProgressBar(int delay);

        void showToast(String text);

        void authenticationCompleted(Integer userId);
    }

    /**
     * Operations offered to View to communicate with Presenter.
     * Processes user interactions, sends data requests to Model, etc.
     */
    interface ProvidedPresenterOps {

        void login(String username, String password);

        void freeResources();

        void saveUserID(int userID);
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

        Observable<UserResponse> getUser(String email, String password);

        void saveUserID(int userID);

        void saveUserName(String userName);
    }

}
