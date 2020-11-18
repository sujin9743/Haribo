package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private ArrayList<SearchedBook> bookArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    private SearchedBookAdapter bookAdapter;

    public String dataKey = "ttbw_wowoo1406002";
    private String requestUrl;
    SearchedBook searchedBook = null;
    String keyword;
    String title, image, author, description, isbn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ImageButton back_btn = findViewById(R.id.search_back_btn);
        final ImageButton search_btn = findViewById(R.id.search_con_btn);
        final EditText search_et = findViewById(R.id.search_keyword_et);

        recyclerView = findViewById(R.id.search_result_RV);
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
                keyword = search_et.getText().toString();
                if (keyword.getBytes().length <= 0) {
                    Toast.makeText(SearchActivity.this, "검색어를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    bookArrayList.clear();
                    MainSearchAsyncTask mainSearchAsyncTask = new MainSearchAsyncTask();
                    mainSearchAsyncTask.execute();
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

    //알라딘 API에서 데이터 불러오기
    public class MainSearchAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

            requestUrl = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=" +dataKey+
                    "&Query=" + keyword + "&Cover=Big&start=1&SearchTarget=Book&output=xml&Version=20131101";

            try {
                URL url = new URL(requestUrl);
                InputStream is = url.openStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new InputStreamReader(is, "UTF-8"));

                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            if(parser.getName().equals("item")) {
                                searchedBook = new SearchedBook();
                            }
                            else if(parser.getName().equals("cover")) {
                                parser.next();
                                if(searchedBook!=null) searchedBook.setBookImageUrl(parser.getText());
                            }
                            else if(parser.getName().equals("title")) {
                                parser.next();
                                if(searchedBook!=null) searchedBook.setBookTitle(parser.getText());
                            }
                            else if(parser.getName().equals("author")) {
                                parser.next();
                                if(searchedBook!=null) searchedBook.setBookWriter(parser.getText());
                            }
                            else if(parser.getName().equals("description")) {
                                parser.next();
                                if(searchedBook!=null) searchedBook.setBookDesc(parser.getText());
                            }
                            else if(parser.getName().equals("isbn")) {
                                parser.next();
                                if(searchedBook!=null) searchedBook.setBookIsbn(parser.getText());
                            }
                            break;
                        case XmlPullParser.TEXT:
                            break;
                        case XmlPullParser.END_TAG:
                            if(parser.getName().equals("item") && searchedBook != null) {
                                bookArrayList.add(searchedBook);
                            }
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                    }
                    eventType = parser.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            SearchedBookAdapter searchedBookAdapter = new SearchedBookAdapter(bookArrayList);
            recyclerView.setAdapter(searchedBookAdapter);

            // TODO: 2020-11-18 도서상세페이지 데이터 넣어야함
            searchedBookAdapter.setOnItemClickListener(new SearchedBookAdapter.OnItemClickListenr() {
                @Override
                public void onItemClick(View v, int position) { //책 누르면 도서 표지 저장
                    Intent intent = new Intent(SearchActivity.this, MBookInfoDetail.class);

                    image = bookArrayList.get(position).getBookImageUrl();
                    title = bookArrayList.get(position).getBookTitle();
                    author = bookArrayList.get(position).getBookWriter();
                    description = bookArrayList.get(position).getBookDesc();
                    isbn = bookArrayList.get(position).getBookIsbn();

                    intent.putExtra("image", image);
                    intent.putExtra("title", title);
                    intent.putExtra("author", author);
                    intent.putExtra("description", description);
                    intent.putExtra("isbn", isbn);

                    startActivity(intent);
                }
            });
        }
    }
}
