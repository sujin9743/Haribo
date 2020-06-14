package jsj.mjc.hobbybook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
HomeFragment homeFragment = new HomeFragment();
MessageFragment messageFragment = new MessageFragment();
RankingFragment rankingFragment = new RankingFragment();
Toolbar mainToolbar, moreToolbar;
TextView rankingToolbarText;
boolean inHome = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton search_btn = findViewById(R.id.main_search_btn);
        ImageButton main_back_btn = findViewById(R.id.main_back_btn);
        mainToolbar = findViewById(R.id.mainToolbar);
        moreToolbar = findViewById(R.id.moreToolbar);
        rankingToolbarText = findViewById(R.id.more_title_tv);

        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bnv);
        //첫 화면을 HomeFragment(순위 화면)으로 설정
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frameLayout, homeFragment).commit();

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.go_home_menu :
                        goHome();
                        return true;
                    case R.id.go_create_menu :
                        inHome = false;
                        return true;
                    case R.id.go_message_menu :
                        inHome = false;
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.main_frameLayout, messageFragment).commit();
                        return true;
                    case R.id.go_notification_menu :
                        inHome = false;
                        return true;
                    case R.id.go_my_menu :
                        inHome = false;
                        return true;
                }
                return false;
            }
        });

        main_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    goHome();
            }
        });
    }
    //장르별 순위 더보기 클릭 시
    public void moreGenreClick(View v){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frameLayout, rankingFragment).commit();
        mainToolbar.setVisibility(View.INVISIBLE);
        moreToolbar.setVisibility(View.VISIBLE);
        inHome = false;
        rankingToolbarText.setText(HomeFragment.getSelectedGenre());
    }
    //하비북 사용자 순위 더보기 클릭 시
    public void moreHBBClick(View v){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frameLayout, rankingFragment).commit();
        mainToolbar.setVisibility(View.INVISIBLE);
        moreToolbar.setVisibility(View.VISIBLE);
        inHome = false;
        rankingToolbarText.setText("하비북 베스트셀러");
    }

    public void goHome() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frameLayout, homeFragment).commit();
        mainToolbar.setVisibility(View.VISIBLE);
        moreToolbar.setVisibility(View.INVISIBLE);
        inHome = true;
    }

    public void onBackPressed(){ //뒤로가기 버튼 눌렀을 때 처리
        if (inHome){ //순위 더보기 화면에 있다면 Home화면으로 돌아감
            super.onBackPressed();
        } else {
            goHome();
        }
    }
}
