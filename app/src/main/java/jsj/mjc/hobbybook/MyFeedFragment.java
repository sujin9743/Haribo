package jsj.mjc.hobbybook;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyFeedFragment extends Fragment {

    ArrayList<FeedReadBookItem> mF_readBookList;
    FeedReadBookAdapter mF_feedReadBookAdapter;
    BottomSheetDialog bottomSheetDialog;
    ImageButton setting_btn;
    TextView alarm_setting, block_setting, genre_setting, logout, myFeed_book_count_txt, myFeed_follower_count_txt, myFeed_following_count_txt, myFeed_user_id;
    Button myFeed_profile_btn;
    CircleImageView myFeed_profileImg;
    String loginId;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageRef;
    int following, follower, read;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myfeed, container, false);

        loginId = MainActivity.loginId;

        myFeed_book_count_txt = view.findViewById(R.id.myFeed_book_count_txt);
        myFeed_user_id = view.findViewById(R.id.myFeed_user_id);
        myFeed_profileImg = view.findViewById(R.id.myFeed_profileImg);

        storageRef = FirebaseStorage.getInstance().getReference();

        //RecyclerView
        mF_readBookList = new ArrayList<>();
        mF_feedReadBookAdapter = new FeedReadBookAdapter(mF_readBookList);
        RecyclerView myFeed_bookCover_recycler = view.findViewById(R.id.myFeed_bookCover_recycler);
        //RecyclerView 항목 클릭 구현
        mF_feedReadBookAdapter.setOnItemClickListener(new FeedReadBookAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {

                String a =mF_readBookList.get(position).getbookReNum();
                Intent intent = new Intent(getContext(), MBookReportDetail.class);
                intent.putExtra(getResources().getString(R.string.lid), loginId);
                intent.putExtra(getString(R.string.did), mF_readBookList.get(position).getbookReNum());
                startActivity(intent);
            }
        });

        //context와 spanCount(한 줄을 몇 개 칸으로 나눌지)
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        myFeed_bookCover_recycler.setLayoutManager(gridLayoutManager);
        myFeed_bookCover_recycler.setAdapter(mF_feedReadBookAdapter);

        //팔로잉/팔로워 목록 조회 화면 이동
        myFeed_follower_count_txt = view.findViewById(R.id.myFeed_follower_count_txt);
        myFeed_follower_count_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FollowListActivity.class);
                intent.putExtra(getString(R.string.st), 0);
                intent.putExtra(getContext().getResources().getString(R.string.lid), loginId);
                intent.putExtra(getResources().getString(R.string.uid), loginId);
                startActivity(intent);
            }
        });
        myFeed_following_count_txt = view.findViewById(R.id.myFeed_following_count_txt);
        myFeed_following_count_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FollowListActivity.class);
                intent.putExtra(getString(R.string.st), 1);
                intent.putExtra(getContext().getResources().getString(R.string.lid), loginId);
                intent.putExtra(getResources().getString(R.string.uid), loginId);
                startActivity(intent);
            }
        });

        //setting 버튼 클릭 시 BottomSheetDialog
        setting_btn = view.findViewById(R.id.setting_btn);
        bottomSheetDialog = new BottomSheetDialog(getContext());
        //Dialog에서 보여줄 View 객체 생성
        View v = getLayoutInflater().inflate(R.layout.bottom_dialog, null);
        bottomSheetDialog.setContentView(v);
        //바깥쪽 터치하면 꺼지도록
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.show();
            }
        });

        //BottomDialogSheet에서 화면 전환
        alarm_setting = v.findViewById(R.id.alarm_setting);
        alarm_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AlarmSettingActivity.class);
                startActivity(intent);
            }
        });
        /*block_setting = v.findViewById(R.id.blocking_setting);
        block_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BlockUserListActivity.class);
                startActivity(intent);
            }
        });*/
        genre_setting = v.findViewById(R.id.genre_setting);
        genre_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectGenreActivity.class);
                intent.putExtra("changeGen", 1);    //cho 회원가입시 선호장르 선택인지, 선호장르 변경인지 구분하려고
                intent.putExtra(getString(R.string.idedt), loginId);
                startActivity(intent);

            }
        });
        logout = v.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        myFeed_profile_btn = view.findViewById(R.id.myFeed_profile_btn);
        myFeed_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ModifyProfileActivity.class);
                intent.putExtra(getContext().getResources().getString(R.string.lid), loginId);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        following = 0;
        follower = 0;
        read = 0;
        //프로필 사진 로드
        StorageReference imgRef = storageRef.child(getString(R.string.pimg) + loginId + getString(R.string.jpg));
        imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(MyFeedFragment.this).load(uri).into(myFeed_profileImg);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError) + exception);
            }
        });
        //닉네임 로드
        db.collection(getString(R.string.mem)).document(loginId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        myFeed_user_id.setText(doc.getString(getContext().getResources().getString(R.string.name)));
                    }
                }
            }
        });
        //팔로잉 로드
        db.collection(getString(R.string.fl)).whereEqualTo(getString(R.string.fler), loginId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        following++;
                    }
                } else {
                    Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError), task.getException());
                }
                myFeed_following_count_txt.setText(String.valueOf(following));
            }
        });
        //팔로워 로드
        db.collection(getString(R.string.fl)).whereEqualTo(getString(R.string.flee), loginId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        follower++;
                    }
                } else {
                    Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError), task.getException());
                }
                myFeed_follower_count_txt.setText(String.valueOf(follower));
            }
        });
        //독서 기록, 독후감 로드
        mF_readBookList.clear();
        db.collection(getString(R.string.br)).whereEqualTo(getResources().getString(R.string.mid), loginId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        FeedReadBookItem data = new FeedReadBookItem(doc.getId(), doc.getString(getString(R.string.brImg)));
                        mF_readBookList.add(data);
                        read++;
                    }
                } else {
                    Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError), task.getException());
                }
                mF_feedReadBookAdapter.notifyDataSetChanged();
                myFeed_book_count_txt.setText(String.valueOf(read));
            }
        });
    }
}
