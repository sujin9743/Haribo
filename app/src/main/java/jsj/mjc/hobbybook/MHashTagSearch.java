package jsj.mjc.hobbybook;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.internal.FlowLayout;


public class MHashTagSearch extends AppCompatActivity{
    EditText edtHash;
    Button xBtn1,xBtn2,xBtn3, xBtn4;
    TextView addBtn,hash1, hash2, hash3, hash4;
    ImageView backBtn;
    TextView okBtn;

    public static final int REQ_CODE_ANOTHER_ACTIVITY = 1001;
    String h1,h2,h3,h4;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hashtag_search);


        edtHash = findViewById(R.id.edtHash);
        addBtn = findViewById(R.id.addBtn);
        xBtn1 = findViewById(R.id.xBtn1);
        xBtn2 = findViewById(R.id.xBtn2);
        xBtn3= findViewById(R.id.xBtn3);
        xBtn4 = findViewById(R.id.xBtn4);
        hash1 = findViewById(R.id.hash1);
        hash2 = findViewById(R.id.hash2);
        hash3 = findViewById(R.id.hash3);
        hash4 = findViewById(R.id.hash4);



        //추가btn 클릭시 해시태그 추가
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String edt = edtHash.getText().toString();

                if(edt.equals("")){//공백일경우
                    Toast.makeText(getApplicationContext(),"입력된 해시태그가 없습니다!",Toast.LENGTH_SHORT).show();
                }else{
                   if(hash1.getText().toString().equals("")){
                       hash1.setText("#"+edt);
                       h1 = "#"+edt;
                       edtHash.setText("");
                       xBtn1.setVisibility(View.VISIBLE);
                   }else{
                       if(hash2.getText().toString().equals("")){
                           hash2.setText("#"+edt);
                           h2 ="#"+edt;
                           edtHash.setText("");
                           xBtn2.setVisibility(View.VISIBLE);
                       }else{
                           if(hash3.getText().toString().equals("")){
                               hash3.setText("#"+edt);
                               h3="#"+edt;
                               edtHash.setText("");
                               xBtn3.setVisibility(View.VISIBLE);
                           }else{
                               if(hash4.getText().toString().equals("")){
                                   hash4.setText("#"+edt);
                                   h4="#"+edt;
                                   edtHash.setText("");
                                   xBtn4.setVisibility(View.VISIBLE);
                               }
                           }
                       }
                   }


                }
            }
        });

        //입력한 해시태그 삭제btn
        xBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hash1.setText("");
                xBtn1.setVisibility(View.INVISIBLE);
            }
        });
        xBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hash2.setText("");
                xBtn2.setVisibility(View.INVISIBLE);
            }
        });
        xBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hash3.setText("");
                xBtn3.setVisibility(View.INVISIBLE);
            }
        });
        xBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hash4.setText("");
                xBtn4.setVisibility(View.INVISIBLE);
            }
        });






        //입력한 해시태그 독후감작성 페이지로 값전달
        okBtn = findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.putExtra("hash1",h1);
                i.putExtra("hash2",h2);
                i.putExtra("hash1",h3);
                i.putExtra("hash2",h4);
                setResult(RESULT_OK,i);
                finish();

            }
        });




        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}