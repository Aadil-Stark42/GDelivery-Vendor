package in.vdeliverzvendor.slot.mvp_getdetail;


import in.vdeliverzvendor.slot.model_getdetail.SlotDetailResponse;

public class SlotDetailPresenter implements  SlotDeatilContract.GetslotdetailIntractor.OnFinishedListener,SlotDetailIntract.OnSlotDetListener {

    SlotDeatilContract slotDeatilContract;
    SlotDetailIntract slotDetailIntract;
    String TAG = SlotDetailPresenter.class.getSimpleName();


    public SlotDetailPresenter(SlotDeatilContract slotDeatilContract, SlotDetailIntract slotDetailIntract){
        this.slotDeatilContract = slotDeatilContract;
        this.slotDetailIntract = slotDetailIntract;
    }

    public void validateDetails(String strSlotid){
        slotDetailIntract.directValidation(strSlotid,this);

    }


    @Override
    public void onFinished(SlotDetailResponse slotDetailResponse) {
        slotDeatilContract.slotdetail_success(slotDetailResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        slotDeatilContract.slotdetail_failure(error_msg);

    }

    @Override
    public void do_logout() {
        slotDeatilContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (slotDeatilContract!=null)
            slotDetailIntract.slotdetailAPICall(this);

    }

    @Override
    public void onError(String msg) {
        slotDeatilContract.slotdetail_failure(msg);

    }
}
