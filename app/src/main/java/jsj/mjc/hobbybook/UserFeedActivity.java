package jsj.mjc.hobbybook;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserFeedActivity extends AppCompatActivity {
    ArrayList<FeedReadBookItem> uF_readBookList;
    FeedReadBookAdapter uF_feedReadBookAdapter;
    TextView user_id, book_count_txt, follower_count_txt, following_count_txt;
    Button message_btn,followBtn;
    CircleImageView userFeed_profileImg;
    String loginId, userId;
    boolean isFollow = false;// 팔로우 유무 확인(조민주 -> 지은)
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageRef;
    int following, follower, read;
    Map<String, Object> follow = new HashMap<>(), notice = new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_feed);

        storageRef = FirebaseStorage.getInstance().getReference();

        loginId = MainActivity.loginId;
        userId = getIntent().getStringExtra(getString(R.string.uid));

        book_count_txt = findViewById(R.id.book_count_txt);
        user_id = findViewById(R.id.user_id);
        userFeed_profileImg = findViewById(R.id.userFeed_profileImg);

        followBtn = findViewById(R.id.follow_btn);
        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                follower = Integer.parseInt(follower_count_txt.getText().toString());
                if(isFollow){
                    followBtn.setBackgroundResource(R.drawable.round_btn_darkgreen);
                    followBtn.setText(R.string.followTxt);
                    isFollow = false;
                    follower_count_txt.setText(String.valueOf(follower-1));
                    db.collection(getString(R.string.fl)).whereEqualTo(getString(R.string.flee), userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot doc : task.getResult()) {
                                    if (doc.getString(getString(R.string.fler)).equals(loginId)) {
                                        db.collection(getString(R.string.fl)).document(doc.getId()).delete().addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataDelError), e);
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    });
                }else {
                    followBtn.setBackgroundResource(R.drawable.round_btn_gray);
                    followBtn.setText(getString(R.string.followingTxt));
                    isFollow = true;
                    follower_count_txt.setText(String.valueOf(follower+1));
                    follow.put(getString(R.string.fler), loginId);
                    follow.put(getString(R.string.flee), userId);
                    db.collection(getString(R.string.fl)).add(follow).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataAddError), e);
                        }
                    });
                    notice.put(getString(R.string.did), userId);
                    notice.put(getString(R.string.sm), loginId);
                    notice.put(getResources().getString(R.string.mid), userId);
                    notice.put(getString(R.string.tp), 2);
                    notice.put(getResources().getString(R.string.time), new Date());
                    db.collection(getString(R.string.ntc)).add(notice).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataAddError), e);
                        }
                    });
                }
            }
        });

        //툴바 설정
        Toolbar userFeed_toolbar = (Toolbar) findViewById(R.id.userFeed_toolbar);
        setSupportActionBar(userFeed_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);

        //RecyclerView
        uF_readBookList = new ArrayList<>();
        uF_feedReadBookAdapter = new FeedReadBookAdapter(uF_readBookList);
        RecyclerView bookCover_recycler = findViewById(R.id.bookCover_recycler);

        //context와 spanCount(한 줄을 몇 개 칸으로 나눌지)
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        bookCover_recycler.setLayoutManager(gridLayoutManager);
        bookCover_recycler.setAdapter(uF_feedReadBookAdapter);

        //팔로잉/팔로워 목록 조회 화면 이동
        follower_count_txt = findViewById(R.id.follower_count_txt);
        follower_count_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FollowListActivity.class);
                intent.putExtra(getResources().getString(R.string.lid), loginId);
                intent.putExtra(getResources().getString(R.string.uid), userId);
                intent.putExtra(getString(R.string.idx), 0);
                startActivity(intent);
            }
        });
        following_count_txt = findViewById(R.id.following_count_txt);
        following_count_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FollowListActivity.class);
                intent.putExtra(getResources().getString(R.string.lid), loginId);
                intent.putExtra(getResources().getString(R.string.uid), userId);
                intent.putExtra(getString(R.string.idx), 1);
                startActivity(intent);
            }
        });

        //쪽지 보내기 화면 이동
        message_btn = findViewById(R.id.message_btn);
        message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MessageSendActivity.class);
                intent.putExtra(getResources().getString(R.string.uid), userId);
                intent.putExtra(getResources().getString(R.string.lid), loginId);
                startActivity(intent);
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
                return true;
            case R.id.overflow_report:
                ReportDialog reportDialog = new ReportDialog(UserFeedActivity.this);
                reportDialog.userId = userId;
                reportDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onResume() {
        super.onResume();
        following = 0;
        follower = 0;
        read = 0;
        //프로필 사진 로드
        StorageReference imgRef = storageRef.child(getString(R.string.pimg) + userId + getString(R.string.jpg));
        imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(UserFeedActivity.this).load(uri).into(userFeed_profileImg);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError) + exception);
            }
        });
        //닉네임 로드
        db.collection(getString(R.string.mem)).document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        user_id.setText(doc.getString(getResources().getString(R.string.name)));
                    }
                }
            }
        });
        //팔로잉 로드
        db.collection(getString(R.string.fl)).whereEqualTo(getString(R.string.fler), userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        following++;
                    }
                } else {
                    Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError), task.getException());
                }
                following_count_txt.setText(String.valueOf(following));
            }
        });
        //팔로워 로드
        db.collection(getString(R.string.fl)).whereEqualTo(getString(R.string.flee), userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        follower++;
                        //팔로우 여부 확인
                        if (document.getString(getString(R.string.fler)).equals(loginId)) {
                            isFollow = true;
                            followBtn.setBackgroundResource(R.drawable.round_btn_gray);
                            followBtn.setText(R.string.followingTxt);
                        }
                    }
                } else {
                    Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError), task.getException());
                }
                follower_count_txt.setText(String.valueOf(follower));
            }
        });
        if (followBtn.getText().equals(getString(R.string.followingTxt))) isFollow = true;
        //독서 기록, 독후감 로드
        uF_readBookList.clear();
        //RecyclerView 항목 클릭 구현
        uF_feedReadBookAdapter.setOnItemClickListener(new FeedReadBookAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(UserFeedActivity.this, MBookReportDetail.class);
                intent.putExtra(getResources().getString(R.string.lid), loginId);
                intent.putExtra(getString(R.string.did), uF_readBookList.get(position).getbookReNum());
                startActivity(intent);
            }
        });
        db.collection(getString(R.string.br)).whereEqualTo(getResources().getString(R.string.mid), userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        if (doc.getBoolean(getString(R.string.open))) {
                            FeedReadBookItem data = new FeedReadBookItem(doc.getId(), doc.getString(getString(R.string.brImg)));
                            uF_readBookList.add(data);
                        }
                        read++;
                    }
                } else {
                    Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError), task.getException());
                }
                uF_feedReadBookAdapter.notifyDataSetChanged();
                book_count_txt.setText(String.valueOf(read));
            }
        });
    }
}
