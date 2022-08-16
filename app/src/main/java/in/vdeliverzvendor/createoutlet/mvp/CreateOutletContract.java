package in.vdeliverzvendor.createoutlet.mvp;


import in.vdeliverzvendor.utils.GeneralResponse;

public interface CreateOutletContract {

    void createoutlet_success(GeneralResponse generalResponse);

    void createoutlet_failure(String msg);

    void dashboard_logout();

    interface GetcreateoutletIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void createoutletAPICall(GetcreateoutletIntractor.OnFinishedListener onFinishedListener);
    }
}
