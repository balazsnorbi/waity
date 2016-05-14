package ro.itec.waity.table.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.andexert.library.RippleView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.itec.waity.R;
import ro.itec.waity.order.view.OrderActivityView;
import ro.itec.waity.table.TableMVP;
import ro.itec.waity.table.presenter.TablePresenter;

/**
 * This activity is used to read NFC tags and QR Codes
 */
public class TableActivity extends AppCompatActivity implements TableMVP.ViewOperations {

    @BindView(R.id.viewFlipper)
    protected ViewFlipper viewFlipper;
    @BindView(R.id.rippleView)
    protected RippleView rippleView;
    private TableMVP.PresenterOperations presenter;
    private boolean firstShown = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        ButterKnife.bind(this);

        setStatusBarTranslucent(true);

        presenter = new TablePresenter(this);

        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));

        initFloatingActionButton();

        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setupForegroundDispatch(this);
        presenter.checkForNFCStatus();
        presenter.registerForNFCChangeEvent(this, true);
        checkInternetConnection();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.stopForegroundDispatch(this);
        presenter.registerForNFCChangeEvent(this, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case IntentIntegrator.REQUEST_CODE:
                if (resultCode != RESULT_CANCELED) {
                    IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                    String result = scanResult.getContents();
                    presenter.onIDObtainedFromQrCode(result);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void nfcStatusEvent(boolean status) {
        inflateLayoutBasedOnNFC(status);
    }

    @Override
    public void onNFCDecoded(boolean status, String result) {
        if (status) { // onSuccess
            presenter.onIDObtainedFromNfc(result);
        } else {
            Toast.makeText(this, "Failed to decode TAG.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void tableValidated(Integer tableId) {
        onIDObtained(tableId);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        presenter.handleNewIntent(intent);
    }

    /**
     * Decides which layout to be active based on the NFC status
     *
     * @param isActive NFC State: true - on, false - off
     */
    private void inflateLayoutBasedOnNFC(boolean isActive) {
        if (isActive) {
            if (!firstShown) {
                viewFlipper.showPrevious();
                firstShown = true;
            }
        } else {
            if (firstShown) {
                viewFlipper.showNext();
                firstShown = false;
            }
        }
    }

    /**
     * Exit point of the activity. When the table ID is obtained, we can forward the user to the OrderActivity
     *
     * @param id String
     */
    private void onIDObtained(int id) {
        presenter.saveDeskID(id);
        Intent intent = new Intent(this, OrderActivityView.class);
        intent.putExtra("ID", id);
        startActivity(intent);
        finish();
    }

    /**
     * Used to initialise the FAB used in this activity
     */
    private void initFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(TableActivity.this);
                integrator.setCaptureActivity(AnyOrientationCaptureActivity.class);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            }
        });
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (!(activeNetworkInfo != null && activeNetworkInfo.isConnected())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.alert_no_internet_title);
            builder.setMessage(R.string.alert_no_internet_message);
            builder.setPositiveButton(R.string.alert_no_internet_positive_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }
}
