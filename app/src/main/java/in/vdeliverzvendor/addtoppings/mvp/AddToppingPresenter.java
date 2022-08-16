package in.vdeliverzvendor.addtoppings.mvp;


import in.vdeliverzvendor.utils.GeneralResponse;

public class AddToppingPresenter implements  AddToppingContract.GetaddtoppingIntractor.OnFinishedListener,AddToppingIntract.OnaddtoppingListener{

    AddToppingContract addToppingContract;
    AddToppingIntract addToppingIntract;
    String TAG = AddToppingPresenter.class.getSimpleName();


    public AddToppingPresenter(AddToppingContract addToppingContract, AddToppingIntract addToppingIntract){
        this.addToppingContract = addToppingContract;
        this.addToppingIntract = addToppingIntract;
    }


    public void validateDetails(String strPId,String strActualPrice,String stravailable,String strtitleid,String strname,String strVariant){
        addToppingIntract.directValidation(strPId,strActualPrice,stravailable,strtitleid,strname,strVariant,this);
    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {
        addToppingContract.addtopping_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        addToppingContract.addtopping_failure(error_msg);

    }

    @Override
    public void do_logout() {
        addToppingContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (addToppingContract!=null)
            addToppingIntract.addtoppingAPICall(this);

    }

    @Override
    public void onError(String msg) {
        addToppingContract.addtopping_failure(msg);

    }
}
