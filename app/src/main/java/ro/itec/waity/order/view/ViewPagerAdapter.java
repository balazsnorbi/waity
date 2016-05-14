package ro.itec.waity.order.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.LinkedList;
import java.util.List;

import ro.itec.waity.order.view.listeners.OnBackPressedListener;


public class ViewPagerAdapter extends FragmentStatePagerAdapter implements OnBackPressedListener{

    List<OnBackPressedListener> listeners = new LinkedList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            ProductsFragment productsFragment = new ProductsFragment();
            listeners.add(productsFragment);
            return productsFragment;
        } else
            return new OrdersFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean doBack() {
        boolean answer = false;
        for (OnBackPressedListener listener : listeners) {
            boolean tmpAnswer = listener.doBack();
            if (tmpAnswer) {
                answer = true;
            }
        }
        return answer;
    }
}
