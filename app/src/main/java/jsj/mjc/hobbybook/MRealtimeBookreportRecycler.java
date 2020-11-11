package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MRealtimeBookreportRecycler extends AppCompatActivity {
    ImageView addBtn,backBtn, searchBtn;


    RecyclerView recyclerView = null;
    MRealtimeBookreportAdapter adapter = null;
    ArrayList<MRealtime> list = new ArrayList<MRealtime>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_realtime_bookreport_recycler);


        recyclerView = findViewById(R.id.mRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MRealtimeBookreportAdapter(list);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        for(int i = 0;i<10;i++){
            MRealtime data = new MRealtime("도로시","드래곤라자"+ " | ","이영도","150","30");
            list.add(data);
        }



        adapter.notifyDataSetChanged();






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
