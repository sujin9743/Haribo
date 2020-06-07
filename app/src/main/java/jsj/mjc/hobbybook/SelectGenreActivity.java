package jsj.mjc.hobbybook;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SelectGenreActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_genre);

        //Toolbar 설정
        Toolbar toolbar = findViewById(R.id.select_genre_toolbar); //Toolbar
        setSupportActionBar(toolbar); //Toolbar 적용
        getSupportActionBar().setTitle("장르 선택"); //Toolbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //toolbar 뒤로가기 아이콘
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp); //뒤로가기 아이콘

    }

    //Toolbar에 건너뛰기 버튼 삽입
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.genre_toolbar_menu, menu);
        return true;
    }
}
