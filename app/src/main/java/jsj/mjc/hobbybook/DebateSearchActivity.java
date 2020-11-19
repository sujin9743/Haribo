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
