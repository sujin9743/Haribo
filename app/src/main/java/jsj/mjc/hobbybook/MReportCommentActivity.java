package jsj.mjc.hobbybook;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;



public class MReportCommentActivity extends AppCompatActivity{
    private ArrayList<MReportComment> commentArrayList;
    public MReportCommentAdapter commentAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final SimpleDateFormat dateFormatter = new SimpleDateFormat("y. M. d. hh:mm");
    CircleImageView myIv;
    EditText comment_edt;
    ImageButton comment_btn;
    String writerId;
    public String loginId, docId;
    public static MReportCommentActivity mReportCommentActivity;
    int brNum, brcNum = 1;
    MReportComment data;
    StorageReference storageRef;
    public int c_bundle = 0, recieve_com = 0;
    Map<String, Object> comment = new HashMap<>(), notice = new HashMap<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_book_comment);
        loginId = getIntent().getStringExtra(getResources().getString(R.string.lid));
        docId = getIntent().getStringExtra("docId");
        brNum = getIntent().getIntExtra("brNum", -1);
        writerId = getIntent().getStringExtra("brWriter");

        mReportCommentActivity = this;
        storageRef = FirebaseStorage.getInstance().getReference();

        //툴바 설정
        Toolbar userFeed_toolbar = (Toolbar) findViewById(R.id.commentToolbar);
        setSupportActionBar(userFeed_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        getSupportActionBar().setTitle("댓글");

        if (brNum == -1) {
            getSupportActionBar().setTitle("오류가 발생했습니다.");
        } else {
            //본인 프로필 사진
            myIv = findViewById(R.id.comment_myIv);
            StorageReference imgRef = storageRef.child("profile_img/" + loginId +".jpg");
            imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(MReportCommentActivity.this).load(uri).into(myIv);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("lll", "프로필 사진 로드 실패 : " + exception);
                }
            });

            //토론글에 달린 댓글 목록 구현
            RecyclerView recyclerView = findViewById(R.id.reportCommentView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);

            commentArrayList = new ArrayList<>();
            commentAdapter = new MReportCommentAdapter(commentArrayList);
            recyclerView.setAdapter(commentAdapter);

            DividerItemDecoration gDividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
            recyclerView.addItemDecoration(gDividerItemDecoration);

            setCommentList();

            //댓글 작성
            comment_edt = findViewById(R.id.comment_edt);
            comment_btn = findViewById(R.id.comment_btn);
            comment_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String comCon = comment_edt.getText().toString();
                    if (!comCon.equals(getResources().getString(R.string.empty))) {
                        comment.put("br_num", brNum);
                        comment.put("brc_num", brcNum);
                        comment.put("c_bundle", c_bundle);
                        comment.put("brc_content", comCon);
                        comment.put("deleted", false);
                        comment.put("inputtime", new Date());
                        comment.put(getResources().getString(R.string.mid), loginId);
                        comment.put("receive_com", recieve_com);
                        db.collection("bookre_com").whereEqualTo("br_num", brNum)
                                .orderBy("inputtime", Query.Direction.DESCENDING).limit(1)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                        brcNum = doc.getLong("brc_num").intValue() + 1;
                                        comment.put("brc_num", brcNum);
                                        if (c_bundle == 0)
                                            comment.put("c_bundle", brcNum);
                                    }
                                } else {
                                    Log.d("lll", "댓글 로드 오류 : ", task.getException());
                                }
                                db.collection("bookre_com").add(comment).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("e", "bookre_com 데이터 등록 실패 : ", e);
                                    }
                                });
                                if (recieve_com == 0) {
                                    if (!loginId.equals(writerId)) {
                                        notice.put("docId", docId);
                                        notice.put("send_mem", loginId);
                                        notice.put(getResources().getString(R.string.mid), writerId);
                                        notice.put("type", 4);
                                        notice.put("inputtime", new Date());
                                        db.collection("notice").add(notice).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("e", "notice 데이터 등록 실패 : ", e);
                                            }
                                        });
                                    }
                                } else {
                                    if (!loginId.equals(commentArrayList.get(commentAdapter.selected).getCWriter())) {
                                        notice.put("docId", docId);
                                        notice.put("send_mem", loginId);
                                        notice.put(getResources().getString(R.string.mid), commentArrayList.get(commentAdapter.selected).getCWriter());
                                        notice.put("type", 6);
                                        notice.put("inputtime", new Date());
                                        db.collection("notice").add(notice).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("e", "notice 데이터 등록 실패 : ", e);
                                            }
                                        });
                                    }
                                }
                                commentAdapter.selected = -1;
                                recieve_com = 0;
                                setCommentList();
                            }
                        });
                        comment_edt.setText(getResources().getString(R.string.empty));
                    }
                }
            });
        }
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
        }
        return super.onOptionsItemSelected(item);
    }

    public void setCommentList() {
        commentArrayList.clear();
        db.collection("bookre_com").whereEqualTo("br_num", brNum).orderBy("c_bundle").orderBy("brc_num").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        if (doc.getBoolean("deleted")) {
                            MReportComment data = new MReportComment(getResources().getString(R.string.empty), 0, doc.getLong("receive_com").intValue(), 0,
                                    "삭제된 댓글입니다.", getResources().getString(R.string.nonamed), getResources().getString(R.string.empty));
                            commentArrayList.add(data);
                        } else {
                            Timestamp timestamp = (Timestamp) doc.getData().get("inputtime");
                            String dateStr = dateFormatter.format(timestamp.toDate());
                            MReportComment data = new MReportComment(doc.getId(), doc.getLong("brc_num").intValue(), doc.getLong("receive_com").intValue(), doc.getLong("c_bundle").intValue(), doc.getString("brc_content"),
                                    doc.getString(getResources().getString(R.string.mid)), dateStr);
                            commentArrayList.add(data);
                        }
                    }
                } else {
                    Log.d("lll", "댓글 로드 오류 : ", task.getException());
                }
                commentAdapter.notifyDataSetChanged();
            }
        });
    }
}