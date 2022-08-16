package in.vdeliverzvendor.slot.mvp_slotupdate;


import in.vdeliverzvendor.utils.GeneralResponse;

public interface SlotUpdateContract {

    void slotupdate_success(GeneralResponse generalResponse);

    void slotupdate_failure(String msg);

    void dashboard_logout();

    interface GetslotupdateIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void slotupdateAPICall(GetslotupdateIntractor.OnFinishedListener onFinishedListener);
    }
}
