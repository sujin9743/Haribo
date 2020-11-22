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
                    db.collection("message").document(messageArrayList.get(position).getDocId()).update("seen", true);
                }
                Intent intent = new Intent(getContext(), MessageViewActivity.class);
                intent.putExtra("docId", messageArrayList.get(position).getDocId());
                intent.putExtra(getResources().getString(R.string.lid), loginId);
                if (messageArrayList.get(position).getmSender().equals(loginId)) {
                    intent.putExtra("userId", messageArrayList.get(position).getmReciever());
                    intent.putExtra("inSend", true);
                } else {
                    intent.putExtra("userId", messageArrayList.get(position).getmSender());
                    intent.putExtra("inSend", false);
                }
                intent.putExtra("dateStr", messageArrayList.get(position).getmDate());
                intent.putExtra("mContent", messageArrayList.get(position).getmText());
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
        db.collection("message").whereEqualTo(getResources().getString(R.string.mid), loginId).orderBy("inputtime", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                                if (doc.getString("receive_mem").equals(loginId)) isOk = true;
                                break;
                            case 2:
                                if (doc.getString("send_mem").equals(loginId)) isOk = true;
                                break;
                        }
                        if (isOk) {
                            Timestamp timestamp = (Timestamp) doc.getData().get("inputtime");
                            String dateStr = dateFormatter.format(timestamp.toDate());
                            Message data = new Message(doc.getId(),  doc.getString("send_mem"), doc.getString("receive_mem"), dateStr, doc.getString("msg_content"), doc.getBoolean("seen"));
                            messageArrayList.add(data);
                        }
                    }
                    messageAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}