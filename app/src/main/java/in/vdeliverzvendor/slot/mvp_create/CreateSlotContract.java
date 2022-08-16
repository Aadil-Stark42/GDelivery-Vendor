package in.vdeliverzvendor.slot.mvp_create;


import in.vdeliverzvendor.utils.GeneralResponse;

public interface CreateSlotContract {

    void createslot_success(GeneralResponse generalResponse);

    void createslot_failure(String msg);

    void dashboard_logout();

    interface GetcreateslotIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void createslotAPICall(GetcreateslotIntractor.OnFinishedListener onFinishedListener);
    }
}


