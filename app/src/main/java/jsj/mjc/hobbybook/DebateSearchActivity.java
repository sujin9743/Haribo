package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DebateSearchActivity extends AppCompatActivity {
    private ArrayList<Debate> debateArrayList;
    private DebateAdapter debateAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debate_search);

        RecyclerView recyclerView = findViewById(R.id.debateSearch_result_RV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        debateArrayList = new ArrayList<>();
        debateAdapter = new DebateAdapter(debateArrayList);
        recyclerView.setAdapter(debateAdapter);

        DividerItemDecoration gDividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(gDividerItemDecoration);

        ImageButton dSearchBack_btn = findViewById(R.id.debateSearch_back_btn);
        ImageButton dSearchCon_btn = findViewById(R.id.debateSearch_con_btn);
        ImageButton dSearchAdd_btn = findViewById(R.id.debateS_write_btn);

        final EditText dKeyword_et = findViewById(R.id.debateSearch_keyword_et);

        dSearchBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dSearchCon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = dKeyword_et.getText().toString();
                if (keyword.getBytes().length <= 0) {
                    Toast.makeText(DebateSearchActivity.this, "검색어를 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < 20; i++) {
                        int j = (int) (Math.random() * 3);
                        //Debate data = new Debate("", "토론글 제목" + i, "토론글의 내용을 한 줄로 보는 거 어떻게 생각해? 완전 좋지 않니",
                                //"2020.06.11 14:16", "작성자" + i, 20, String.valueOf(j));
                        //debateArrayList.add(data);
                    }
                    debateAdapter.notifyDataSetChanged();
                }
            }
        });

        dSearchAdd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DebateSearchActivity.this, DebateAddActivity.class);
                startActivity(intent);
            }
        });
    }
}
