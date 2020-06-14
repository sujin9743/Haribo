package jsj.mjc.hobbybook;

import android.os.Bundle;
import android.view.LayoutInflater;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecommendBookActivity extends AppCompatActivity {
    ArrayList<RecommendBookItem> booklist;
    RecommendBookAdapter bookListAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_recommend);

        booklist = new ArrayList<>();
        bookListAdapter = new RecommendBookAdapter(booklist);

        RecyclerView bookRc_recycler = findViewById(R.id.bookRc_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        bookRc_recycler.setLayoutManager(linearLayoutManager);

        //recyclerView 구분선 추가
        bookRc_recycler.addItemDecoration(new DividerItemDecoration(bookRc_recycler.getContext(), 1));

        //임시 데이터 삽입
        for(int i=0; i<10; i++) {
            RecommendBookItem data = new RecommendBookItem("책 제목"+i, "지은이"+i, "출판사"+i, (float)4.5, "(4.5)");
            booklist.add(data);
        }

        bookRc_recycler.setAdapter(bookListAdapter);
    }
}
