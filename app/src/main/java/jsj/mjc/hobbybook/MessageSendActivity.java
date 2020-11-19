package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//cho 수정
public class MessageSendActivity extends AppCompatActivity {

    FirebaseFirestore db;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_send);


        ImageButton backBtn = findViewById(R.id.backBtn);
        ImageButton sendBtn = findViewById(R.id.okBtn);
        EditText sendName = findViewById(R.id.sendName);
        final EditText mSend_et = findViewById(R.id.mSend_et);

        // UserFeedActivity 에서 userID 받아옴
        Intent i = getIntent();
        final String receive_user = i.getStringExtra(getResources().getString(R.string.uid));
        sendName.setText(receive_user+"에게");

        db = FirebaseFirestore.getInstance();
        final Map<String, Object> saveMessage = new HashMap<>();

        //파이어스토어 message컬렉션의 msg_num 데이터 갖고와서 카운트하기
        /*임시 데이터*/ final int msg_num = 0;


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //쪽지 보낸 시간
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy.MM.dd. HH:mm");
                String formatDate = sdfNow.format(date);

                saveMessage.put("inputtime",formatDate );

                saveMessage.put("msg_content", mSend_et.getText().toString());

                saveMessage.put("msg_num", msg_num);
                saveMessage.put("receive_mem", receive_user);
                saveMessage.put("receive_deleted", "false");
                saveMessage.put("seen", "true");
                saveMessage.put("send_deleted", "false");
                saveMessage.put("send_mem", "test");

                // TODO: 2020-11-15 document() 괄호안에 고정 문자 수정해야됨   
                db.collection("message").document("test4").set(saveMessage).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
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
