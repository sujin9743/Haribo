package jsj.mjc.hobbybook;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    String str;
    private ArrayList<Notice> noticeList;
    public interface OnItemClickListenr {
        void onItemClick(View v, int position);
    }

    private OnItemClickListenr noticeListener = null;

    public void setOnItemClickListener(OnItemClickListenr listener) {
        this.noticeListener = listener;
    }


    public class NoticeViewHolder extends RecyclerView.ViewHolder {
        protected TextView noticeTv;
        protected TextView noticedateTv;

        public NoticeViewHolder(View view) {
            super(view);
            this.noticeTv = view.findViewById(R.id.notice_t);
            this.noticedateTv = view.findViewById(R.id.notice_date_t);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (noticeListener != null) {
                            noticeListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }
    }

    public NoticeAdapter(ArrayList<Notice> list) {
        this.noticeList = list;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_notice, viewGroup, false);
        NoticeViewHolder viewHolder = new NoticeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NoticeViewHolder viewHolder, final int position) {
        db.collection(viewHolder.noticeTv.getContext().getResources().getString(R.string.mem)).document(noticeList.get(position).getSendId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    switch (noticeList.get(position).getType()) {
                        case 2:
                            str = viewHolder.noticeTv.getContext().getResources().getString(R.string.noticeFl);
                            break;
                        case 3:
                        case 4:
                            str = viewHolder.noticeTv.getContext().getResources().getString(R.string.noticeRe);
                            break;
                        case 5:
                        case 6:
                            str = viewHolder.noticeTv.getContext().getResources().getString(R.string.noticeReRe);
                            break;
                        default:
                    }
                    DocumentSnapshot doc = task.getResult();
                    viewHolder.noticeTv.setText(doc.getString(viewHolder.noticeTv.getContext().getResources().getString(R.string.name)) + str);
                }
            }
        });
        viewHolder.noticedateTv.setText(noticeList.get(position).getDateText());
    }

    public int getItemCount() {
        return (null != noticeList ? noticeList.size() : 0);
    }
}
