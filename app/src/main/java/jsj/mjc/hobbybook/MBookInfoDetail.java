package jsj.mjc.hobbybook;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MBookInfoDetail extends AppCompatActivity {

    RecyclerView recyclerView;
    MBookCommentAdapter adapter;
    ArrayList<MBookCom> list = new ArrayList<>();
    MBookCom item;


    ImageView backBtn, bookImage;
    LinearLayout letsGoReport;
    TextView reviewBtn, bookName;

    TextView editor, bookInfo;
    String getBookImage, getBookTitle,getBookAuthor, getBookDesc;
    Dialog reviewDialog;

    FirebaseFirestore db;
    private ImageView back;
    TextView upload;
    RatingBar stars,stars_show;
    EditText edt;
    String isbn ="9788954641630";
    Boolean deleted = true; //todo deleted, rv_num 수정 필요
    int rv_num = 0;
    int starsSum=0;
    int saveDStars;

    Float starsAvg;
    int docSize;
    ArrayList starArray = new ArrayList();
    ArrayList rStarsArray = new ArrayList();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_info_detail);

        //댓글 리사이클러뷰
        recyclerView = findViewById(R.id.reviewLayout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));

        item = new MBookCom();
        adapter = new MBookCommentAdapter(list);
        recyclerView.setAdapter(adapter);



        bookImage = findViewById(R.id.bookImage);
        bookName = findViewById(R.id.bookName);

        editor = findViewById(R.id.editor);
        bookInfo = findViewById(R.id.bookInfo);

        getBookImage = getIntent().getStringExtra("image");
        getBookTitle = getIntent().getStringExtra("title");
        getBookAuthor = getIntent().getStringExtra("author");
        getBookDesc = getIntent().getStringExtra("description");
        isbn = getIntent().getStringExtra("isbn");


        Glide.with(getApplicationContext()).load(getBookImage).into(bookImage);
        bookName.setText(getBookTitle);
        editor.setText(getBookAuthor);
        bookInfo.setText(getBookDesc);

        //bookInfo 책소개(정보) api 연결

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        letsGoReport = findViewById(R.id.letsGoReport);
        letsGoReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MBookReportDetail.class);
                startActivity(i);

                Toast.makeText(getApplicationContext(),"좋아요가 가장 많은 독후감 게시물로 이동됩니다.",Toast.LENGTH_SHORT).show();
            }
        });


        //별 점수 db에서 받아와서 값 넣어주기
        stars_show = findViewById(R.id.star);
        db = FirebaseFirestore.getInstance();
        db.collection("review").whereEqualTo("book_isbn",isbn).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        docSize = task.getResult().size();
                        starArray.add(document.getData().get("stars").toString());
                        //for(int i = 0; i < task.getResult().size(); i++){ //쿼리실행으로 가져온 문서수만큼만 돌아라

                            //Log.d(task.getResult().size()+"","그래그래그래그래그래그래그래그래그래그래그래그래그래그래그래그래그래그래그래그래그래");
                            //String []rStars_txt = new String[task.getResult().size()];
                            //rStars_txt[i] = document.get("stars").toString();
                            //rStars[i] = Integer.parseInt(rStars_txt[i]);

                            //docSize =task.getResult().size();
                        //}
                    }
                    //평점 출력
                    for(int i=0; i<starArray.size(); i++) {
                        rStarsArray.add(starArray.get(i));
                    }
                    for(int i=0; i<starArray.size(); i++) {
                        starsSum += Integer.parseInt((String) rStarsArray.get(i));
                        starsAvg = (float)starsSum/docSize;
                    }
                    stars_show.setRating(starsAvg);
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });

        //for(int i = 0; i<docSize;i++){
        //    int starsSum=0;
        //    starsSum += rStars[i];
        //    starsAvg = Math.round(starsSum/docSize);

        //}

        reviewBtn = findViewById(R.id.reviewBtn);
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //리뷰 다이얼로그 띄우기
                reviewDialog = new Dialog(MBookInfoDetail.this);
                reviewDialog.setContentView(R.layout.m_review_dialog);

                back = reviewDialog.findViewById(R.id.backBtn);
                upload = reviewDialog.findViewById(R.id.upload);
                stars = reviewDialog.findViewById(R.id.stars);
                edt = reviewDialog.findViewById(R.id.edt);

                db = FirebaseFirestore.getInstance();
                final Map<String, Object> saveReview = new HashMap<>();
                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy.MM.dd. HH:mm");
                        String formatDate = sdfNow.format(date);

//todo 투진...isbn...넣어즁나ㅣ우ㅡ미

                        saveReview.put("book_isbn",isbn);
                        saveReview.put("deleted",deleted);
                        saveReview.put("inputtime",formatDate);
                        saveReview.put("mem_id","test");
                        saveReview.put("rv_content",edt.getText().toString());
                        saveReview.put("rv_num",rv_num);
                        saveReview.put("stars",saveDStars);

                        //입력한 모든 데이터 서버에 저장
                        db.collection("review").document().set(saveReview).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                        reviewDialog.dismiss();
                    }
                });
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        reviewDialog.dismiss();
                    }
                });

                stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                      saveDStars = Math.round(rating);
                    }
                });

                //다이얼로그 창 주변부분 터치 가능여부
                //false면 터치 불가 true 터치 가능
                reviewDialog.setCanceledOnTouchOutside(false);

                reviewDialog.show();

            }
        });

    }

}
