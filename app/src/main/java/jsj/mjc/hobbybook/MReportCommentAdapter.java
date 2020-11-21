package jsj.mjc.hobbybook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import jsj.mjc.hobbybook.MReportComment;

public class MReportCommentAdapter extends RecyclerView.Adapter<MReportCommentAdapter.ViewHolder>{

    public int selected;
    private ArrayList<MReportComment> mReportCommentArrayList;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImg;
        TextView profileText;
        ImageButton overflowBtn;
        TextView date,comment;


        public ViewHolder(@NonNull View item) {
            super(item);

            this.profileImg = item.findViewById(R.id.profileImg);
            this.profileText = item.findViewById(R.id.profileText);
            this.overflowBtn = item.findViewById(R.id.overflowBtn);
            this.date = item.findViewById(R.id.date);
            this.comment = item.findViewById(R.id.comment);

        }
    }

    MReportCommentAdapter(ArrayList<MReportComment> list){
        this.mReportCommentArrayList = list;
    }
    @NonNull
    @Override
    public MReportCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.m_report_comment_item, parent, false) ;
        MReportCommentAdapter.ViewHolder vh = new MReportCommentAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull final MReportCommentAdapter.ViewHolder holder, int position) {

        holder.date.setText(mReportCommentArrayList.get(position).getDate());
        holder.comment.setText(mReportCommentArrayList.get(position).getComment());
        //프로필 사진
        StorageReference imgRef = storageRef.child("profile_img/" + mReportCommentArrayList.get(position).getProfileText() +".jpg");
        imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.profileImg.getContext()).load(uri).into(holder.profileImg);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("e", "프로필 사진 로드 실패 : " + exception);
            }
        });
        db.collection("member").document(mReportCommentArrayList.get(position).getProfileText()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        holder.profileText.setText(doc.getString(holder.profileText.getContext().getResources().getString(R.string.name)));
                    }
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != mReportCommentArrayList ? mReportCommentArrayList.size() : 0);
    }


}