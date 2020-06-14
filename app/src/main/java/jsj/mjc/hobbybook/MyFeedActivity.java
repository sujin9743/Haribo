package jsj.mjc.hobbybook;

import android.os.Bundle;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyFeedActivity extends AppCompatActivity {
    ArrayList<FeedReadBookItem> mF_readBookList;
    FeedReadBookAdapter mF_feedReadBookAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myfeed);

        //RecyclerView
        mF_readBookList = new ArrayList<>();
        mF_feedReadBookAdapter = new FeedReadBookAdapter(mF_readBookList);

        RecyclerView myFeed_bookCover_recycler = findViewById(R.id.myFeed_bookCover_recycler);
        //context와 spanCount(한 줄을 몇 개 칸으로 나눌지)
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        myFeed_bookCover_recycler.setLayoutManager(gridLayoutManager);

        //recyclerView 구분선 추가
        //bookRc_recycler.addItemDecoration(new DividerItemDecoration(bookRc_recycler.getContext(), 1));

        //todo 1. RecyclerView 책표지 데이터 삽입
        for(int i=0; i<10; i++) {
            FeedReadBookItem data = new FeedReadBookItem();
            mF_readBookList.add(data);
        }

        myFeed_bookCover_recycler.setAdapter(mF_feedReadBookAdapter);
    }
}
