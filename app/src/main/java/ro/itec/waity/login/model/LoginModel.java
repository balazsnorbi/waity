package ro.itec.waity.login.model;

import ro.itec.waity.api.ApiServiceManager;
import ro.itec.waity.api.model.UserResponse;
import ro.itec.waity.bl.shared_preferences.KeyList;
import ro.itec.waity.bl.shared_preferences.PreferencesMgr;
import ro.itec.waity.login.LoginMvp;
import rx.Observable;

public class LoginModel implements LoginMvp.ProvidedModelOps {

    @Override
    public Observable<UserResponse> getUser(String email, String password) {
        return ApiServiceManager.getWaityApiService().getUser(email, password);
    }

    @Override
    public void saveUserID(int userID) {
        PreferencesMgr.INSTANCE.writeInt(KeyList.KEY_USER_ID, userID);
    }

    @Override
    public void saveUserName(String userName) {
        PreferencesMgr.INSTANCE.writeString(KeyList.KEY_USER_NAME, userName);
    }

}
