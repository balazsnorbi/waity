package ro.itec.waity.table.model;

import ro.itec.waity.bl.nfc.NFCManager;
import ro.itec.waity.table.TableMVP;

/**
 * Created by Norbert on 5/14/2016.
 */
public class TableModel implements TableMVP.RequiredModelOperations{

   @Override
   public boolean getNFCStatus() {
      return NFCManager.INSTANCE.isNFCEnabled();
   }
}
