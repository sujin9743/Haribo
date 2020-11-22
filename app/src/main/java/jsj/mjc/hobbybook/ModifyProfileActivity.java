package jsj.mjc.hobbybook;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ModifyProfileActivity extends AppCompatActivity {
    TextView modify_email_spinner;
    String loginId;
    private static final int PICK_FROM_ALBUM=1;
    CircleImageView modify_profile_Img;
    EditText modify_id_edt, modify_pw_edt, modify_pwCk_edt;
    TextView modify_email_id_edt;
    TextView modify_del_tv, modify_pwReCk_txt, modify_email_txt;
    Button add_profile_Img;
    boolean pw_chk = false;
    StorageReference storageRef;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile_modify);

        loginId = getIntent().getStringExtra(getResources().getString(R.string.lid));
        docRef = db.collection(getString(R.string.mem)).document(loginId);

        //툴바 설정
        Toolbar profile_modify_toolbar = (Toolbar) findViewById(R.id.profile_modify_toolbar);
        setSupportActionBar(profile_modify_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_24dp);

        modify_id_edt = findViewById(R.id.modify_id_edt);
        modify_pw_edt = findViewById(R.id.modify_pw_edt);
        modify_pwCk_edt = findViewById(R.id.modify_pwCk_edt);
        modify_pwReCk_txt = findViewById(R.id.modify_pwReCk_txt);
        modify_email_txt = findViewById(R.id.emailConTxt);
        modify_email_id_edt = findViewById(R.id.emailTxt);
        modify_email_spinner = findViewById(R.id.emailConTxt);
        //modify_email_id_edt = findViewById(R.id.modify_email_id_edt);

        //이메일 Spinner 설정
        /*modify_email_spinner = findViewById(R.id.modify_email_spinner); //이메일 spinner
        final ArrayAdapter<String> email_spinner_adapter = new ArrayAdapter<>(this,
                R.layout.spinner_text, (String[])getResources().getStringArray(R.array.email));
        email_spinner_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        modify_email_spinner.setAdapter(email_spinner_adapter);*/

        //갤러리 사진 받기
        storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imgRef = storageRef.child(getString(R.string.pimg) + loginId +getString(R.string.jpg));
        modify_profile_Img = findViewById(R.id.modify_profile_Img);
        imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(ModifyProfileActivity.this).load(uri).into(modify_profile_Img);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataLoadError) + exception);
            }
        });
        modify_profile_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImg();
            }
        });
        add_profile_Img = findViewById(R.id.add_profile_Img);
        add_profile_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImg();
            }
        });

        //기존 데이터 띄우기
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        //TODO 사진 업로드
                        modify_id_edt.setText(doc.getString(getResources().getString(R.string.name)));
                        modify_email_txt.setText(doc.getString(getString(R.string.emf)) + getString(R.string.golbang) + doc.getString(getString(R.string.emb)));
                    }
                }
            }
        });

        modify_del_tv = findViewById(R.id.modify_del_tv);
        modify_del_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ModifyProfileActivity.this);
                builder.setTitle(R.string.memdel).setMessage(R.string.realMemDel).setCancelable(true);
                builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(ModifyProfileActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        MainActivity.mainActivity.finish();
                        finish();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.profile_modify_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
             //수정 버튼 클릭 시 이벤트 구현
            case R.id.modifyBtn:
                Map<String, Object> user = new HashMap<>();
                //TODO 사진 업로드 처리, 이메일 인증 처리
                user.put(getResources().getString(R.string.name), modify_id_edt.getText().toString());
                if (modify_pw_edt.getText().toString().equals(getResources().getString(R.string.empty))) {
                    modify_pwReCk_txt.setVisibility(View.GONE);
                    pw_chk = true;
                } else {
                    if (modify_pw_edt.getText().toString().equals(modify_pwCk_edt.getText().toString())) {
                        user.put(getString(R.string.password), modify_pw_edt.getText().toString());
                        modify_pwReCk_txt.setVisibility(View.GONE);
                        pw_chk = true;
                    } else {
                        modify_pwReCk_txt.setVisibility(View.VISIBLE);
                        pw_chk = false;
                    }
                }
                if (pw_chk) {

                    user.put(getString(R.string.emf), modify_email_id_edt.getText().toString());
                    user.put(getString(R.string.emb), modify_email_spinner.getText().toString());

                    db.collection(getString(R.string.mem)).document(loginId).update(user).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(getResources().getString(R.string.logTag), getResources().getString(R.string.dataUpdateError), e);
                        }
                    });
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void changeImg () {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,PICK_FROM_ALBUM);
    }
}
