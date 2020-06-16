package jsj.mjc.hobbybook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecommendBookAdapter extends RecyclerView.Adapter<RecommendBookAdapter.RecommendBookViewHolder> {

    private ArrayList<RecommendBookItem> booklist;


    public class RecommendBookViewHolder extends RecyclerView.ViewHolder {

        ImageView bookImg;
        TextView bookTitle, bookWriter, bookPublisher, bookRateTxt;
        RatingBar bookRate;

        public RecommendBookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImg = itemView.findViewById(R.id.bookImg);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            bookWriter = itemView.findViewById(R.id.bookWirter);
            bookPublisher = itemView.findViewById(R.id.bookPublisher);
            bookRate = itemView.findViewById(R.id.bookRate);
            bookRateTxt = itemView.findViewById(R.id.bookRateTxt);
        }
    }

    //데이터 리스트 객체 전달
    RecommendBookAdapter(ArrayList<RecommendBookItem> booklist) {
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
            viewHolder.bookImg.setImageResource(R.drawable.ic_baseline_android_24);
            viewHolder.bookTitle.setText(booklist.get(position).getBookTitle());
            viewHolder.bookWriter.setText(booklist.get(position).getBookWriter());
            viewHolder.bookPublisher.setText(booklist.get(position).getBookPublisher());
            viewHolder.bookRate.setRating(booklist.get(position).getbookRate());
            viewHolder.bookRateTxt.setText(booklist.get(position).getBookRateTxt());
    }
    @Override
    public int getItemCount() {
        return booklist.size();
    }

}
