package jsj.mjc.hobbybook;

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
    //private ArrayList<MReportComment> mReportCommentArrayList =null;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    private LayoutInflater mInflater;
    private Context mContext;

    public interface OnItemClickListenr {  //RecyclerView 항목별 클릭 구현
        void onItemClick(View v, int position);
    }

    private OnItemClickListenr mListener = null;

    public void setOnItemClickListener(OnItemClickListenr listener) {
        this.mListener = listener;
    }

    private ArrayList<MReportComment> mReportCommentArrayList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImg;
        TextView profileText;
        ImageButton overflowBtn;
        TextView date,comment;

        public ViewHolder(@NonNull View item) {
            super(item);

            profileText = item.findViewById(R.id.profileText);
            overflowBtn = item.findViewById(R.id.overflowBtn);
            date = item.findViewById(R.id.date);
            comment = item.findViewById(R.id.comment);

        }
    }

    MReportCommentAdapter(ArrayList<MReportComment> list){
        this.mReportCommentArrayList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.m_report_comment_item, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        StorageReference imgRef = storageRef.child("profile_img/"+mReportCommentArrayList.get(position).getProfileText()+".jpg");
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
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if(doc.exists()){
                        holder.profileText.setText(doc.getString(holder.profileText.getContext().getResources().getString(R.string.name)));
                    }
                }
            }
        });
        holder.date.setText(mReportCommentArrayList.get(position).getDate());
        holder.comment.setText(mReportCommentArrayList.get(position).getComment());

        //MReportComment item = mReportCommentArrayList.get(position);


    }

    @Override
    public int getItemCount() {
        return mReportCommentArrayList.size();
    }

}