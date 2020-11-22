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

        rcBook_DB.collection(getString(R.string.cate)).document(loginId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
                            genre_name = getString(R.string.g0);
                            textGen.add(genre_name);
                            break;
                        case 2:
                            genre_name = getString(R.string.g1);
                            textGen.add(genre_name);
                            break;
                        case 3:
                            genre_name = getString(R.string.g2);
                            textGen.add(genre_name);
                            break;

                        case 4:
                            genre_name = getString(R.string.g3);
                            textGen.add(genre_name);
                            break;

                        case 5:
                            genre_name = getString(R.string.g4);
                            textGen.add(genre_name);
                            break;

                        case 6:
                            genre_name = getString(R.string.g5);
                            textGen.add(genre_name);
                            break;

                        case 7:
                            genre_name = getString(R.string.g6);
                            textGen.add(genre_name);
                            break;

                        case 8:
                            genre_name = getString(R.string.g7);
                            textGen.add(genre_name);
                            break;

                        case 9:
                            genre_name = getString(R.string.g8);
                            textGen.add(genre_name);
                            break;

                        case 10:
                            genre_name = getString(R.string.g9);
                            textGen.add(genre_name);
                            break;

                        case 11:
                            genre_name = getString(R.string.g10);
                            textGen.add(genre_name);
                            break;

                        case 12:
                            genre_name = getString(R.string.g11);
                            textGen.add(genre_name);
                            break;

                        case 13:
                            genre_name = getString(R.string.g12);
                            textGen.add(genre_name);
                            break;

                        case 14:
                            genre_name = getString(R.string.g13);
                            textGen.add(genre_name);
                            break;

                        case 15:
                            genre_name = getString(R.string.g14);
                            textGen.add(genre_name);
                            break;

                        case 16:
                            genre_name = getString(R.string.g15);
                            textGen.add(genre_name);
                            break;

                        case 17:
                            genre_name = getString(R.string.g16);
                            textGen.add(genre_name);
                            break;

                        case 18:
                            genre_name = getString(R.string.g17);
                            textGen.add(genre_name);
                            break;

                        case 19:
                            genre_name = getString(R.string.g18);
                            textGen.add(genre_name);
                            break;

                        case 20:
                            genre_name = getString(R.string.g19);
                            textGen.add(genre_name);
                            break;

                        case 21:
                            genre_name = getString(R.string.g20);
                            textGen.add(genre_name);
                            break;

                        case 22:
                            genre_name = getString(R.string.g21);
                            textGen.add(genre_name);
                            break;

                        case 23:
                            genre_name = getString(R.string.g22);
                            textGen.add(genre_name);
                            break;

                        case 24:
                            genre_name = getString(R.string.g23);
                            textGen.add(genre_name);
                            break;


                    }
                }

                for(int i=0;i<textGen.size();i++)
                    Log.d(TAG,""+textGen);
                String a=""+textGen;
                Log.d(TAG,""+a);
                tv.setText(getString(R.string.likeGenre)+a);

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
                            if (parser.getName().equals(getString(R.string.item))) {
                                bookItem = new RecommendBookItem();
                            } else if (parser.getName().equals(getString(R.string.cover))) {
                                parser.next();
                                if (bookItem != null) bookItem.setBookImgUrl(parser.getText());
                            } else if (parser.getName().equals(getString(R.string.ttle))) {
                                parser.next();
                                if (bookItem != null) bookItem.setBookTitle(parser.getText());
                            } else if (parser.getName().equals(getString(R.string.auth))) {
                                parser.next();
                                if (bookItem != null) bookItem.setBookWriter(parser.getText());
                            } else if (parser.getName().equals(getString(R.string.pub))) {
                                parser.next();
                                if (bookItem != null) bookItem.setBookPublisher(parser.getText());
                            } else if (parser.getName().equals(getString(R.string.crr))) {
                                parser.next();
                                if (bookItem != null)
                                    bookRating = Float.parseFloat(parser.getText());
                                bookItem.setBookRate(bookRating / 2);
                                bookItem.setBookRateTxt(String.valueOf(bookRating / 2));
                            } else if (parser.getName().equals(getString(R.string.desc))) {
                                parser.next();
                                if (bookItem != null) bookItem.setBookDesc(parser.getText());
                            }
                            break;
                        case XmlPullParser.TEXT:
                            break;
                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals(getString(R.string.item)) && bookItem != null) {
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
                    intent.putExtra(getString(R.string.ttle), title);
                    intent.putExtra(getString(R.string.img), image);
                    intent.putExtra(getString(R.string.desc), description);
                    intent.putExtra(getString(R.string.auth), author);
                    startActivity(intent);
                }
            });
        }
    }


}
