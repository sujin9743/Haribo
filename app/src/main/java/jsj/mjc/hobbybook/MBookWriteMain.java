package jsj.mjc.hobbybook;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MBookWriteMain extends AppCompatActivity {
    ImageView backBtn, bookSearchIcon, addImgIcon, hashIcon, keyIcon;
    TextView shareBtn;

    int i = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_report_write_main);

        backBtn = findViewById(R.id.backBtn);
        bookSearchIcon = findViewById(R.id.bookSearchIcon);
        addImgIcon = findViewById(R.id.addImgIcon);
        hashIcon =findViewById(R.id.hashTag);
        keyIcon = findViewById(R.id.keyIcon);
        shareBtn = findViewById(R.id.shareBtn);

        //하단바 아이콘 클릭시
        bookSearchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        addImgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        hashIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        keyIcon.setOnClickListener(new View.OnClickListener() { //독후감 게시물 공개유무
            @Override
            public void onClick(View view) {
                //i변수로 공개윰 판별(1:공개, 0:비공개)
              if(i==1){     //공개 상태이므로 비공개로 전환

              }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

