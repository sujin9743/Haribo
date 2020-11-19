package jsj.mjc.hobbybook;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FollowListActivity extends AppCompatActivity {
    //FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    FragmentManager fragmentManager = getSupportFragmentManager();
    FollowerFragment followerFragment = new FollowerFragment();
    FollowingFragment followingFragment = new FollowingFragment();
    String loginId, userId;
    int seetab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follow_list);

        loginId = getIntent().getStringExtra(getResources().getString(R.string.lid));
        userId = getIntent().getStringExtra(getResources().getString(R.string.uid));
        seetab = getIntent().getIntExtra("seetab", 0);

        //툴바 설정
        Toolbar profile_modify_toolbar = (Toolbar) findViewById(R.id.followList_toolbar);
        setSupportActionBar(profile_modify_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);


        //첫 화면
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.follow_tab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewChange(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        TabLayout.Tab tab = tabLayout.getTabAt(seetab);
        tab.select();
        viewChange(seetab);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //탭 Fragment 전환
    protected void viewChange(int index) {
        //Fragment 변경 사항을 commit()할 때 마다 새로운 FragmentTransaction만들어야 함
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (index) {
            case 0 :
                fragmentTransaction.replace(R.id.follow_frameLayout, followerFragment).commit();
                break;
            case 1 :
                fragmentTransaction.replace(R.id.follow_frameLayout, followingFragment).commit();
                break;
        }
    }
}
