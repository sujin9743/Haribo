package jsj.mjc.hobbybook;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class DebateListActivity extends AppCompatActivity {
    private ArrayList<Debate> debateArrayList;
    private DebateAdapter debateAdapter;
    String loginId;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    SimpleDateFormat dateFormatter = new SimpleDateFormat("y. M. d. hh:mm");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debate_list);

        loginId = getIntent().getStringExtra("loginId");

        RecyclerView recyclerView = findViewById(R.id.dList_RV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        debateArrayList = new ArrayList<>();
        debateAdapter = new DebateAdapter(debateArrayList);
        recyclerView.setAdapter(debateAdapter);

        DividerItemDecoration gDividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(gDividerItemDecoration);
        //RecyclerView 항목 클릭 구현
        debateAdapter.setOnItemClickListener(new DebateAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(DebateListActivity.this, DebateDetailActivity.class);
                startActivity(intent);
            }
        });

        ImageButton dListBack_btn = findViewById(R.id.dList_back_btn);
        ImageButton dListSearch_btn = findViewById(R.id.dList_search_btn);
        ImageButton dListAdd_btn = findViewById(R.id.dList_write_btn);

        dListBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dListSearch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DebateListActivity.this, DebateSearchActivity.class);
                startActivity(intent);
            }
        });

        dListAdd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DebateListActivity.this, DebateAddActivity.class);
                startActivity(intent);
            }
        });

        db.collection("debate").orderBy("inputtime", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                         String date = dateFormatter.format(doc.getDate("inputtime"));
                         Debate data = new Debate(doc.getId(), doc.getString("d_title"), doc.getString("d_content"),
                                "2020.06.11 14:16", doc.getString("mem_id"));

                        debateArrayList.add(data);
                        debateAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.d("lll", "독후감 오류 : ", task.getException());
                }
            }
        });
    }
}
