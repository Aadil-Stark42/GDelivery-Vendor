package in.vdeliverzvendor.slot.mvp_delete;


import in.vdeliverzvendor.utils.GeneralResponse;

public interface SlotDeleteContract {

    void slotdelete_success(GeneralResponse generalResponse);

    void slotdelete_failure(String msg);

    void dashboard_logout();

    interface GetslotdeleteIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void slotdeleteAPICall(GetslotdeleteIntractor.OnFinishedListener onFinishedListener);
    }

}
