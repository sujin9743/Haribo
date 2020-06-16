package jsj.mjc.hobbybook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ArrayList<Ranking> gRankingArrayList;
    private ArrayList<Ranking> hbbRankingArrayList;
    private RankingAdapter gRankingAdapter;
    private RankingAdapter hbbRankingAdapter;
    private int count;
    private int max = 5; //불러올 순위 수
    private Context mContext;
    public static String selectedGenre;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        final Spinner genreSpinner = view.findViewById(R.id.spinnerRaking);

        RecyclerView gRecyclerView = view.findViewById(R.id.genreRankingRV);
        RecyclerView hbbRecyclerView = view.findViewById(R.id.HBBRankingRV);
        LinearLayoutManager gLinearLayoutManager = new LinearLayoutManager(mContext);
        LinearLayoutManager hbbLinearLayoutManager = new LinearLayoutManager(mContext);
        gRecyclerView.setLayoutManager(gLinearLayoutManager);
        hbbRecyclerView.setLayoutManager(hbbLinearLayoutManager);

        gRankingArrayList = new ArrayList<>();
        gRankingAdapter = new RankingAdapter(gRankingArrayList);
        gRecyclerView.setAdapter(gRankingAdapter);

        hbbRankingArrayList = new ArrayList<>();
        hbbRankingAdapter = new RankingAdapter(hbbRankingArrayList);
        hbbRecyclerView.setAdapter(hbbRankingAdapter);

        DividerItemDecoration gDividerItemDecoration = new DividerItemDecoration(mContext, gLinearLayoutManager.getOrientation());
        gRecyclerView.addItemDecoration(gDividerItemDecoration);

        DividerItemDecoration hbbDividerItemDecoration = new DividerItemDecoration(mContext, hbbLinearLayoutManager.getOrientation());
        gRecyclerView.addItemDecoration(hbbDividerItemDecoration);
        //TODO 추후에 알라딘 API, 평점 및 리뷰에서 가져온 데이터 삽입
        for (count = 1; count <= max; count++) { //TODO Spinner 값에 따른 데이터 변경 처리
            Ranking data = new Ranking(Integer.toString(count), "", count + "위 책 제목", count + "위 책 저자");
            gRankingArrayList.add(data);
        }

        for (count = 1; count <= max; count++) {
            Ranking data = new Ranking(Integer.toString(count), "", count + "위 책 제목", count + "위 책 저자");
            hbbRankingArrayList.add(data);
        }

        gRankingAdapter.notifyDataSetChanged();
        hbbRankingAdapter.notifyDataSetChanged();

        gRankingAdapter.setOnItemClickListener(new RankingAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(mContext, MBookInfoDetail.class);
                startActivity(intent);
            }
        });

        hbbRankingAdapter.setOnItemClickListener(new RankingAdapter.OnItemClickListenr() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(mContext, MBookInfoDetail.class);
                startActivity(intent);
            }
        });

        //TODO 사용자가 선택한 장르만 Adapt 설정
        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedGenre = genreSpinner.getSelectedItem().toString();
                //TODO 선택 장르 변경 시 해당 장르로 순위 업데이트
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //TODO RecyclerView 터치 시 도서 상세 페이지로 이동
        return view;
    }

    @Override
    public void onAttach(Context context) { //Fragment가 Activity에 삽입될 때 실행
        super.onAttach(context);
        mContext = context;
    }

    public static String getSelectedGenre() {
        return selectedGenre;
    }
}
