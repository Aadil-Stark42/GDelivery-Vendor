package in.vdeliverzvendor.userdetail.mvp_update;


import java.io.File;

import in.vdeliverzvendor.utils.GeneralResponse;

public class UserUpdatePresenter implements  UserUpdateContract.GetuserupdateIntractor.OnFinishedListener,UserUpdateIntract.OnuserupdateListener  {

    UserUpdateContract userUpdateContract;
    UserUpdateIntract userUpdateIntract;
    String TAG = UserUpdatePresenter.class.getSimpleName();


    public UserUpdatePresenter(UserUpdateContract updateContract, UserUpdateIntract userUpdateIntract){
        this.userUpdateContract = updateContract;
        this.userUpdateIntract = userUpdateIntract;
    }

    public void validateDetails(String strname, String strphonenum, String stremailid, File file){
        userUpdateIntract.directValidation(strname,strphonenum,stremailid,file,this);
    }


    @Override
    public void onFinished(GeneralResponse generalResponse) {
        userUpdateContract.userupdate_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        userUpdateContract.userupdate_failure(error_msg);
    }

    @Override
    public void do_logout() {
        userUpdateContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (userUpdateContract!=null)
            userUpdateIntract.userupdateAPICall(this);
    }

    @Override
    public void onError(String msg) {
        userUpdateContract.userupdate_failure(msg);
    }
}
