package ro.itec.waity.table.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import ro.itec.waity.api.model.TableResponse;
import ro.itec.waity.table.TableMVP;
import ro.itec.waity.table.model.TableModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class TablePresenter implements TableMVP.PresenterOperations {
    private static final String TAG = TablePresenter.class.getSimpleName();

    private CompositeSubscription subscriptions = new CompositeSubscription();

    private final TableMVP.ViewOperations view;
    private final TableMVP.ModelOperations model;

    public TablePresenter(TableMVP.ViewOperations view) {
        this.view = view;
        this.model = new TableModel(this);
    }

    @Override
    public void checkForNFCStatus() {
        forwardNFCEventToView(model.getNFCStatus());
    }

    @Override
    public void setupForegroundDispatch(final Activity activity) {
        model.setupForegroundDispatch(activity);
    }

    @Override
    public void stopForegroundDispatch(final Activity activity) {
        model.stopForegroundDispatch(activity);
    }

    @Override
    public void registerForNFCChangeEvent(final Activity activity, final boolean register) {
        model.registerForNFCChangeEvent(activity, register);
    }

    @Override
    public void notifyNFCEvent(boolean status) {
        forwardNFCEventToView(status);
    }

    @Override
    public void handleNewIntent(Intent intent) {
        model.handleNewIntent(intent);
    }

    @Override
    public void onNFCDecoded(boolean status, String result) {
        view.onNFCDecoded(status, result);
    }

    @Override
    public void saveDeskID(int id) {
        model.saveDeskID(id);
    }

    @Override
    public void onIDObtainedFromQrCode(String id) {
        subscriptions.add(model.getTableIdFromQr(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TableResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(TableResponse tableResponse) {
                        Log.d(TAG, "onNext: " + tableResponse.toString());
                        processTableResponse(tableResponse);
                    }
                }));
    }

    @Override
    public void onIDObtainedFromNfc(String id) {
        subscriptions.add(model.getTableIdFromNfc(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TableResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(TableResponse tableResponse) {
                        Log.d(TAG, "onNext: " + tableResponse.toString());
                        processTableResponse(tableResponse);
                    }
                }));
    }

    private void processTableResponse(TableResponse tableResponse) {
        if (tableResponse.getStatus().equals("ok")) {
            view.tableValidated(tableResponse.getTableId());
        } else {
            //TODO: show error
        }
    }

    private void forwardNFCEventToView(boolean nfcStatus) {
        view.nfcStatusEvent(nfcStatus);
    }
}
