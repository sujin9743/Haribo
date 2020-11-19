package jsj.mjc.hobbybook;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DebateAddActivity extends AppCompatActivity {
    String loginId;
    Map<String, Object> post = new HashMap<>();
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debate_add);

        loginId = getIntent().getStringExtra(getResources().getString(R.string.lid));

        ImageButton dAdd_back_btn = findViewById(R.id.dAdd_back_btn);
        TextView dAdd_add_btn = findViewById(R.id.dAdd_add_btn);
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
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                if (strTitle.getBytes().length <= 0 || strText.getBytes().length <= 0)
                    Toast.makeText(DebateAddActivity.this, "빈칸을 확인해 주세요.", Toast.LENGTH_SHORT).show();
                else {
                    post.put("d_title", strTitle);
                    post.put("d_content", strText);
                    post.put(getResources().getString(R.string.mid), loginId);
                    post.put("deleted", false);
                    post.put("inputtime", date);
                    db.collection("debate").orderBy("inputtime", Query.Direction.DESCENDING).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    int dNum = doc.getLong("d_num").intValue() + 1;
                                    post.put("d_num", dNum);
                                }
                            } else {
                                Log.d("lll", "토론글 로드 오류 : ", task.getException());
                            }
                            db.collection("debate").add(post).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("e", "user 데이터 등록 실패 : ", e);
                                }
                            });
                        }
                    });
                    Toast.makeText(DebateAddActivity.this, "게시물이 등록됐습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
