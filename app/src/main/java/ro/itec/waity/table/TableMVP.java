package ro.itec.waity.table;

/**
 * Created by Norbert on 5/13/2016.
 */
public class TableMVP {

   public interface RequiredViewOperations {
      void nfcStatusEvent(boolean status);
   }

   public interface RequiredPresenterOperations {
      void checkForNFCStatus();
   }

   public interface RequiredModelOperations {
      boolean getNFCStatus();
   }
}
