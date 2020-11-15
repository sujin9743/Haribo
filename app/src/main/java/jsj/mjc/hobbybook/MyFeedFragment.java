package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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
    TextView alarm_setting, block_setting, genre_setting, logout, myFeed_follower_count_txt, myFeed_following_count_txt, myFeed_user_id;
    Button myFeed_profile_btn;
    CircleImageView myFeed_profileImg;
    String loginId = "test";
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageRef = storage.getReference();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myfeed, container, false);

        myFeed_user_id = view.findViewById(R.id.myFeed_user_id);
        myFeed_profileImg = view.findViewById(R.id.myFeed_profileImg);

        //RecyclerView
        mF_readBookList = new ArrayList<>();
        mF_feedReadBookAdapter = new FeedReadBookAdapter(mF_readBookList);
        RecyclerView myFeed_bookCover_recycler = view.findViewById(R.id.myFeed_bookCover_recycler);

        //context와 spanCount(한 줄을 몇 개 칸으로 나눌지)
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        myFeed_bookCover_recycler.setLayoutManager(gridLayoutManager);

        //todo 1. RecyclerView 책표지 데이터 삽입
        for(int i=0; i<10; i++) {
            FeedReadBookItem data = new FeedReadBookItem();
            mF_readBookList.add(data);
        }
        myFeed_bookCover_recycler.setAdapter(mF_feedReadBookAdapter);

        //RecyclerView 항목 클릭 구현
        mF_feedReadBookAdapter.setOnItemClickListener(new FeedReadBookAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getContext(), MBookReportDetail.class);
                startActivity(intent);
            }
        });

        //팔로잉/팔로워 목록 조회 화면 이동
        myFeed_follower_count_txt = view.findViewById(R.id.myFeed_follower_count_txt);
        myFeed_follower_count_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FollowListActivity.class);
                startActivity(intent);
            }
        });
        myFeed_following_count_txt = view.findViewById(R.id.myFeed_following_count_txt);
        myFeed_following_count_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FollowListActivity.class);
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
        block_setting = v.findViewById(R.id.blocking_setting);
        block_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BlockUserListActivity.class);
                startActivity(intent);
            }
        });
        genre_setting = v.findViewById(R.id.genre_setting);
        genre_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectGenreActivity.class);
                intent.putExtra("changeGen", 1);    //cho 회원가입시 선호장르 선택인지, 선호장르 변경인지 구분하려고
                startActivity(intent);

            }
        });
        logout = v.findViewById(R.id.logout);

        myFeed_profile_btn = view.findViewById(R.id.myFeed_profile_btn);
        myFeed_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ModifyProfileActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        db.collection("member").document(loginId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        myFeed_user_id.setText(doc.getString("nickname"));
                    }
                }
            }
        });
    }
}
