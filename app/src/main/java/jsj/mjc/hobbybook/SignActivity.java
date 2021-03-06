package jsj.mjc.hobbybook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
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
    Button id_Ck_Btn, email_ck_btn, sign_btn;
    EditText id_Edt, pw_Edt, pw_Ck_Edt, pw_CkQA_Edt, email_id_edt, email_num_edt;
    CheckBox clause_Ck, info_Ck;
    TextView pw_ReCk_Txt, id_Ck_Txt, email_Ck_Txt, access_term_btn, personal_info_btn;
    boolean id_chk = false, pw_chk = false, email_chk = false;
    LayoutInflater layoutInflater;
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
        email_Ck_Txt = findViewById(R.id.email_Ck_Txt);
        clause_Ck = findViewById(R.id.clause_Ck);
        info_Ck = findViewById(R.id.info_Ck);
        pw_ReCk_Txt = findViewById(R.id.pw_ReCk_Txt);
        id_Ck_Txt = findViewById(R.id.id_Ck_Txt);
        access_term_btn = findViewById(R.id.access_term_btn);
        personal_info_btn = findViewById(R.id.personal_info_btn);

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
                db.collection(getString(R.string.mem)).document(id_Edt.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            id_Ck_Txt.setVisibility(View.VISIBLE);
                            if (doc.exists()) {
                                id_Ck_Txt.setText(R.string.unableId);
                            } else {
                                id_Ck_Txt.setText(R.string.ableId);
                                id_chk = true;
                            }
                        } else Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError), task.getException());
                    }
                });
            }
        });

        //이메일 중복확인 버튼
        email_ck_btn = findViewById(R.id.email_ck_btn);
        email_ck_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email_chk = true;
                email_Ck_Txt.setVisibility(View.VISIBLE);
                db.collection(getString(R.string.mem)).whereEqualTo(getString(R.string.emf), email_id_edt.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                if (email_spinner.getSelectedItem().toString().equals(doc.getString(getString(R.string.emb)))) {
                                    email_Ck_Txt.setText(R.string.unableEmail);
                                    email_chk = false;
                                }
                            }
                        } else Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError), task.getException());
                    }
                });
                if (email_chk)
                    email_Ck_Txt.setText(R.string.ableEmail);
            }
        });

        access_term_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccessTermActivity.class);
                startActivity(intent);
            }
        });

        personal_info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PersonalInfoActivity.class);
                startActivity(intent);
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
                if (id_Edt.getText().toString().equals(getResources().getString(R.string.empty)) || pw_Edt.getText().toString().equals(getResources().getString(R.string.empty))
                        || pw_CkQA_Edt.getText().toString().equals(getResources().getString(R.string.empty)) || email_id_edt.getText().toString().equals(getResources().getString(R.string.empty))
                        || !id_chk || !pw_chk || !email_chk|| !clause_Ck.isChecked() || !info_Ck.isChecked()) {
                    Toast.makeText(SignActivity.this, R.string.needCkTxt, Toast.LENGTH_SHORT).show();
                } else {
                    //member 컬렉션 데이터 등록
                    Map<String, Object> user = new HashMap<>();
                    user.put(getString(R.string.id), id_Edt.getText().toString());
                    user.put(getResources().getString(R.string.name), id_Edt.getText().toString());
                    user.put(getString(R.string.password), pw_Edt.getText().toString());
                    user.put(getString(R.string.pwq), pw_spinner.getSelectedItem().toString());
                    user.put(getString(R.string.pwa), pw_CkQA_Edt.getText().toString());
                    user.put(getString(R.string.emf), email_id_edt.getText().toString());
                    user.put(getString(R.string.emb), email_spinner.getSelectedItem().toString());
                    user.put(getString(R.string.report), 0);
                    user.put(getString(R.string.notiCm), false);
                    user.put(getString(R.string.notiFl), false);
                    user.put(getString(R.string.notiLk), false);
                    user.put(getString(R.string.notiMs), false);

                    db.collection(getString(R.string.mem)).document(id_Edt.getText().toString()).set(user).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataAddError), e);
                        }
                    });

                    //category 컬렉션 데이터 등록
                    Map<String, Object> cate = new HashMap<>();
                    cate.put(getResources().getString(R.string.mid), id_Edt.getText().toString());
                    for(int i = 1; i <= 24; i++) {
                        cate.put(getResources().getString(R.string.empty) + i, false);
                    }
                    db.collection(getString(R.string.cate)).document(id_Edt.getText().toString()).set(cate).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataAddError), e);
                        }
                    });

                    Intent intent = new Intent(getApplicationContext(), SelectGenreActivity.class);
                    intent.putExtra(getString(R.string.cg),0);
                    intent.putExtra(getString(R.string.idedt), id_Edt.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}