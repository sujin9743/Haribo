package jsj.mjc.hobbybook;

import android.content.Intent;
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

import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyFeedFragment extends Fragment {

    ArrayList<FeedReadBookItem> mF_readBookList;
    FeedReadBookAdapter mF_feedReadBookAdapter;
    BottomSheetDialog bottomSheetDialog;
    ImageButton setting_btn;
    TextView alarm_setting, block_setting, genre_setting, logout;
    Button myFeed_profile_btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myfeed, container, false);

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
}
