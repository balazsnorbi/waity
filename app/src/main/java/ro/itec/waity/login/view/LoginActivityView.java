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

public class LoginActivityView extends AppCompatActivity implements LoginMVP.RequiredViewOps {
    @BindView(R.id.bt_login_login)
    Button btLogin;

    @BindView(R.id.et_login_username)
    EditText etUsername;

    @BindView(R.id.et_login_password)
    EditText etPassword;

    private LoginMVP.ProvidedPresenterOps presenter;
public class LoginActivityView extends AppCompatActivity implements LoginMVP.RequiredViewOps, NFCEventListener {

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

        // Initialise the NFC manager to make the application ready to read NFC tags
        NFCManager.INSTANCE.init(this);

        // Check for intent type and decide if it's NFC related or not
        NFCManager.INSTANCE.handleIntent(this, getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // It's important, that the activity is in the foreground (resumed). Otherwise an IllegalStateException is thrown.
        NFCManager.INSTANCE.setupForegroundDispatch(this);

        // Primary check for NFC status: at this point, the user will be able to enable the NFC without manually accessing the settings menu
        NFCManager.INSTANCE.checkNFCStatus(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Call this before onPause, otherwise an IllegalArgumentException is thrown as well.
        NFCManager.INSTANCE.stopForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        /**
         * This method gets called, when a new Intent gets associated with the current activity instance.
         * Instead of creating a new activity, onNewIntent will be called. For more information have a look
         * at the documentation.
         *
         * In our case this method gets called, when the user attaches a Tag to the device.
         */
        NFCManager.INSTANCE.handleIntent(this, intent);
    }

    @Override
    public void onNFCReadCompleted(boolean success, String nfcString) {
        if (success) {
            Toast.makeText(this, nfcString, Toast.LENGTH_LONG).show();
        }
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
