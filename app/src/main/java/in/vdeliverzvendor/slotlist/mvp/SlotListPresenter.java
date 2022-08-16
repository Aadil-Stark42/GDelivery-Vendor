package in.vdeliverzvendor.slotlist.mvp;


import in.vdeliverzvendor.slotlist.model.SlotListResponse;

public class SlotListPresenter  implements  SlotListContract.GetslotlistIntractor.OnFinishedListener,SlotListIntract.OnSlotListener {

    SlotListContract slotListContract;
    SlotListIntract slotListIntract;
    String TAG = SlotListPresenter.class.getSimpleName();


    public SlotListPresenter(SlotListContract slotListContract, SlotListIntract slotListIntract){
        this.slotListContract = slotListContract;
        this.slotListIntract = slotListIntract;
    }

    public void validateDetails(String strShopId){
        slotListIntract.directValidation(strShopId,this);

    }

    @Override
    public void onFinished(SlotListResponse slotListResponse) {
        slotListContract.slotlist_success(slotListResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        slotListContract.slotlist_failure(error_msg);
    }

    @Override
    public void do_logout() {
        slotListContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (slotListContract!=null)
            slotListIntract.slotlistAPICall(this);

    }

    @Override
    public void onError(String msg) {
        slotListContract.slotlist_failure(msg);
    }
}
