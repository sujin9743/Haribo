package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import androidx.appcompat.app.AppCompatActivity;

public class MBookInfoDetail extends AppCompatActivity {

    ImageView backBtn, bookImage;
    LinearLayout letsGoReport;
    TextView reviewBtn, bookName;

    TextView editor, bookInfo;
    String getBookImage, getBookTitle,getBookAuthor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_info_detail);

        bookImage = findViewById(R.id.bookImage);
        bookName = findViewById(R.id.bookName);

        editor = findViewById(R.id.editor);
        bookInfo = findViewById(R.id.bookInfo);

        getBookImage = getIntent().getStringExtra("image");
        getBookTitle = getIntent().getStringExtra("title");
        getBookAuthor = getIntent().getStringExtra("author");

        Glide.with(getApplicationContext()).load(getBookImage).into(bookImage);
        bookName.setText(getBookTitle);

        editor.setText(getBookAuthor);
        //bookInfo 수진짱 책소개(정보) api 연결해쥬뗌므~~~

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

        reviewBtn = findViewById(R.id.reviewBtn);
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MReviewDialog mReviewDialog = new MReviewDialog(MBookInfoDetail.this);
                mReviewDialog.show();



            }
        });

    }
}
