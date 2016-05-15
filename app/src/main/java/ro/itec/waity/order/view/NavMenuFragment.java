package ro.itec.waity.order.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mxn.soul.flowingdrawer_core.MenuFragment;

import ro.itec.waity.R;

/**
 * Created by Norbert on 5/15/2016.
 */
public class NavMenuFragment extends MenuFragment {

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view = null;
      if (container != null) {
         view = inflater.inflate(R.layout.fragment_menu, container, false);
      }
      return setupReveal(view);
   }
}
