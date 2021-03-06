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
        docId = intent.getStringExtra(getString(R.string.did));
        loginId = intent.getStringExtra(getResources().getString(R.string.lid));
        userId = intent.getStringExtra(getResources().getString(R.string.uid));
        isSend = intent.getBooleanExtra(getString(R.string.isSend), false);

        mView_sendDate.setText(intent.getStringExtra(getString(R.string.dstr)));
        mView_text.setText(intent.getStringExtra(getString(R.string.mCon)));

        if (isSend) {
            db.collection(getString(R.string.mem)).document(loginId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        mView_sender.setText(doc.getString(getResources().getString(R.string.name)) + getString(R.string.right));
                    }
                }
            });
            db.collection(getString(R.string.mem)).document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        mView_receiver.setText(doc.getString(getResources().getString(R.string.name)));
                    }
                }
            });
        } else {

            db.collection(getString(R.string.mem)).document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        mView_sender.setText(doc.getString(getResources().getString(R.string.name)) + getString(R.string.right));
                    }
                }
            });
            db.collection(getString(R.string.mem)).document(loginId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        mView_receiver.setText(doc.getString(getResources().getString(R.string.name)));
                    }
                }
            });
        }
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
                builder.setMessage(R.string.wannaDel);
                builder.setCancelable(true);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.collection(getString(R.string.msg)).document(docId).delete().addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataDelError) + e);
                            }
                        });
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
