package in.vdeliverzvendor.productcategory.mvp_create;


import in.vdeliverzvendor.utils.GeneralResponse;

public class CreateCategoryPresenter implements CreateCategoryContract.GetCreateCategoryIntractor.OnFinishedListener,CreateCategoryIntract.OncreatecategoryListener {

    CreateCategoryContract createCategoryContract;
    CreateCategoryIntract createCategoryIntract;
    String TAG = CreateCategoryPresenter.class.getSimpleName();


    public CreateCategoryPresenter(CreateCategoryContract createCategoryContract, CreateCategoryIntract createCategoryIntract){
        this.createCategoryContract = createCategoryContract;
        this.createCategoryIntract = createCategoryIntract;
    }

    public void validateDetails(String strname){
        createCategoryIntract.directValidation(strname,this);

    }


    @Override
    public void onFinished(GeneralResponse generalResponse) {
        createCategoryContract.createcategory_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        createCategoryContract.createcategory_failure(error_msg);
    }

    @Override
    public void do_logout() {
        createCategoryContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (createCategoryContract!=null)
            createCategoryIntract.createcategoryAPICall(this);

    }

    @Override
    public void onError(String msg) {
        createCategoryContract.createcategory_failure(msg);
    }
}
