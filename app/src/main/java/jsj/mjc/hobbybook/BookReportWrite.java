package jsj.mjc.hobbybook;

//조민주_독후감쓰기



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BookReportWrite extends AppCompatActivity {
    ImageView backBtn;

    BottomNavigationView btmNV;
    Menu menu;
    int i=1;

    private FragmentManager fgmManager = getSupportFragmentManager();
    private MHashTagSearch fgmHashtag = new MHashTagSearch();
    private MBookSearch fgmBookSearch = new MBookSearch();
    private MReportFragmentSubMain fgmBookReportSub = new MReportFragmentSubMain();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_report_write);

        //바텀네비게이션
        btmNV = findViewById(R.id.bookReportbtm);
        menu = btmNV.getMenu();
        btmNV.setOnNavigationItemSelectedListener(new ItemSelectedListener());


        //프래그먼트
        FragmentTransaction transaction = fgmManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fgmBookReportSub).commitAllowingStateLoss();

    }


    //자물쇠 아이콘 클릭시 이벤트 처리
    private class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fgmManager.beginTransaction();

            //int 변수로 공개유무 판별(1:공개, 0:비공개 처음엔 공개로 되어있으니 1로 지정 )
            if(i==1){   //공개 상태이므로 비공개로 전환하는 스위치문으로
                switch (item.getItemId()) {
                    case R.id.unlock:
                        item.setIcon(R.drawable.ic_lock_24dp);
                        item.setTitle("비공개");
                        i = 0;
                        break;
                    case R.id.bookCh:
                        transaction.replace(R.id.frameLayout, fgmBookSearch).commitAllowingStateLoss();
                        break;
                    case R.id.hashtag:
                        transaction.replace(R.id.frameLayout, fgmHashtag).commitAllowingStateLoss();
                        break;
                }
            }else{
                switch (item.getItemId()){
                    case R.id.unlock:
                        item.setIcon(R.drawable.ic_lock_open_24dp);
                        item.setTitle("공개");
                        i=1;
                        break;
                    case R.id.bookCh:
                        transaction.replace(R.id.frameLayout, fgmBookSearch).commitAllowingStateLoss();
                        break;
                    case R.id.hashtag:
                        transaction.replace(R.id.frameLayout, fgmHashtag).commitAllowingStateLoss();
                        break;
                }
            }
            return true;

        };



        }
    }

