package in.vdeliverzvendor.getotoformobilenum.mvp;


import in.vdeliverzvendor.signup.model.SignupResponse;

public class GetOtpformobileVerPresenter implements GetOtpformobileVerIntract.OnLoginListener,
        GetOtpformobileVerContract.GetSendOtpIntractor.OnFinishedListener{


    GetOtpformobileVerContract signupContract;
    GetOtpformobileVerIntract signupIntract;
    String TAG = GetOtpformobileVerPresenter.class.getSimpleName();


    public GetOtpformobileVerPresenter(GetOtpformobileVerContract signupContract, GetOtpformobileVerIntract signupIntract){
        this.signupContract = signupContract;
        this.signupIntract = signupIntract;
    }

    public void validateDetails(String mobile){
        signupIntract.directValidation(mobile,this);

    }

    @Override
    public void onFinished(SignupResponse signupResponse) {
        signupContract.sendotp_success(signupResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        signupContract.sendotp_failure(error_msg);
    }

    @Override
    public void do_logout() {
        signupContract.dashboard_logout();
    }


    @Override
    public void onSuccess() {
        if(signupContract != null) {
            signupIntract.sendotpAPICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        signupContract.sendotp_failure(msg);
    }


}
