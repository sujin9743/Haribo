package jsj.mjc.hobbybook;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

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

import java.io.ObjectInput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class MReportCommentActivity extends AppCompatActivity{

    EditText reportCommentEdt;
    CircleImageView edt_profileImg;
    ImageButton okBtn;
    ImageView backBtn;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    StorageReference imgRef;
    Map<String, Object> saveCom = new HashMap<>();


    RecyclerView recyclerView = null;
    MReportCommentAdapter mReportCommentAdapter = null;
    ArrayList<MReportComment> mlist;

    final SimpleDateFormat dateFormatter = new SimpleDateFormat("y. M. d. hh:mm");
    public String brNumString, mem_id;
    int brNum;
    int brcNum = 1;

    MReportComment item;
    String loginId;

    MReportCommentActivity mReportCommentActivity;
    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_book_comment);
        mReportCommentActivity = this;

        reportCommentEdt = findViewById(R.id.reportCommentEdt);
        edt_profileImg = findViewById(R.id.edt_profileImg);
        okBtn = findViewById(R.id.okBtn);


        Intent intent = getIntent();
        loginId = intent.getStringExtra(getResources().getString(R.string.lid));
        brNumString = intent.getStringExtra("br_num");
        brNum = Integer.parseInt(brNumString);


        storageRef = FirebaseStorage.getInstance().getReference();


            //본인 프로필 사진
            edt_profileImg = findViewById(R.id.edt_profileImg);
            imgRef = storageRef.child("profile_img/" + loginId +".jpg");
            imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(MReportCommentActivity.this).load(uri).into(edt_profileImg);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("TAG", "프로필 사진 로드 실패 : " + exception);
                }
            });

            // 댓글 목록 구현
            RecyclerView recyclerView = findViewById(R.id.reportCommentView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);

            mlist = new ArrayList<>();
            mReportCommentAdapter = new MReportCommentAdapter(mlist);
            recyclerView.setAdapter(mReportCommentAdapter);

            DividerItemDecoration gDividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
            recyclerView.addItemDecoration(gDividerItemDecoration);

            setCommentList();

            //댓글 작성
            reportCommentEdt = findViewById(R.id.reportCommentEdt);
            okBtn = findViewById(R.id.okBtn);
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String reportCom = reportCommentEdt.getText().toString();
                    if(!reportCom.equals(getResources().getString(R.string.empty))) {
                        saveCom.put("br_num",brNum);
                        saveCom.put("brc_content",reportCom);
                        saveCom.put("brc_num",brcNum);
                        saveCom.put("deleted",false);
                        saveCom.put("inputtime",new Date());
                        saveCom.put("mem_id",loginId);

                        db.collection("bookre_com").whereEqualTo("br_num", brNum)
                                .orderBy("inputtime", Query.Direction.DESCENDING).limit(1)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                        brcNum = doc.getLong("brc_num").intValue() + 1;
                                        saveCom.put("brc_num", brcNum);
                                    }
                                } else {
                                    Log.d("TAG", "댓글 로드 오류 : ", task.getException());
                                }
                                db.collection("bookre_com").add(saveCom).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("e", "user 데이터 등록 실패 : ", e);
                                    }
                                });
                                mReportCommentAdapter.selected = -1;
                                setCommentList();
                            }
                        });
                        reportCommentEdt.setText(getResources().getString(R.string.empty));
                    }
                }
            });

            backBtn = findViewById(R.id.backBtn);
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
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
                ReportDialog reportDialog = new ReportDialog(MReportCommentActivity.this);
                reportDialog.show();
                break;
            case R.id.overflow_block:
                MCutOffDialog mCutOffDialog = new MCutOffDialog(MReportCommentActivity.this);
                mCutOffDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setCommentList() {
        mlist.clear();
        db.collection("bookre_com").whereEqualTo("br_num", brNum).orderBy("brc_num").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        if (doc.getBoolean("deleted")) {
                            // TODO: 2020-11-21 삭제 댓글 구현
                        } else {
                            Timestamp timestamp = (Timestamp) doc.getData().get("inputtime");
                            String date = dateFormatter.format(timestamp.toDate());
                            item.setProfileText(doc.getData().get("mem_id").toString());
                            item.setBrcNum(doc.getLong("brc_num").intValue());
                            item.setBrNum(doc.getLong("br_num").intValue());
                            item.setComment(doc.getData().get("content").toString());
                            item.setDate(date);
                            item.setDocId(doc.getId());
                            mlist.add(item);
                            mReportCommentAdapter.notifyDataSetChanged();

                        }
                    }
                } else {
                    Log.d("lll", "댓글 로드 오류 : ", task.getException());
                }
            }
        });
    }
}