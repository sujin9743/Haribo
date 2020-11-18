package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

public class FollowerFragment extends Fragment {
    ArrayList<User> userlist;
    UserListAdapter userListAdapter;
    String loginId, userId;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follower, container, false);
        loginId = getActivity().getIntent().getStringExtra("loginId");
        userId = getActivity().getIntent().getStringExtra("userId");

        //RecyclerView
        userlist = new ArrayList<>();
        userListAdapter = new UserListAdapter(userlist);

        RecyclerView follower_recycler = view.findViewById(R.id.follower_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        follower_recycler.setLayoutManager(linearLayoutManager);

        //RecyclerView 항목 클릭 구현
        userListAdapter.setOnItemClickListener(new UserListAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getContext(), UserFeedActivity.class);
                intent.putExtra("loginId", loginId);
                intent.putExtra("userId", userlist.get(position).getUserId());
                startActivity(intent);
            }
        });

        //recyclerView 구분선 추가
        follower_recycler.addItemDecoration(new DividerItemDecoration(follower_recycler.getContext(), 1));
        follower_recycler.setAdapter(userListAdapter);

        //팔로잉 로드
        db.collection("follow").whereEqualTo("followee", userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        User data = new User(document.getString("follower"));
                        userlist.add(data);
                    }
                    userListAdapter.notifyDataSetChanged();
                } else {
                    Log.d("lll", "팔로잉 로드 오류 : ", task.getException());
                }
            }
        });

        return view;
    }
}
