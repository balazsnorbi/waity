package ro.itec.waity.login.presenter;

import android.util.Log;

import ro.itec.waity.api.UserResponse;
import ro.itec.waity.login.LoginMvp;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class LoginPresenter implements LoginMvp.ProvidedPresenterOps {
    private static final String TAG = LoginPresenter.class.getSimpleName();

    private final LoginMvp.RequiredViewOps view;
    private final LoginMvp.ProvidedModelOps model;

    private final CompositeSubscription subscriptions = new CompositeSubscription();

    public LoginPresenter(LoginMvp.RequiredViewOps view, LoginMvp.ProvidedModelOps model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void login(String username, String password) {
        Log.d(TAG, "login: ");

        if (username.isEmpty()) {
            view.showUsernameNotBlankError();
            return;
        }

        if (password.isEmpty()) {
            view.showPasswordNotBlankError();
            return;
        }

        view.showProgressBar();
        subscriptions.add(model.getUser(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                        view.hideProgressBar(1000);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        view.hideProgressBar(0);
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
                        processResponse(userResponse);
                    }
                }));
    }

    private void processResponse(UserResponse userResponse) {
        Log.i(TAG, "processResponse: " + userResponse.toString());
        if (userResponse.getStatus().equals("ok")) {
            view.authenticationCompleted(userResponse.getUserId());
        } else if (userResponse.getStatus().equals("not_found")){
            view.hideProgressBar(0);
            view.showToast("Email or password not valid");
        }
    }
}
