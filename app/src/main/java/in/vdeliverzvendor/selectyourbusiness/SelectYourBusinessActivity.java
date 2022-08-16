package in.vdeliverzvendor.selectyourbusiness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.vdeliverzvendor.R;
import in.vdeliverzvendor.signup.SignupActivity;

public class SelectYourBusinessActivity extends AppCompatActivity {

    RecyclerView rv_list;
    TextView tv_continue;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_select_your_business);

            tv_continue=findViewById(R.id.tv_continue);

            iv_back=findViewById(R.id.iv_back);

            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            rv_list=findViewById(R.id.rv_catlist);
            CatListAdapter catListAdapter = new CatListAdapter(this);
            rv_list.setHasFixedSize(true);
            rv_list.setLayoutManager(new LinearLayoutManager(this));
            rv_list.setAdapter(catListAdapter);

            tv_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myintent=new Intent(SelectYourBusinessActivity.this, SignupActivity.class);
                    startActivity(myintent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
