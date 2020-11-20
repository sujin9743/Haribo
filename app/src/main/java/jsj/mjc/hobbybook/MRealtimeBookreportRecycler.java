package jsj.mjc.hobbybook;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MRealtimeBookreportRecycler extends AppCompatActivity {
    ImageView addBtn, backBtn, searchBtn;

    RecyclerView recyclerView;
    MRealtimeBookreportAdapter adapter;
    ArrayList<MRealtime> list = new ArrayList<>();
    MRealtime item;
    FirebaseFirestore rtBook_DB;

    String loginId;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_realtime_bookreport_recycler);


        loginId = getIntent().getStringExtra(getResources().getString(R.string.lid));



        recyclerView = findViewById(R.id.mRecycler);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //recyclerView 구분선 추가


        item = new MRealtime();
        adapter = new MRealtimeBookreportAdapter(list);
        recyclerView.setAdapter(adapter);

        //firebase
        rtBook_DB = FirebaseFirestore.getInstance();

        rtBook_DB.collection("bookre").orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (int i = 0; i < task.getResult().size(); i++) {
                        DocumentSnapshot doc = task.getResult().getDocuments().get(i);
                        item.setProfileText(doc.getData().get(getResources().getString(R.string.mid)).toString());
                        item.setBookImgPage(doc.getData().get("br_img").toString());
                        item.setBrTitle(doc.getData().get("br_title").toString());
                        item.setBookInfo(doc.getData().get("book_description").toString());
                        item.setLikeCnt(doc.getData().get("book_like").toString());
//                        item.setBookNum(doc.getData().get("br_num").toString());
                        Log.d("TAG", "좋아요!!" + doc.getLong("book_like").intValue());

                        list.add(item);

                        adapter.setOnItemClickListener(new MRealtimeBookreportAdapter.OnItemClickListenr() {
                            @Override
                            public void onItemClick(View v, int position) {
                                String mem_id, br_title, description, br_num;
                                mem_id = list.get(position).getProfileText();
                                br_title = list.get(position).getBrTitle();
                                description = list.get(position).getBookInfo();
                                br_num = list.get(position).getBookNum();

                                Intent i = new Intent(getApplicationContext(), MBookReportDetail.class);
                                i.putExtra(getResources().getString(R.string.mid),mem_id);
                                i.putExtra("br_title",br_title);
                                i.putExtra("imMyFeed","0");
                                i.putExtra("description", description);
                                i.putExtra("br_num", br_num);
                                i.putExtra(getResources().getString(R.string.lid), loginId);
                                startActivity(i);
                            }
                        });
                        adapter.notifyDataSetChanged();
                        item = new MRealtime();
                        //adapter = new MRealtimeBookreportAdapter(list);
                    }
                    //recyclerView.setAdapter(adapter);
                }
            }
        });

        //for(int i = 0;i<10;i++){
        //    MRealtime data = new MRealtime("도로시","82년생의 김지영"+ " | ","82살 김지영을 읽고","150","30");
        //    list.add(data);
        //}

        //recyclerView.setAdapter(adapter);


        addBtn = findViewById(R.id.addBookReport);
        backBtn = findViewById(R.id.backBtn);
        searchBtn = findViewById(R.id.searchBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MBookWriteMain.class);
                startActivity(intent);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MBookSearch.class);
                startActivity(intent);
            }
        });
    }
}
