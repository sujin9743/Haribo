package jsj.mjc.hobbybook;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MBookWriteMain extends AppCompatActivity {
    ImageView backBtn, bookSearchIcon, addImgIcon, hashTagIcon, keyIcon, imgSearchBookCover;//sj
    TextView shareBtn;
    EditText bookName,reportName, contents;
    TextView hashtag1,hashtag2,hashtag3,hashtag4;
    Button imgDeleteBtn; //sj(이미지 삭제 버튼)
    private static final int PICK_FROM_ALBUM=1;

    private FirebaseFirestore db;

    int i = 1;

    int img_request_Code = 1;
    int hash_request_Code = 2;

    String bookCoverImg, author;
    String hash1, hash2, hash3, hash4;
    String isbn, description;
    String bTitle;
    String loginId;
    int bookLike = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_report_write_main);

        loginId = MainActivity.loginId;

        imgSearchBookCover = findViewById(R.id.imgSearchBookCover);
        imgDeleteBtn = findViewById(R.id.imgDeleteBtn);

        //파이어베이스
        db = FirebaseFirestore.getInstance();
        final Map<String, Object> saveReport = new HashMap<>();


        bookName = findViewById(R.id.bookName);
        reportName = findViewById(R.id.reportName);
        hashtag1 = findViewById(R.id.hashTag1);
        hashtag2 = findViewById(R.id.hashTag2);
        hashtag3 = findViewById(R.id.hashTag3);
        hashtag4 = findViewById(R.id.hashTag4);
        contents = findViewById(R.id.contents);

        backBtn = findViewById(R.id.backBtn);
        bookSearchIcon = findViewById(R.id.bookSearchIcon);
        addImgIcon = findViewById(R.id.addImgIcon);
        hashTagIcon =findViewById(R.id.hashTagIcon);
        keyIcon = findViewById(R.id.keyIcon);
        shareBtn = findViewById(R.id.shareBtn);


        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bookName.getText() == null || reportName.getText() == null | contents.getText() == null) {
                    Toast.makeText(MBookWriteMain.this, getString(R.string.empotyCkTxt), Toast.LENGTH_LONG).show();
                } else {
                    long now = System.currentTimeMillis();
                    Date formatDate = new Date(now);

                    saveReport.put(getString(R.string.bisbn), isbn);
                    saveReport.put(getString(R.string.brCon), contents.getText().toString());
                    saveReport.put(getString(R.string.brImg), bookCoverImg);
                    saveReport.put(getString(R.string.brTitle), reportName.getText().toString());
                    saveReport.put(getString(R.string.bTitle), bTitle);
                    saveReport.put(getString(R.string.b_auth), author);
                    saveReport.put(getString(R.string.h1), hash1);
                    saveReport.put(getString(R.string.h2), hash2);
                    saveReport.put(getString(R.string.h3), hash3);
                    saveReport.put(getString(R.string.h4), hash4);
                    saveReport.put(getString(R.string.date), formatDate);
                    saveReport.put(getResources().getString(R.string.mid), loginId);
                    saveReport.put(getString(R.string.open), true);
                    saveReport.put(getString(R.string.b_desc), description);
                    saveReport.put(getString(R.string.bl), bookLike);

                    //독후감 번호
                    db.collection(getString(R.string.br)).orderBy(getString(R.string.date), Query.Direction.DESCENDING)
                            .limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    int brNum = doc.getLong(getString(R.string.brn)).intValue() + 1;
                                    saveReport.put(getString(R.string.brn), brNum);
                                }
                            } else {
                                Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError), task.getException());
                            }
                            db.collection(getString(R.string.br)).document().set(saveReport).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataAddError), e);
                                }
                            });
                        }
                    });

                    finish();
                }

            }
        });
        //하단바 아이콘 클릭시
        bookSearchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentA = new Intent(getApplicationContext(),MBookSearch.class);
                startActivityForResult(intentA, img_request_Code);
            }
        });
        addImgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent,PICK_FROM_ALBUM);

            }
        });

        keyIcon.setOnClickListener(new View.OnClickListener() { //독후감 게시물 공개유무
            @Override
            public void onClick(View view) {
                //i변수로 공개윰 판별(1:공개, 0:비공개)
                if(i==1){
                    saveReport.put(getString(R.string.bropen),getString(R.string.tru)); //서버 공개 저장
                    keyIcon.setImageResource(R.drawable.ic_lock_24dp);
                    i=0;
                }else if(i==0){
                    saveReport.put(getString(R.string.bropen),getString(R.string.fals));
                    keyIcon.setImageResource(R.drawable.ic_lock_open_24dp);
                    i=1;

                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        hashTagIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MHashTagSearch.class);
                startActivityForResult(intent, hash_request_Code);
            }
        });

        //책표지 이미지 삭제 버튼(이미지, 도서명 삭제)
        imgDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgSearchBookCover.setImageResource(0);
                bookCoverImg = null;
                imgDeleteBtn.setVisibility(View.INVISIBLE);
                bookName.setText(null);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //검색한 책 표지 불러오기
        if(requestCode == 1 && resultCode == RESULT_OK) {
            bookCoverImg = data.getStringExtra(getString(R.string.img));
            isbn = data.getStringExtra(getString(R.string.ibn));
            author = data.getStringExtra(getString(R.string.auth));
            description = data.getStringExtra(getString(R.string.desc));
            Glide.with(getApplicationContext()).load(bookCoverImg).into(imgSearchBookCover);
            imgDeleteBtn.setVisibility(View.VISIBLE);
            bTitle = data.getStringExtra(getString(R.string.ttle));
            bookName.setText(bTitle);
        }

        //입력한 해시태그 불러오기
        if (requestCode == 2 && resultCode == RESULT_OK) {

            hash1 = data.getStringExtra(getString(R.string.h1));
            hash2 = data.getStringExtra(getString(R.string.h2));
            hash3 = data.getStringExtra(getString(R.string.h3));
            hash4 = data.getStringExtra(getString(R.string.h4));
            hashtag1.setText(hash1);
            hashtag2.setText(hash2);
            hashtag3.setText(hash3);
            hashtag4.setText(hash4);
        }
    }
}

