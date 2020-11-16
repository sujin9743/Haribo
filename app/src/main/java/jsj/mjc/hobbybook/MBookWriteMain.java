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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
    final static int CODE=1;    //해시태그 intent 구분

    String bookCoverImg; //sj
    String hash1, hash2, hash3, hash4;
    String isbn;
   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_report_write_main);

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

                //저장 시간 구함
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy.MM.dd. HH:mm");
                String formatDate = sdfNow.format(date);

                saveReport.put("bookisbn",isbn);
                saveReport.put("br_content",contents.getText().toString());
                //saveReport.put("br_num",brNum); 책넘버 어떤거 받는지 몰라서 일단 주석처리
                saveReport.put("br_img", bookCoverImg);
                saveReport.put("br_title",reportName.getText().toString());
                saveReport.put("has1", hash1);
                saveReport.put("has2", hash2);
                saveReport.put("has3", hash3);
                saveReport.put("has4", hash4);
                saveReport.put("date", formatDate);




                //입력한 모든 데이터 서버에 저장
                db.collection("bookre").document("test").set(saveReport).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                finish();

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
                //Log.d("태그","메시지");

            }
        });





        keyIcon.setOnClickListener(new View.OnClickListener() { //독후감 게시물 공개유무
            @Override
            public void onClick(View view) {
                //i변수로 공개윰 판별(1:공개, 0:비공개)
              if(i==1){
                  saveReport.put("br_open","true"); //서버 공개 저장
                  keyIcon.setImageResource(R.drawable.ic_lock_24dp);
                  i=0;
              }else if(i==0){
                  saveReport.put("br_open","false");
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

        //sj
        imgDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgSearchBookCover.setImageResource(0);
                bookCoverImg = null;
                imgDeleteBtn.setVisibility(View.INVISIBLE);

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //수진 (검색한 책 표지 불러오기)
        if(requestCode == 1 && resultCode == RESULT_OK) {
            bookCoverImg = data.getStringExtra("image");
            isbn = data.getStringExtra("isbn");
            Glide.with(getApplicationContext()).load(bookCoverImg).into(imgSearchBookCover);
            imgDeleteBtn.setVisibility(View.VISIBLE);
            //cho 도서명
            String bTitle = data.getStringExtra("br_title");
            bookName.setText(bTitle);
        }

        //입력한 해시태그 불러오기
        if (requestCode == 2 && resultCode == RESULT_OK) {

            hash1 = data.getStringExtra("hash1");
            hash2 = data.getStringExtra("hash2");
            hash3 = data.getStringExtra("hash3");
            hash4 = data.getStringExtra("hash4");

            Log.d("TAG", "해시태그: " + hash1 + hash2 + hash3 + hash4);

            hashtag1.setText(hash1);
            hashtag2.setText(hash2);
            hashtag3.setText(hash3);
            hashtag4.setText(hash4);
        }
    }
}

