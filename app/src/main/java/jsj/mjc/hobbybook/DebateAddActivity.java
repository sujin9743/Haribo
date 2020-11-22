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
                    Toast.makeText(DebateAddActivity.this, getResources().getString(R.string.empotyCkTxt), Toast.LENGTH_SHORT).show();
                else {
                    post.put(getResources().getString(R.string.dt), strTitle);
                    post.put(getResources().getString(R.string.dc), strText);
                    post.put(getResources().getString(R.string.mid), loginId);
                    post.put(getResources().getString(R.string.isDel), false);
                    post.put(getResources().getString(R.string.time), date);
                    db.collection(getResources().getString(R.string.dbt)).orderBy(getResources().getString(R.string.time), Query.Direction.DESCENDING).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    int dNum = doc.getLong(getResources().getString(R.string.dn)).intValue() + 1;
                                    post.put(getResources().getString(R.string.dn), dNum);
                                }
                            } else {
                                Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError), task.getException());
                            }
                            db.collection(getResources().getString(R.string.dbt)).add(post).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataAddError), e);
                                }
                            });
                        }
                    });
                    Toast.makeText(DebateAddActivity.this, getResources().getString(R.string.uploadOk), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
