package in.vdeliverzvendor.slot.mvp_create;


import java.util.List;

import in.vdeliverzvendor.utils.GeneralResponse;

public class CreateSlotPresenter implements  CreateSlotContract.GetcreateslotIntractor.OnFinishedListener,CreateSlotIntract.OncreateslotListener {

    CreateSlotContract createSlotContract;
    CreateSlotIntract createSlotIntract;
    String TAG = CreateSlotPresenter.class.getSimpleName();

    public CreateSlotPresenter(CreateSlotContract createSlotContract, CreateSlotIntract createSlotIntract){
        this.createSlotContract = createSlotContract;
        this.createSlotIntract = createSlotIntract;
    }

    public void validateDetails(String strfrom, String strTo, List<String> weekdays,String strshopid){
        createSlotIntract.directValidation(strfrom,strTo,weekdays,strshopid,this);

    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {
        createSlotContract.createslot_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        createSlotContract.createslot_failure(error_msg);
    }

    @Override
    public void do_logout() {
        createSlotContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (createSlotContract!=null)
            createSlotIntract.createslotAPICall(this);

    }

    @Override
    public void onError(String msg) {
        createSlotContract.createslot_failure(msg);
    }
}
