package jsj.mjc.hobbybook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.okhttp.internal.framed.FrameReader;

import java.util.HashMap;
import java.util.Map;

public class SelectGenreActivity extends AppCompatActivity {

    Button[] genreBtn = new Button[30];
    static int[] select_state = new int[30]; //버튼 클릭 횟수를 저장하는 변수
    TextView genre_btn;
    ImageButton genre_backBtn;
    DocumentSnapshot doc;
    int id_num = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_genre);

        Intent intent = getIntent(); //cho
        final int changeGen = intent.getExtras().getInt("changeGen");


        //선택된 장르 버튼 색상 변경
        int i;
        for (i = 0; i < genreBtn.length; i++) {
            genreBtn[i] = findViewById(R.id.g0 + i);
            select_state[i] = 0;
        }
        for (i = 0; i < genreBtn.length; i++) {
            genreBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.g0: {
                            id_num = 0;
                            setBtnColorChange(v, id_num); //버튼 색 변경 사용자 정의 함수 호출
                            break;
                        }//CHO
                        case R.id.g1: {
                            id_num = 1;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g2: {
                            id_num = 2;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g3: {
                            id_num = 3;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g4: {
                            id_num = 4;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g5: {
                            id_num = 5;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g6: {
                            id_num = 6;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g7: {
                            id_num = 7;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g8: {
                            id_num = 8;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g9: {
                            id_num = 9;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g10: {
                            id_num = 10;
                            setBtnColorChange(v, id_num);
                        }
                        case R.id.g11: {
                            id_num = 11;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g12: {
                            id_num = 12;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g13: {
                            id_num = 13;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g14: {
                            id_num = 14;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g15: {
                            id_num = 15;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g16: {
                            id_num = 16;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g17: {
                            id_num = 17;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g18: {
                            id_num = 18;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g19: {
                            id_num = 19;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g20: {
                            id_num = 20;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g21: {
                            id_num = 21;
                            setBtnColorChange(v, id_num);
                        }
                        case R.id.g22: {
                            id_num = 22;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g23: {
                            id_num = 23;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g24: {
                            id_num = 24;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g25: {
                            id_num = 25;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g26: {
                            id_num = 26;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g27: {
                            id_num = 27;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g28: {
                            id_num = 28;
                            setBtnColorChange(v, id_num);
                            break;
                        }
                        case R.id.g29: {
                            id_num = 29;
                            setBtnColorChange(v, id_num);
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
                finish();
            }
        });

        //완료 버튼 클릭 시

        genre_btn = findViewById(R.id.genre_btn);
        genre_btn.setOnClickListener(new View.OnClickListener() {
            @Override

            //cho 회원가입시 선호장르 선택인지, 선호장르 변경인지
            public void onClick(View v) {

                //CHO
                if (changeGen == 1) {
                    Toast.makeText(getApplicationContext(), "선호장르 변경 완료", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), SignCompleteActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    //버튼 클릭 횟수에 따른 색상 변경 함수
    public void setBtnColorChange(View v, int i) {

        Intent intent = getIntent();
        final Intent intent2 = new Intent(getApplicationContext(), RecommendUserActivity.class);
        final String id_Edt = intent.getExtras().getString("id_Edt");
        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();





        if (select_state[i] == 0) {
            v.setBackgroundResource(R.drawable.genre_select_btn_box);
            select_state[i]++;

            firebaseFirestore.collection("category").document(id_Edt).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    firebaseFirestore.collection("category").document(id_Edt).update(String.valueOf(id_num+1), true);
                }
            });

        } else if (select_state[i] == 1) {
            v.setBackgroundResource(R.drawable.genre_btn_box);
            select_state[i] = 0;

            firebaseFirestore.collection("category").document(id_Edt).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    firebaseFirestore.collection("category").document(id_Edt).update(String.valueOf(id_num+1), false);
                    intent2.putExtra("id_num", String.valueOf(id_num+1));
                    startActivity(intent2);
                }
            });
        }
    }
}