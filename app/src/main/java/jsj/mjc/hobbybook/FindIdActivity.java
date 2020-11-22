package jsj.mjc.hobbybook;

import android.content.Context;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar; //Toolbar -> androidx 사용하는 경우

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FindIdActivity extends AppCompatActivity {

    Spinner email_spinner;
    EditText email_fId_edt;
    ImageButton findId_backBtn;
    Button findId_Btn;

    String email_f, email_b;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.id_find);

        email_fId_edt = findViewById(R.id.email_fId_edt);

        //이메일 Spinner 설정
        email_spinner = findViewById(R.id.email_spinner); //이메일 spinner
        ArrayAdapter<String> email_spinner_adapter = new ArrayAdapter<>(this,
                R.layout.spinner_text, (String[])getResources().getStringArray(R.array.email));
        email_spinner_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        email_spinner.setAdapter(email_spinner_adapter);

        //툴바 뒤로가기
        findId_backBtn = findViewById(R.id.findId_backBtn);
        findId_backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //spinner 선택
        email_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                email_b = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //확인 버튼 클릭 시
        findId_Btn = findViewById(R.id.findId_Btn);
        findId_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("member").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                if(doc.getString("email_f").equals(email_fId_edt.getText().toString())
                                        && doc.getString("email_b").equals(email_spinner.getSelectedItem().toString())) {
                                    Intent intent = new Intent(getApplicationContext(), FindIdResultActivity.class);
                                    intent.putExtra("id", doc.getString("id"));
                                    startActivity(intent);
                                    break;
                                }
                                else {
                                    Log.d("TAG", "이메일 뒤 다름");
                                    Toast.makeText(FindIdActivity.this, "해당하는 이메일이 없습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            }
        });
    }
}
