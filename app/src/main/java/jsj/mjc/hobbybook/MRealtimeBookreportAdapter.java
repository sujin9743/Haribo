package jsj.mjc.hobbybook;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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


        int i = 0;
        int intLikeCnt;

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




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION);
                    if(rlistener !=null){
                        rlistener.onItemClick(v,position);
                    }
                }
            });
        }
    }
}