package ro.itec.waity.table;

import android.app.Activity;
import android.content.Intent;

import ro.itec.waity.api.model.TableResponse;
import rx.Observable;
import rx.Subscription;

/**
 * Created by Norbert on 5/13/2016.
 */
public class TableMVP {

    /**
     * Public methods offered by the view for the presenter
     */
    public interface ViewOperations {

        /**
         * Called when the NFC status has been updated
         *
         * @param status boolean true-on false-off
         */
        void nfcStatusEvent(boolean status);

        /**
         * This is called by the presenter when the NFC tag was red
         *
         * @param status Success status
         * @param result Tag
         */
        void onNFCDecoded(boolean status, String result);

       /**
        * Table validation
        * @param tableId Integer
        */
        void tableValidated(Integer tableId);

        /**
         * The model callback for user check to Presenter
         * @param detected boolean
         * @param user User
         */
        void onUserDetected(boolean detected, int user);
    }

    /**
     * Methods offered by the presenter for the View and the Model
     */
    public interface PresenterOperations {

       /**
        * Called by the View who asks the presenter to check for the current user
        */
       void checkForCurrentUser();

       /**
        * The model callback for user check to Presenter
        * @param detected boolean
        * @param user User
        */
        void onUserDetected(boolean detected, int user);

        /**
         * Used to check for NFC status, a callback will be triggered
         */
        void checkForNFCStatus();

        /**
         * Blocks the given activity from running multiple instances
         *
         * @param activity Activity
         */
        void setupForegroundDispatch(Activity activity);

        /**
         * Allows activity to run multiple instances
         *
         * @param activity Activity
         */
        void stopForegroundDispatch(Activity activity);

        /**
         * The activity given will listen as a broadcast receiver for the NFC change event from system
         *
         * @param activity Activity
         * @param register ON/OFF
         */
        void registerForNFCChangeEvent(Activity activity, boolean register);

        /**
         * Notification callback with NFC value
         *
         * @param status boolean
         */
        void notifyNFCEvent(boolean status);

        /**
         * Called when an Activity receives a new intent -> maybe a new NFC tag was red?
         *
         * @param intent Intent
         */
        void handleNewIntent(Intent intent);

        /**
         * Called by the model when the NFC tag was decoded
         *
         * @param status boolean (succeeded?)
         * @param result String
         */
        void onNFCDecoded(boolean status, String result);

        /**
         * Order to save the desk ID as a shared preferences
         *
         * @param id String
         */
        void saveDeskID(int id);

       /**
        * Called when an ID was successfully scanned from a QR code
        * @param id String
        */
        void onIDObtainedFromQrCode(String id);

       /**
        * Called when the ID was successfully obtained from an NFC tag
        * @param id
        */
        void onIDObtainedFromNfc(String id);
    }

    /**
     * This interface contains all the methods offered by the model for the presenter
     */
    public interface ModelOperations {

       /**
        * This method is called by the presenter to order model to retrieve the current user if exists
        */
       void checkForUser();

        /**
         * Getter for NFC status
         *
         * @return boolean NFC on / off
         */
        boolean getNFCStatus();

        /**
         * Blocks the given activity from running multiple instances
         *
         * @param activity Activity
         */
        void setupForegroundDispatch(Activity activity);

        /**
         * Allows activity to run multiple instances
         *
         * @param activity Activity
         */
        void stopForegroundDispatch(Activity activity);

        /**
         * The activity given will listen as a broadcast receiver for the NFC change event from system
         *
         * @param activity Activity
         * @param register ON/OFF
         */
        void registerForNFCChangeEvent(Activity activity, boolean register);

        /**
         * Triggers a new NFC event
         *
         * @param newNfcStatus boolean NFC Status on/off
         */
        void notifyNFCEvent(boolean newNfcStatus);

        /**
         * Called when an Activity receives a new intent -> maybe a new NFC tag was red?
         *
         * @param intent Intent
         */
        void handleNewIntent(Intent intent);

        /**
         * Called by the model when the NFC tag was decoded
         *
         * @param status boolean (succeeded?)
         * @param result String
         */
        void onNFCDecoded(boolean status, String result);

        /**
         * Order to save the desk ID as a shared preferences
         *
         * @param id String
         */
        void saveDeskID(int id);

        Observable<TableResponse> getTableIdFromQr(String id);

        Observable<TableResponse> getTableIdFromNfc(String id);


    }
}
