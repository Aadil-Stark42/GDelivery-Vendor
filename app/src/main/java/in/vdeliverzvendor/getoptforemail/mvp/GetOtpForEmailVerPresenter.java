package in.vdeliverzvendor.getoptforemail.mvp;

import in.vdeliverzvendor.signup.model.SignupResponse;

public class GetOtpForEmailVerPresenter  implements GetOtpForEmailVerContract.GetEmailotpIntractor.OnFinishedListener,GetOtpForEmailVerIntract.OnGetotpListener {

    GetOtpForEmailVerContract getOtpForEmailVerContract;
    GetOtpForEmailVerIntract getOtpForEmailVerIntract;
    String TAG = GetOtpForEmailVerPresenter.class.getSimpleName();


    public GetOtpForEmailVerPresenter(GetOtpForEmailVerContract getOtpForEmailVerContract, GetOtpForEmailVerIntract getOtpForEmailVerIntract){
        this.getOtpForEmailVerContract = getOtpForEmailVerContract;
        this.getOtpForEmailVerIntract = getOtpForEmailVerIntract;
    }

    public void validateDetails(String mobile){
        getOtpForEmailVerIntract.directValidation(mobile,this);

    }

    @Override
    public void onFinished(SignupResponse signupResponse) {
        getOtpForEmailVerContract.getemailotp_success(signupResponse);

    }

    @Override
    public void onFailure(String error_msg) {
            getOtpForEmailVerContract.getemailotp_failure(error_msg);
    }

    @Override
    public void do_logout() {
        getOtpForEmailVerContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (getOtpForEmailVerContract!=null)
            getOtpForEmailVerIntract.getemailotpAPICall(this);

    }

    @Override
    public void onError(String msg) {
        getOtpForEmailVerContract.getemailotp_failure(msg);

    }
}
