package jsj.mjc.hobbybook;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder> {
    private ArrayList<Ranking> rankingList;

    public class RankingViewHolder extends RecyclerView.ViewHolder {
        protected TextView rankingNumTv;
        protected ImageView rankingIv;
        protected TextView rankingTitleTv;
        protected TextView rankingWriterTv;

        public RankingViewHolder(View view) {
            super(view);
            this.rankingNumTv = view.findViewById(R.id.rankingNumTv);
            this.rankingIv = view.findViewById(R.id.rankingIv);
            this.rankingTitleTv = view.findViewById(R.id.rankingTitleTv);
            this.rankingWriterTv = view.findViewById(R.id.rankingWriterTv);

        }
    }

    public RankingAdapter(ArrayList<Ranking> list) {
        this.rankingList = list;
    }

    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_ranking, viewGroup, false);
        RankingViewHolder viewHolder = new RankingViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RankingViewHolder viewHolder, int position) {
        viewHolder.rankingNumTv.setText(rankingList.get(position).getRankingNum());
        viewHolder.rankingIv.setImageResource(R.drawable.ic_baseline_android_24); //추후 Glide 통해 이미지 변경
        viewHolder.rankingTitleTv.setText(rankingList.get(position).getRankingTitle());
        viewHolder.rankingWriterTv.setText(rankingList.get(position).getRankingWriter());
    }

    public int getItemCount() {
        return (null != rankingList ? rankingList.size() : 0);
    }
}
