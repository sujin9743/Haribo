package jsj.mjc.hobbybook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;

import de.hdodenhof.circleimageview.CircleImageView;

import static jsj.mjc.hobbybook.R.drawable.heart_full;
import static jsj.mjc.hobbybook.R.drawable.heart_line;

public class MRealtimeBookreportAdapter extends RecyclerView.Adapter<MRealtimeBookreportAdapter.ViewHolder>{
    //리스트 하나 클릭시 이동

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    String member_id, loginId;

    public interface OnItemClickListenr {
        void onItemClick(View v, int position);
    }

    private OnItemClickListenr rlistener = null;

    public void setOnItemClickListener(OnItemClickListenr listener){this.rlistener = listener;}

    private ArrayList<MRealtime> mRealtime;

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImg;
        TextView profileText, bookName, bookCreator,likeCnt, commentCnt;
        ImageView bookImgPage;
        ImageView heart;

        int i = 0;
        int intLikeCnt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.profileImg = itemView.findViewById(R.id.profileImg);
            this.profileText = itemView.findViewById(R.id.profileText);
            this.bookName = itemView.findViewById(R.id.bookName);
            this.likeCnt = itemView.findViewById(R.id.likeCnt);
            this.bookImgPage = itemView.findViewById(R.id.bookImgPage);
            this.heart = itemView.findViewById(R.id.heart);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION);
                    if (MRealtimeBookreportAdapter.this.rlistener != null) {
                        MRealtimeBookreportAdapter.this.rlistener.onItemClick(v, position);
                    }
                }
            });
        }
    }

    MRealtimeBookreportAdapter(ArrayList<MRealtime> list){
        this.mRealtime = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.m_realtime_bookreport_list, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MRealtimeBookreportAdapter.ViewHolder holder, final int position) {
        member_id = mRealtime.get(position).getProfileText();
        //독후감 쓴 사용자의 닉네임 출력
        db.collection(holder.bookImgPage.getContext().getResources().getString(R.string.mem)).document(member_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
        StorageReference imgRef = storageRef.child(holder.itemView.getContext().getResources().getString(R.string.pimg) + member_id + holder.itemView.getContext().getResources().getString(R.string.jpg));
        imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(holder.itemView.getContext()).load(uri).into(holder.profileImg);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(holder.itemView.getContext().getResources().getString(R.string.logTag), holder.itemView.getContext().getResources().getString(R.string.dataLoadError) + exception);
            }
        });
        holder.bookName.setText(mRealtime.get(position).getBrTitle());
        holder.likeCnt.setText(mRealtime.get(position).getLikeCnt());
        holder.profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.profileImg.getContext(),UserFeedActivity.class);
                intent.putExtra(holder.profileImg.getContext().getResources().getString(R.string.uid),mRealtime.get(position).getProfileText());
                holder.profileImg.getContext().startActivity(intent);
            }
        });
        Glide.with(holder.itemView.getContext()).load(mRealtime.get(position).getBookImgPage()).into(holder.bookImgPage);
    }

    @Override
    public int getItemCount() {
        return mRealtime.size();
    }

}