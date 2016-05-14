package ro.itec.waity.table.view;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
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
public class TableActivity extends AppCompatActivity implements TableMVP.RequiredViewOperations {

   @BindView(R.id.viewFlipper)
   protected ViewFlipper viewFlipper;
   @BindView(R.id.rippleView)
   protected RippleView rippleView;
   private TableMVP.RequiredPresenterOperations presenter;
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
               onIDObtained(result);
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
         onIDObtained(result);
      } else {
         Toast.makeText(this, "Failed to decode TAG.", Toast.LENGTH_SHORT).show();
      }
   }

   @Override
   protected void onNewIntent(Intent intent) {
      super.onNewIntent(intent);
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
    * @param id
    */
   private void onIDObtained(String id) {
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
}
