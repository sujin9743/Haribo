package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar; //Toolbar -> androidx 사용하는 경우

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FindIdActivity extends AppCompatActivity {

    Spinner email_spinner;
    ImageButton findId_backBtn;
    Button findId_Btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.id_find);

        //이메일 Spinner 설정
        email_spinner = findViewById(R.id.email_spinner); //이메일 spinner
        ArrayAdapter<String> email_spinner_adapter = new ArrayAdapter<>(this,
                R.layout.spinner_text, (String[])getResources().getStringArray(R.array.email));
        email_spinner_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        email_spinner.setAdapter(email_spinner_adapter);

        //툴바 뒤로가기
        findId_backBtn = findViewById(R.id.findId_backBtn);
        findId_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //확인 버튼 클릭 시
        findId_Btn = findViewById(R.id.findId_Btn);
        findId_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindIdResultActivity.class);
                startActivity(intent);
            }
        });
    }
}
