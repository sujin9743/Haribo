package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MBookWriteMain extends AppCompatActivity {
    ImageView backBtn, bookSearchIcon, addImgIcon, hashTagIcon, keyIcon;
    TextView shareBtn;
    private static final int PICK_FROM_ALBUM=1;

    int i = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_report_write_main);

        backBtn = findViewById(R.id.backBtn);
        bookSearchIcon = findViewById(R.id.bookSearchIcon);
        addImgIcon = findViewById(R.id.addImgIcon);
        hashTagIcon =findViewById(R.id.hashTagIcon);
        keyIcon = findViewById(R.id.keyIcon);
        shareBtn = findViewById(R.id.shareBtn);

        //하단바 아이콘 클릭시
        bookSearchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MBookSearch.class);
                startActivity(i);
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
        hashTagIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MHashTagSearch.class);
                startActivity(i);
            }
        });
        keyIcon.setOnClickListener(new View.OnClickListener() { //독후감 게시물 공개유무
            @Override
            public void onClick(View view) {
                //i변수로 공개윰 판별(1:공개, 0:비공개)
              if(i==1){     //공개 상태이므로 비공개로 전환
                  keyIcon.setImageResource(R.drawable.ic_lock_24dp);
                  i=0;
              }else if(i==0){
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

    }

}

