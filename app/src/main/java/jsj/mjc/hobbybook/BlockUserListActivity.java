package jsj.mjc.hobbybook;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

public class BlockUserListActivity extends AppCompatActivity {
    ArrayList<UserlistItem> userlist;
    BlockListAdapter blockListAdapter;

    String blockUser;
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.block_user_list);

        //툴바 설정
        Toolbar blockList_toolbar = (Toolbar) findViewById(R.id.blockList_toolbar);
        setSupportActionBar(blockList_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_24dp);

        //RecyclerView
        userlist = new ArrayList<>();
        blockListAdapter = new BlockListAdapter(userlist);

        RecyclerView blockList_recycler = findViewById(R.id.blockList_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        blockList_recycler.setLayoutManager(linearLayoutManager);

        //recyclerView 구분선 추가
        blockList_recycler.addItemDecoration(new DividerItemDecoration(blockList_recycler.getContext(), 1));


        blockList_recycler.setAdapter(blockListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.alarmsetting_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            //수정 버튼 클릭 시 이벤트 구현
            case R.id.completeBtn:
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
