package jsj.mjc.hobbybook;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MNotice extends Fragment {
    ArrayList<Notice> noticeArrayList;
    private NoticeAdapter noticeAdapter;
    String loginId;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    final SimpleDateFormat dateFormatter = new SimpleDateFormat("y. M. d. hh:mm");
    Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notice, container, false);
        loginId = MainActivity.loginId;

        //RecyclerView
        noticeArrayList = new ArrayList<>();
        noticeAdapter = new NoticeAdapter(noticeArrayList);

        RecyclerView notice_recycler = view.findViewById(R.id.notice_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        notice_recycler.setLayoutManager(linearLayoutManager);

        //RecyclerView 항목 클릭 구현
        noticeAdapter.setOnItemClickListener(new NoticeAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, final int position) {
                switch (noticeArrayList.get(position).getType()) {
                    case 1:
                    case 4://독후감 이동
                        break;
                    case 2://유저피드 이동
                        intent = new Intent(getContext(), UserFeedActivity.class);
                        intent.putExtra(getResources().getString(R.string.lid), loginId);
                        intent.putExtra(getResources().getString(R.string.uid), noticeArrayList.get(position).getSendId());
                        startActivity(intent);
                        break;
                    case 3:
                    case 5://토론글 이동
                        db.collection("debate").document(noticeArrayList.get(position).getDocId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot doc = task.getResult();
                                    intent = new Intent(getContext(), DebateDetailActivity.class);
                                    intent.putExtra(getResources().getString(R.string.lid), loginId);
                                    intent.putExtra("docId", noticeArrayList.get(position).getDocId());
                                    intent.putExtra("debateNum", doc.getLong("d_num").intValue());
                                    intent.putExtra("debateWriter", doc.getString(getResources().getString(R.string.mid)));
                                    startActivity(intent);
                                } else {
                                    Log.d("lll", "토론글 로드 실패 : " + task.getException());
                                }
                            }
                        });
                        break;
                    default:
                }
            }
        });

        //recyclerView 구분선 추가
        notice_recycler.addItemDecoration(new DividerItemDecoration(notice_recycler.getContext(), 1));
        notice_recycler.setAdapter(noticeAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        noticeArrayList.clear();
        db.collection("notice").whereEqualTo(getResources().getString(R.string.mid), loginId).orderBy("inputtime", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult()) {
                        Timestamp timestamp = (Timestamp) doc.getData().get("inputtime");
                        String dateStr = dateFormatter.format(timestamp.toDate());
                        Notice data = new Notice(doc.getString("docId"), doc.getString("send_mem"), dateStr, doc.getLong("type").intValue());
                        noticeArrayList.add(data);
                    }
                } else {
                    Log.d("lll", "알림 로드 실패 : " + task.getException());
                }
                noticeAdapter.notifyDataSetChanged();
            }
        });
    }
}
