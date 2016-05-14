package ro.itec.waity.bl.persistence.autocomplete;

import com.orm.SugarRecord;

/**
 * Created by Norbert on 5/15/2016.
 */
public class User extends SugarRecord{
   public String userName;

   public User() {};

   public User(String userName) {
      this.userName = userName;
   }
}
