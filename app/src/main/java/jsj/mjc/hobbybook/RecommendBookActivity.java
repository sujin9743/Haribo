package jsj.mjc.hobbybook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecommendBookActivity extends AppCompatActivity {
    ArrayList<RecommendBookItem> booklist = new ArrayList<>();
    RecyclerView bookRc_recycler;

    //API연동
    final String TAG = "RecommendBookActivity";
    public String dataKey = "ttbw_wowoo1406002";
    private String requestUrl;
    RecommendBookItem bookItem = null;

    float bookRating;
    String selectGenre;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_recommend);

        //툴바 뒤로가기
        ImageButton bookRc_backBtn = findViewById(R.id.bookRc_backBtn);
        bookRc_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //RecyclerView
        bookRc_recycler = findViewById(R.id.bookRc_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        bookRc_recycler.setLayoutManager(linearLayoutManager);

        //recyclerView 구분선 추가
        bookRc_recycler.addItemDecoration(new DividerItemDecoration(bookRc_recycler.getContext(), 1));

        //firebase
        final FirebaseFirestore rcBook_DB = FirebaseFirestore.getInstance();

        DocumentReference docRef = rcBook_DB.collection("category").document("test");
        /*docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });*/

        rcBook_DB.collection("category").document("test").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    List list = (List) document.getData().get("");
                }
            }
        });


        //API 연동
        RecommendBookAsyncTask recommendBookAsyncTask = new RecommendBookAsyncTask();
        recommendBookAsyncTask.execute();
        /*//임시 데이터 삽입
        for(int i=0; i<10; i++) {
            RecommendBookItem data = new RecommendBookItem("책 제목"+i, "지은이"+i, "출판사"+i, (float)4.5, "(4.5)");
            booklist.add(data);
        }*/

        /*//RecyclerView 항목 클릭 구현
        bookListAdapter.setOnItemClickListener(new RecommendBookAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), MBookInfoDetail.class);
                startActivity(intent);
            }
        });*/
    }

    //알라딘 API에서 데이터 불러오기
    public class RecommendBookAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

            requestUrl = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=" + dataKey +
                    "&QueryType=ItemEditorChoice&CategoryId=38414&MaxResults=20&start=1&SearchTarget=Book&output=xml&Version=20131101";

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
            bookRc_recycler.setAdapter(bookListAdapter);

            bookListAdapter.setOnItemClickListener(new RecommendBookAdapter.OnItemClickListenr() {
                @Override
                public void onItemClick(View v, int position) { //책 누르면 도서 상세정보 페이지로 이동
                    String title, image;
                    title = booklist.get(position).getBookTitle();
                    image = booklist.get(position).getBookImgUrl();

                    Intent intent = new Intent(getApplicationContext(), MBookInfoDetail.class);
                    intent.putExtra("title", title);
                    intent.putExtra("image", image);
                    startActivity(intent);
                }
            });
        }
    }
}
