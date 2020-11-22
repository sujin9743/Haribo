package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity {
    Button loginBtn;
    TextView findID, findPW, joinUS;
    EditText loginID, loginPW;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginID = findViewById(R.id.loginID);
        loginPW = findViewById(R.id.loginPW);

        //로그인 버튼 클릭 시
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loginID.getText().toString().equals(getResources().getString(R.string.empty)) && !loginPW.getText().toString().equals(getResources().getString(R.string.empty))) {
                    db.collection(getResources().getString(R.string.mem)).document(loginID.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot doc = task.getResult();
                                if (loginPW.getText().toString().equals(doc.getString(getString(R.string.password)))) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra(getResources().getString(R.string.lid), loginID.getText().toString());
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, R.string.idPwCkTxt, Toast.LENGTH_SHORT).show();
                                    loginID.setText(getResources().getString(R.string.empty));
                                    loginPW.setText(getResources().getString(R.string.empty));
                                }
                            } else {
                                Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError) + task.getException());
                            }
                        }
                    });
                }
            }
        });

        //화면 전환
        findID = findViewById(R.id.findID);
        findID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindIdActivity.class);
                startActivity(intent);
            }
        });
        findPW = findViewById(R.id.findPW);
        findPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindPwActivity.class);
                startActivity(intent);
            }
        });
        joinUS = findViewById(R.id.joinUS);
        joinUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignActivity.class);
                startActivity(intent);
            }
        });
    }
}
