package jsj.mjc.hobbybook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DebateAdapter extends RecyclerView.Adapter<DebateAdapter.DebateViewHolder> {
    private ArrayList<Debate> debateList;

    public class DebateViewHolder extends RecyclerView.ViewHolder {
        protected TextView debateTitleTv;
        protected TextView debateTextTv;
        protected TextView debateDateTv;
        protected TextView debateCommentTv;
        protected ImageView debateIv;

        public DebateViewHolder(View view) {
            super(view);
            this.debateTitleTv = view.findViewById(R.id.debate_title_tv);
            this.debateTextTv = view.findViewById(R.id.debate_text_tv);
            this.debateDateTv = view.findViewById(R.id.debate_date_tv);
            this.debateCommentTv = view.findViewById(R.id.debate_comment_tv);
            this.debateIv = view.findViewById(R.id.debate_iv);
        }
    }

    public DebateAdapter(ArrayList<Debate> list) {
        this.debateList = list;
    }

    @NonNull
    @Override
    public DebateViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_debate, viewGroup, false);
        DebateViewHolder viewHolder = new DebateViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DebateViewHolder viewHolder, int position) {
        viewHolder.debateTitleTv.setText(debateList.get(position).getDebateTitle());
        viewHolder.debateTextTv.setText(debateList.get(position).getDebateText());

        String strDate = debateList.get(position).getDebateDate() + "  |  " + debateList.get(position).getDebateWriter();
        viewHolder.debateDateTv.setText(strDate);

        viewHolder.debateCommentTv.setText(String.valueOf(debateList.get(position).getDebateComment()));

        if (debateList.get(position).getDebateImageUrl().equals("1"))
            viewHolder.debateIv.setVisibility(View.GONE);
        else viewHolder.debateIv.setImageResource(R.drawable.ic_baseline_android_24); //추후 Glide 통해 이미지 변경
    }

    public int getItemCount() {
        return (null != debateList ? debateList.size() : 0);
    }
}
