package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;


import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.appcompat.widget.Toolbar; //Toolbar -> androidx 사용하는 경우
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignActivity extends AppCompatActivity {
    Spinner pw_spinner, email_spinner;
    ImageButton sign_backBtn;
    Button sign_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign);

        //비밀번호 확인 Spinner 설정
        pw_spinner = findViewById(R.id.pwQ_spinner); //비밀번호 확인 질문 spinner
        //spinner Text설정 & 항목 추가
        ArrayAdapter<String> pw_spinner_adapter = new ArrayAdapter<>(this,
                R.layout.spinner_text, (String[])getResources().getStringArray(R.array.question));
        //spinner 항목 추가 방식
        pw_spinner_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        pw_spinner.setAdapter(pw_spinner_adapter);

        //이메일 Spinner 설정
        email_spinner = findViewById(R.id.email_spinner); //이메일 spinner
        ArrayAdapter<String> email_spinner_adapter = new ArrayAdapter<>(this,
                R.layout.spinner_text, (String[])getResources().getStringArray(R.array.email));
        email_spinner_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        email_spinner.setAdapter(email_spinner_adapter);

        //툴바 뒤로가기
        sign_backBtn = findViewById(R.id.sign_backBtn);
        sign_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //다음 버튼 클릭 시
        sign_btn = findViewById(R.id.sign_btn);
        sign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectGenreActivity.class);
                startActivity(intent);
            }
        });
    }
}
