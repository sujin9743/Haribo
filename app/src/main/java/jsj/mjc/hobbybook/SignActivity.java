package jsj.mjc.hobbybook;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar; //Toolbar -> androidx 사용하는 경우
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignActivity extends AppCompatActivity {
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign);

        Toolbar toolbar = findViewById(R.id.sign_toolbar);
        setSupportActionBar(toolbar); //Toolbar 적용
        getSupportActionBar().setTitle("회원가입"); //Toolbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //toolbar 뒤로가기 아이콘
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp); //뒤로가기 아이콘 지정
    }
}
