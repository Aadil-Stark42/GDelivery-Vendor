package in.vdeliverzvendor.slot.mvp_slotupdate;

import java.util.List;

import in.vdeliverzvendor.utils.GeneralResponse;

public class SlotUpdatePresenter implements  SlotUpdateContract.GetslotupdateIntractor.OnFinishedListener,SlotUpdateIntract.OnslotsupdateListener {

    SlotUpdateContract slotUpdateContract;
    SlotUpdateIntract slotUpdateIntract;
    String TAG = SlotUpdatePresenter.class.getSimpleName();

    public SlotUpdatePresenter(SlotUpdateContract slotUpdateContract, SlotUpdateIntract slotUpdateIntract){
        this.slotUpdateContract = slotUpdateContract;
        this.slotUpdateIntract = slotUpdateIntract;
    }

    public void validateDetails(String strfrom, String strTo, List<String> weekdays, String strshopid,String strslotid){
        slotUpdateIntract.directValidation(strfrom,strTo,weekdays,strshopid,strslotid,this);

    }


    @Override
    public void onFinished(GeneralResponse generalResponse) {
        slotUpdateContract.slotupdate_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        slotUpdateContract.slotupdate_failure(error_msg);
    }

    @Override
    public void do_logout() {
        slotUpdateContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (slotUpdateContract!=null)
            slotUpdateIntract.slotupdateAPICall(this);
    }

    @Override
    public void onError(String msg) {
        slotUpdateContract.slotupdate_failure(msg);
    }
}
