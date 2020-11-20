package jsj.mjc.hobbybook;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
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
    ArrayList<MReportComment> mlist = new ArrayList<>();

    final SimpleDateFormat dateFormatter = new SimpleDateFormat("y. M. d. hh:mm");
    public String brNumString, mem_id;
    int brNum;
    int brcNum = 1;

    String loginId;
    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_book_comment);

        reportCommentEdt = findViewById(R.id.reportCommentEdt);
        edt_profileImg = findViewById(R.id.edt_profileImg);
        okBtn = findViewById(R.id.okBtn);

        recyclerView = findViewById(R.id.reportCommentView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mReportCommentAdapter =  new MReportCommentAdapter(mlist);
        recyclerView.setAdapter(mReportCommentAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),1));


        Intent intent = getIntent();
        loginId = intent.getStringExtra(getResources().getString(R.string.lid));
        brNumString = intent.getStringExtra("br_num");
        brNum = Integer.parseInt(brNumString);

        mlist.clear();
        db.collection("bookre_com").orderBy("inputtime", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        if(doc.get("br_num").toString().equals(brNumString)){
                            Log.d("TAG냐냐냐냐냐냐냐ㅑ냐", doc.getId() + " => " + doc.getData());

                            if(doc.getBoolean("deleted")){
                                // TODO: 2020-11-21 삭제된 댓글 기능 구현
                            }else {
                                Timestamp timestamp  = (Timestamp) doc.getData().get("inputtime");
                                String date = dateFormatter.format(timestamp.toDate());

                                MReportComment data = new MReportComment(doc.getString("mem_id"),
                                        doc.getLong("brc_num").intValue(), doc.getLong("br_num").intValue(),
                                        doc.getString("content"),date,doc.getId());
                                Log.d("ddddddddddddd", doc.getId() + " => " + doc.getString("mem_id"));
                                Log.d("ddddddddddddd", doc.getId() + " => " + doc.getLong("brc_num").intValue());
                                Log.d("ddddddddddddd", doc.getId() + " => " + doc.getLong("br_num").intValue());
                                Log.d("ddddddddddddd", doc.getId() + " => " +  date);
                                Log.d("ddddddddddddd", doc.getId() + " => " + doc.getId());

                                mlist.add(data);

                            }
                        }
                    }
                }mReportCommentAdapter.notifyDataSetChanged();
            }
        });

/*
        //댓글 리사이클
       mlist.clear();
        db.collection("bookre_com").whereEqualTo("br_num",brNum).orderBy("brc_num").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            for(QueryDocumentSnapshot doc : task.getResult()){
                                Log.d("TAG", "onComplete:dddddddddddddddddddddddddddddddddddddddddddddddddd ");
                                if(doc.getBoolean("deleted")){
                                    // TODO: 2020-11-21 삭제된 댓글 기능 구현
                                }else {
                                Timestamp timestamp  = (Timestamp) doc.getData().get("inputtime");
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
                        }
                    }
                });
*/


        //프로필 사진
        imgRef = storageRef.child("profile_img/" + loginId +".jpg");
        imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(MReportCommentActivity.this).load(uri).into(edt_profileImg);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "프로필 사진 로드 실패 : " + e);
            }
        });

        //댓글 입력
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reportCom = reportCommentEdt.getText().toString();
                if(!reportCom.equals(getResources().getString(R.string.empty))){
                    saveCom.put("br_num",brNum);
                    saveCom.put("brc_content",reportCom);
                    saveCom.put("brc_num",brcNum);
                    saveCom.put("deleted",false);
                    saveCom.put("inputtime",new Date());
                    saveCom.put("mem_id",loginId);
                    db.collection("bookre_com").whereEqualTo("br_num",brNum)
                            .orderBy("inputtime", Query.Direction.DESCENDING).limit(1)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot doc : task.getResult()){
                                    brcNum = doc.getLong("brc_num").intValue()+1;
                                    saveCom.put("brc_num",brcNum);
                                }
                            }else{
                                Log.d("TAG", "댓글 로드 오류 : ", task.getException());
                            }
                            db.collection("bookre_com").add(saveCom).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "user 데이터 등록 실패 : ", e);
                                }
                            });

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
}