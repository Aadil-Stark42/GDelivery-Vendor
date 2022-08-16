package in.vdeliverzvendor.editoutlet.mvp;


import in.vdeliverzvendor.utils.GeneralResponse;

public interface EditOutletContract {

    void editoutlet_success(GeneralResponse generalResponse);

    void editoutlet_failure(String msg);

    void dashboard_logout();

    interface GetUpdateoutletIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void eupdateoutletAPICall(GetUpdateoutletIntractor.OnFinishedListener onFinishedListener);
    }
}


