package jsj.mjc.hobbybook;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecommendUserActivity extends AppCompatActivity {
   // ArrayList<UserlistItem> userlist;
    //UserListAdapter userListAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_recommend);

        userlist = new ArrayList<>();
        userListAdapter = new UserListAdapter(userlist);

        RecyclerView userRc_recycler = findViewById(R.id.userRc_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        userRc_recycler.setLayoutManager(linearLayoutManager);

        //recyclerView 구분선 추가
        userRc_recycler.addItemDecoration(new DividerItemDecoration(userRc_recycler.getContext(), 1));

        //임시 데이터 삽입
        for(int i=0; i<20; i++) {
            UserlistItem data = new UserlistItem("하리보");
            userlist.add(data);
        }

        userRc_recycler.setAdapter(userListAdapter);
    }
}
