package ro.itec.waity.login.presenter;

import android.util.Log;

import ro.itec.waity.api.ApiServiceManager;
import ro.itec.waity.api.UserResponse;
import ro.itec.waity.login.LoginMVP;
import ro.itec.waity.login.model.LoginModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class LoginPresenter implements LoginMVP.ProvidedPresenterOps {
    private static final String TAG = LoginPresenter.class.getSimpleName();

    private final LoginMVP.ProvidedModelOps model;

    private final CompositeSubscription subscriptions = new CompositeSubscription();

    public LoginPresenter(LoginModel model) {
        this.model = model;
    }

    @Override
    public void login(String username, String password) {
        Log.d(TAG, "login: ");
        subscriptions.add(ApiServiceManager.getWaityApiService().
                getUser(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<UserResponse>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.w(TAG, "onError: ", e);
            }

            @Override
            public void onNext(UserResponse userResponse) {
                Log.d(TAG, "onNext: " + userResponse.toString());
            }
        }));
    }
}
