package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ResetPwActivity extends AppCompatActivity {
    ImageButton resetPw_backBtn;
    Button resetPw_Btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        //툴바 뒤로가기
        resetPw_backBtn = findViewById(R.id.resetPw_backBtn);
        resetPw_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //확인 클릭 시
        resetPw_Btn = findViewById(R.id.resetPw_Btn);
        resetPw_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
