package in.vdeliverzvendor.outletstatus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Objects;

import in.vdeliverzvendor.R;
import in.vdeliverzvendor.editoutlet.OutletEditActivity;

public class OutLetStatusUpdateActivity extends AppCompatActivity {

    ImageView iv_menu,iv_back;
    RelativeLayout rl_edit;
    String TAG=OutLetStatusUpdateActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet_statuschanges);

        try{
        iv_menu=findViewById(R.id.iv_menu);
        rl_edit=findViewById(R.id.rl_edit);
        iv_back=findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_edit.setVisibility(View.VISIBLE);

            }
        });

        rl_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_edit.setVisibility(View.GONE);
                Intent myintent=new Intent(OutLetStatusUpdateActivity.this, OutletEditActivity.class);
                startActivity(myintent);
            }
        });

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dcaddadasd: errorr rr + "+e.getMessage());
        }
    }
}
