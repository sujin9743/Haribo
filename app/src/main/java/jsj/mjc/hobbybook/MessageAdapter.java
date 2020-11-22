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
import androidx.constraintlayout.widget.ConstraintLayout;
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
    private ArrayList<Message> messageList;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public interface OnItemClickListenr {
        void onItemClick(View v, int position);
    }

    private OnItemClickListenr messageListener = null;

    public void setOnItemClickListener(OnItemClickListenr listener) {
        this.messageListener = listener;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        protected TextView messageSenderTv;
        protected TextView messageRecieverTv;
        protected TextView messageDateTv;
        protected TextView messageTextTv;
        protected ConstraintLayout mLayout;

        public MessageViewHolder(View view) {
            super(view);
            this.messageSenderTv = view.findViewById(R.id.message_sender);
            this.messageRecieverTv = view.findViewById(R.id.message_receiver);
            this.messageDateTv = view.findViewById(R.id.message_sendDate);
            this.messageTextTv = view.findViewById(R.id.message_text);
            this.mLayout = view.findViewById(R.id.mLayout);

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
    public void onBindViewHolder(@NonNull final MessageViewHolder viewHolder, int position) {
        db.collection(viewHolder.messageSenderTv.getContext().getResources().getString(R.string.mem)).document(messageList.get(position).getmSender()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    viewHolder.messageSenderTv.setText(doc.getString(viewHolder.messageSenderTv.getContext().getResources().getString(R.string.name)) + viewHolder.messageSenderTv.getContext().getResources().getString(R.string.right));
                }
            }
        });
        db.collection(viewHolder.messageSenderTv.getContext().getResources().getString(R.string.mem)).document(messageList.get(position).getmReciever()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    viewHolder.messageRecieverTv.setText(doc.getString(viewHolder.messageRecieverTv.getContext().getResources().getString(R.string.name)));
                }
            }
        });
        viewHolder.messageDateTv.setText(messageList.get(position).getmDate());
        viewHolder.messageTextTv.setText(messageList.get(position).getmText());
        if (!messageList.get(position).getSeen())
            viewHolder.mLayout.setBackgroundColor(viewHolder.mLayout.getContext().getResources().getColor(R.color.beige));
        else viewHolder.mLayout.setBackground(null);
    }

    public int getItemCount() {
        return (null != messageList ? messageList.size() : 0);
    }
}
