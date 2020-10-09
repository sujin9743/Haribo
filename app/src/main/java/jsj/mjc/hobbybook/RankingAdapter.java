package jsj.mjc.hobbybook;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder> {
    private LayoutInflater mInflater;
    private Context mContext;

    public interface OnItemClickListenr {  //RecyclerView 항목별 클릭 구현
        void onItemClick(View v, int position);
    }

    private OnItemClickListenr mListener = null;

    public void setOnItemClickListener(OnItemClickListenr listener) {
        this.mListener = listener;
    }

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

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //항목마다 ClickListener 설정
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }
    }

    public RankingAdapter(Context context, ArrayList<Ranking> list) {
        this.rankingList = list;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
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
        //viewHolder.rankingIv.setImageResource(R.drawable.test_img); //추후 Glide 통해 이미지 변경
        viewHolder.rankingTitleTv.setText(rankingList.get(position).getRankingTitle());
        viewHolder.rankingWriterTv.setText("책 제목" + rankingList.get(position).getRankingWriter());
        //이미지 로딩 라이브러리 Glide 사용
        Glide.with(viewHolder.itemView.getContext()).load(rankingList.get(position).getRankingImageUrl()).into(viewHolder.rankingIv);
    }

    public int getItemCount() {
        return (null != rankingList ? rankingList.size() : 0);
    }
}
