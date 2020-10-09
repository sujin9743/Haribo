package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import de.hdodenhof.circleimageview.CircleImageView;
//조민주

public class MBookReportDetail extends AppCompatActivity {
    ImageView backBtn,heartIcon;
    TextView hashTag,hashTag2,reportTitle;
    ViewPager bookImgPage;
    LinearLayout forBookInfo,forReview,porfileLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_report_detail);

        backBtn = findViewById(R.id.backBtn);
        heartIcon = findViewById(R.id.heartIcon);
        porfileLayout = findViewById(R.id.profileLayout);
        hashTag = findViewById(R.id.hashTag);
        hashTag2 = findViewById(R.id.hashTag2);
        reportTitle = findViewById(R.id.reportTitle);
        bookImgPage = findViewById(R.id.bookImgPage);
        forBookInfo = findViewById(R.id.forBookInfo);
        forReview = findViewById(R.id.forReview);


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
     heartIcon.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             heartIcon.setImageResource(R.drawable.heart_line);
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
