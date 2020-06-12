package jsj.mjc.hobbybook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SelectGenreActivity extends AppCompatActivity {

    Button [] genreBtn = new Button[30];
    static int [] select_state = new int[30]; //버튼 클릭 횟수를 저장하는 변수
    TextView genre_btn;
    ImageButton genre_backBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_genre);

        //선택된 장르 버튼 색상 변경
        int i;
        for(i=0; i<genreBtn.length; i++) {
            genreBtn[i] = findViewById(R.id.g0+i);
            select_state[i] = 0;
        }
        for(i=0; i<genreBtn.length; i++) {
            genreBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.g0: {
                                int id_num = 0;
                                setBtnColorChange(v, id_num); //버튼 색 변경 사용자 정의 함수 호출
                                break;
                            }
                        }
                }
            });
        }

        //툴바 뒤로가기
        genre_backBtn = findViewById(R.id.genre_backBtn);
        genre_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //완료 버튼 클릭 시
        genre_btn = findViewById(R.id.genre_btn);
        genre_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignCompleteActivity.class);
                startActivity(intent);
            }
        });

    }

    //버튼 클릭 횟수에 따른 색상 변경 함수
    public void setBtnColorChange(View v, int i) {
        if(select_state[i] == 0) {
            v.setBackgroundResource(R.drawable.genre_select_btn_box);
            select_state[i] ++;
        } else if (select_state[i] == 1){
            v.setBackgroundResource(R.drawable.genre_btn_box);
            select_state[i] = 0;
        }
    }
}
