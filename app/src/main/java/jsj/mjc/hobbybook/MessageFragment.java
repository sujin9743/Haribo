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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageFragment extends Fragment {
    private ArrayList<Message> messageArrayList;
    private MessageAdapter messageAdapter;
    Context mContext;

    String doc;
    String send,receive;
    FirebaseFirestore db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.message_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);


        messageArrayList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageArrayList);
        recyclerView.setAdapter(messageAdapter);

        //chang
        Spinner spinner = view.findViewById(R.id.message_spinner);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String s, r;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    messageArrayList.clear();
                    //cho
                    db = FirebaseFirestore.getInstance();
                    db.collection("message")
                            .whereEqualTo("send_mem", "test")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d("TAG냐냐냐냐냐냐냐ㅑ냐", document.getId() + " => " + document.getData());

                                            Message data = new Message(document.get("msg_num").toString()
                                                    , document.get("send_mem").toString()+" > "
                                                    , document.get("receive_mem").toString()
                                                    , document.get("inputtime").toString()
                                                    ,document.get("msg_content").toString());
                                            messageArrayList.add(data);
                                            messageAdapter.notifyDataSetChanged();



                                        }
                                    } else {
                                        Log.d("TAG", "Error getting documents: ", task.getException());
                                    }
                                }
                            });

                    db.collection("message")
                            .whereEqualTo("receive_mem", "test")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d("TAG냐냐냐냐냐냐냐ㅑ냐", document.getId() + " => " + document.getData());

                                            Message data = new Message(document.get("msg_num").toString()
                                                    , document.get("send_mem").toString()+" > "
                                                    , document.get("receive_mem").toString()
                                                    , document.get("inputtime").toString()
                                                    ,document.get("msg_content").toString());
                                            messageArrayList.add(data);
                                            messageAdapter.notifyDataSetChanged();


                                        }
                                    } else {
                                        Log.d("TAG", "Error getting documents: ", task.getException());
                                    }
                                }
                            });

                }
                if (position == 1) {
                    messageArrayList.clear();
                    //cho
                    db = FirebaseFirestore.getInstance();
                    db.collection("message")
                            .whereEqualTo("receive_mem", "test")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d("TAG냐냐냐냐냐냐냐ㅑ냐", document.getId() + " => " + document.getData());

                                                Message data = new Message(document.get("msg_num").toString()
                                                        , document.get("send_mem").toString()+" > "
                                                        , document.get("receive_mem").toString()
                                                        , document.get("inputtime").toString()
                                                        ,document.get("msg_content").toString());
                                                messageArrayList.add(data);
                                                messageAdapter.notifyDataSetChanged();


                                        }
                                    } else {
                                        Log.d("TAG", "Error getting documents: ", task.getException());
                                    }
                                }
                            });



                    /*
                    messageArrayList.clear();
                    for (int i = 0; i < 20; i++) {
                        //Todo DB 연동 후 수정 예정(chang)
                        s = "보낸사람";
                        r = " > 나";

                        Message data = new Message("1", s, r, "2020.06.14 15:55", "이것은 쪽지 내용 미리 보기~ 아~ 과제 힘들다~ 하지만 해야 하지~ 시시싫어요 도망갈래");
                        messageArrayList.add(data);
                        messageAdapter.notifyDataSetChanged();


                    } */
                } else if (position == 2) {

                    messageArrayList.clear();
                    //cho
                    db = FirebaseFirestore.getInstance();
                    db.collection("message")
                            .whereEqualTo("send_mem", "test")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d("TAG냐냐냐냐냐냐냐ㅑ냐", document.getId() + " => " + document.getData());

                                            Message data = new Message(document.get("msg_num").toString()
                                                    , document.get("send_mem").toString()+" > "
                                                    , document.get("receive_mem").toString()
                                                    , document.get("inputtime").toString()
                                                    ,document.get("msg_content").toString());
                                            messageArrayList.add(data);
                                            messageAdapter.notifyDataSetChanged();

                                        }
                                    } else {
                                        Log.d("TAG", "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                    }
                }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        DividerItemDecoration gDividerItemDecoration = new DividerItemDecoration(mContext, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(gDividerItemDecoration);

        messageAdapter.setOnItemClickListener(new MessageAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(mContext, MessageViewActivity.class);
                doc = messageArrayList.get(position).getmDate();
                intent.putExtra("send",doc);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}


/*
                    for (int i = 0; i < 20; i++) {
                        int color;
                        int j = (int) (Math.random() * 3);
                        String s = "보낸사람";
                        String r = " > 나";
                        if (j == 1) {
                            color = getResources().getColor(R.color.beige);
                        } else {
                            color = getResources().getColor(R.color.white);
                            if (j == 2) {
                                s = "나";
                                r = " > 받는사람";
                            }
                        }

                        Message data = new Message("" + color, s, r, "2020.06.14 15:55", "이것은 쪽지 내용 미리 보기~ 아~ 과제 힘들다~ 하지만 해야 하지~ 시시싫어요 도망갈래");
                        messageArrayList.add(data);
                        messageAdapter.notifyDataSetChanged();
                    }
                    */