package jsj.mjc.hobbybook;

import android.annotation.SuppressLint;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DebateAdapter extends RecyclerView.Adapter<DebateAdapter.DebateViewHolder> {
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<Debate> debateList;
    public interface OnItemClickListenr {
        void onItemClick(View v, int position);
    }

    private OnItemClickListenr debateListener = null;

    public void setOnItemClickListener(OnItemClickListenr listener) {
        this.debateListener = listener;
    }


    public class DebateViewHolder extends RecyclerView.ViewHolder {
        protected TextView debateTitleTv;
        protected TextView debateTextTv;
        protected TextView debateDateTv;
        protected TextView debateCommentTv;

        public DebateViewHolder(View view) {
            super(view);
            this.debateTitleTv = view.findViewById(R.id.debate_title_tv);
            this.debateTextTv = view.findViewById(R.id.debate_text_tv);
            this.debateDateTv = view.findViewById(R.id.debate_date_tv);
            this.debateCommentTv = view.findViewById(R.id.debate_comment_tv);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (debateListener != null) {
                            debateListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }
    }

    public DebateAdapter(ArrayList<Debate> list) {
        this.debateList = list;
    }

    @NonNull
    @Override
    public DebateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_debate, viewGroup, false);
        return new DebateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DebateViewHolder viewHolder, final int position) {
        viewHolder.debateTitleTv.setText(debateList.get(position).getDebateTitle());
        viewHolder.debateTextTv.setText(debateList.get(position).getDebateText());
        db.collection(viewHolder.debateDateTv.getContext().getResources().getString(R.string.mem)).document(debateList.get(position).getDebateWriter()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        String strDate = debateList.get(position).getDebateDate() + viewHolder.debateDateTv.getContext().getResources().getString(R.string.pipe) + doc.getString(viewHolder.debateDateTv.getContext().getResources().getString(R.string.name));
                        viewHolder.debateDateTv.setText(strDate);
                    }
                }
            }
        });
        db.collection(viewHolder.debateDateTv.getContext().getResources().getString(R.string.dc)).whereEqualTo(viewHolder.debateDateTv.getContext().getResources().getString(R.string.dn), debateList.get(position).getDebateNum()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                int comment = 0;
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (!document.getBoolean(viewHolder.debateDateTv.getContext().getResources().getString(R.string.isDel)))
                            comment++;
                    }
                } else {
                    Log.d(viewHolder.debateDateTv.getContext().getResources().getString(R.string.logTag), viewHolder.debateDateTv.getContext().getResources().getString(R.string.dataLoadError) + task.getException());
                }
                viewHolder.debateCommentTv.setText(String.valueOf(comment));
            }
        });
    }

    public int getItemCount() {
        return (null != debateList ? debateList.size() : 0);
    }
}
