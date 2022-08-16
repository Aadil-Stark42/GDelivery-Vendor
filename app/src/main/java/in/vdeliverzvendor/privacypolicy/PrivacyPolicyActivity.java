package in.vdeliverzvendor.privacypolicy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.utils.BaseActivity;
import in.vdeliverzvendor.utils.MnxConstant;
import in.vdeliverzvendor.utils.MnxPreferenceManager;

public class PrivacyPolicyActivity extends BaseActivity {

    WebView webView;
    ImageView iv_search,iv_back;
    TextView tv_title;
    String TAG=PrivacyPolicyActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacypolicy);
        try {

        webView=findViewById(R.id.webview_privacypolicy);
        iv_search=findViewById(R.id.iv_search);
        iv_back=findViewById(R.id.iv_back);
        tv_title=findViewById(R.id.tv_name);
        iv_search.setVisibility(View.GONE);
        tv_title.setText("Privacy Policy");

        String privacy= MnxPreferenceManager.getString(MnxConstant.PRIVACY,null);
        if (privacy!=null && !(privacy.isEmpty())) {
            Log.i("CONTENTTEST","CONTENTTEST"+"Terms"+privacy);
            webView.loadUrl(privacy);
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "asfdafdfdf df: errorr rr + "+e.getMessage());
        }
    }
}
