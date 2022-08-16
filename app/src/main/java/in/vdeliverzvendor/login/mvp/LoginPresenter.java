package in.vdeliverzvendor.login.mvp;

import in.vdeliverzvendor.login.model.LoginResponse;

public class LoginPresenter implements  LoginIntractor.OnLoginListener,LoginContractor.GetLoginIntractor.OnFinishedListener {

    LoginContractor loginContract;
    LoginIntractor loginIntract;
    String TAG = LoginPresenter.class.getSimpleName();


    public LoginPresenter(LoginContractor loginContract, LoginIntractor loginIntract){
        this.loginContract = loginContract;
        this.loginIntract = loginIntract;
    }

    public void validateDetails(String stremail,String strpassword,String strfcm){
        loginIntract.directValidation(stremail,strpassword,strfcm,this);

    }

    @Override
    public void onFinished(LoginResponse loginResponse) {
        loginContract.login_success(loginResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        loginContract.login_failure(error_msg);

    }

    @Override
    public void do_logout() {
        loginContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (loginContract!=null)
            loginIntract.loginAPICall(this);

    }

    @Override
    public void onError(String msg) {
        loginContract.login_failure(msg);

    }
}
