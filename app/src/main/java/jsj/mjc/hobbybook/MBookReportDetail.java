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

        docId = getIntent().getStringExtra(getString(R.string.did));
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


        db.collection(getString(R.string.br)).document(docId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    doc = task.getResult();
                    hashTag1.setText(doc.getString(getString(R.string.h1)));
                    hashTag2.setText(doc.getString(getString(R.string.h2)));
                    hashTag3.setText(doc.getString(getString(R.string.h3)));
                    hashTag4.setText(doc.getString(getString(R.string.h4)));
                    report_content.setText(doc.getString(getString(R.string.brCon)));
                    report_bookMaker.setText(doc.getString(getString(R.string.b_auth)));
                    report_bookName.setText(doc.getString(getString(R.string.bTitle)));
                    reportTitle.setText(doc.getString(getString(R.string.brTitle)));
                    Glide.with(getApplicationContext()).load(doc.getString(getString(R.string.brImg))).into(bookImgPage);
                    heartCnt.setText(String.valueOf(doc.getLong(getString(R.string.bl)).intValue()));
                    writerId = doc.getString(getResources().getString(R.string.mid));
                    brNum = doc.getLong(getString(R.string.br_n)).intValue();

                    //도서 정보 상세 페이지로 이동
                    forBookInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getApplicationContext(), MBookInfoDetail.class);
                            i.putExtra(getString(R.string.ttle), doc.getString(getString(R.string.bTitle)));
                            i.putExtra(getString(R.string.img), doc.getString(getString(R.string.brImg)));
                            i.putExtra(getString(R.string.auth), doc.getString(getString(R.string.b_auth)));
                            i.putExtra(getString(R.string.desc), doc.getString(getString(R.string.b_desc)));
                            i.putExtra(getString(R.string.ibn), doc.getString(getString(R.string.bisbn)));
                            startActivity(i);
                        }
                    });

                    imgRef = storageRef.child(getString(R.string.pimg) + writerId + getString(R.string.jpg));
                    imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(MBookReportDetail.this).load(uri).into(profileImg);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError) + exception);
                        }
                    });

                    db.collection(getString(R.string.mem)).document(doc.getString(getResources().getString(R.string.mid))).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot doc = task.getResult();
                                if (doc.exists()) {
                                    profileText.setText(doc.getString(getResources().getString(R.string.name)));
                                }
                            } else {
                                Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError) + task.getException());
                            }
                        }
                    });

                } else {
                    Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError) + task.getException());
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
                save.put(getString(R.string.bl), heart_cnt);

                db.collection(getString(R.string.br)).document(docId).update(getString(R.string.bl), heart_cnt).addOnFailureListener(new OnFailureListener() {
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
                i.putExtra(getString(R.string.did), docId);
                i.putExtra(getString(R.string.brn), brNum);
                i.putExtra(getString(R.string.brw), writerId);
                startActivity(i);
            }
        });

    }}
