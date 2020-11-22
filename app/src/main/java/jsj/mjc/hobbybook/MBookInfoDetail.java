package jsj.mjc.hobbybook;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class MBookInfoDetail extends AppCompatActivity {

    RecyclerView recyclerView;
    MBookCommentAdapter adapter;
    ArrayList<MBookCom> list = new ArrayList<MBookCom>();


    final SimpleDateFormat dateFormatter = new SimpleDateFormat("y. M. d. hh:mm");
    ImageView backBtn, bookImage;
    TextView reviewBtn, bookName;

    TextView editor, bookInfo;
    String getBookImage, getBookTitle,getBookAuthor, getBookDesc;
    Dialog reviewDialog;

    FirebaseFirestore db;
    private ImageView back;
    TextView upload;
    RatingBar stars,stars_show;
    EditText edt;
    String isbn, loginId;
    Boolean deleted = true; //todo deleted, rv_num 수정 필요
    int rv_num = 0;
    int starsSum=0;
    int saveDStars;

    int starsAvg;
    int docSize;
    ArrayList starArray = new ArrayList();
    ArrayList rStarsArray = new ArrayList();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_info_detail);


        loginId = MainActivity.loginId;

        //댓글 리사이클러뷰
        recyclerView = findViewById(R.id.reviewLayout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));
        adapter = new MBookCommentAdapter(list);
        recyclerView.setAdapter(adapter);



        db = FirebaseFirestore.getInstance();
        db.collection(getResources().getString(R.string.rv)).orderBy(getResources().getString(R.string.time), Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        if(doc.get(getResources().getString(R.string.b_isbn)).toString().equals(isbn)){
                        Timestamp ts = (Timestamp) doc.getData().get(getResources().getString(R.string.time));
                        String date = dateFormatter.format(ts.toDate());
                        MBookCom data = new MBookCom(doc.get(getResources().getString(R.string.mid)).toString(), date, doc.get(getResources().getString(R.string.rvCon)).toString());

                        list.add(data);

                    }
                } }adapter.notifyDataSetChanged();

            }
        });


        bookImage = findViewById(R.id.bookImage);
        bookName = findViewById(R.id.bookName);

        editor = findViewById(R.id.editor);
        bookInfo = findViewById(R.id.bookInfo);

        getBookImage = getIntent().getStringExtra(getString(R.string.img));
        getBookTitle = getIntent().getStringExtra(getString(R.string.ttle));
        getBookAuthor = getIntent().getStringExtra(getString(R.string.auth));
        getBookDesc = getIntent().getStringExtra(getString(R.string.desc));
        isbn = getIntent().getStringExtra(getString(R.string.ibn));



        Glide.with(getApplicationContext()).load(getBookImage).into(bookImage);
        bookName.setText(getBookTitle);
        editor.setText(getBookAuthor);
        bookInfo.setText(getBookDesc);

        //bookInfo 책소개(정보) api 연결
        final Intent i = new Intent(getApplicationContext(), MBookReportDetail.class);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //별 점수 db에서 받아와서 값 넣어주기
        stars_show = findViewById(R.id.star);

        db.collection(getResources().getString(R.string.rv)).whereEqualTo(getResources().getString(R.string.b_isbn),isbn).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        docSize = task.getResult().size();
                        starArray.add(document.getData().get(getResources().getString(R.string.star)).toString());
                    }
                    //평점 출력
                    for(int i=0; i<starArray.size(); i++) {
                        rStarsArray.add(starArray.get(i));
                    }
                    for(int i=0; i<starArray.size(); i++) {
                        starsSum += Integer.parseInt((String) rStarsArray.get(i));
                        starsAvg = Math.round(starsSum/docSize);
                    }
                    stars_show.setRating(starsAvg);
                } else {
                    Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError), task.getException());
                }
            }
        });


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

                        long now = System.currentTimeMillis();;
                        Date formatDate = new Date(now);

                        saveReview.put(getString(R.string.b_isbn),isbn);
                        saveReview.put(getResources().getString(R.string.isDel),deleted);
                        saveReview.put(getResources().getString(R.string.time),formatDate);
                        saveReview.put(getResources().getString(R.string.mid),loginId);
                        saveReview.put(getString(R.string.rvCon),edt.getText().toString());
                        saveReview.put(getString(R.string.rvN),rv_num);
                        saveReview.put(getString(R.string.star),saveDStars);

                        //입력한 모든 데이터 서버에 저장
                        db.collection(getString(R.string.rv)).document().set(saveReview).addOnFailureListener(new OnFailureListener() {
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
