package jsj.mjc.hobbybook;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageFragment extends Fragment {
    private ArrayList<Message> messageArrayList;
    private MessageAdapter messageAdapter;
    Context mContext;
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

        DividerItemDecoration gDividerItemDecoration = new DividerItemDecoration(mContext, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(gDividerItemDecoration);

        for (int i = 0; i < 20; i++) {
            int color;
            int j = (int)(Math.random() * 3);
            String s = "보낸사람";
            String r = " > 나";
            if(j == 1) {
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
        }
        messageAdapter.notifyDataSetChanged();

        messageAdapter.setOnItemClickListener(new MessageAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(mContext, MessageViewActivity.class);
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
