package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FollowerFragment extends Fragment {
    ArrayList<UserlistItem> userlist;
    UserListAdapter userListAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follower, container, false);

        //RecyclerView
        userlist = new ArrayList<>();
        userListAdapter = new UserListAdapter(userlist);

        RecyclerView follower_recycler = view.findViewById(R.id.follower_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        follower_recycler.setLayoutManager(linearLayoutManager);

        //recyclerView 구분선 추가
        follower_recycler.addItemDecoration(new DividerItemDecoration(follower_recycler.getContext(), 1));

        //임시 데이터 삽입
        for(int i=0; i<20; i++) {
            //todo . 내가 팔로우하지 않은 사람만 팔로우 버튼 뜨게
            UserlistItem data = new UserlistItem("하리보", "팔로우");
            userlist.add(data);
        }
        follower_recycler.setAdapter(userListAdapter);

        //RecyclerView 항목 클릭 구현
        userListAdapter.setOnItemClickListener(new UserListAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getContext(), UserFeedActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
