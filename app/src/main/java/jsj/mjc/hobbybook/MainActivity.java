package jsj.mjc.hobbybook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
HomeFragment homeFragment = new HomeFragment();
MessageFragment messageFragment = new MessageFragment();
RankingFragment rankingFragment = new RankingFragment();
Toolbar mainToolbar, moreToolbar;
TextView rankingToolbarText;
boolean inHome = true;
DrawerLayout drawerLayout;
View drawerView;
Button nav_closeBtn;
TextView realtimebr, addbr, recommend_user, recommend_book, go_debage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton search_btn = findViewById(R.id.main_search_btn);
        ImageButton main_back_btn = findViewById(R.id.main_back_btn);
        //mainToolbar = findViewById(R.id.mainToolbar);
        moreToolbar = findViewById(R.id.moreToolbar);
        rankingToolbarText = findViewById(R.id.more_title_tv);

        //툴바 설정(햄버거 버튼)
        mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로가기 버튼(햄버거버튼으로 사용)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24); //햄버거 버튼 아이콘 지정

        //hamburger Button -> Navigation Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawerLayout);
        drawerView = (View) findViewById(R.id.nav_view);
        drawerLayout.setDrawerListener(listener);
        //navigationview x버튼 클릭 시 이벤트
        nav_closeBtn = findViewById(R.id.nav_closeBtn);
        nav_closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
            }
        });
        //NavigationDrawer 내부 사용자 추천 Text 클릭 시 화면 이동
        recommend_user = findViewById(R.id.recommend_user);
        recommend_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecommendUserActivity.class);
                startActivity(intent);
            }
        });

        //NavigationDrawer 내부 도서 추천 Text 클릭 시 화면 이동
        recommend_book = findViewById(R.id.recommend_book);
        recommend_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecommendBookActivity.class);
                startActivity(intent);
            }
        });

        //NavigationDrawer 내부 토론게시판 메뉴 클릭 시 화면 이동
        go_debage = findViewById(R.id.debate);
        go_debage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DebateListActivity.class);
                startActivity(intent);
            }
        });


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

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        //슬라이드 했을 때 호출
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }
        //Drawer가 오픈된 상황일 때 호출
        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }
        //Drawer가 닫힌 상황일 때 호출
        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }
        //특정 상태가 변경될 때 호출
        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //툴바 햄버거버튼 클릭 시 네비게이션 드로어
            case android.R.id.home:
                drawerLayout.openDrawer(drawerView);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
