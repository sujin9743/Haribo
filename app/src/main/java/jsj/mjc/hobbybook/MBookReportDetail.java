package jsj.mjc.hobbybook;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
//조민주

public class MBookReportDetail extends AppCompatActivity {
    ImageView backBtn,heartIcon;
    TextView hashTag1,hashTag2,hashTag3,hashTag4,reportTitle;
    TextView profileText,report_bookName, report_bookMaker, heartCnt;
    TextView report_content;
    ImageView bookImgPage;
    LinearLayout forBookInfo,forReview,porfileLayout;
    CircleImageView profileImg;

    FirebaseFirestore db= FirebaseFirestore.getInstance();;
    StorageReference storageRef= FirebaseStorage.getInstance().getReference();;
    StorageReference imgRef;

    String doc;
    String content, bMaker, bName;
    String h1,h2,h3,h4;
    String bookImg, isbn;
    Date date;
    String bookInfo, bookNum;
    int br_num;
    Boolean open;

    String review_max,review_max_mem;
    String mem_id;
    String mem_id_feed;
    int imSort;
    int i =0;
    String memID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_report_detail);

        backBtn = findViewById(R.id.backBtn);
        heartIcon = findViewById(R.id.heartIcon);
        porfileLayout = findViewById(R.id.profileLayout);
        hashTag1 = findViewById(R.id.hashTag1);
        hashTag2 = findViewById(R.id.hashTag2);
        hashTag3 = findViewById(R.id.hashTag3);
        hashTag4 = findViewById(R.id.hashTag4);
        reportTitle = findViewById(R.id.reportTitle);
        bookImgPage = findViewById(R.id.bookImgPage);
        forBookInfo = findViewById(R.id.forBookInfo);
        forReview = findViewById(R.id.forReview);

        heartCnt = findViewById(R.id.heartCnt);

        profileImg = findViewById(R.id.profileImg);
        profileText = findViewById(R.id.profileText);
        report_bookMaker = findViewById(R.id.report_bookMaker);
        report_bookName = findViewById(R.id.report_bookName);

        report_content = findViewById(R.id.content);

        final String bookre_num, br_title;
        String imSortText;

        final Intent intent = getIntent();
        imSortText = intent.getStringExtra("imMyFeed");
        mem_id_feed = intent.getStringExtra("mem_id");

        imSort = Integer.parseInt(imSortText);
        bookre_num = intent.getStringExtra("bookre_num");
        br_title = intent.getStringExtra("br_title");
        bookInfo = intent.getStringExtra("description");
        mem_id = intent.getStringExtra(getResources().getString(R.string.mid));

        //도서 상세 페이지에서 넘어옴
        review_max = intent.getStringExtra("book_isbn");

       // bookNum = intent.getStringExtra("br_num");
       // br_num = Integer.parseInt(bookNum);


        if (imSort == 1) {
            //마이피드나 유저피드에서 넘어왔을 때
            db.collection("bookre").document(bookre_num).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();

                            doc = document.getId();
                          //  profileText.setText(document.get(getResources().getString(R.string.mid)).toString());


                            h1 = document.getString("has1");
                            h2 = document.getString("has2");
                            h3 = document.getString("has3");
                            h4 = document.getString("has4");
                            hashTag1.setText(h1);
                            hashTag2.setText(h2);
                            hashTag3.setText(h3);
                            hashTag4.setText(h4);


                            content = document.getString("br_content");
                            bMaker = document.getString("book_author");
                            bName = document.getString("book_title");
                            bookInfo = document.getString("book_description");
                            report_content.setText(content);
                            report_bookMaker.setText(bMaker);
                            report_bookName.setText(bName);
                            reportTitle.setText(document.get("br_title").toString());


                            bookImg = document.get("br_img").toString();
                            Glide.with(getApplicationContext()).load(bookImg).into(bookImgPage);


                            heartCnt.setText(document.get("book_like").toString());


                            // date = document.getString("date");
                            isbn = document.getString("bookisbn");
                            //br_num = document.get("br_num").toString();
                            open = document.getBoolean("open");
                        }
                    });
            //닉네임
            db.collection("member").document(mem_id_feed).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc.exists()) {
                            profileText.setText(doc.getString(getResources().getString(R.string.name)));
                        }
                    } else {
                        Log.d("lll", "작성자 로드 실패 : " + task.getException());
                    }
                }
            });
            //프로필 사진 변경
            imgRef = storageRef.child("profile_img/" + mem_id_feed + ".jpg");
            imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(MBookReportDetail.this).load(uri).into(profileImg);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("e", "프로필 사진 로드 실패 : " + exception);
                }
            });



        } else{
            // TODO: 2020-11-17 whereEqualTo 2번째 인자 동적으로 바꿔야됨. 지금은 고정값임

            db.collection("bookre").whereEqualTo("br_title", br_title)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            doc = document.getId();
                         //   profileText.setText(document.get(getResources().getString(R.string.mid)).toString());


                            h1 = document.getString("has1");
                            h2 = document.getString("has2");
                            h3 = document.getString("has3");
                            h4 = document.getString("has4");
                            hashTag1.setText(h1);
                            hashTag2.setText(h2);
                            hashTag3.setText(h3);
                            hashTag4.setText(h4);


                            content = document.getString("br_content");
                            bMaker = document.getString("book_author");
                            bName = document.getString("book_title");
                            report_content.setText(content);
                            report_bookMaker.setText(bMaker);
                            report_bookName.setText(bName);
                            reportTitle.setText(document.get("br_title").toString());


                            bookImg = document.get("br_img").toString();
                            Glide.with(getApplicationContext()).load(bookImg).into(bookImgPage);


                            heartCnt.setText(document.get("book_like").toString());

                            date = document.getDate("date");
                            isbn = document.getString("bookisbn");
                            br_num =document.getLong("br_num").intValue();
                            open = document.getBoolean("open");

                            intent.putExtra("br_num",br_num);
                            intent.putExtra("mem_id",mem_id);
                        }
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("HHTT", "onFailure: " + e.toString());
                }
            });

            //닉네임
            db.collection("member").document(mem_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc.exists()) {
                            profileText.setText(doc.getString(getResources().getString(R.string.name)));
                        }
                    } else {
                        Log.d("lll", "작성자 로드 실패 : " + task.getException());
                    }
                }
            });
            //프로필 사진 변경
            imgRef = storageRef.child("profile_img/" + mem_id + ".jpg");
            imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(MBookReportDetail.this).load(uri).into(profileImg);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("e", "프로필 사진 로드 실패 : " + exception);
                }
            });
