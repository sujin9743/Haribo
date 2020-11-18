package jsj.mjc.hobbybook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {

    private ArrayList<User> userlist;

    public interface OnItemClickListenr {  //RecyclerView 항목별 클릭 구현
        void onItemClick(View v, int position);
    }

    private OnItemClickListenr mListener = null;

    public void setOnItemClickListener(OnItemClickListenr listener) {
        this.mListener = listener;
    }

   public class UserListViewHolder extends RecyclerView.ViewHolder {

       CircleImageView user_profileImg;
       TextView user_id;
       //Button user_btn;
       int i=0;


       public UserListViewHolder(@NonNull View itemView) {
           super(itemView);
           user_profileImg = itemView.findViewById(R.id.user_profileImg);
           user_id = itemView.findViewById(R.id.user_id);
           //user_btn = itemView.findViewById(R.id.user_btn);


           /*user_btn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(i==0){
                       user_btn.setBackgroundResource(R.drawable.round_btn_gray);
                       user_btn.setText("팔로잉");
                       i++;
                   }else if(i==1){
                       user_btn.setBackgroundResource(R.drawable.round_btn_darkgreen);
                       user_btn.setText("팔로우");
                       i--;
               }}

           });*/
           itemView.setOnClickListener(new View.OnClickListener() {
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

   //데이터 리스트 객체 전달
   UserListAdapter(ArrayList<User> userlist) {
       this.userlist = userlist;
   }

   //아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recycler_userlist, parent, false);
        UserListViewHolder viewHolder = new UserListViewHolder(view);
        return viewHolder;
    }

    //position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder viewHolder, int position) {
        viewHolder.user_profileImg.setImageResource(R.drawable.ic_baseline_android_24);
        viewHolder.user_id.setText(userlist.get(position).getUserId());
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }
}

