package jsj.mjc.hobbybook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class MBookSearch extends AppCompatActivity {
    ArrayList<RecommendBookItem> booklist = new ArrayList<>();
    RecyclerView bookSearch_recycler;

    Button btnBookSearch;
    EditText edtBookSearch;
    String bookTitle;

    float bookRating;
    public String dataKey = "ttbw_wowoo1406002";
    private String requestUrl;
    RecommendBookItem bookItem = null;

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
                bookTitle = edtBookSearch.getText().toString();
                BookSearchAsyncTask bookSearchAsyncTask = new BookSearchAsyncTask();
                bookSearchAsyncTask.execute();
            }
        });
    }

    //알라딘 API에서 데이터 불러오기
    public class BookSearchAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

            requestUrl = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=" +dataKey+
                    "&Query=" + bookTitle + "&QueryType=Title&start=1&SearchTarget=Book&output=xml&Version=20131101";

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
                            break;
                        case XmlPullParser.TEXT:
                            break;
                        case XmlPullParser.END_TAG:
                            if(parser.getName().equals("item") && bookItem != null) {
                                booklist.add(bookItem);
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

                    Intent intentB = new Intent();
                    intentB.putExtra("image", image);
                    setResult(RESULT_OK, intentB);
                    finish();
                }
            });
        }
    }
}
