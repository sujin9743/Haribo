package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FindPwActivity extends AppCompatActivity {
    Spinner pw_spinner;
    ImageButton findPw_backBtn;
    Button findPw_Btn;
    EditText id_Edt, pw_CkQA_Edt;
    Spinner pwQ_spinner;
    TextView id_Ck_Txt, pw_CkQA_Txt;
    Boolean idCk = false, pwQAck = false, pwCk = false;

    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_password);

        id_Edt = findViewById(R.id.id_Edt);
        pw_CkQA_Edt = findViewById(R.id.pw_CkQA_Edt);
        pwQ_spinner = findViewById(R.id.pwQ_spinner);
        id_Ck_Txt = findViewById(R.id.id_Ck_Txt);
        pw_CkQA_Txt = findViewById(R.id.pw_CkQA_Txt);

        //비밀번호 확인 Spinner 설정
        pw_spinner = findViewById(R.id.pwQ_spinner); //비밀번호 확인 질문 spinner
        //spinner Text설정 & 항목 추가
        ArrayAdapter<String> pw_spinner_adapter = new ArrayAdapter<>(this,
                R.layout.spinner_text, (String[])getResources().getStringArray(R.array.question));
        //spinner 항목 추가 방식
        pw_spinner_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        pw_spinner.setAdapter(pw_spinner_adapter);

        //툴바 뒤로가기
        findPw_backBtn = findViewById(R.id.findPw_backBtn);
        findPw_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //확인 클릭 시
        findPw_Btn = findViewById(R.id.findPw_Btn);
        findPw_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((id_Edt.getText().toString()).equals("") || (pw_CkQA_Edt.getText().toString()).equals("")) {
                    Toast.makeText(getApplicationContext(), "빈 칸을 확인해주세요.", Toast.LENGTH_LONG).show();


                }else{
                db.collection("member").document(id_Edt.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            id_Ck_Txt.setVisibility(View.VISIBLE);
                            if (doc.exists()) {
                                id_Ck_Txt.setText(R.string.equalId);
                            } else {
                                id_Ck_Txt.setText(R.string.notEqualId);
                            }
                        } else Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError), task.getException());
                    }
                });
                Intent intent = new Intent(getApplicationContext(), ResetPwActivity.class);
                intent.putExtra("sendID",id_Edt.getText().toString());
                startActivity(intent);
                }
            }
        });
    }
}
