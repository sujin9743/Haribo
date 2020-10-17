package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MRealtimeBookreportRecycler extends AppCompatActivity {

    private ArrayList<MRealtime> mRealtimeList;
    private MRealtimeBookreportAdapter mAdapter;

    ImageView addBtn,backBtn, searchBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_realtime_bookreport_recycler);




        RecyclerView mRecycler = findViewById(R.id.mRecycler);
        LinearLayoutManager mLinearLayout = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLinearLayout);

        mRealtimeList = new ArrayList<>();

        mAdapter = new MRealtimeBookreportAdapter(mRealtimeList);
        mRecycler.setAdapter(mAdapter);






        addBtn = findViewById(R.id.addBookReport);
        backBtn = findViewById(R.id.backBtn);
        searchBtn = findViewById(R.id.searchBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MBookWriteMain.class);
                startActivity(intent);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MBookSearch.class);
                startActivity(intent);
            }
        });


    }
}
