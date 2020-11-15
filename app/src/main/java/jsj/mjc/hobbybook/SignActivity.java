package jsj.mjc.hobbybook;

        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
        import java.lang.reflect.Array;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Map;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.widget.Toolbar; //Toolbar -> androidx 사용하는 경우
        import androidx.appcompat.app.AppCompatActivity;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.FirebaseFirestore;

public class SignActivity extends AppCompatActivity {
    Spinner pw_spinner, email_spinner;
    ImageButton sign_backBtn;
    Button id_Ck_Btn, sign_btn;
    EditText id_Edt, pw_Edt, pw_Ck_Edt, pw_CkQA_Edt, email_id_edt, email_num_edt;
    CheckBox clause_Ck, info_Ck;
    TextView pw_ReCk_Txt, id_Ck_Txt;
    boolean id_chk = false, pw_chk = false, email_chk = false;
    //firebase firestore 선언(지은)
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        id_Edt = findViewById(R.id.id_Edt);
        pw_Edt = findViewById(R.id.pw_Edt);
        pw_Ck_Edt = findViewById(R.id.pw_Ck_Edt);
        pw_CkQA_Edt = findViewById(R.id.pw_CkQA_Edt);
        email_id_edt = findViewById(R.id.email_id_edt);
        email_num_edt = findViewById(R.id.email_num_edt);
        clause_Ck = findViewById(R.id.clause_Ck);
        info_Ck = findViewById(R.id.info_Ck);
        pw_ReCk_Txt = findViewById(R.id.pw_ReCk_Txt);
        id_Ck_Txt = findViewById(R.id.id_Ck_Txt);

        //비밀번호 확인 Spinner 설정
        pw_spinner = findViewById(R.id.pwQ_spinner); //비밀번호 확인 질문 spinner
        //spinner Text설정 & 항목 추가
        ArrayAdapter<String> pw_spinner_adapter = new ArrayAdapter<>(this,
                R.layout.spinner_text, (String[])getResources().getStringArray(R.array.question));
        //spinner 항목 추가 방식
        pw_spinner_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        pw_spinner.setAdapter(pw_spinner_adapter);

        //이메일 Spinner 설정
        email_spinner = findViewById(R.id.email_spinner); //이메일 spinner
        ArrayAdapter<String> email_spinner_adapter = new ArrayAdapter<>(this,
                R.layout.spinner_text, (String[])getResources().getStringArray(R.array.email));
        email_spinner_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        email_spinner.setAdapter(email_spinner_adapter);

        //툴바 뒤로가기
        sign_backBtn = findViewById(R.id.sign_backBtn);
        sign_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //아이디 중복확인 버튼(지은)
        id_Ck_Btn = findViewById(R.id.id_Ck_Btn);
        id_Ck_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id_chk = false;
                db.collection("member").document(id_Edt.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            id_Ck_Txt.setVisibility(View.VISIBLE);
                            if (doc.exists()) {
                                id_Ck_Txt.setText("아이디를 사용할 수 없습니다.");
                            } else {
                                id_Ck_Txt.setText("사용 가능한 아이디입니다.");
                                id_chk = true;
                            }
                        } else Log.d("e", "데이터 조회 실패", task.getException());
                    }
                });
            }
        });

        //다음 버튼 클릭 시
        sign_btn = findViewById(R.id.sign_btn);
        sign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이메일 확인을 추가하지 않았음(지은)
                if (pw_Edt.getText().toString().equals(pw_Ck_Edt.getText().toString())) {
                    pw_chk = true;
                    pw_ReCk_Txt.setVisibility(View.GONE);
                } else {
                    pw_chk = false;
                    pw_ReCk_Txt.setVisibility(View.VISIBLE);
                }
                if (id_Edt.getText().toString().equals("") || pw_Edt.getText().toString().equals("") || pw_CkQA_Edt.getText().toString().equals("") || email_id_edt.getText().toString().equals("") || !id_chk || !pw_chk || !clause_Ck.isChecked() || !info_Ck.isChecked()) {
                    Toast.makeText(SignActivity.this, "필수 항목을 확인해 주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    //member 컬렉션 데이터 등록
                    Map<String, Object> user = new HashMap<>();
                    user.put("id", id_Edt.getText().toString());
                    user.put("nickname", id_Edt.getText().toString());
                    user.put("pw", pw_Edt.getText().toString());
                    user.put("pw_q", pw_spinner.getSelectedItem().toString());
                    user.put("pw_a", pw_CkQA_Edt.getText().toString());
                    user.put("email_f", email_id_edt.getText().toString());
                    user.put("email_b", email_spinner.getSelectedItem().toString());
                    user.put("report_c", 0);
                    user.put("noti_cm", false);
                    user.put("noti_fl", false);
                    user.put("noti_lk", false);
                    user.put("noti_ms", false);

                    db.collection("member").document(id_Edt.getText().toString()).set(user).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("e", "user 데이터 등록 실패 : ", e);
                        }
                    });

                    //category 컬렉션 데이터 등록
                    Map<String, Object> cate = new HashMap<>();
                    cate.put("mem_id", id_Edt.getText().toString());
                    for(int i = 1; i <= 24; i++) {
                        cate.put("" + i, false);
                    }
                    db.collection("category").document(id_Edt.getText().toString()).set(cate).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("e", "category 데이터 등록 실패 : ", e);
                        }
                    });

                    Intent intent = new Intent(getApplicationContext(), SelectGenreActivity.class);
                    intent.putExtra("changeGen",0);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
