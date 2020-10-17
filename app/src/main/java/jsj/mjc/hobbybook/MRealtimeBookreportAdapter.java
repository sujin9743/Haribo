package jsj.mjc.hobbybook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MRealtimeBookreportAdapter extends RecyclerView.Adapter<MRealtimeBookreportAdapter.MRealtimeBookreportViewHolder> {
    @NonNull

    private ArrayList<MRealtime> MRealtime;

    public MRealtimeBookreportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.m_realtime_bookreport_recycler,parent, false);
        MRealtimeBookreportViewHolder viewHolder = new MRealtimeBookreportViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MRealtimeBookreportViewHolder holder, int position) {
        //샘플데이터 삽입
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MRealtimeBookreportViewHolder extends RecyclerView.ViewHolder {
        protected CircleImageView profileImg;
        protected TextView profileText, bookName, bookCreator, likeCnt, commentCnt;
        protected ViewPager bookImgPage;
        protected ImageView heart;
        public MRealtimeBookreportViewHolder(@NonNull View view) {
            super(view);
            this.profileImg = view.findViewById(R.id.profileImg);
            this.profileText = view.findViewById(R.id.profileImg);
            this.bookName = view.findViewById(R.id.bookName);
            this.bookCreator = view.findViewById(R.id.bookCreator);
            this.likeCnt = view.findViewById(R.id.likeCnt);
            this.commentCnt = view.findViewById(R.id.commentCnt);
            this.bookImgPage = view.findViewById(R.id.bookImgPage);
            this.heart = view.findViewById(R.id.heart);
        }
    }
    public MRealtimeBookreportAdapter(ArrayList<MRealtime> list){
        this.MRealtime = list;
    }

}
