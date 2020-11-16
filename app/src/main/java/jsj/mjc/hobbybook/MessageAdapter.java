package jsj.mjc.hobbybook;

import android.graphics.Color;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {




    public interface OnItemClickListenr {
        void onItemClick(View v, int position);
    }

    private OnItemClickListenr messageListener = null;

    public void setOnItemClickListener(OnItemClickListenr listener) {
        this.messageListener = listener;
    }

    private ArrayList<Message> messageList;

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        protected TextView messageSenderTv;
        protected TextView messageRecieverTv;
        protected TextView messageDateTv;
        protected TextView messageTextTv;
        protected Layout mLayout;




        public MessageViewHolder(View view) {
            super(view);
            this.messageSenderTv = view.findViewById(R.id.message_sender);
            this.messageRecieverTv = view.findViewById(R.id.message_receiver);
            this.messageDateTv = view.findViewById(R.id.message_sendDate);
            this.messageTextTv = view.findViewById(R.id.message_text);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (messageListener != null) {
                            messageListener.onItemClick(v, position);
                        }
                    }
                }
            });




        }
    }

    public MessageAdapter(ArrayList<Message> list) {
        this.messageList = list;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_message, viewGroup, false);
        MessageViewHolder viewHolder = new MessageViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder viewHolder, int position) {
        viewHolder.messageSenderTv.setText(messageList.get(position).getmSender());
        viewHolder.messageRecieverTv.setText(messageList.get(position).getmReciever());
        viewHolder.messageDateTv.setText(messageList.get(position).getmDate());
        viewHolder.messageTextTv.setText(messageList.get(position).getmText());

        viewHolder.itemView.setBackgroundColor(Integer.parseInt(messageList.get(position).getmNum()));
    }

    public int getItemCount() {
        return (null != messageList ? messageList.size() : 0);
    }
}
