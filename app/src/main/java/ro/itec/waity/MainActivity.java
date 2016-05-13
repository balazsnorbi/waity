package ro.itec.waity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ro.itec.waity.table.backend.NFCEventListener;
import ro.itec.waity.table.backend.NFCManager;

public class MainActivity extends AppCompatActivity implements NFCEventListener{

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

      FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
      fab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
         }
      });

      // Initialise the NFC manager to make the application ready to read NFC tags
      NFCManager.INSTANCE.init(this);

      // Check for intent type and decide if it's NFC related or not
      NFCManager.INSTANCE.handleIntent(this, getIntent());
   }

   @Override
   protected void onResume() {
      super.onResume();

      /**
       * It's important, that the activity is in the foreground (resumed). Otherwise
       * an IllegalStateException is thrown.
       */
      NFCManager.INSTANCE.setupForegroundDispatch(this);
   }

   @Override
   protected void onPause() {
      super.onPause();

      /**
       * Call this before onPause, otherwise an IllegalArgumentException is thrown as well.
       */
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
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu_main, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
      int id = item.getItemId();

      //noinspection SimplifiableIfStatement
      if (id == R.id.action_settings) {
         return true;
      }

      return super.onOptionsItemSelected(item);
   }

   @Override
   public void onNFCReadCompleted(boolean success, String nfcString) {
      if (success) {
         Toast.makeText(this, nfcString, Toast.LENGTH_LONG).show();
      }
   }
}
