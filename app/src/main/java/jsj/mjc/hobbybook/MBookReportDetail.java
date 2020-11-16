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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;
//조민주

public class MBookReportDetail extends AppCompatActivity {
    ImageView backBtn,heartIcon;
    TextView hashTag1,hashTag2,hashTag3,hashTag4,reportTitle;
    TextView profileText,report_bookName, report_bookMaker;
    TextView report_content;
    ImageView bookImgPage;
    LinearLayout forBookInfo,forReview,porfileLayout;
    CircleImageView profileImg;
    FirebaseFirestore db;
    StorageReference storageRef;
    int i =0;
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

        profileImg = findViewById(R.id.profileImg);
        profileText = findViewById(R.id.profileText);
        report_bookMaker = findViewById(R.id.report_bookMaker);
        report_bookName = findViewById(R.id.report_bookName);

        report_content = findViewById(R.id.content);

        // TODO: 2020-11-17 whereEqualTo 2번째 인자 동적으로 바꿔야됨. 지금은 고정값임
        db = FirebaseFirestore.getInstance();
        db.collection("bookre").whereEqualTo("br_num",1)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG냐냐냐냐냐냐냐ㅑ냐", document.getId() + " => " + document.getData());

                        profileText.setText(document.get("mem_id").toString());

                        String h1,h2,h3,h4;
                        h1 = document.getString("has1");
                        h2 = document.getString("has2");
                        h3 = document.getString("has3");
                        h4 = document.getString("has4");
                        hashTag1.setText(h1);
                        hashTag2.setText(h2);
                        hashTag3.setText(h3);
                        hashTag4.setText(h4);

                        String content, bMaker, bName;
                        content = document.getString("br_content");
                        bMaker = document.getString("book_author");
                        bName = document.getString("book_title");
                        report_content.setText(content);
                        report_bookMaker.setText(bMaker);
                        report_bookName.setText(bName);
                        reportTitle.setText(document.get("br_title").toString());

                        String bookImg;
                        bookImg = document.get("br_img").toString();
                        Glide.with(getApplicationContext()).load(bookImg).into(bookImgPage);

                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });

        String loginId = "test";
        storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imgRef = storageRef.child("profile_img/" + loginId +".png");
        imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(MBookReportDetail.this).load(uri).into(profileImg);
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
                Intent i = new Intent(getApplicationContext(),UserFeedActivity.class);
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
             Intent i = new Intent(getApplicationContext(),MBookInfoDetail.class);
             startActivity(i);
         }
     });




    heartIcon.setImageResource(R.drawable.heart_line);
    heartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  //0이면 빈하트
                if (i==0){
                    heartIcon.setImageResource(R.drawable.heart_full);
                    i=1;
                }else{
                    heartIcon.setImageResource(R.drawable.heart_line);
                    i=0;
                }
            }
        });


        //리뷰 페이지으로 이동
     forReview = findViewById(R.id.forReview);
     forReview.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent i = new Intent(getApplicationContext(), MBookComment.class);
             startActivity(i);
         }
     });
    }

}
