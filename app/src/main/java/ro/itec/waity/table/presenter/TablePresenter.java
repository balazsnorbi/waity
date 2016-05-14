package ro.itec.waity.table.presenter;

import ro.itec.waity.table.TableMVP;
import ro.itec.waity.table.model.TableModel;

/**
 * Created by Norbert on 5/14/2016.
 */
public class TablePresenter implements TableMVP.RequiredPresenterOperations{

   private TableMVP.RequiredViewOperations viewOperations;
   private TableMVP.RequiredModelOperations modelOperations;

   public TablePresenter(TableMVP.RequiredViewOperations viewOperations) {
      this.viewOperations = viewOperations;
      this.modelOperations = new TableModel();
   }

   @Override
   public void checkForNFCStatus() {
      boolean nfcStatus = modelOperations.getNFCStatus();
      viewOperations.nfcStatusEvent(nfcStatus);
   }
}
