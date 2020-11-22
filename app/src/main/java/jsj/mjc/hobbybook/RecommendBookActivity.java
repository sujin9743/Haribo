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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
    RecommendBookAdapter recommendBookAdapter;
    RecyclerView bookRc_recycler;
    TextView tv;
    ArrayList<Integer> loginGen;
    ArrayList<String> textGen;

    String loginId;

    //API연동
    final String TAG = "RecommendBookActivity";
    public String dataKey = "ttbw_wowoo1406002";
    private String requestUrl;
    RecommendBookItem bookItem;

    float bookRating;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    ArrayList<Integer> genre_array = new ArrayList<>();
    RecommendBookAsyncTask recommendBookAsyncTask;

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

        loginId = MainActivity.loginId;

        //RecyclerView
        bookRc_recycler = findViewById(R.id.bookRc_recycler);
        tv = findViewById(R.id.txt);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        bookRc_recycler.setLayoutManager(linearLayoutManager);

        //recyclerView 구분선 추가
        bookRc_recycler.addItemDecoration(new DividerItemDecoration(bookRc_recycler.getContext(), 1));

        recommendBookAdapter = new RecommendBookAdapter(booklist);
        bookRc_recycler.setAdapter(recommendBookAdapter);

        //firebase
        final FirebaseFirestore rcBook_DB = FirebaseFirestore.getInstance();

        rcBook_DB.collection("category").document(loginId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        HashMap map = (HashMap) document.getData();
                        for (int i = 0; i < map.size() - 1; i++) {
                            String selectGenreNum = Integer.toString(i + 1);
                            String selectGenreBool = map.get(selectGenreNum).toString();
                            if (selectGenreBool == "true") {
                                switch (selectGenreNum) {
                                    case "1": { genre_array.add(1230); break; }
                                    case "2": { genre_array.add(55890); break; }
                                    case "3": { genre_array.add(170); break; }
                                    case "4": { genre_array.add(38414); break; }
                                    case "5": { genre_array.add(39398); break; }
                                    case "6": { genre_array.add(987); break; }
                                    case "7": { genre_array.add(8257); break; }
                                    case "8": { genre_array.add(2551); break; }
                                    case "9": { genre_array.add(8259); break; }
                                    case "10": { genre_array.add(1); break; }
                                    case "11": { genre_array.add(1383); break; }
                                    case "12": { genre_array.add(1108); break; }
                                    case "13": { genre_array.add(55889); break; }
                                    case "14": { genre_array.add(1196); break; }
                                    case "15": { genre_array.add(74); break; }
                                    case "16": { genre_array.add(517); break; }
                                    case "17": { genre_array.add(1322); break; }
                                    case "18": { genre_array.add(13789); break; }
                                    case "19": { genre_array.add(656); break; }
                                    case "20": { genre_array.add(336); break; }
                                    case "21": { genre_array.add(112011); break; }
                                    case "22": { genre_array.add(28402); break; }
                                    case "23": { genre_array.add(17195); break; }
                                    case "24": { genre_array.add(38410); break; }
                                }
                            }
                        }
                        for (int i = 0; i < genre_array.size(); i++) {
                            recommendBookAsyncTask = new RecommendBookAsyncTask();
                            recommendBookAsyncTask.execute(genre_array.get(i));
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        loginGen = new ArrayList<>();
        textGen = new ArrayList<>();

        rcBook_DB.collection("category").document(loginId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot doc = task.getResult();
                for (int i = 1; i <= 24; i++) {
                    if (doc.getBoolean(String.valueOf(i)))
                        loginGen.add(i);
                }
                String genre_name = null;
                Log.d(TAG, "" + loginGen);
                for (int i : loginGen) {
                    switch (i) {
                        case 1:
                            genre_name = "가정/요리/뷰티";
                            textGen.add(genre_name);
                            break;
                        case 2:
                            genre_name = "건강/취미/레저";
                            textGen.add(genre_name);
                            break;
                        case 3:
                            genre_name = "경제경영";
                            textGen.add(genre_name);
                            break;

                        case 4:
                            genre_name = "고 전";
                            textGen.add(genre_name);
                            break;

                        case 5:
                            genre_name = "공무원 수험서";
                            textGen.add(genre_name);
                            break;

                        case 6:
                            genre_name = "과학";
                            textGen.add(genre_name);
                            break;

                        case 7:
                            genre_name = "대학교재/전문서적";
                            textGen.add(genre_name);
                            break;

                        case 8:
                            genre_name = "만화";
                            textGen.add(genre_name);
                            break;

                        case 9:
                            genre_name = "사회과학";
                            textGen.add(genre_name);
                            break;

                        case 10:
                            genre_name = "소설/시/희곡";
                            textGen.add(genre_name);
                            break;

                        case 11:
                            genre_name = "수험서/자격증";
                            textGen.add(genre_name);
                            break;

                        case 12:
                            genre_name = "어린이";
                            textGen.add(genre_name);
                            break;

                        case 13:
                            genre_name = "에세이";
                            textGen.add(genre_name);
                            break;

                        case 14:
                            genre_name = "여 행";
                            textGen.add(genre_name);
                            break;

                        case 15:
                            genre_name = "역 사";
                            textGen.add(genre_name);
                            break;

                        case 16:
                            genre_name = "예술/대중문화";
                            textGen.add(genre_name);
                            break;

                        case 17:
                            genre_name = "외국어";
                            textGen.add(genre_name);
                            break;

                        case 18:
                            genre_name = "유아";
                            textGen.add(genre_name);
                            break;

                        case 19:
                            genre_name = "인문학";
                            textGen.add(genre_name);
                            break;

                        case 20:
                            genre_name = "자기계발";
                            textGen.add(genre_name);
                            break;

                        case 21:
                            genre_name = "장르소설";
                            textGen.add(genre_name);
                            break;

                        case 22:
                            genre_name = "잡 지";
                            textGen.add(genre_name);
                            break;

                        case 23:
                            genre_name = "전집/중고전집";
                            textGen.add(genre_name);
                            break;

                        case 24:
                            genre_name = "종교/역학";
                            textGen.add(genre_name);
                            break;


                    }
                }

                for(int i=0;i<textGen.size();i++)
                    Log.d(TAG,""+textGen);
                String a=""+textGen;
                Log.d(TAG,""+a);
                tv.setText("선호 장르 : "+a);

            }
        });
    }

    //알라딘 API에서 데이터 불러오기
    public class RecommendBookAsyncTask extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... values) {

            requestUrl = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=" + dataKey +
                    "&QueryType=ItemEditorChoice&CategoryId=" + values[0].intValue() + "&MaxResults=5&start=1&SearchTarget=Book&output=xml&Version=20131101";
            ;

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
                            if (parser.getName().equals("item")) {
                                bookItem = new RecommendBookItem();
                            } else if (parser.getName().equals("cover")) {
                                parser.next();
                                if (bookItem != null) bookItem.setBookImgUrl(parser.getText());
                            } else if (parser.getName().equals("title")) {
                                parser.next();
                                if (bookItem != null) bookItem.setBookTitle(parser.getText());
                            } else if (parser.getName().equals("author")) {
                                parser.next();
                                if (bookItem != null) bookItem.setBookWriter(parser.getText());
                            } else if (parser.getName().equals("publisher")) {
                                parser.next();
                                if (bookItem != null) bookItem.setBookPublisher(parser.getText());
                            } else if (parser.getName().equals("customerReviewRank")) {
                                parser.next();
                                if (bookItem != null)
                                    bookRating = Float.parseFloat(parser.getText());
                                bookItem.setBookRate(bookRating / 2);
                                bookItem.setBookRateTxt(String.valueOf(bookRating / 2));
                            } else if (parser.getName().equals("description")) {
                                parser.next();
                                if (bookItem != null) bookItem.setBookDesc(parser.getText());
                            }
                            break;
                        case XmlPullParser.TEXT:
                            break;
                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals("item") && bookItem != null) {
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
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);

            recommendBookAdapter.notifyDataSetChanged();
            bookItem = new RecommendBookItem();

            recommendBookAdapter.setOnItemClickListener(new RecommendBookAdapter.OnItemClickListenr() {
                @Override
                public void onItemClick(View v, int position) { //책 누르면 도서 상세정보 페이지로 이동
                    String title, image, description, author;
                    title = booklist.get(position).getBookTitle();
                    image = booklist.get(position).getBookImgUrl();
                    description = booklist.get(position).getBookDesc();
                    author = booklist.get(position).getBookWriter();

                    Intent intent = new Intent(getApplicationContext(), MBookInfoDetail.class);
                    intent.putExtra("title", title);
                    intent.putExtra("image", image);
                    intent.putExtra("description", description);
                    intent.putExtra("author", author);
                    startActivity(intent);
                }
            });
        }
    }


}
