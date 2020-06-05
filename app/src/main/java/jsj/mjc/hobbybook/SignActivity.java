package jsj.mjc.hobbybook;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import java.util.ArrayList;

import androidx.appcompat.widget.Toolbar; //Toolbar -> androidx 사용하는 경우
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignActivity extends AppCompatActivity {
    Spinner pw_spinner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign);

        //Todo 1.Toolbar 설정
        Toolbar toolbar = findViewById(R.id.sign_toolbar); //Toolbar
        setSupportActionBar(toolbar); //Toolbar 적용
        getSupportActionBar().setTitle("회원가입"); //Toolbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //toolbar 뒤로가기 아이콘
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp); //뒤로가기 아이콘 지정

        //Todo 2.비밀번호 확인 Spinner 설정
        pw_spinner = findViewById(R.id.pwQ_spinner); //비밀번호 확인 질문 spinner
        //spinner Text설정 & 항목 추가
        ArrayAdapter<String> pw_spinner_adapter = new ArrayAdapter<>(this,
                R.layout.spinner_text, (String[])getResources().getStringArray(R.array.question));
        //spinner 항목 추가 방식
        pw_spinner_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        pw_spinner.setAdapter(pw_spinner_adapter);
    }
}
