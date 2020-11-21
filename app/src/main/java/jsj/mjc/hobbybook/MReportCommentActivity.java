package jsj.mjc.hobbybook;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

public class MReportCommentActivity extends AppCompatActivity{

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    RecyclerView recyclerView = null;
    MReportCommentAdapter mReportCommentAdapter = null;
    ArrayList<MReportComment> mlist = new ArrayList<>();
    final SimpleDateFormat dateFormatter = new SimpleDateFormat("y. M. d. hh:mm");
    String brNumString, memID;
    int brNum;
    MReportComment data = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_book_comment);

        recyclerView = findViewById(R.id.reportCommentView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),1));

        Intent intent = getIntent();
        brNumString = intent.getStringExtra("br_num");
        brNum = Integer.parseInt(brNumString);

        //mlist.clear();
        db.collection("bookre_com").whereEqualTo("br_num",brNum).orderBy("brc_num").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot doc : task.getResult()){
                                Timestamp timestamp  = (Timestamp) doc.getData().get("inputtime");
                                String date = dateFormatter.format(timestamp.toDate());

                                data = new MReportComment();
                                data.setDocId(doc.getId());
                                data.setProfileText(doc.getString("mem_id"));
                                data.setDate(date);
                                data.setComment(doc.getString("brc_content"));
                                data.setBrcNum(doc.getLong("brc_num").intValue());
                                data.setBrNum(doc.getLong("br_num").intValue());
                                //MReportComment data = new MReportComment(doc.getId(), doc.get("mem_id").toString(), date, doc.get("brc_content").toString(),doc.getLong("brc_num").intValue(),doc.getLong("br_num").intValue());
                            mlist.add(data);
                            //mReportCommentAdapter.notifyDataSetChanged();
                            }
                            mReportCommentAdapter =  new MReportCommentAdapter(recyclerView.getContext(), mlist);
                            recyclerView.setAdapter(mReportCommentAdapter);
                        }
                    }
                });

    }
}