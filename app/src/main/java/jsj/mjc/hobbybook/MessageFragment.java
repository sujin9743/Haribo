package jsj.mjc.hobbybook;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class MessageFragment extends Fragment {
    private ArrayList<Message> messageArrayList;
    private MessageAdapter messageAdapter;
    String loginId;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    final SimpleDateFormat dateFormatter = new SimpleDateFormat("y. M. d. hh:mm");
    Spinner spinner;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.message_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        loginId = MainActivity.loginId;

        messageArrayList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageArrayList);
        recyclerView.setAdapter(messageAdapter);

        DividerItemDecoration gDividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(gDividerItemDecoration);

        messageAdapter.setOnItemClickListener(new MessageAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                if (!messageArrayList.get(position).getSeen()) {
                    messageArrayList.get(position).setSeen(true);
                    db.collection(getString(R.string.msg)).document(messageArrayList.get(position).getDocId()).update(getString(R.string.seen), true);
                }
                Intent intent = new Intent(getContext(), MessageViewActivity.class);
                intent.putExtra(getString(R.string.did), messageArrayList.get(position).getDocId());
                intent.putExtra(getString(R.string.lid), loginId);
                if (messageArrayList.get(position).getmSender().equals(loginId)) {
                    intent.putExtra(getString(R.string.uid), messageArrayList.get(position).getmReciever());
                    intent.putExtra(getString(R.string.inSend), true);
                } else {
                    intent.putExtra(getString(R.string.uid), messageArrayList.get(position).getmSender());
                    intent.putExtra(getString(R.string.inSend), false);
                }
                intent.putExtra(getString(R.string.dstr), messageArrayList.get(position).getmDate());
                intent.putExtra(getString(R.string.mCon), messageArrayList.get(position).getmText());
                startActivity(intent);
            }
        });

        spinner = view.findViewById(R.id.message_spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                 loadMessage();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMessage();
    }

    public void loadMessage() {
        db.collection(getString(R.string.msg)).whereEqualTo(getResources().getString(R.string.mid), loginId).orderBy(getResources().getString(R.string.time), Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    messageArrayList.clear();
                    for (DocumentSnapshot doc : task.getResult()) {
                        boolean isOk = false;
                        switch (spinner.getSelectedItemPosition()) {
                            case 0:
                                isOk = true;
                                break;
                            case 1:
                                if (doc.getString(getString(R.string.rmem)).equals(loginId)) isOk = true;
                                break;
                            case 2:
                                if (doc.getString(getString(R.string.sm)).equals(loginId)) isOk = true;
                                break;
                        }
                        if (isOk) {
                            Timestamp timestamp = (Timestamp) doc.getData().get(getResources().getString(R.string.time));
                            String dateStr = dateFormatter.format(timestamp.toDate());
                            Message data = new Message(doc.getId(),  doc.getString(getString(R.string.sm)), doc.getString(getString(R.string.rmem)), dateStr, doc.getString(getString(R.string.msgCon)),doc.getBoolean(getString(R.string.seen)));
                            messageArrayList.add(data);
                        }
                    }
                    messageAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}