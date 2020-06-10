package jsj.mjc.hobbybook;

import android.os.Bundle;
import android.view.View;
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

        dAdd_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dAdd_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DebateAddActivity.this, "게시물이 등록됐습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
