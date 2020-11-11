package jsj.mjc.hobbybook;

import android.content.Context;
import android.text.Layout;
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

public class MRealtimeBookreportAdapter extends RecyclerView.Adapter<MRealtimeBookreportAdapter.ViewHolder>{

    private ArrayList<MRealtime> mRealtime = null;

    MRealtimeBookreportAdapter(ArrayList<MRealtime> list){
        this.mRealtime = list;
    }

    @Override
    public MRealtimeBookreportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.m_realtime_bookreport_list, parent, false);
        MRealtimeBookreportAdapter.ViewHolder vh = new MRealtimeBookreportAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MRealtimeBookreportAdapter.ViewHolder holder, int position) {
        MRealtime item = mRealtime.get(position);

        //holder.profileImg.set..(item.getProfileImg());
        holder.profileText.setText(item.getProfileText());
        holder.bookName.setText(item.getBookName());
        holder.bookCreator.setText(item.getBookCreator());
        holder.likeCnt.setText(item.getLikeCnt());
        holder.commentCnt.setText(item.getCommentCnt());
        //holder.bookImgPage.setBookImgPage(item.getBookImgPage());
        //holder.heart.setHeart(item.getHeart());

    }

    @Override
    public int getItemCount() {
        return mRealtime.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected CircleImageView profileImg;
        protected TextView profileText, bookName, bookCreator,likeCnt, commentCnt;
        protected ViewPager bookImgPage;
        protected ImageView heart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.profileImg = itemView.findViewById(R.id.profileImg);
            this.profileText = itemView.findViewById(R.id.profileText);
            this.bookName = itemView.findViewById(R.id.bookName);
            this.bookCreator = itemView.findViewById(R.id.bookCreator);
            this.likeCnt = itemView.findViewById(R.id.likeCnt);
            this.commentCnt = itemView.findViewById(R.id.commentCnt);
            this.bookImgPage = itemView.findViewById(R.id.bookImgPage);
            this.heart = itemView.findViewById(R.id.heart);
        }
    }
}