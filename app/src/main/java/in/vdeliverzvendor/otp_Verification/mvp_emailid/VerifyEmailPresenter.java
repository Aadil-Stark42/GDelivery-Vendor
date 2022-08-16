package in.vdeliverzvendor.otp_Verification.mvp_emailid;


import in.vdeliverzvendor.utils.GeneralResponse;

public class VerifyEmailPresenter implements  VerifyEmailContract.GetVerifyOtpIntractor.OnFinishedListener,VerifyEmailIntract.OnotpListener {

    VerifyEmailContract verifyEmailContract;
    VerifyEmailIntract verifyEmailIntract;
    String TAG= VerifyEmailPresenter.class.getSimpleName();


    public VerifyEmailPresenter(VerifyEmailContract verifyEmailContract,
                                    VerifyEmailIntract verifyEmailIntract){
        this.verifyEmailContract = verifyEmailContract;
        this.verifyEmailIntract = verifyEmailIntract;
    }

    public void validateDetails(String stremail,String otp){
        verifyEmailIntract.directValidation(stremail,otp,this);

    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {

        verifyEmailContract.verifyemailotp_success(generalResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        verifyEmailContract.verifyemailotp_failure(error_msg);
    }

    @Override
    public void do_logout() {
        verifyEmailContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {

        if (verifyEmailContract!=null)
            verifyEmailIntract.verifyemailotpAPICall(this);

    }

    @Override
    public void onError(String msg) {
        verifyEmailContract.verifyemailotp_failure(msg);
    }
}
