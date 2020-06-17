package jsj.mjc.hobbybook;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DebateCommentAdapter extends RecyclerView.Adapter<DebateCommentAdapter.DebateCommentViewHolder> {
    private ArrayList<DebateComment> debateCommentList;

    public class DebateCommentViewHolder extends RecyclerView.ViewHolder {
        protected TextView dcWriterTv;
        protected TextView dcDateTv;
        protected TextView dcTextTv;
        protected ImageView dcWriterIv;
        protected ImageView reCommentIv;

        public DebateCommentViewHolder(View view) {
            super(view);
            this.dcWriterTv = view.findViewById(R.id.dComment_writer_tv);
            this.dcDateTv = view.findViewById(R.id.dComment_date_tv);
            this.dcTextTv = view.findViewById(R.id.dComment_text_tv);
            this.dcWriterIv = view.findViewById(R.id.debate_comment_tv);
            this.reCommentIv = view.findViewById(R.id.dReply_iv);
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
    public void onBindViewHolder(@NonNull DebateCommentViewHolder viewHolder, int position) {
        viewHolder.dcWriterTv.setText(debateCommentList.get(position).getDcWriter());
        viewHolder.dcDateTv.setText(debateCommentList.get(position).getDcDate());
        viewHolder.dcTextTv.setText(debateCommentList.get(position).getDcText());
        //추후 Glide 통해 이미지 변경

        if (!debateCommentList.get(position).getDcNum().equals("0"))
            if (!debateCommentList.get(position).getRcNum().equals("1"))
                viewHolder.reCommentIv.setVisibility(View.VISIBLE);
    }

    public int getItemCount() {
        return (null != debateCommentList ? debateCommentList.size() : 0);
    }
}
