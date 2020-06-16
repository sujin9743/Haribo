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

public class SearchActivity extends AppCompatActivity {
    private ArrayList<SearchedBook> bookArrayList;
    private SearchedBookAdapter bookAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ImageButton back_btn = findViewById(R.id.search_back_btn);
        ImageButton search_btn = findViewById(R.id.search_con_btn);
        final EditText search_et = findViewById(R.id.search_keyword_et);

        RecyclerView recyclerView = findViewById(R.id.search_result_RV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        bookArrayList = new ArrayList<>();
        bookAdapter = new SearchedBookAdapter(bookArrayList);
        recyclerView.setAdapter(bookAdapter);

        DividerItemDecoration gDividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(gDividerItemDecoration);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = search_et.getText().toString();
                if (keyword.getBytes().length <= 0) {
                    Toast.makeText(SearchActivity.this, "검색어를 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < 10; i++) {
                        SearchedBook data = new SearchedBook("", "검색한 책 제목", "검색한 책 저자");
                        bookArrayList.add(data);
                    }
                    bookAdapter.notifyDataSetChanged();
                }
            }
        });

        bookAdapter.setOnItemClickListener(new SearchedBookAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(SearchActivity.this, MBookInfoDetail.class);
                startActivity(intent);
            }
        });
    }
}
