package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.IntRange;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserFeedActivity extends AppCompatActivity {
    ArrayList<FeedReadBookItem> uF_readBookList;
    FeedReadBookAdapter uF_feedReadBookAdapter;
    TextView follower_count_txt, following_count_txt;
    Button message_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_feed);


        //툴바 설정
        Toolbar userFeed_toolbar = (Toolbar) findViewById(R.id.userFeed_toolbar);
        setSupportActionBar(userFeed_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);

        //RecyclerView
        uF_readBookList = new ArrayList<>();
        uF_feedReadBookAdapter = new FeedReadBookAdapter(uF_readBookList);

        RecyclerView bookCover_recycler = findViewById(R.id.bookCover_recycler);
        //context와 spanCount(한 줄을 몇 개 칸으로 나눌지)
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        bookCover_recycler.setLayoutManager(gridLayoutManager);

        //todo 1. RecyclerView 책표지 데이터 삽입
        for(int i=0; i<10; i++) {
            FeedReadBookItem data = new FeedReadBookItem();
            uF_readBookList.add(data);
        }
        bookCover_recycler.setAdapter(uF_feedReadBookAdapter);

        //RecyclerView 항목 클릭 구현
        uF_feedReadBookAdapter.setOnItemClickListener(new FeedReadBookAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), MBookReportDetail.class);
                startActivity(intent);
            }
        });

        //팔로잉/팔로워 목록 조회 화면 이동
        follower_count_txt = findViewById(R.id.follower_count_txt);
        follower_count_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FollowListActivity.class);
                startActivity(intent);
            }
        });
        following_count_txt = findViewById(R.id.following_count_txt);
        following_count_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FollowListActivity.class);
                startActivity(intent);
            }
        });

        //쪽지 보내기 화면 이동
        message_btn = findViewById(R.id.message_btn);
        message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MessageSendActivity.class);
                startActivity(intent);
            }
        });
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
                return true;
            case R.id.overflow_report:
                ReportDialog reportDialog = new ReportDialog(UserFeedActivity.this);
                reportDialog.show();
                return true;
            //case R.id.overflow_block: {
            //}
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
