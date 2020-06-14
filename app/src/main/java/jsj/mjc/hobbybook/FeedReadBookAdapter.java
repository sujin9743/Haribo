package jsj.mjc.hobbybook;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedReadBookAdapter extends RecyclerView.Adapter<FeedReadBookAdapter.FeedReadBookViewHolder> {

    private ArrayList<FeedReadBookItem> readbooklist;

    public class FeedReadBookViewHolder extends RecyclerView.ViewHolder {

        ImageView bookCoverImg;

        public FeedReadBookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookCoverImg = itemView.findViewById(R.id.bookcoverImg);
        }
    }

    //데이터 리스트 객체 전달
    FeedReadBookAdapter(ArrayList<FeedReadBookItem> readbooklist) {
        this.readbooklist = readbooklist;
    }

    //아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @NonNull
    @Override
    public FeedReadBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    //position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull FeedReadBookViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
