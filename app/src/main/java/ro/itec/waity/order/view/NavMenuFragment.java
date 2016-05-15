package ro.itec.waity.order.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

      TextView userName = (TextView)view.findViewById(R.id.nav_draw_user_name);
      userName.setText(PreferencesMgr.INSTANCE.readString(KeyList.KEY_USER_NAME));

      TextView tableID = (TextView)view.findViewById(R.id.tv_tableID);
      tableID.setText("" + PreferencesMgr.INSTANCE.readInt(KeyList.KEY_DESK_ID));

      Button button = (Button)view.findViewById(R.id.btn_logout);
      button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Toast.makeText(getContext(), "Logout nou!", Toast.LENGTH_SHORT).show();
         }
      });

      return setupReveal(view);
   }
}
