package ro.itec.waity.order.view;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.mxn.soul.flowingdrawer_core.FlowingView;
import com.mxn.soul.flowingdrawer_core.LeftDrawerLayout;

import ro.itec.waity.R;
import ro.itec.waity.bl.shared_preferences.KeyList;
import ro.itec.waity.bl.shared_preferences.PreferencesMgr;
import ro.itec.waity.login.view.LoginActivityView;
import ro.itec.waity.order.view.adapters.ViewPagerAdapter;
import ro.itec.waity.order.view.listeners.LogoutRequestListener;

public class OrderActivityView extends AppCompatActivity implements LogoutRequestListener {
   private static final String TAG = OrderActivityView.class.getSimpleName();

   private TabLayout tabLayout;
   private ViewPager viewPager;
   private ViewPagerAdapter viewPagerAdapter;
   private LeftDrawerLayout mLeftDrawerLayout;
   private FlowingView flowingView;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_order);

      setStatusBarTranslucent(true);

      tabLayout = (TabLayout) findViewById(R.id.order_tabs);
      viewPager = (ViewPager) findViewById(R.id.order_viewpager);

      viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
      viewPager.setAdapter(viewPagerAdapter);

      final TabLayout.Tab products = tabLayout.newTab();
      final TabLayout.Tab orders = tabLayout.newTab();

      products.setIcon(R.drawable.ic_action_restaurant);
      orders.setIcon(R.drawable.ic_payment);

      tabLayout.addTab(products, 0);
      tabLayout.addTab(orders, 1);

      tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.colorAccent));
      tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorPrimary));

      tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

         @Override
         public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
         }

         @Override
         public void onTabUnselected(TabLayout.Tab tab) {
            // Nothing to do here now
         }

         @Override
         public void onTabReselected(TabLayout.Tab tab) {
            // Nothing to do here now
         }
      });

      viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

      initDrawer();
   }

   @Override
   public void onBackPressed() {
      if (viewPagerAdapter.doBack()) {
         Log.d(TAG, "onBackPressed: back in fragment implementation");
      } else {
         super.onBackPressed();
      }
   }

   @Override
   protected void onResume() {
      super.onResume();
      NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
      PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
      nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
   }

   @Override
   protected void onPause() {
      super.onPause();
      NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
      nfcAdapter.disableForegroundDispatch(this);
   }

   @Override
   protected void onNewIntent(Intent intent) {
      if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
         // Drop NFC event
      }
   }

   protected void setStatusBarTranslucent(boolean makeTranslucent) {
      if (makeTranslucent) {
         getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      } else {
         getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      }
   }

   private void initDrawer() {
      mLeftDrawerLayout = (LeftDrawerLayout) findViewById(R.id.id_drawerlayout);
      flowingView = (FlowingView) findViewById(R.id.sv);
      FragmentManager fm = getSupportFragmentManager();
      NavMenuFragment navMenuFragment = (NavMenuFragment) fm.findFragmentById(R.id.id_container_menu);
      if (navMenuFragment == null) {
         navMenuFragment = new NavMenuFragment();
         fm.beginTransaction().add(R.id.id_container_menu, navMenuFragment).commit();
      }

      navMenuFragment.setLogoutListener(this);

      mLeftDrawerLayout.setFluidView(flowingView);
      mLeftDrawerLayout.setMenuFragment(navMenuFragment);
   }

   @Override
   public void onLogoutRequested() {
      PreferencesMgr.INSTANCE.writeInt(KeyList.KEY_DESK_ID, 0);
      PreferencesMgr.INSTANCE.writeInt(KeyList.KEY_USER_ID, 0);
      PreferencesMgr.INSTANCE.writeString(KeyList.KEY_USER_NAME, "");
      startActivity(new Intent(this, LoginActivityView.class));
      finish();
   }
}
