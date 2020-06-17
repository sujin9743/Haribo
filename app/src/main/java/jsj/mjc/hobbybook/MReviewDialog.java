package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MReviewDialog extends AppCompatActivity {

    ImageView backBtn;
    TextView upload;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_review_dialog);

        backBtn = findViewById(R.id.backBtn);
        upload = findViewById(R.id.upload);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MReviewDialog.this, MBookInfoDetail.class);
                startActivity(i);
            }
        });
    }
}
