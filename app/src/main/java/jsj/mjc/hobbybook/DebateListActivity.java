package jsj.mjc.hobbybook;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class DebateListActivity extends AppCompatActivity {
    private ArrayList<Debate> debateArrayList;
    private DebateAdapter debateAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debate_list);

        RecyclerView recyclerView = findViewById(R.id.dList_RV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        debateArrayList = new ArrayList<>();
        debateAdapter = new DebateAdapter(debateArrayList);
        recyclerView.setAdapter(debateAdapter);

        DividerItemDecoration gDividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(gDividerItemDecoration);

        for (int i = 0; i < 20; i++) {
            int j = (int)(Math.random() * 3);
            Debate data = new Debate("", "토론글 제목" + i, "토론글의 내용을 한 줄로 보는 거 어떻게 생각해? 완전 좋지 않니",
                    "2020.06.11 14:16", "작성자" + i, 20, "" + j);
            debateArrayList.add(data);
        }
        debateAdapter.notifyDataSetChanged();

        ImageButton dListBack_btn = findViewById(R.id.dList_back_btn);
        ImageButton dListSearch_btn = findViewById(R.id.dList_search_btn);
        ImageButton dListAdd_btn = findViewById(R.id.dList_write_btn);

        dListBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dListSearch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DebateListActivity.this, DebateSearchActivity.class);
                startActivity(intent);
            }
        });

        dListAdd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DebateListActivity.this, DebateAddActivity.class);
                startActivity(intent);
            }
        });
    }
}
