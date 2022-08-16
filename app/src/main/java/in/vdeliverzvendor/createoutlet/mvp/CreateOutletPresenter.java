package in.vdeliverzvendor.createoutlet.mvp;


import java.io.File;
import java.util.List;

import in.vdeliverzvendor.utils.GeneralResponse;

public class CreateOutletPresenter implements  CreateOutletContract.GetcreateoutletIntractor.OnFinishedListener,CreateOutletIntract.OnCreateOutletListener {

    CreateOutletContract createOutletContract;
    CreateOutletIntract createOutletIntract;
    String TAG = CreateOutletPresenter.class.getSimpleName();


    public CreateOutletPresenter(CreateOutletContract createOutletContract, CreateOutletIntract createOutletIntract){
        this.createOutletContract = createOutletContract;
        this.createOutletIntract = createOutletIntract;
    }

    public void validateDetails(File fileshop,File filebanner,String strShopName,String strStreet,String strArea,String strCity,
                                String strLat,String strLong,String strPhoneNum,String strEmail,String strComission,String strPrice,
                                String strRadius,String strDesc,String strAssign,String strOpeningTime,String strEndTime,List<String> weekdayslist,List<Integer> cuisinelist,String strminimumorderamount){
        createOutletIntract.directValidation(fileshop,filebanner,strShopName,strStreet,strArea,strCity,strLat,strLong,
                strPhoneNum,strEmail,strComission,strPrice,strRadius,strDesc,strAssign,strOpeningTime,strEndTime,weekdayslist,cuisinelist,strminimumorderamount,this);

    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {
        createOutletContract.createoutlet_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        createOutletContract.createoutlet_failure(error_msg);

    }

    @Override
    public void do_logout() {
        createOutletContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (createOutletContract!=null)
            createOutletIntract.createoutletAPICall(this);

    }

    @Override
    public void onError(String msg) {
        createOutletContract.createoutlet_failure(msg);

    }
}
