package jsj.mjc.hobbybook;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.Spinner;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ArrayList<Ranking> gRankingArrayList = new ArrayList<>();
    private ArrayList<Ranking> hbbRankingArrayList;
    private RankingAdapter gRankingAdapter, hbbRankingAdapter;
    RecyclerView gRecyclerView, hbbRecyclerView;
    private int count;
    private int max = 5; //불러올 순위 수
    private Context mContext;
    public static String selectedGenre;
    public static int selectGenreNum;

    //API연동
    final String TAG = "HomeFragment";
    public String dataKey = "ttbw_wowoo1406002";
    Ranking item = null;
    String requestUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        final Spinner genreSpinner = view.findViewById(R.id.spinnerRaking);

        gRecyclerView = view.findViewById(R.id.genreRankingRV);
        hbbRecyclerView = view.findViewById(R.id.HBBRankingRV);

        LinearLayoutManager gLinearLayoutManager = new LinearLayoutManager(mContext);
        LinearLayoutManager hbbLinearLayoutManager = new LinearLayoutManager(mContext);
        gRecyclerView.setLayoutManager(gLinearLayoutManager);
        hbbRecyclerView.setLayoutManager(hbbLinearLayoutManager);

        //gRankingArrayList = new ArrayList<>();
        //gRankingAdapter = new RankingAdapter(getContext(), gRankingArrayList);
        //gRecyclerView.setAdapter(gRankingAdapter);

        hbbRankingArrayList = new ArrayList<>();
        hbbRankingAdapter = new RankingAdapter(getContext(), hbbRankingArrayList);
        hbbRecyclerView.setAdapter(hbbRankingAdapter);

        DividerItemDecoration gDividerItemDecoration = new DividerItemDecoration(mContext, gLinearLayoutManager.getOrientation());
        gRecyclerView.addItemDecoration(gDividerItemDecoration);

        DividerItemDecoration hbbDividerItemDecoration = new DividerItemDecoration(mContext, hbbLinearLayoutManager.getOrientation());
        gRecyclerView.addItemDecoration(hbbDividerItemDecoration);
        //TODO 추후에 알라딘 API, 평점 및 리뷰에서 가져온 데이터 삽입
        //for (count = 1; count <= max; count++) { //TODO Spinner 값에 따른 데이터 변경 처리
        //    Ranking data = new Ranking(Integer.toString(count), "", count + "위 책 제목", count + "위 책 저자");
        //    gRankingArrayList.add(data);
        //}

        //for (count = 1; count <= max; count++) {
        //    Ranking data = new Ranking(Integer.toString(count), "", count + "위 책 제목", count + "위 책 저자");
        //    hbbRankingArrayList.add(data);
        //}

        //gRankingAdapter.notifyDataSetChanged();
        hbbRankingAdapter.notifyDataSetChanged();

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
                switch (selectedGenre) {
                    case "종합 베스트셀러": selectGenreNum = 0; break;
                    case "가정/요리/뷰티": selectGenreNum = 1230; break;
                    case "건강/취미/레저": selectGenreNum = 78344; break;
                    case "경제경영": selectGenreNum = 78346; break;
                    case "고전": selectGenreNum = 38414; break;
                    case "공무원 수험서": selectGenreNum = 39398; break;
                    case "과학": selectGenreNum = 987; break;
                    case "대학교재/전문서적": selectGenreNum = 8257; break;
                    case "만화": selectGenreNum = 2551; break;
                    case "사회과학": selectGenreNum = 789; break;
                    case "소설/시/희곡": selectGenreNum = 1; break;
                    case "수험서/자격증": selectGenreNum = 1383; break;
                    case "어린이": selectGenreNum = 1108; break;
                    case "에세이": selectGenreNum = 55889; break;
                    case "여행": selectGenreNum = 1196; break;
                    case "역사": selectGenreNum = 74; break;
                    case "예술/대중문화": selectGenreNum = 517; break;
                    case "외국어": selectGenreNum = 1322; break;
                    case "유아": selectGenreNum = 13789; break;
                    case "인문학": selectGenreNum = 656; break;
                    case "자기계발": selectGenreNum = 336; break;
                    case "장르소설": selectGenreNum = 112011; break;
                    case "잡지": selectGenreNum = 28402; break;
                    case "전집/중고전집": selectGenreNum = 38426; break;
                    case "종교/역학": selectGenreNum = 38410; break;
                }
                BestsellerAsyncTask bestsellerAsyncTask = new BestsellerAsyncTask();
                bestsellerAsyncTask.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //TODO RecyclerView 터치 시 도서 상세 페이지로 이동
        return view;
    }

    //알라딘 API에서 데이터 불러오기
    public class BestsellerAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            requestUrl = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=" + dataKey +
                    "&QueryType=Bestseller&CategoryId=" + selectGenreNum + "&MaxResults=5&start=1&SearchTarget=Book&output=xml&Version=20131101";

            try {
                URL url = new URL(requestUrl);
                InputStream is = url.openStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new InputStreamReader(is, "UTF-8"));

                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            if(parser.getName().equals("item")) {
                                item = new Ranking();
                            }
                            else if(parser.getName().equals("bestRank")) {
                                parser.next();
                                if(item!=null) item.setRankingNum(parser.getText());
                            }
                            else if(parser.getName().equals("title")) {
                                parser.next();
                                if(item!=null) item.setRankingTitle(parser.getText());
                            }
                            else if(parser.getName().equals("author")) {
                                parser.next();
                                if(item!=null) item.setRankingWriter(parser.getText());
                            }
                            else if(parser.getName().equals("cover")) {
                                parser.next();
                                if(item!=null) item.setRankingImageUrl(parser.getText());
                            }
                            break;
                        case XmlPullParser.TEXT:
                            break;
                        case XmlPullParser.END_TAG:
                            if(parser.getName().equals("item") && item != null) {
                                gRankingArrayList.add(item);
                            }
                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                    }
                    eventType = parser.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            gRankingAdapter = new RankingAdapter(mContext, gRankingArrayList);
            if(gRankingArrayList.size() > 5) {
                gRankingAdapter.removeHFItem(0);
            }
            gRecyclerView.setAdapter(gRankingAdapter);

            gRankingAdapter.setOnItemClickListener(new RankingAdapter.OnItemClickListenr() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(mContext, MBookInfoDetail.class);
                    startActivity(intent);
                }
            });

            gRankingAdapter.setOnItemClickListener(new RankingAdapter.OnItemClickListenr() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(mContext, MBookInfoDetail.class);
                    startActivity(intent);
                }
            });
        }
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
