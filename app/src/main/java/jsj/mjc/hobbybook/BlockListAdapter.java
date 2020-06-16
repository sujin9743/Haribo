package jsj.mjc.hobbybook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class BlockListAdapter extends RecyclerView.Adapter<BlockListAdapter.BlockListViewHolder> {

    private ArrayList<UserlistItem> userlist;

    public class BlockListViewHolder extends RecyclerView.ViewHolder {

        CircleImageView blockUser_profileImg;
        TextView blockUser_id;
        Button blockUser_btn;

        public BlockListViewHolder(@NonNull View itemView) {
            super(itemView);
            blockUser_profileImg = itemView.findViewById(R.id.blockUser_profileImg);
            blockUser_id = itemView.findViewById(R.id.blockUser_id);
            blockUser_btn = itemView.findViewById(R.id.blockUser_btn);
        }
    }

    //데이터 리스트 객체 전달
    BlockListAdapter(ArrayList<UserlistItem> userlist) {
        this.userlist = userlist;
    }

    //아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @NonNull
    @Override
    public BlockListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recycler_blocklist, parent, false);
        BlockListViewHolder viewHolder = new BlockListViewHolder(view);
        return viewHolder;
    }

    //position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull BlockListViewHolder viewHolder, int position) {
        viewHolder.blockUser_profileImg.setImageResource(R.drawable.ic_baseline_android_24);
        viewHolder.blockUser_id.setText(userlist.get(position).getId());
        viewHolder.blockUser_btn.setText(userlist.get(position).getBtnTxt());
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }
}
