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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

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
    DocumentSnapshot doc;

    String docId, loginId, writerId;
    Boolean isLike = false;
    int brNum;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_report_detail);

        docId = getIntent().getStringExtra("docId");
        loginId = getIntent().getStringExtra(getResources().getString(R.string.lid));

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


        db.collection("bookre").document(docId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    doc = task.getResult();
                    hashTag1.setText(doc.getString("has1"));
                    hashTag2.setText(doc.getString("has2"));
                    hashTag3.setText(doc.getString("has3"));
                    hashTag4.setText(doc.getString("has4"));
                    report_content.setText(doc.getString("br_content"));
                    report_bookMaker.setText(doc.getString("book_author"));
                    report_bookName.setText(doc.getString("book_title"));
                    reportTitle.setText(doc.getString("br_title"));
                    Glide.with(getApplicationContext()).load(doc.getString("br_img")).into(bookImgPage);
                    heartCnt.setText(String.valueOf(doc.getLong("book_like").intValue()));
                    writerId = doc.getString(getResources().getString(R.string.mid));
                    brNum = doc.getLong("br_num").intValue();

                    //도서 정보 상세 페이지로 이동
                    forBookInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getApplicationContext(), MBookInfoDetail.class);
                            i.putExtra("title", doc.getString("book_title"));
                            i.putExtra("image", doc.getString("br_img"));
                            i.putExtra("author", doc.getString("book_author"));
                            i.putExtra("description", doc.getString("book_description"));
                            i.putExtra("isbn", doc.getString("bookisbn"));
                            startActivity(i);
                        }
                    });

                    imgRef = storageRef.child("profile_img/" + writerId + ".jpg");
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

                    db.collection("member").document(doc.getString(getResources().getString(R.string.mid))).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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

                } else {
                    Log.d("e", "데이터 로드 실패 : " + task.getException());
                }
            }
        });

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
                i.putExtra(getResources().getString(R.string.uid), writerId);
                i.putExtra(getResources().getString(R.string.lid), loginId);
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
                if (isLike) {
                    heartIcon.setImageResource(R.drawable.heart_line);
                    isLike = false;
                    heart_cnt--;
                    heartCnt.setText(Integer.toString(heart_cnt));
                } else {
                    heartIcon.setImageResource(R.drawable.heart_full);
                    isLike = true;
                    heart_cnt++;
                    heartCnt.setText(Integer.toString(heart_cnt));
                }
                save.put("book_like", heart_cnt);

                db.collection("bookre").document(docId).update("book_like", heart_cnt).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

        //댓글 페이지로 이동
        forReview = findViewById(R.id.forReview);
        forReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MReportCommentActivity.class);
                i.putExtra(getResources().getString(R.string.lid), loginId);
                i.putExtra("docId", docId);
                i.putExtra("brNum", brNum);
                i.putExtra("brWriter", writerId);
                startActivity(i);
            }
        });

    }}
