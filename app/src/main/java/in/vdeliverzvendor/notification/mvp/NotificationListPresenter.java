package in.vdeliverzvendor.notification.mvp;


import in.vdeliverzvendor.notification.model.NotificationListResponse;

public class NotificationListPresenter implements  NotificationListContract.GetnotificationlistIntractor.OnFinishedListener,
        NotificationListIntract.OnnotificationlistListener {

    NotificationListContract notificationListContract;
    NotificationListIntract notificationListIntract;
    String TAG = NotificationListPresenter.class.getSimpleName();

    public NotificationListPresenter(NotificationListContract notificationListContract,
                                     NotificationListIntract notificationListIntract){
        this.notificationListContract = notificationListContract;
        this.notificationListIntract = notificationListIntract;
    }

    public void validateDetails(String strpage){
        notificationListIntract.directValidation(strpage,this);
    }

    @Override
    public void onFinished(NotificationListResponse notificationListResponse) {
        notificationListContract.notificationlist_success(notificationListResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        notificationListContract.notificationlist_failure(error_msg);

    }

    @Override
    public void do_logout() {
        notificationListContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (notificationListContract!=null)
            notificationListIntract.notificationlistAPICall(this);
    }

    @Override
    public void onError(String msg) {
        notificationListContract.notificationlist_failure(msg);

    }
}
