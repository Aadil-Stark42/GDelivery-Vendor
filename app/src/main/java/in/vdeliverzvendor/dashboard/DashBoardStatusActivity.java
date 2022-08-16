package in.vdeliverzvendor.dashboard;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.mdgiitr.suyash.graphkit.BarGraph;
import com.mdgiitr.suyash.graphkit.DataPoint;

import java.util.ArrayList;

import in.vdeliverzvendor.R;

public class DashBoardStatusActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_status);


        final BarGraph barGraph = findViewById(R.id.barGraph);
        ArrayList<DataPoint> points = new ArrayList<>();
        for(int i=0;i<5;i++) {
            points.add(new DataPoint(i+"A", i, getResources().getColor(R.color.brown)));
        }

//        points.add(new DataPoint("January", 10, getResources().getColor(R.color.orange)));
//        points.add(new DataPoint("February", 60, getResources().getColor(R.color.orange)));
//        points.add(new DataPoint("March", 44, getResources().getColor(R.color.orange)));
//        points.add(new DataPoint("April", 20, getResources().getColor(R.color.orange)));
//        points.add(new DataPoint("May", 65, getResources().getColor(R.color.orange)));

        barGraph.setPoints(points);
//        final BarGraph barGraphweek = findViewById(R.id.barweek);
//        ArrayList<DataPoint> pointsweek = new ArrayList<>();
//        points.add(new DataPoint("Week1", 10, getResources().getColor(R.color.orange)));
//        points.add(new DataPoint("Week2", 20, getResources().getColor(R.color.orange)));
//        points.add(new DataPoint("Week3", 30, getResources().getColor(R.color.orange)));
//        points.add(new DataPoint("Week4", 40, getResources().getColor(R.color.orange)));
//        points.add(new DataPoint("Week5", 50, getResources().getColor(R.color.orange)));
//        barGraphweek.setPoints(pointsweek);
    }
}
