package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MessageViewActivity extends AppCompatActivity {
    FirebaseFirestore db;
    String send,receive;
    TextView mView_sender,mView_receiver,mView_sendDate,mView_text;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_view);

        //툴바 설정
        Toolbar userFeed_toolbar = (Toolbar) findViewById(R.id.mView_toolbar);
        setSupportActionBar(userFeed_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);

        mView_sender = findViewById(R.id.mView_sender);
        mView_receiver = findViewById(R.id.mView_receiver);
        mView_sendDate = findViewById(R.id.mView_sendDate);
        mView_text = findViewById(R.id.mView_text);

        Intent intent = getIntent();
        send = intent.getStringExtra("send");
        Log.d(send,"ㅇㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎ");

        //db를 시작해볼까나
        db = FirebaseFirestore.getInstance();

        db.collection("message").whereEqualTo("inputtime",send).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());
                        mView_sender.setText(document.get("send_mem").toString() + " > ");
                        mView_receiver.setText(document.get("receive_mem").toString());
                        mView_sendDate.setText(document.get("inputtime").toString());
                        mView_text.setText(document.get("msg_content").toString());

                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.messageoverflow, menu);

        return true;
    }

    //툴바 오버플로우 메뉴 이벤트 처리
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.overflow_report:
                ReportDialog reportDialog = new ReportDialog(MessageViewActivity.this);
                reportDialog.show();
                break;
            case R.id.overflow_block:
                String blockUser;
                blockUser = mView_receiver.getText().toString();
                Toast.makeText(getApplicationContext(),blockUser+"님을 차단했습니다.",Toast.LENGTH_SHORT).show();
            case R.id.overflow_delete:
                // TODO: 2020-11-18 샘플 데이터는 소중하니까.. 마지막에 할게요
        }
        return super.onOptionsItemSelected(item);
    }
}
