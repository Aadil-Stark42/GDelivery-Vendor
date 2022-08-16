package in.vdeliverzvendor.slot.mvp_delete;


import in.vdeliverzvendor.utils.GeneralResponse;

public class SlotDeletePresenter implements  SlotDeleteContract.GetslotdeleteIntractor.OnFinishedListener,SlotDeleteIntract.OnSlotDeleteListener {

    SlotDeleteContract slotDeleteContract;
    SlotDeleteIntract slotDeleteIntract;
    String TAG = SlotDeletePresenter.class.getSimpleName();


    public SlotDeletePresenter(SlotDeleteContract slotDeleteContract, SlotDeleteIntract slotDeleteIntract){
        this.slotDeleteContract = slotDeleteContract;
        this.slotDeleteIntract = slotDeleteIntract;
    }

    public void validateDetails(String strSlotid){
        slotDeleteIntract.directValidation(strSlotid,this);
    }



    @Override
    public void onFinished(GeneralResponse generalResponse) {
        slotDeleteContract.slotdelete_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        slotDeleteContract.slotdelete_failure(error_msg);
    }

    @Override
    public void do_logout() {
        slotDeleteContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (slotDeleteContract!=null)
            slotDeleteIntract.slotdeleteAPICall(this);
    }

    @Override
    public void onError(String msg) {
        slotDeleteContract.slotdelete_failure(msg);
    }
}
