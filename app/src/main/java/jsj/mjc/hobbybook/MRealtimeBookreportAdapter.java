package jsj.mjc.hobbybook;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Dictionary;

import de.hdodenhof.circleimageview.CircleImageView;

import static jsj.mjc.hobbybook.R.drawable.heart_full;
import static jsj.mjc.hobbybook.R.drawable.heart_line;

public class MRealtimeBookreportAdapter extends RecyclerView.Adapter<MRealtimeBookreportAdapter.ViewHolder>{
    //리스트 하나 클릭시 이동


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
            this.commentCnt = itemView.findViewById(R.id.commentCnt);
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

            String lCnt = likeCnt.getText().toString();
            intLikeCnt = Integer.parseInt(lCnt);

            heart.setImageResource(heart_line);
            heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(i == 0){
                        heart.setImageResource(heart_full);
                        i++;
                        intLikeCnt++;

                    }else{
                        heart.setImageResource(heart_line);
                        i--;
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
    public void onBindViewHolder(@NonNull MRealtimeBookreportAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(mRealtime.get(position).getProfileImg()).into(holder.profileImg);
        //holder.profileImg.setImageURI(mRealtime.get(position).getProfileImg());
        holder.profileText.setText(mRealtime.get(position).getProfileText());
        holder.bookName.setText(mRealtime.get(position).getBrTitle());
        //holder.bookCreator.setText(mRealtime.get(position).getBookCreator());
        holder.likeCnt.setText(mRealtime.get(position).getLikeCnt());
        holder.commentCnt.setText(mRealtime.get(position).getCommentCnt());
        //holder.bookImgPage.setImageURI(item.getBookImgPage());
        Glide.with(holder.itemView.getContext()).load(mRealtime.get(position).getBookImgPage()).into(holder.bookImgPage);
        //holder.heart.setHeart(item.getHeart());

    }

    @Override
    public int getItemCount() {
        return mRealtime.size();
    }

}