package ro.itec.waity.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ro.itec.waity.R;
import ro.itec.waity.login.LoginMVP;
import ro.itec.waity.login.model.LoginModel;
import ro.itec.waity.login.presenter.LoginPresenter;
import ro.itec.waity.bl.nfc.NFCEventListener;
import ro.itec.waity.bl.nfc.NFCManager;

public class LoginActivityView extends AppCompatActivity implements LoginMVP.RequiredViewOps{
    @BindView(R.id.bt_login_login)
    Button btLogin;

    @BindView(R.id.et_login_username)
    EditText etUsername;

    @BindView(R.id.et_login_password)
    EditText etPassword;

    private LoginMVP.ProvidedPresenterOps presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setStatusBarTranslucent(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        presenter = new LoginPresenter(new LoginModel());
    }

    @OnClick(R.id.bt_login_login)
    void onLoginClick() {
        presenter.login(etUsername.getText().toString(), etPassword.getText().toString());
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
