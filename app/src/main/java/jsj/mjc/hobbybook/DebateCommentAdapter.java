package jsj.mjc.hobbybook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

public class DebateCommentAdapter extends RecyclerView.Adapter<DebateCommentAdapter.DebateCommentViewHolder> {
    private ArrayList<DebateComment> debateCommentList;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    public class DebateCommentViewHolder extends RecyclerView.ViewHolder {
        protected TextView dcWriterTv;
        protected TextView dcDateTv;
        protected TextView dcTextTv;
        protected CircleImageView dcWriterIv;
        protected ImageView reCommentIv;
        protected ImageButton dCommentMoreBtn;  //조민주

        public DebateCommentViewHolder(View view) {
            super(view);
            this.dcWriterTv = view.findViewById(R.id.dComment_writer_tv);
            this.dcDateTv = view.findViewById(R.id.dComment_date_tv);
            this.dcTextTv = view.findViewById(R.id.dComment_text_tv);
            this.dcWriterIv = view.findViewById(R.id.dComment_iv);
            this.reCommentIv = view.findViewById(R.id.dReply_iv);
            this.dCommentMoreBtn = view.findViewById(R.id.dComment_more_btn);

            dCommentMoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public DebateCommentAdapter(ArrayList<DebateComment> list) {
        this.debateCommentList = list;
    }

    @NonNull
    @Override
    public DebateCommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_debate_comment, viewGroup, false);
        DebateCommentViewHolder viewHolder = new DebateCommentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DebateCommentViewHolder viewHolder, final int position) {
        StorageReference imgRef = storageRef.child("profile_img/" + debateCommentList.get(position).getDcWriter() +".jpg");
        imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(viewHolder.dcWriterIv.getContext()).load(uri).into(viewHolder.dcWriterIv);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("e", "프로필 사진 로드 실패 : " + exception);
            }
        });
        db.collection("member").document(debateCommentList.get(position).getDcWriter()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        viewHolder.dcWriterTv.setText(doc.getString("nickname"));
                    }
                }
            }
        });
        viewHolder.dcDateTv.setText(debateCommentList.get(position).getDcDate());
        viewHolder.dcTextTv.setText(debateCommentList.get(position).getDcText());

        if (debateCommentList.get(position).getRcNum() != 0)
            viewHolder.reCommentIv.setVisibility(View.VISIBLE);
    }

    public int getItemCount() {
        return (null != debateCommentList ? debateCommentList.size() : 0);
    }
}
