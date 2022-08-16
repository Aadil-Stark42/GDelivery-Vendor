package in.vdeliverzvendor.userdetail.mvp;


import in.vdeliverzvendor.userdetail.model.UserDetailResponse;

public interface UserDetailContract {

    void userdetail_success(UserDetailResponse userDetailResponse);

    void userdetail_failure(String msg);

    void dashboard_logout();

    interface GetuserdetailIntractor {

        interface OnFinishedListener {
            void onFinished(UserDetailResponse userDetailResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void userdetailAPICall(GetuserdetailIntractor.OnFinishedListener onFinishedListener);
    }
}
