package jsj.mjc.hobbybook;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;


public class MBookCommentItem extends AppCompatActivity {
    CircleImageView profileImg;
    TextView profileText;
    TextView date, reviewText, delete;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_book_comment_item);

        profileImg = findViewById(R.id.profileImg);
        profileText =findViewById(R.id.profileText);
        date =findViewById(R.id.date);
        reviewText = findViewById(R.id.reviewText);

    }


}
