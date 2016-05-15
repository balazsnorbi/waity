package ro.itec.waity.order.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mxn.soul.flowingdrawer_core.MenuFragment;

import org.w3c.dom.Text;

import ro.itec.waity.R;
import ro.itec.waity.bl.shared_preferences.KeyList;
import ro.itec.waity.bl.shared_preferences.PreferencesMgr;

/**
 * Created by Norbert on 5/15/2016.
 */
public class NavMenuFragment extends MenuFragment {

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_menu, container, false);
      TextView textView = (TextView)view.findViewById(R.id.nav_draw_user_name);
      textView.setText(PreferencesMgr.INSTANCE.readString(KeyList.KEY_USER_NAME));

      return setupReveal(view);
   }
}
