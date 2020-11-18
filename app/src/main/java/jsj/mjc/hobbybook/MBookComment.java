package jsj.mjc.hobbybook;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class MBookComment extends AppCompatActivity {

    ImageView backBtn;

    CircleImageView profileImg;
    TextView profileText;
    TextView date, reviewText, delete;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_book_comment);

        profileImg = findViewById(R.id.profileImg);
        profileText =findViewById(R.id.profileText);
        date =findViewById(R.id.date);
        reviewText = findViewById(R.id.reviewText);
        delete = findViewById(R.id.delete);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
