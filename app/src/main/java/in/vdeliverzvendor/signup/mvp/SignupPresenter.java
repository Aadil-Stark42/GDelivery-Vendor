package in.vdeliverzvendor.signup.mvp;


import in.vdeliverzvendor.signup.model.SignupResponse;

public class SignupPresenter implements SignupIntract.OnLoginListener,
        SignupContract.GetSignupIntractor.OnFinishedListener{


    SignupContract signupContract;
    SignupIntract signupIntract;
    String TAG = SignupPresenter.class.getSimpleName();


    public SignupPresenter(SignupContract signupContract, SignupIntract signupIntract){
        this.signupContract = signupContract;
        this.signupIntract = signupIntract;
    }

    public void validateDetails(String name,String email,String mobile,String password,String confirm_password){
        signupIntract.directValidation(name,email,mobile,password,confirm_password,this);

    }

    @Override
    public void onFinished(SignupResponse signupResponse) {
        signupContract.signup_success(signupResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        signupContract.signup_failure(error_msg);
    }

    @Override
    public void do_logout() {
        signupContract.dashboard_logout();
    }


    @Override
    public void onSuccess() {
        if(signupContract != null) {
            signupIntract.signupAPICall(this);
        }
    }

    @Override
    public void onError(String msg) {
        signupContract.signup_failure(msg);
    }


}
