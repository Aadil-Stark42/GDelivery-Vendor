package in.vdeliverzvendor.settings.logout_mvp;


import in.vdeliverzvendor.utils.GeneralResponse;

public class LogoutPresenter implements  LogoutContract.GetlogoutIntractor.OnFinishedListener,LogoutIntract.OnlogoutListener {

    LogoutContract logoutContract;
    LogoutIntract logoutIntract;
    String TAG = LogoutPresenter.class.getSimpleName();


    public LogoutPresenter(LogoutContract logoutContract, LogoutIntract logoutIntract){
        this.logoutContract = logoutContract;
        this.logoutIntract = logoutIntract;
    }

    public void validateDetails(){
        logoutIntract.directValidation(this);

    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {
        logoutContract.logout_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        logoutContract.logout_failure(error_msg);

    }

    @Override
    public void do_logout() {
        logoutContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (logoutContract!=null)
            logoutIntract.logoutAPICall(this);

    }

    @Override
    public void onError(String msg) {
        logoutContract.logout_failure(msg);

    }
}
