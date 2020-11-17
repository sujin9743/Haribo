package jsj.mjc.hobbybook;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RankingFragment extends Fragment {
    ArrayList<Ranking> rankingArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    private int count;
    private int max = 20; //불러올 순위 수
    private Context mContext;

    //API연동
    final String TAG = "RankingFragment";
    public String dataKey = "ttbw_wowoo1406002";
    private String requestUrl;
    Ranking item = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);

        recyclerView = view.findViewById(R.id.rankingRV);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration gDividerItemDecoration = new DividerItemDecoration(mContext, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(gDividerItemDecoration);

        BestsellerAsyncTask bestsellerAsyncTask = new BestsellerAsyncTask();
        bestsellerAsyncTask.execute();

        //TODO 추후에 어디 더보기인지에 따라 알라딘 API, 평점 및 리뷰에서 가져온 데이터 삽입
        //for (count = 1; count <= max; count++) {
        //    Ranking data = new Ranking(Integer.toString(count), "", count + "위 책 제목", count + "위 책 저자");
        //    rankingArrayList.add(data);
        //}
        //rankingAdapter.notifyDataSetChanged();

        return view;
    }

    //알라딘 API에서 데이터 불러오기
    public class BestsellerAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {

            requestUrl = "http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=" + dataKey +
                    "&QueryType=Bestseller&CategoryId=" + HomeFragment.selectGenreNum + "&MaxResults=20&start=1&SearchTarget=Book&output=xml&Version=20131101";

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
                                rankingArrayList.add(item);
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            RankingAdapter rankingAdapter = new RankingAdapter(mContext, rankingArrayList);
            if(rankingArrayList.size() > 20) {
                rankingAdapter.removeRFItem(0);
            }
            recyclerView.setAdapter(rankingAdapter);

            rankingAdapter.setOnItemClickListener(new RankingAdapter.OnItemClickListenr() {
                @Override
                public void onItemClick(View v, int position) { //책 누르면 도서 상세정보 페이지로 이동
                    String title, image;
                    title = rankingArrayList.get(position).getRankingTitle();
                    image = rankingArrayList.get(position).getRankingImageUrl();

                    Intent intent = new Intent(mContext, MBookInfoDetail.class);
                    intent.putExtra("title", title);
                    intent.putExtra("image", image);
                    startActivity(intent);
                }
            });
        }
    }


    @Override   //Fragment가 Activity에 삽입될 때 실행
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
