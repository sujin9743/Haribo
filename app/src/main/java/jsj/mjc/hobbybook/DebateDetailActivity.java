package jsj.mjc.hobbybook;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DebateDetailActivity extends AppCompatActivity {
    private ArrayList<DebateComment> debateCommentArrayList;
    private DebateCommentAdapter debateCommentAdapter;
    CircleImageView dDetail_writerIv, dDetail_myIv;
    TextView dDetail_writerTv, dDetail_title_tv, dDetail_text_tv, dDetail_date_tv, dDetail_comNum_tv;
    String loginId, writerId, docId;
    int dNum, comNum;
    final SimpleDateFormat dateFormatter = new SimpleDateFormat("y. M. d. hh:mm");
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debate_detail);

        loginId = getIntent().getStringExtra("loginId");
        docId = getIntent().getStringExtra("docId");
        dNum = getIntent().getIntExtra("debateNum", -1);
        writerId = getIntent().getStringExtra("debateWriter");

        storageRef = FirebaseStorage.getInstance().getReference();

        //툴바 설정
        Toolbar userFeed_toolbar = (Toolbar) findViewById(R.id.dDetailToolbar);
        setSupportActionBar(userFeed_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);

        LinearLayout writer = findViewById(R.id.dDetail_writer);

        dDetail_title_tv = findViewById(R.id.dDetail_title_tv);

        if (dNum == -1) {
            dDetail_title_tv.setText("오류가 발생했습니다.");
        } else {
            //토론글 작성자 프로필 사진, 닉네임 클릭 시 해당 사용자의 피드로 이동
            dDetail_writerIv = findViewById(R.id.dDetail_writerIv);
            dDetail_writerTv = findViewById(R.id.dDetail_writerTv);
            writer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DebateDetailActivity.this, UserFeedActivity.class);
                    intent.putExtra("userId", writerId);
                    startActivity(intent);
                }
            });
            //작성자 프로필 사진
            StorageReference imgRef = storageRef.child("profile_img/" + writerId +".jpg");
            imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(DebateDetailActivity.this).load(uri).into(dDetail_writerIv);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("lll", "프로필 사진 로드 실패 : " + exception);
                }
            });
            //작성자 닉네임
            db.collection("member").document(writerId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc.exists()) {
                            dDetail_writerTv.setText(doc.getString("nickname"));
                        }
                    } else {
                        Log.d("lll", "작성자 로드 실패 : " + task.getException());
                    }
                }
            });
            dDetail_text_tv = findViewById(R.id.dDetail_text_tv);
            dDetail_date_tv = findViewById(R.id.dDetail_date_tv);
            dDetail_comNum_tv = findViewById(R.id.dDetail_comNum_tv);
            //토론글 정보
            db.collection("debate").document(docId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc.exists()) {
                            dDetail_title_tv.setText(doc.getString("d_title"));
                            dDetail_text_tv.setText(doc.getString("d_content"));
                            Timestamp timestamp = (Timestamp) doc.getData().get("inputtime");
                            String dateStr = dateFormatter.format(timestamp.toDate());
                            dDetail_date_tv.setText(dateStr);
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("lll", "게시물 로드 실패 : " + e);
                }
            });


            //본인 프로필 사진
            dDetail_myIv = findViewById(R.id.dDetail_myIv);
            imgRef = storageRef.child("profile_img/" + loginId +".jpg");
            imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(DebateDetailActivity.this).load(uri).into(dDetail_myIv);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("lll", "프로필 사진 로드 실패 : " + exception);
                }
            });

            //토론글에 달린 댓글 목록 구현
            RecyclerView recyclerView = findViewById(R.id.dDetail_comment_RV);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);

            debateCommentArrayList = new ArrayList<>();
            debateCommentAdapter = new DebateCommentAdapter(debateCommentArrayList);
            recyclerView.setAdapter(debateCommentAdapter);

            DividerItemDecoration gDividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
            recyclerView.addItemDecoration(gDividerItemDecoration);

            db.collection("debate_com").whereEqualTo("d_num", dNum).orderBy("dc_bundle").orderBy("dc_num").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Timestamp timestamp = (Timestamp) doc.getData().get("inputtime");
                            String dateStr = dateFormatter.format(timestamp.toDate());
                            DebateComment data = new DebateComment(doc.getLong("dc_num").intValue(), doc.getLong("receive_com").intValue(), doc.getString("dc_content"),
                                    doc.getString("mem_id"), dateStr);
                            debateCommentArrayList.add(data);
                            comNum++;
                        }
                    } else {
                        Log.d("lll", "댓글 로드 오류 : ", task.getException());
                    }
                    dDetail_comNum_tv.setText(String.valueOf(comNum));
                    debateCommentAdapter.notifyDataSetChanged();
                }
            });
        }

        //ImageView dDetail_iv = findViewById(R.id.dDetail_iv);

        //if (str.equals("1"))
            //dDetail_iv.setVisibility(View.GONE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.userfeedtoolbar, menu);
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
                ReportDialog reportDialog = new ReportDialog(DebateDetailActivity.this);
                reportDialog.show();
                break;
            case R.id.overflow_block:
                MCutOffDialog mCutOffDialog = new MCutOffDialog(DebateDetailActivity.this);
                mCutOffDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
