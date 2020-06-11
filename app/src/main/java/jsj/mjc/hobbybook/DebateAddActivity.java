package jsj.mjc.hobbybook;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DebateAddActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debate_add);

        ImageButton dAdd_back_btn = findViewById(R.id.dAdd_back_btn);
        ImageButton dAdd_add_btn = findViewById(R.id.dAdd_add_btn);
        final EditText dAdd_title_et = findViewById(R.id.dAdd_title_et);
        final EditText dAtt_text_et = findViewById(R.id.dAdd_text_et);

        dAdd_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dAdd_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strTitle = dAdd_title_et.getText().toString();
                String strText = dAtt_text_et.getText().toString();
                if (strTitle.getBytes().length <= 0 || strText.getBytes().length <= 0)
                    Toast.makeText(DebateAddActivity.this, "빈칸을 확인해 주세요.", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(DebateAddActivity.this, "게시물이 등록됐습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
