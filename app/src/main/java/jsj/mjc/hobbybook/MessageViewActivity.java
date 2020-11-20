package jsj.mjc.hobbybook;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
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
    FirebaseFirestore db = FirebaseFirestore.getInstance();;
    String docId, loginId, userId;
    Boolean isSend;
    TextView mView_sender,mView_receiver,mView_sendDate,mView_text;

    String receiver,sender;
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
        docId = intent.getStringExtra("docId");
        loginId = intent.getStringExtra(getResources().getString(R.string.lid));
        userId = intent.getStringExtra(getResources().getString(R.string.uid));
        isSend = intent.getBooleanExtra("isSend", false);

        mView_sendDate.setText(intent.getStringExtra("dateStr"));
        mView_text.setText(intent.getStringExtra("mContent"));

        if (isSend) {
            db.collection("member").document(loginId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        mView_sender.setText(doc.getString(getResources().getString(R.string.name)) + " > ");
                    }
                }
            });
            db.collection("member").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        mView_receiver.setText(doc.getString(getResources().getString(R.string.name)));
                    }
                }
            });
        } else {

            db.collection("member").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        mView_sender.setText(doc.getString(getResources().getString(R.string.name)) + " > ");
                    }
                }
            });
            db.collection("member").document(loginId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        mView_receiver.setText(doc.getString(getResources().getString(R.string.name)));
                    }
                }
            });
        }

        /*db.collection("message").whereEqualTo("inputtime",send).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("TAG", document.getId() + " => " + document.getData());

                        mView_sender.setText(document.get("send_mem").toString() + " > ");
                        mView_receiver.setText(document.get("receive_mem").toString());
                        mView_sendDate.setText(document.get("inputtime").toString());
                        mView_text.setText(document.get("msg_content").toString());


                        receiver = document.get("receive_mem").toString();
                        sender = document.get("send_mem").toString();

                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });*/


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
            case R.id.overflow_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("삭제하시겠습니까?");
                builder.setCancelable(true);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.collection("message").document(docId).delete().addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("e", "message 데이터 삭제 실패" + e);
                            }
                        });
                        finish();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
