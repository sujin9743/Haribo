package jsj.mjc.hobbybook;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MBookCommentAdapter extends RecyclerView.Adapter<MBookCommentAdapter.ViewHolder> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    String id;

/*
    public interface OnItemClickListenr {
        void onItemClick(View v, int position);
    }

    private OnItemClickListenr mlistener = null;

    public void setOnItemClickListener(OnItemClickListenr listener){this.mlistener = listener;}


 */
    private ArrayList<MBookCom> mlist;


    public class ViewHolder extends RecyclerView.ViewHolder {

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
/*
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

 */

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2020-11-18 db 삭제
                }
            });

        }
    }

    MBookCommentAdapter(ArrayList<MBookCom> list){this.mlist = list;}

    public MBookCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_info_detail,parent,false);
       ViewHolder viewHolder = new ViewHolder(view);
       return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MBookCommentAdapter.ViewHolder holder, int position) {

        id = mlist.get(position).getProfileText();
        db.collection("member").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if(doc.exists()) {
                        holder.profileText.setText(doc.getString("nickname"));
                    }
                }
            }
        });
        //프로필 사진 출력
        StorageReference imgRef = storageRef.child("profile_img/" + id + ".jpg");
        imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView.getContext()).load(uri).into(holder.profileImg);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("e", "프로필 사진 로드 실패 : " + exception);
            }
        });
        holder.date.setText(mlist.get(position).getDate());
        holder.reviewText.setText(mlist.get(position).getReviewText());



    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

}
