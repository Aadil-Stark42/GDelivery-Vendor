package in.vdeliverzvendor.slotlist.mvp;


import in.vdeliverzvendor.slotlist.model.SlotListResponse;

public interface SlotListContract {

    void slotlist_success(SlotListResponse slotListResponse);

    void slotlist_failure(String msg);

    void dashboard_logout();

    interface GetslotlistIntractor {

        interface OnFinishedListener {
            void onFinished(SlotListResponse slotListResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void slotlistAPICall(GetslotlistIntractor.OnFinishedListener onFinishedListener);
    }
}
