package jsj.mjc.hobbybook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchedBookAdapter extends RecyclerView.Adapter<SearchedBookAdapter.SearchedBookViewHolder> {
    public interface OnItemClickListenr {  //RecyclerView 항목별 클릭 구현
        void onItemClick(View v, int position);
    }

    private OnItemClickListenr mListener = null;

    public void setOnItemClickListener(OnItemClickListenr listener) {
        this.mListener = listener;
    }

    private ArrayList<SearchedBook> searchedBookList;

    public class SearchedBookViewHolder extends RecyclerView.ViewHolder {
        protected ImageView searchedBookIv;
        protected TextView searchedBookTitleTv;
        protected TextView searchedBookWriterTv;

        public SearchedBookViewHolder(View view) {
            super(view);
            this.searchedBookIv = view.findViewById(R.id.bookListIv);
            this.searchedBookTitleTv = view.findViewById(R.id.bookTitleTv);
            this.searchedBookWriterTv = view.findViewById(R.id.bookWriterTv);

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

    public SearchedBookAdapter(ArrayList<SearchedBook> list) {
        this.searchedBookList = list;
    }

    @NonNull
    @Override
    public SearchedBookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_booklist, viewGroup, false);
        SearchedBookViewHolder viewHolder = new SearchedBookViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchedBookViewHolder viewHolder, int position) {
        Glide.with(viewHolder.itemView.getContext()).load(searchedBookList.get(position).getBookImageUrl()).into(viewHolder.searchedBookIv);
        viewHolder.searchedBookTitleTv.setText(searchedBookList.get(position).getBookTitle());
        viewHolder.searchedBookWriterTv.setText(searchedBookList.get(position).getBookWriter());
    }

    public int getItemCount() {
        return (null != searchedBookList ? searchedBookList.size() : 0);
    }
}
