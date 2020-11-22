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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_realtime_bookreport_recycler);


        recyclerView = findViewById(R.id.mRecycler);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //recyclerView 구분선 추가
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));

        item = new MRealtime();
        adapter = new MRealtimeBookreportAdapter(list);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MRealtimeBookreportAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {

                Intent i = new Intent(getApplicationContext(), MBookReportDetail.class);
                i.putExtra(getResources().getString(R.string.lid), MainActivity.loginId);
                i.putExtra(getString(R.string.did), list.get(position).getDocId());
                startActivity(i);
            }
        });

        //firebase
        rtBook_DB = FirebaseFirestore.getInstance();

        rtBook_DB.collection(getString(R.string.br)).orderBy(getString(R.string.date), Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult()) {
                        item = new MRealtime();
                        item.setDocId(doc.getId());
                        item.setProfileText(doc.getData().get(getResources().getString(R.string.mid)).toString());
                        item.setBookImgPage(doc.getData().get(getString(R.string.brImg)).toString());
                        item.setBrTitle(doc.getData().get(getString(R.string.brTitle)).toString());
                        item.setBookInfo(doc.getData().get(getString(R.string.b_desc)).toString());
                        item.setLikeCnt(doc.getData().get(getString(R.string.bl)).toString());
                        list.add(item);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

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
