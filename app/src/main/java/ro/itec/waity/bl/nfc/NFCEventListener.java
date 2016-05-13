package ro.itec.waity.bl.nfc;

/**
 * Created by Norbert on 5/13/2016.
 */
public interface NFCEventListener {

   /**
    * Method called when the NFC tag read succeeded
    * @param nfcString NFC Tag
    */
   void onNFCReadCompleted(boolean success, String nfcString);
}
