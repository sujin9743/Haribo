package jsj.mjc.hobbybook;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

        //툴바 설정
        Toolbar userFeed_toolbar = (Toolbar) findViewById(R.id.dDetailToolbar);
        setSupportActionBar(userFeed_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);

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

        ImageView dDetail_iv = findViewById(R.id.dDetail_iv);

        String str = ((DebateListActivity)DebateListActivity.context_dList).imgNum;

        if (str.equals("1"))
            dDetail_iv.setVisibility(View.GONE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.userfeedtoolbar, menu);
        return true;
    }

    //툴바 오버플로우 메뉴 이벤트 처리
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.overflow_report:
                ReportDialog reportDialog = new ReportDialog(DebateDetailActivity.this);
                reportDialog.show();
                break;
            case R.id.overflow_block:
        }
        return super.onOptionsItemSelected(item);
    }
}
