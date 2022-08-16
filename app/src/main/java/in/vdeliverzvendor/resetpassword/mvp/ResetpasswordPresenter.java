package in.vdeliverzvendor.resetpassword.mvp;

import in.vdeliverzvendor.userdetail.mvp.UserDetailPresenter;
import in.vdeliverzvendor.utils.GeneralResponse;

public class ResetpasswordPresenter  implements  ResetPasswordContract.GetresetpasswordIntractor.OnFinishedListener,ResetPasswordIntract.OnresetpasswordListener{

    ResetPasswordContract resetPasswordContract;
    ResetPasswordIntract resetPasswordIntract;
    String TAG = UserDetailPresenter.class.getSimpleName();


    public ResetpasswordPresenter(ResetPasswordContract resetPasswordContract, ResetPasswordIntract resetPasswordIntract){
        this.resetPasswordContract = resetPasswordContract;
        this.resetPasswordIntract = resetPasswordIntract;
    }

    public void validateDetails(String strmobile,String strpassword,String strconfirmpassword){
        resetPasswordIntract.directValidation(strmobile,strpassword,strconfirmpassword,this);

    }



    @Override
    public void onFinished(GeneralResponse generalResponse) {
        resetPasswordContract.resetpassword_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        resetPasswordContract.resetpassword_failure(error_msg);

    }

    @Override
    public void do_logout() {
        resetPasswordContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (resetPasswordContract!=null)
            resetPasswordIntract.resetpasswordAPICall(this);

    }

    @Override
    public void onError(String msg) {
        resetPasswordContract.resetpassword_failure(msg);
    }
}
