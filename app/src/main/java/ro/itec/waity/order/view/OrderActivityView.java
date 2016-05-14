package ro.itec.waity.order.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import ro.itec.waity.R;

public class OrderActivityView extends AppCompatActivity {
    private static final String TAG = OrderActivityView.class.getSimpleName();

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;


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

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPagerAdapter.doBack()) {
            Log.d(TAG, "onBackPressed: back in fragment implementation");
        } else {
            super.onBackPressed();
        }
    }
}
