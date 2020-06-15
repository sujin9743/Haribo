package jsj.mjc.hobbybook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchedBookAdapter extends RecyclerView.Adapter<SearchedBookAdapter.SearchedBookViewHolder> {
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
        viewHolder.searchedBookIv.setImageResource(R.drawable.testimg); //추후 Glide 통해 이미지 변경
        viewHolder.searchedBookTitleTv.setText(searchedBookList.get(position).getBookTitle());
        viewHolder.searchedBookWriterTv.setText(searchedBookList.get(position).getBookWriter());
    }

    public int getItemCount() {
        return (null != searchedBookList ? searchedBookList.size() : 0);
    }
}
