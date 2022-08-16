package in.vdeliverzvendor.otp_Verification.mvp_mobilenum;


import in.vdeliverzvendor.utils.GeneralResponse;

public class VerifyMobileOtpPresenter implements VerifyMobileOtpIntract.OnLoginListener,
        VerifyMobileOtpContract.GetVerifyOtpIntractor.OnFinishedListener{


    VerifyMobileOtpContract verifyMobileOtpContract;
    VerifyMobileOtpIntract verifyMobileOtpIntract;
    String TAG= VerifyMobileOtpPresenter.class.getSimpleName();


    public VerifyMobileOtpPresenter(VerifyMobileOtpContract verifyMobileOtpContract,
                                    VerifyMobileOtpIntract verifyMobileOtpIntract){
        this.verifyMobileOtpContract = verifyMobileOtpContract;
        this.verifyMobileOtpIntract = verifyMobileOtpIntract;
    }

    public void validateDetails(String mobile,String otp){
        verifyMobileOtpIntract.directValidation(mobile,otp,this);

    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {
        verifyMobileOtpContract.verifymobileotp_success(generalResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        verifyMobileOtpContract.verifymobileotp_failure(error_msg);
    }

    @Override
    public void do_logout() {
        verifyMobileOtpContract.dashboard_logout();
    }


    @Override
    public void onSuccess() {
        if(verifyMobileOtpContract != null) {
            verifyMobileOtpIntract.verifymobileotpAPICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        verifyMobileOtpContract.verifymobileotp_failure(msg);
    }


}