/*
        String loginId = "test";
        storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imgRef = storageRef.child("profile_img/" + loginId +".png");
        imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(MBookReportDetail.this).load(uri).into(profileImg);
            }
        });

 */
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            porfileLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), UserFeedActivity.class);
                    startActivity(i);
                }
            });

/*        hashTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MHashTagSearch.class);
                startActivity(i);
            }
        });

*/
            //도서 정보 상세 페이지로 이동
            forBookInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), MBookInfoDetail.class);
                    i.putExtra("title", bName);
                    i.putExtra("image", bookImg);
                    i.putExtra("author", bMaker);
                    i.putExtra("description", bookInfo);
                    i.putExtra("isbn", isbn);
                    startActivity(i);
                }
            });

            final Map<String, Object> save = new HashMap<>();

            heartIcon.setImageResource(R.drawable.heart_line);
            heartIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //0이면 빈하트
                    int heart_cnt;
                    heart_cnt = Integer.parseInt(heartCnt.getText().toString());
                    if (i == 0) {
                        heartIcon.setImageResource(R.drawable.heart_full);
                        i = 1;
                        heart_cnt++;
                        heartCnt.setText(Integer.toString(heart_cnt));
                    } else {
                        heartIcon.setImageResource(R.drawable.heart_line);
                        i = 0;
                        heart_cnt--;
                        heartCnt.setText(Integer.toString(heart_cnt));
                    }

                    save.put("book_like", heart_cnt);



                    db.collection("bookre").document(doc).update(save).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            });


            //리뷰 페이지으로 이동
            forReview = findViewById(R.id.forReview);
            forReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), MReportCommentActivity.class);
                    i.putExtra("br_num", "8");
                    startActivity(i);
                }
            });
        }

    }}
