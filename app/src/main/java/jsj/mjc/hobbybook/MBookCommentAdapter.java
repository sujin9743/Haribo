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

    private ArrayList<MBookCom> mlist = null;


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

        }
    }

    MBookCommentAdapter(ArrayList<MBookCom> list){this.mlist = list;}

    public MBookCommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.m_book_comment_item, parent, false) ;
        MBookCommentAdapter.ViewHolder vh = new MBookCommentAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull final MBookCommentAdapter.ViewHolder holder, int position) {

        MBookCom item = mlist.get(position);

        id = item.getProfileText();
        db.collection("member").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if(doc.exists()) {
                        holder.profileText.setText(doc.getString(holder.profileText.getContext().getResources().getString(R.string.name)));
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

        holder.date.setText(item.getDate());
        holder.reviewText.setText(item.getReviewText());



    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

}
