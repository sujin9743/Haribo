package jsj.mjc.hobbybook;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FindIdResultActivity extends AppCompatActivity {
    ImageButton findId_result_backBtn;
    Button findPw_Btn, login_Btn;
    TextView findId;
    String id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_id_result);

        findId = findViewById(R.id.findID_Id);
        id = getIntent().getStringExtra("id");

        //툴바 뒤로가기
        findId_result_backBtn = findViewById(R.id.findId_result_backBtn);
        findId_result_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //아이디 찾기 결과 출력
        findId.setText(id);

        //비밀번호 찾기 클릭 시
        findPw_Btn = findViewById(R.id.findPw_Btn);
        findPw_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindPwActivity.class);
                startActivity(intent);
            }
        });
        //로그인하기 클릭 시
        login_Btn = findViewById(R.id.login_Btn);
        login_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }

        });
    }
}
