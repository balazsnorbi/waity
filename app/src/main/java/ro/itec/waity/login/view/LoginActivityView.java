package ro.itec.waity.login.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ro.itec.waity.BuildConfig;
import ro.itec.waity.R;
import ro.itec.waity.login.LoginMvp;
import ro.itec.waity.login.model.LoginModel;
import ro.itec.waity.login.presenter.LoginPresenter;
import ro.itec.waity.order.view.OrderActivityView;
import ro.itec.waity.table.view.TableActivity;

public class LoginActivityView extends AppCompatActivity implements LoginMvp.RequiredViewOps{
    private static final String TAG = LoginActivityView.class.getName();

    @BindView(R.id.iv_login_waity)
    ImageView ivTextLogo;
    @BindView(R.id.et_login_username)
    EditText etUsername;
    @BindView(R.id.et_login_password)
    EditText etPassword;
    @BindView(R.id.bt_login_login)
    Button btLogin;
    @BindView(R.id.iv_login_logo)
    ImageView ivLogo;

    private ProgressDialog progress;

    private LoginMvp.ProvidedPresenterOps presenter;
    private Drawable errorIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loadImages();
        setStatusBarTranslucent(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setProgressBar();
        setErrorIndicatorBounds();

        presenter = new LoginPresenter(this, new LoginModel());

        // TODO: remove in prod
        if (BuildConfig.DEBUG) {
            etUsername.setText("c1@ligaac.ro");
            etPassword.setText("parola1");
        }
    }

    private void loadImages() {
        Glide.with(this)
                .load(R.drawable.login_logo_waity)
                .crossFade()
                .into(ivTextLogo);
        Glide.with(this)
                .load(R.drawable.login_waiter)
                .crossFade()
                .into(ivLogo);
    }

    private void setErrorIndicatorBounds() {
        // Setting custom drawable instead of red error indicator,
        errorIndicator = getDrawable(R.drawable.ic_error_outline);

        int left = 0;
        int top = 0;
        int right = errorIndicator.getIntrinsicHeight();
        int bottom = errorIndicator.getIntrinsicWidth();
        errorIndicator.setBounds(new Rect(left, top, right, bottom));
    }

    private void setProgressBar() {
        progress = new ProgressDialog(this);
        progress.setTitle("Authenticating");
        progress.setCancelable(false);
        progress.setMessage("Checking password and username ...");
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

    @Override
    public void showUsernameNotBlankError() {
        etUsername.setError("Username cannot be empty", errorIndicator);
    }

    @Override
    public void showPasswordNotBlankError() {
        etPassword.setError("Password cannot be empty", errorIndicator);
    }

    @Override
    public void showProgressBar() {
        progress.show();
    }

    @Override
    public void hideProgressBar(int delay) {
        btLogin.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress.dismiss();
            }
        }, delay);

    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void authenticationCompleted(Integer userId) {
        Log.i(TAG, "authenticationCompleted: " + userId);

        // TODO: set current user id

//        startActivity(new Intent(this, OrderActivityView.class));
        startActivity(new Intent(this, TableActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progress.dismiss();
        presenter.freeResources();
    }
}
