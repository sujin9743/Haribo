package jsj.mjc.hobbybook;

import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyFeedFragment extends Fragment {

    ArrayList<FeedReadBookItem> mF_readBookList;
    FeedReadBookAdapter mF_feedReadBookAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myfeed, container, false);


        //RecyclerView
        mF_readBookList = new ArrayList<>();
        mF_feedReadBookAdapter = new FeedReadBookAdapter(mF_readBookList);
        RecyclerView myFeed_bookCover_recycler = view.findViewById(R.id.myFeed_bookCover_recycler);

        //context와 spanCount(한 줄을 몇 개 칸으로 나눌지)
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        myFeed_bookCover_recycler.setLayoutManager(gridLayoutManager);

        //todo 1. RecyclerView 책표지 데이터 삽입
        for(int i=0; i<10; i++) {
            FeedReadBookItem data = new FeedReadBookItem();
            mF_readBookList.add(data);
        }

        myFeed_bookCover_recycler.setAdapter(mF_feedReadBookAdapter);

        return view;
    }
}
