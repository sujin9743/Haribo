package jsj.mjc.hobbybook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.StructuredQuery;

public class RecommendUserActivity extends AppCompatActivity {
    ArrayList<User> userlist;
    UserListAdapter userListAdapter;
    String loginId;
    ArrayList<Integer> loginGen;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView userRc_recycler;
    /*UserlistItem[] data;
    String[] list,arrlist,mam;
    ArrayList<String> newData;
    ArrayList<String> noRearrayList;
    ArrayList<String> arrayList;
    RecyclerView userRc_recycler;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_recommend);

        //툴바 뒤로가기 버튼
        ImageButton userRc_backBtn = findViewById(R.id.userRc_backBtn);
        userRc_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loginId = getIntent().getStringExtra(getResources().getString(R.string.lid));

        //RecyclerView
        userlist = new ArrayList<>();
        userListAdapter = new UserListAdapter(userlist);

        userRc_recycler = findViewById(R.id.userRc_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        userRc_recycler.setLayoutManager(linearLayoutManager);

        //RecyclerView 항목 클릭 구현
        userListAdapter.setOnItemClickListener(new UserListAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(RecommendUserActivity.this, UserFeedActivity.class);
                intent.putExtra(getResources().getString(R.string.uid), userlist.get(position).getUserId());
                startActivity(intent);
            }
        });

        //recyclerView 구분선 추가
        userRc_recycler.addItemDecoration(new DividerItemDecoration(userRc_recycler.getContext(), 1));
        userRc_recycler.setAdapter(userListAdapter);

        loginGen = new ArrayList<>();

        db.collection("category").document(loginId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    for (int i = 1; i <= 24; i++) {
                        if (doc.getBoolean(String.valueOf(i)))
                            loginGen.add(i);
                    }
                    if (loginGen.size() == 0) {
                        db.collection("member").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    int cnt = 0;
                                    for (DocumentSnapshot doc : task.getResult()) {
                                        if (!loginId.equals(doc.getId())) {
                                            User data = new User(doc.getString("id"));
                                            userlist.add(data);
                                        }
                                        if (cnt >= 20)
                                            break;
                                    }
                                }else {
                                    Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError), task.getException());
                                }
                                userListAdapter.notifyDataSetChanged();
                            }
                        });
                    } else {
                        db.collection("category").whereEqualTo(String.valueOf(loginGen.get(0)), true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    int cnt = 0;
                                    for (DocumentSnapshot doc : task.getResult()) {
                                        if (!loginId.equals(doc.getId())) {
                                            Boolean sameLike = true;
                                            for (int i : loginGen) {
                                                if (!doc.getBoolean(String.valueOf(i)))
                                                    sameLike = false;
                                            }
                                            if (sameLike) {
                                                User data = new User(doc.getString(getResources().getString(R.string.mid)));
                                                userlist.add(data);
                                                cnt++;
                                            }
                                        }
                                        if (cnt >= 20)
                                            break;
                                    }
                                }else {
                                    Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError), task.getException());
                                }
                                userListAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } else {
                    Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError), task.getException());
                }
            }
        });
    }
}
