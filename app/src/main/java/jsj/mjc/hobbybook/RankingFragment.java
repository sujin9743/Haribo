package jsj.mjc.hobbybook;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RankingFragment extends Fragment {
    private ArrayList<Ranking> rankingArrayList;
    private RankingAdapter rankingAdapter;
    private int count;
    private int max = 20; //불러올 순위 수
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rankingRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);

        rankingArrayList = new ArrayList<>();
        rankingAdapter = new RankingAdapter(rankingArrayList);
        recyclerView.setAdapter(rankingAdapter);

        DividerItemDecoration gDividerItemDecoration = new DividerItemDecoration(mContext, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(gDividerItemDecoration);
        //TODO 추후에 어디 더보기인지에 따라 알라딘 API, 평점 및 리뷰에서 가져온 데이터 삽입
        for (count = 1; count <= max; count++) {
            Ranking data = new Ranking(Integer.toString(count), "", count + "위 책 제목", count + "위 책 저자");
            rankingArrayList.add(data);
        }
        rankingAdapter.notifyDataSetChanged();

        return view;
    }

    @Override   //Fragment가 Activity에 삽입될 때 실행
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
