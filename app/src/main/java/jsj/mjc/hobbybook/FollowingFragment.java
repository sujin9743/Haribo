package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class FollowingFragment extends Fragment { //팔로잉 TAB
    ArrayList<User> userlist;
    UserListAdapter userListAdapter;
    String loginId, userId;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);
        loginId = getActivity().getIntent().getStringExtra(getResources().getString(R.string.lid));
        userId = getActivity().getIntent().getStringExtra(getResources().getString(R.string.uid));

        //RecyclerView
        userlist = new ArrayList<>();
        userListAdapter = new UserListAdapter(userlist);

        RecyclerView following_recycler = view.findViewById(R.id.following_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        following_recycler.setLayoutManager(linearLayoutManager);

        //RecyclerView 항목 클릭 구현
        userListAdapter.setOnItemClickListener(new UserListAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getContext(), UserFeedActivity.class);
                intent.putExtra(getResources().getString(R.string.uid), userlist.get(position).getUserId());
                startActivity(intent);
            }
        });

        //recyclerView 구분선 추가
        following_recycler.addItemDecoration(new DividerItemDecoration(following_recycler.getContext(), 1));
        following_recycler.setAdapter(userListAdapter);

        //팔로잉 로드
        db.collection(getResources().getString(R.string.fl)).whereEqualTo(getResources().getString(R.string.fler), userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        User data = new User(document.getString(getResources().getString(R.string.flee)));
                        userlist.add(data);
                    }
                    userListAdapter.notifyDataSetChanged();
                } else {
                    Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError), task.getException());
                }
            }
        });

        return view;
    }
}
