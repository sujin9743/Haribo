package jsj.mjc.hobbybook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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

public class MReportCommentAdapter extends RecyclerView.Adapter<MReportCommentAdapter.MReportCommentViewHolder>{
    private ArrayList<MReportComment> commentList;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    public int selected = -1;

    public class MReportCommentViewHolder extends RecyclerView.ViewHolder {
        protected ConstraintLayout dc_layout;
        protected TextView dcWriterTv;
        protected TextView dcDateTv;
        protected TextView dcTextTv;
        protected CircleImageView dcWriterIv;
        protected ImageView reCommentIv;
        protected ImageButton dCommentMoreBtn;  //조민주

        public MReportCommentViewHolder(View view) {
            super(view);
            this.dc_layout = view.findViewById(R.id.dc_layout);
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

    public MReportCommentAdapter(ArrayList<MReportComment> list) {
        this.commentList = list;
    }

    @NonNull
    @Override
    public MReportCommentAdapter.MReportCommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_debate_comment, viewGroup, false);
        MReportCommentAdapter.MReportCommentViewHolder viewHolder = new MReportCommentAdapter.MReportCommentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MReportCommentAdapter.MReportCommentViewHolder viewHolder, final int position) {
        if (commentList.get(position).getCDate().equals(viewHolder.dcWriterTv.getResources().getString(R.string.empty))) {
            viewHolder.dcWriterTv.setText(commentList.get(position).getCWriter());
            viewHolder.dCommentMoreBtn.setVisibility(View.INVISIBLE);
            viewHolder.dcWriterIv.setImageResource(R.drawable.ic_outline_person_outline_24);
        }
        else {
            StorageReference imgRef = storageRef.child(viewHolder.dcWriterIv.getContext().getResources().getString(R.string.pimg) + commentList.get(position).getCWriter() +viewHolder.dcWriterIv.getContext().getResources().getString(R.string.jpg));
            imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(viewHolder.dcWriterIv.getContext()).load(uri).into(viewHolder.dcWriterIv);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d(viewHolder.dcWriterIv.getContext().getResources().getString(R.string.logTag), viewHolder.dcWriterIv.getContext().getResources().getString(R.string.dataLoadError) + exception);
                }
            });
            db.collection(viewHolder.dcWriterIv.getContext().getResources().getString(R.string.mem)).document(commentList.get(position).getCWriter()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc.exists()) {
                            viewHolder.dcWriterTv.setText(doc.getString(viewHolder.dcWriterTv.getContext().getResources().getString(R.string.name)));
                        }
                    }
                }
            });
            viewHolder.dcDateTv.setText(commentList.get(position).getCDate());
            viewHolder.dcWriterIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(viewHolder.dcWriterIv.getContext(), UserFeedActivity.class);
                    intent.putExtra(viewHolder.dcWriterIv.getContext().getResources().getString(R.string.uid), commentList.get(position).getCWriter());
                    viewHolder.dcWriterIv.getContext().startActivity(intent);
                }
            });

            viewHolder.dcWriterTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(viewHolder.dcWriterTv.getContext(), UserFeedActivity.class);
                    intent.putExtra(viewHolder.dcWriterTv.getContext().getString(R.string.uid), commentList.get(position).getCWriter());
                    viewHolder.dcWriterTv.getContext().startActivity(intent);
                }
            });

            viewHolder.dCommentMoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(viewHolder.dCommentMoreBtn.getContext(), viewHolder.dCommentMoreBtn);
                    if (commentList.get(position).getCWriter().equals(MReportCommentActivity.mReportCommentActivity.loginId)) {
                        popup.inflate(R.menu.menu_mydcomment);
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.dcoption_del:
                                        db.collection(viewHolder.dcWriterIv.getContext().getResources().getString(R.string.brc)).document(commentList.get(position).docId).update(viewHolder.dcWriterIv.getContext().getResources().getString(R.string.isDel), true);
                                        MReportCommentActivity.mReportCommentActivity.setCommentList();
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });
                    } else {
                        popup.inflate(R.menu.menu_dcomment);
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.dcoption_reply:
                                        MReportCommentActivity.mReportCommentActivity.c_bundle = commentList.get(position).getCBundle();
                                        MReportCommentActivity.mReportCommentActivity.recieve_com = commentList.get(position).getCNum();
                                        selected = position;
                                        notifyDataSetChanged();
                                        return true;
                                    case R.id.dcoption_report:
                                        ReportDialog reportDialog = new ReportDialog(viewHolder.dCommentMoreBtn.getContext());
                                        reportDialog.userId = commentList.get(position).getCWriter();
                                        reportDialog.show();
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });
                    }
                    popup.show();
                }
            });
        }
        viewHolder.dcTextTv.setText(commentList.get(position).getCText());
        if (position == selected)
            viewHolder.dc_layout.setBackgroundColor(viewHolder.dc_layout.getContext().getResources().getColor(R.color.beige));
        else viewHolder.dc_layout.setBackground(null);

        if (commentList.get(position).getRcNum() != 0)
            viewHolder.reCommentIv.setVisibility(View.VISIBLE);
    }

    public int getItemCount() {
        return (null != commentList ? commentList.size() : 0);
    }
}