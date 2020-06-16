package jsj.mjc.hobbybook;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecommendUserActivity extends AppCompatActivity {
    ArrayList<UserlistItem> userlist;
    UserListAdapter userListAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_recommend);

        //툴바 뒤로가기 버튼
        ImageButton userRc_backBtn = findViewById(R.id.userRc_backBtn);
        userRc_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //RecyclerView
        userlist = new ArrayList<>();
        userListAdapter = new UserListAdapter(userlist);

        RecyclerView userRc_recycler = findViewById(R.id.userRc_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        userRc_recycler.setLayoutManager(linearLayoutManager);

        //recyclerView 구분선 추가
        userRc_recycler.addItemDecoration(new DividerItemDecoration(userRc_recycler.getContext(), 1));

        //임시 데이터 삽입
        for(int i=0; i<20; i++) {
            UserlistItem data = new UserlistItem("하리보", "팔로우");
            userlist.add(data);
        }
        userRc_recycler.setAdapter(userListAdapter);

        //RecyclerView 항목 클릭 구현
        userListAdapter.setOnItemClickListener(new UserListAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), UserFeedActivity.class);
                startActivity(intent);
            }
        });
    }
}
