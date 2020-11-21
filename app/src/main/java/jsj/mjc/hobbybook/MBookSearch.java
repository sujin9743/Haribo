package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.internal.Version;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MBookSearch extends AppCompatActivity {
    ArrayList<RecommendBookItem> booklist = new ArrayList<>();
    RecyclerView bookSearch_recycler;

    Button btnBookSearch;
    EditText edtBookSearch;
    String keyword;

    float bookRating;
    public String dataKey = "ttbw_wowoo1406002";
    private String requestUrl;
    RecommendBookItem bookItem = null;
    String isbn, title, author, description;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_search);

        ImageView backBtn;
        TextView okBtn;

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //sj(도서 검색 api구현)
        btnBookSearch = findViewById(R.id.btnBookSearch);
        edtBookSearch = findViewById(R.id.edtBookSearch);
        bookSearch_recycler = findViewById(R.id.bookSearch_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        bookSearch_recycler.setLayoutManager(linearLayoutManager);

        //recyclerView 구분선 추가
        bookSearch_recycler.addItemDecoration(new DividerItemDecoration(bookSearch_recycler.getContext(), 1));

        btnBookSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = edtBookSearch.getText().toString();
                if (keyword.getBytes().length <= 0) {
                    Toast.makeText(MBookSearch.this, "검색어를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    booklist.clear();
                    BookSearchAsyncTask bookSearchAsyncTask = new BookSearchAsyncTask();
                    bookSearchAsyncTask.execute();
                }
            }
        });
    }

    //알라딘 API에서 데이터 불러오기
    public class BookSearchAsyncTask extends AsyncTask<String, String, String> {
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
                                bookItem = new RecommendBookItem();
                            }
                            else if(parser.getName().equals("cover")) {
                                parser.next();
                                if(bookItem!=null) bookItem.setBookImgUrl(parser.getText());
                            }
                            else if(parser.getName().equals("title")) {
                                parser.next();
                                if(bookItem!=null) bookItem.setBookTitle(parser.getText());
                            }
                            else if(parser.getName().equals("author")) {
                                parser.next();
                                if(bookItem!=null) bookItem.setBookWriter(parser.getText());
                            }
                            else if(parser.getName().equals("publisher")) {
                                parser.next();
                                if(bookItem!=null) bookItem.setBookPublisher(parser.getText());
                            }
                            else if(parser.getName().equals("customerReviewRank")) {
                                parser.next();
                                if(bookItem!=null) bookRating = Float.parseFloat(parser.getText());
                                bookItem.setBookRate(bookRating/2);
                                bookItem.setBookRateTxt(String.valueOf(bookRating/2));
                            }
                            else if(parser.getName().equals("isbn")) {
                                parser.next();
                                if(bookItem!=null) bookItem.setBookIsbn(parser.getText());
                            }
                            else if(parser.getName().equals("description")) {
                                parser.next();
                                if(bookItem!=null) bookItem.setBookDesc(parser.getText());
                            }
                            break;
                        case XmlPullParser.TEXT:
                            break;
                        case XmlPullParser.END_TAG:
                            if(parser.getName().equals("item") && bookItem != null) {
                                booklist.add(bookItem);
                                bookItem = new RecommendBookItem();
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

            RecommendBookAdapter bookListAdapter = new RecommendBookAdapter(booklist);
            bookSearch_recycler.setAdapter(bookListAdapter);

            bookListAdapter.setOnItemClickListener(new RecommendBookAdapter.OnItemClickListenr() {
                @Override
                public void onItemClick(View v, int position) { //책 누르면 도서 표지 저장
                    String image;
                    image = booklist.get(position).getBookImgUrl();
                    //도서제목 저장
                    title = booklist.get(position).getBookTitle();
                    author = booklist.get(position).getBookWriter();
                    isbn = booklist.get(position).getBookIsbn();
                    description = booklist.get(position).getBookDesc();

                    Intent intentB = new Intent();
                    intentB.putExtra("image", image);
                    intentB.putExtra("isbn", isbn);
                    intentB.putExtra("title", title);
                    intentB.putExtra("author", author);
                    intentB.putExtra("description", description);
                    setResult(RESULT_OK, intentB);
                    finish();
                }
            });
        }
    }
}
