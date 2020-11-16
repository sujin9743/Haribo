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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MRealtimeBookreportRecycler extends AppCompatActivity {
    ImageView addBtn, backBtn, searchBtn;

    RecyclerView recyclerView;
    MRealtimeBookreportAdapter adapter;
    ArrayList<MRealtime> list = new ArrayList<>();
    MRealtime item;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_realtime_bookreport_recycler);


        recyclerView = findViewById(R.id.mRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //recyclerView 구분선 추가
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));

        item = new MRealtime();
        adapter = new MRealtimeBookreportAdapter(list);
        recyclerView.setAdapter(adapter);

        //firebase
        final FirebaseFirestore rtBook_DB = FirebaseFirestore.getInstance();

        rtBook_DB.collection("bookre").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (int i = 0; i < task.getResult().size(); i++) {
                        DocumentSnapshot doc = task.getResult().getDocuments().get(i);
                        Log.d("TAG", "data: " + doc);
                        Log.d("TAG", "data: " + doc.getData());
                        Log.d("TAG", "data2: " + doc.getData().get("br_img").toString());
                        Log.d("TAG", "data2: " + doc.getData().get("book_title").toString());
                        Log.d("TAG", "data2: " + doc.getData().get("book_author").toString());
                        item.setProfileText(doc.getData().get("mem_id").toString());
                        item.setBookImgPage(doc.getData().get("br_img").toString());
                        item.setBrTitle(doc.getData().get("br_title").toString());
                        list.add(item);
                        adapter.setOnItemClickListener(new MRealtimeBookreportAdapter.OnItemClickListenr() {
                            @Override
                            public void onItemClick(View v, int position) {
                                Intent i = new Intent(getApplicationContext(), MBookReportDetail.class);
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
