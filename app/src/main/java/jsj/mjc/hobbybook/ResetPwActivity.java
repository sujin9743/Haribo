package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ResetPwActivity extends AppCompatActivity {
    ImageButton resetPw_backBtn;
    Button resetPw_Btn;
    EditText id_edt, pw_edt, pw_ck_edt;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> savePW = new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        id_edt = findViewById(R.id.id_Edt);
        pw_edt = findViewById(R.id.pw_Edt);
        pw_ck_edt = findViewById(R.id.pw_Ck_Edt);
        //FindRwActivity에서 넘어온 아이디, setText하기
        final String setID = getIntent().getStringExtra("sendID");
        id_edt.setText(setID);

        //툴바 뒤로가기
        resetPw_backBtn = findViewById(R.id.resetPw_backBtn);
        resetPw_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //확인 클릭 시
        resetPw_Btn = findViewById(R.id.resetPw_Btn);
        resetPw_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((id_edt.getText().toString()).equals("") || (pw_edt.getText().toString()).equals("") || (pw_ck_edt.getText().toString()).equals("")){
                    Toast.makeText(getApplicationContext(),"빈칸을 확인해주세요.",Toast.LENGTH_SHORT).show();
                }else{
                    if((pw_edt.getText().toString()).equals(pw_ck_edt.getText().toString())){
                        savePW.put(getResources().getString(R.string.password), pw_edt.getText().toString());

                        db.collection(getResources().getString(R.string.mem)).document(setID).update(getResources().getString(R.string.password), pw_edt.getText().toString()).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG", "onFailure: 업데이트 실패 " + e);
                            }
                        });
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                        startActivity(intent);
                    }else {
                       Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다. 확인해주세요.",Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }
}
