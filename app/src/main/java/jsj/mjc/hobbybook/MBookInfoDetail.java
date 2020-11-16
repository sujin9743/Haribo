package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;

public class MBookInfoDetail extends AppCompatActivity {

    ImageView backBtn, bookImage;
    LinearLayout letsGoReport;
    TextView reviewBtn, bookName;

    String getBookImage, getBookTitle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_info_detail);

        bookImage = findViewById(R.id.bookImage);
        bookName = findViewById(R.id.bookName);

        getBookImage = getIntent().getStringExtra("image");
        getBookTitle = getIntent().getStringExtra("title");

        Glide.with(getApplicationContext()).load(getBookImage).into(bookImage);
        bookName.setText(getBookTitle);

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
