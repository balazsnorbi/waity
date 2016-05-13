package ro.itec.waity.login.model;

import ro.itec.waity.api.ApiServiceManager;
import ro.itec.waity.api.model.UserResponse;
import ro.itec.waity.login.LoginMvp;
import rx.Observable;

public class LoginModel implements LoginMvp.ProvidedModelOps {

    @Override
    public Observable<UserResponse> getUser(String email, String password) {
        return ApiServiceManager.getWaityApiService().getUser(email, password);
    }

}
