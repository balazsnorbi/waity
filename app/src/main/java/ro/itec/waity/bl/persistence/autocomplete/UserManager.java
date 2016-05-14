package ro.itec.waity.bl.persistence.autocomplete;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Norbert on 5/15/2016.
 */
public enum UserManager {
   INSTANCE;

   public final void addUserName(String userName){
      User user = new User(userName);
      user.save();
   }

   public final List<String> getUsers() {
      List<String> userNames = new ArrayList<>();
      for (User user : User.listAll(User.class)) {
         userNames.add(user.userName);
      }
      return userNames;
   }
}
