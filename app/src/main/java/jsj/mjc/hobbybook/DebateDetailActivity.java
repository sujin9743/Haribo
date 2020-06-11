package jsj.mjc.hobbybook;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DebateDetailActivity extends AppCompatActivity {
    private ArrayList<DebateComment> debateCommentArrayList;
    private DebateCommentAdapter debateCommentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debate_detail);

        RecyclerView recyclerView = findViewById(R.id.dDetail_comment_RV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        debateCommentArrayList = new ArrayList<>();
        debateCommentAdapter = new DebateCommentAdapter(debateCommentArrayList);
        recyclerView.setAdapter(debateCommentAdapter);

        DividerItemDecoration gDividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(gDividerItemDecoration);

        for (int i = 0; i < 20; i++) {
            int j = (int)(Math.random() * 3);
            DebateComment data = new DebateComment("" + i, "" + j, "댓글 예시 댓글 예시 예쁘게 보이도록 조금만 길게 써보자 으랏차 으랏차 우리는 모두 친구",
                    "댓글 작성자" + i, "2020.06.12. 12:34");
            debateCommentArrayList.add(data);
        }
        debateCommentAdapter.notifyDataSetChanged();

        ImageButton dDetail_back_btn = findViewById(R.id.dDetail_back_btn);
        ImageButton dDetail_more_btn = findViewById(R.id.dDetail_more_btn);
        ImageView dDetail_iv = findViewById(R.id.dDetail_iv);

        String str = ((DebateListActivity)DebateListActivity.context_dList).imgNum;

        if (str.equals("1"))
            dDetail_iv.setVisibility(View.GONE);


            dDetail_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dDetail_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //옵션 메뉴 열리게
            }
        });
    }
}
