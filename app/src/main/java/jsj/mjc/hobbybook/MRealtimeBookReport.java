package jsj.mjc.hobbybook;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import de.hdodenhof.circleimageview.CircleImageView;

public class MRealtimeBookReport extends AppCompatActivity {


    ImageView backBtn, serchBtn,Heart,addBookReport;
    CircleImageView profileImg;
    TextView profileText, bookName, bookCreator, likeCount, commentCnt;
    ViewPager bookImgPage;
    LinearLayout viewPageLayout, commentLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realtime_book_report);

        backBtn = findViewById(R.id.backBtn);
        serchBtn = findViewById(R.id.serchBtn);
        profileImg = findViewById(R.id.profileImg);
        profileText = findViewById(R.id.profileText);
        addBookReport = findViewById(R.id.addBookReport);
        bookCreator = findViewById(R.id.bookCreator);
        bookName = findViewById(R.id.bookName);
        viewPageLayout = findViewById(R.id.viewPageLayout);
        commentLayout  = findViewById(R.id.commentLayout);


        bookName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), MBookReportDetail.class);
                startActivity(i);
            }
        });
        bookCreator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), MBookReportDetail.class);
                startActivity(i);
            }
});
      viewPageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), MBookReportDetail.class);
                startActivity(i);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        serchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MRealtimeBookReport.this, MRealtimeBookReportSearch.class);
                startActivityForResult(intent,1);
            }
        });
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),UserFeedActivity.class);
                startActivity(i);
            }
        });
        profileText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),UserFeedActivity.class);
                startActivity(i);
            }
        });
        addBookReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),BookReportWrite.class);
                startActivity(i);
            }
        });
        commentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MBookComment.class);
                startActivity(i);
            }
        });

        Heart = findViewById(R.id.Heart);
        Heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }

        });
    }

}
