package jsj.mjc.hobbybook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MBookCommentAdapter extends RecyclerView.Adapter<MBookCommentAdapter.ViewHolder> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    String id;


    public interface OnItemClickListenr {
        void onItemClick(View v, int position);
    }

    private OnItemClickListenr mlistener = null;

    public void setOnItemClickListener(OnItemClickListenr listener){this.mlistener = listener;}

    private ArrayList<MBookCom> mBookComs;


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView backBtn;

        CircleImageView profileImg;
        TextView profileText;
        TextView date, reviewText, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.profileImg = itemView.findViewById(R.id.profileImg);
            this.profileText =itemView.findViewById(R.id.profileText);
            this.date =itemView.findViewById(R.id.date);
            this.reviewText = itemView.findViewById(R.id.reviewText);
            this.delete = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION);
                    if (MBookCommentAdapter.this.mlistener != null) {
                        MBookCommentAdapter.this.mlistener.onItemClick(v, position);
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2020-11-18 db 삭제
                }
            });

        }
    }

    MBookCommentAdapter(ArrayList<MBookCom> list){this.mBookComs = list;}

    public MBookCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.m_book_comment_item,parent,false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MBookCommentAdapter.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return mBookComs.size();
    }

}
