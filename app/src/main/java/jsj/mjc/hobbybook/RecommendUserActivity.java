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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firestore.v1.StructuredQuery;

public class RecommendUserActivity extends AppCompatActivity {
    ArrayList<UserlistItem> userlist;
    UserListAdapter userListAdapter;
    int following;
    UserlistItem data;

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

        //RecyclerView
        userlist = new ArrayList<>();
        userListAdapter = new UserListAdapter(userlist);

        RecyclerView userRc_recycler = findViewById(R.id.userRc_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        userRc_recycler.setLayoutManager(linearLayoutManager);


        //recyclerView 구분선 추가
        userRc_recycler.addItemDecoration(new DividerItemDecoration(userRc_recycler.getContext(), 1));

        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("category").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                    Log.d("TAG",list.toString());
                }
            }
        });

        //todo. 인텐트 동시에 뿌리기
            firebaseFirestore.collection("category").whereEqualTo("mem_id","1").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            HashMap map = (HashMap) document.getData();
                            for (int i = 0; i < map.size() - 1; i++) {
                                String selectGenreNum = Integer.toString(i + 1);
                                String selectGenreBool = map.get(selectGenreNum).toString();
                                String strselectGenreNum = null;
                                if (selectGenreBool == "true") {
                                    switch (selectGenreNum) {
                                        //장르 여러 개 선택했을 때 구현해야 함
                                        case "1": {
                                            strselectGenreNum = "1";
                                            break;
                                        }
                                        case "2": {
                                            strselectGenreNum = "2";
                                            break;
                                        }
                                        case "3": {
                                            strselectGenreNum = "3";
                                            break;
                                        }
                                        case "4": {
                                            strselectGenreNum = "4";
                                            break;
                                        }
                                        case "5": {
                                            strselectGenreNum = "5";
                                            break;
                                        }
                                        case "6": {
                                            strselectGenreNum = "6";
                                            break;
                                        }
                                        case "7": {
                                            strselectGenreNum = "7";
                                            break;
                                        }
                                        case "8": {
                                            strselectGenreNum = "8";
                                            break;
                                        }
                                        case "9": {
                                            strselectGenreNum = "9";
                                            break;
                                        }
                                        case "10": {
                                            strselectGenreNum = "10";
                                            break;
                                        }
                                        case "11": {
                                            strselectGenreNum = "11";
                                            break;
                                        }
                                        case "12": {
                                            strselectGenreNum = "12";
                                            break;
                                        }
                                        case "13": {
                                            strselectGenreNum = "13";
                                            break;
                                        }
                                        case "14": {
                                            strselectGenreNum = "14";
                                            break;
                                        }
                                        case "15": {
                                            strselectGenreNum = "15";
                                            break;
                                        }
                                        case "16": {
                                            strselectGenreNum = "16";
                                            break;
                                        }
                                        case "17": {
                                            strselectGenreNum = "17";
                                            break;
                                        }
                                        case "18": {
                                            strselectGenreNum = "18";
                                            break;
                                        }
                                        case "19": {
                                            strselectGenreNum = "19";
                                            break;
                                        }
                                        case "20": {
                                            strselectGenreNum = "20";
                                            break;
                                        }
                                        case "21": {
                                            strselectGenreNum = "21";
                                            break;
                                        }
                                        case "22": {
                                            strselectGenreNum = "22";
                                            break;
                                        }
                                        case "23": {
                                            strselectGenreNum = "23";
                                            break;
                                        }
                                        case "24": {
                                            strselectGenreNum = "24";
                                            break;
                                        }
                                    }
                                    Log.d("태그", "data: " + strselectGenreNum);
                                }
                            }

                            data = new UserlistItem(document.getId(), "팔로우");
                            userlist.add(data);
                            userListAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });


        userRc_recycler.setAdapter(userListAdapter);

        //RecyclerView 항목 클릭 구현
        userListAdapter.setOnItemClickListener(new UserListAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), UserFeedActivity.class);
                startActivity(intent);
            }
        });
    }
}
