package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MessageSendActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String loginId, userId;
    TextView sendName;
    EditText mSend_et;
    Map<String, Object> message = new HashMap<>();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_send);


        ImageButton backBtn = findViewById(R.id.backBtn);
        ImageButton sendBtn = findViewById(R.id.okBtn);
        sendName = findViewById(R.id.sendName);
        mSend_et = findViewById(R.id.mSend_et);

        loginId = getIntent().getStringExtra(getResources().getString(R.string.lid));
        userId = getIntent().getStringExtra(getResources().getString(R.string.uid));

        db.collection("member").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    sendName.setText(doc.getString(getResources().getString(R.string.name)) + " 님 에게");
                }
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mContent = mSend_et.getText().toString();
                if (!mContent.equals(getResources().getString(R.string.empty))) {
                    message.put("send_mem", loginId);
                    message.put("receive_mem", userId);
                    message.put("msg_content", mContent);
                    message.put("deleted", false);
                    message.put("inputtime", new Date());

                    message.put("seen", true);
                    message.put(getResources().getString(R.string.mid), loginId);
                    db.collection("message").add(message).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("e", "message 데이터 등록 실패 : " + e);
                        }
                    });

                    message.put("seen", false);
                    message.put(getResources().getString(R.string.mid), userId);
                    db.collection("message").add(message).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("e", "message 데이터 등록 실패 : " + e);
                        }
                    });;
                }
                finish();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
