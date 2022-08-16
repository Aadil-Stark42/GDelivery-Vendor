package in.vdeliverzvendor.editoutlet.mvp;

import java.io.File;
import java.util.List;

import in.vdeliverzvendor.utils.GeneralResponse;

public class EditOutletPresenter implements  EditOutletContract.GetUpdateoutletIntractor.OnFinishedListener,EditOutletIntract.OnEditoutletListener {

    EditOutletContract editOutletContract;
    EditOutletIntract editOutletIntract;
    String TAG = EditOutletPresenter.class.getSimpleName();


    public EditOutletPresenter(EditOutletContract editOutletContract, EditOutletIntract editOutletIntract){
        this.editOutletContract = editOutletContract;
        this.editOutletIntract = editOutletIntract;
    }
    public void validateDetails(String strsshopid,String strminamount, String strradius, String strdesc, String strassign,
                                String stropentime, String strendtime, List<String> weekdaylist, File fileshop, File filebanner){
        editOutletIntract.directValidation(strsshopid,strminamount,strradius,strdesc,strassign,stropentime,strendtime,weekdaylist,fileshop,filebanner,this);

    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {
        editOutletContract.editoutlet_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        editOutletContract.editoutlet_failure(error_msg);

    }

    @Override
    public void do_logout() {
        editOutletContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (editOutletContract!=null)
            editOutletIntract.eupdateoutletAPICall(this);

    }

    @Override
    public void onError(String msg) {
        editOutletContract.editoutlet_failure(msg);

    }
}
