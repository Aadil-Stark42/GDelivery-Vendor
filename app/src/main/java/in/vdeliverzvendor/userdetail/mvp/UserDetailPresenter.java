package in.vdeliverzvendor.userdetail.mvp;


import in.vdeliverzvendor.userdetail.model.UserDetailResponse;

public class UserDetailPresenter implements  UserDetailContract.GetuserdetailIntractor.OnFinishedListener,UserDetailIntract.OnuserdetailListener {

    UserDetailContract userDetailContract;
    UserDetailIntract userDetailIntract;
    String TAG = UserDetailPresenter.class.getSimpleName();


    public UserDetailPresenter(UserDetailContract userDetailContract, UserDetailIntract userDetailIntract){
        this.userDetailContract = userDetailContract;
        this.userDetailIntract = userDetailIntract;
    }

    public void validateDetails(){
        userDetailIntract.directValidation(this);

    }

    @Override
    public void onFinished(UserDetailResponse userDetailResponse) {
        userDetailContract.userdetail_success(userDetailResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        userDetailContract.userdetail_failure(error_msg);
    }

    @Override
    public void do_logout() {
        userDetailContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (userDetailContract!=null)
            userDetailIntract.userdetailAPICall(this);
    }

    @Override
    public void onError(String msg) {
        userDetailContract.userdetail_failure(msg);
    }
}
