package jsj.mjc.hobbybook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecommendBookAdapter extends RecyclerView.Adapter<RecommendBookAdapter.RecommendBookViewHolder> {
    private LayoutInflater mInflater;
    private Context mContext;

    public interface OnItemClickListenr {  //RecyclerView 항목별 클릭 구현
        void onItemClick(View v, int position);
    }

    private OnItemClickListenr mListener = null;

    public void setOnItemClickListener(OnItemClickListenr listener) {
        this.mListener = listener;
    }

    private ArrayList<RecommendBookItem> booklist;

    public class RecommendBookViewHolder extends RecyclerView.ViewHolder {

        ImageView bookImg;
        TextView bookTitle, bookWriter, bookPublisher, bookRateTxt;
        RatingBar bookRate;

        public RecommendBookViewHolder(@NonNull View itemView) {
            super(itemView);
            this.bookImg = itemView.findViewById(R.id.bookImg);
            this.bookTitle = itemView.findViewById(R.id.bookTitle);
            this.bookWriter = itemView.findViewById(R.id.bookWirter);
            this.bookPublisher = itemView.findViewById(R.id.bookPublisher);
            this.bookRate = itemView.findViewById(R.id.bookRate);
            this.bookRateTxt = itemView.findViewById(R.id.bookRateTxt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //항목마다 ClickListener 설정
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (RecommendBookAdapter.this.mListener != null) {
                            RecommendBookAdapter.this.mListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }
    }

    //데이터 리스트 객체 전달
    public RecommendBookAdapter(ArrayList<RecommendBookItem> booklist) {
        this.booklist = booklist;
    }

    //아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @NonNull
    @Override
    public RecommendBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recycler_rc_booklist, parent, false);
        RecommendBookViewHolder viewHolder = new RecommendBookViewHolder(view);
        return viewHolder;
    }

    //position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull RecommendBookViewHolder viewHolder, int position) {
            //이미지 로딩 라이브러리 Glide 사용
            Glide.with(viewHolder.itemView.getContext()).load(booklist.get(position).getBookImgUrl()).into(viewHolder.bookImg);
            //viewHolder.bookImg.setImageResource(R.drawable.ic_baseline_android_24);
            viewHolder.bookTitle.setText(booklist.get(position).getBookTitle());
            viewHolder.bookWriter.setText(booklist.get(position).getBookWriter());
            viewHolder.bookPublisher.setText(booklist.get(position).getBookPublisher());
            viewHolder.bookRate.setRating(booklist.get(position).getBookRate());
            viewHolder.bookRateTxt.setText(booklist.get(position).getBookRateTxt());
    }
    @Override
    public int getItemCount() {
        return booklist.size();
    }

}
