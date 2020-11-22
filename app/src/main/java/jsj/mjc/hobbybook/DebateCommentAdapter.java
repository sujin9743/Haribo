package jsj.mjc.hobbybook;

import android.annotation.SuppressLint;
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

public class DebateCommentAdapter extends RecyclerView.Adapter<DebateCommentAdapter.DebateCommentViewHolder> {
    private ArrayList<DebateComment> debateCommentList;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    public int selected = -1;

    public class DebateCommentViewHolder extends RecyclerView.ViewHolder {
        protected ConstraintLayout dc_layout;
        protected TextView dcWriterTv;
        protected TextView dcDateTv;
        protected TextView dcTextTv;
        protected CircleImageView dcWriterIv;
        protected ImageView reCommentIv;
        protected ImageButton dCommentMoreBtn;

        public DebateCommentViewHolder(View view) {
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

    public DebateCommentAdapter(ArrayList<DebateComment> list) {
        this.debateCommentList = list;
    }

    @NonNull
    @Override
    public DebateCommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_debate_comment, viewGroup, false);
        return new DebateCommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DebateCommentViewHolder viewHolder, final int position) {
        if (debateCommentList.get(position).getDcDate().equals(viewHolder.dcWriterTv.getResources().getString(R.string.empty))) {
            viewHolder.dcWriterTv.setText(debateCommentList.get(position).getDcWriter());
            viewHolder.dCommentMoreBtn.setVisibility(View.INVISIBLE);
            viewHolder.dcWriterIv.setImageResource(R.drawable.ic_outline_person_outline_24);
        }
        else {
            StorageReference imgRef = storageRef.child(viewHolder.dcWriterIv.getContext().getResources().getString(R.string.pimg) + debateCommentList.get(position).getDcWriter() + viewHolder.dcWriterIv.getContext().getResources().getString(R.string.jpg));
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
            db.collection(viewHolder.dcWriterTv.getContext().getResources().getString(R.string.mem)).document(debateCommentList.get(position).getDcWriter()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
            viewHolder.dcDateTv.setText(debateCommentList.get(position).getDcDate());
            viewHolder.dcWriterIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(viewHolder.dcWriterIv.getContext(), UserFeedActivity.class);
                    intent.putExtra(viewHolder.dcWriterIv.getContext().getResources().getString(R.string.uid), debateCommentList.get(position).getDcWriter());
                    viewHolder.dcWriterIv.getContext().startActivity(intent);
                }
            });

            viewHolder.dcWriterTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(viewHolder.dcWriterTv.getContext(), UserFeedActivity.class);
                    intent.putExtra(viewHolder.dcWriterTv.getContext().getString(R.string.uid), debateCommentList.get(position).getDcWriter());
                    viewHolder.dcWriterTv.getContext().startActivity(intent);
                }
            });

            viewHolder.dCommentMoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(viewHolder.dCommentMoreBtn.getContext(), viewHolder.dCommentMoreBtn);
                    if (debateCommentList.get(position).getDcWriter().equals(DebateDetailActivity.debateDetailActivity.loginId)) {
                        popup.inflate(R.menu.menu_mydcomment);
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.dcoption_del:
                                        db.collection(viewHolder.dCommentMoreBtn.getContext().getResources().getString(R.string.dc)).document(debateCommentList.get(position).docId).update(viewHolder.dCommentMoreBtn.getContext().getResources().getString(R.string.isDel), true);
                                        DebateDetailActivity.debateDetailActivity.setCommentList();
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
                                        DebateDetailActivity.debateDetailActivity.dc_bundle = debateCommentList.get(position).getDcBundle();
                                        DebateDetailActivity.debateDetailActivity.recieve_com = debateCommentList.get(position).getDcNum();
                                        selected = position;
                                        notifyDataSetChanged();
                                        return true;
                                    case R.id.dcoption_report:
                                        ReportDialog reportDialog = new ReportDialog(viewHolder.dCommentMoreBtn.getContext());
                                        reportDialog.userId = debateCommentList.get(position).getDcWriter();
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
        viewHolder.dcTextTv.setText(debateCommentList.get(position).getDcText());
        if (position == selected)
            viewHolder.dc_layout.setBackgroundColor(viewHolder.dc_layout.getContext().getResources().getColor(R.color.beige));
        else viewHolder.dc_layout.setBackground(null);

        if (debateCommentList.get(position).getRcNum() != 0)
            viewHolder.reCommentIv.setVisibility(View.VISIBLE);
    }

    public int getItemCount() {
        return (null != debateCommentList ? debateCommentList.size() : 0);
    }
}
