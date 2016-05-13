package ro.itec.waity.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ro.itec.waity.R;
import ro.itec.waity.login.LoginMVP;

public class LoginActivityView extends AppCompatActivity implements LoginMVP.RequiredViewOps {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
    }
}
