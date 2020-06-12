package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FindPwActivity extends AppCompatActivity {
    Spinner pw_spinner;
    ImageButton findPw_backBtn;
    Button findPw_Btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_password);

        //비밀번호 확인 Spinner 설정
        pw_spinner = findViewById(R.id.pwQ_spinner); //비밀번호 확인 질문 spinner
        //spinner Text설정 & 항목 추가
        ArrayAdapter<String> pw_spinner_adapter = new ArrayAdapter<>(this,
                R.layout.spinner_text, (String[])getResources().getStringArray(R.array.question));
        //spinner 항목 추가 방식
        pw_spinner_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        pw_spinner.setAdapter(pw_spinner_adapter);

        //툴바 뒤로가기
        findPw_backBtn = findViewById(R.id.findPw_backBtn);
        findPw_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //확인 클릭 시
        findPw_Btn = findViewById(R.id.findPw_Btn);
        findPw_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResetPwActivity.class);
                startActivity(intent);
            }
        });
    }
}
