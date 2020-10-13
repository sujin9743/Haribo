package jsj.mjc.hobbybook;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class MCutOffDialog extends Dialog {
    public MCutOffDialog(@NonNull Context context) {
        super(context);
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_cut_off_dialog);

        Button cancelBtn, okBtn;
        TextView user;

        user = findViewById(R.id.user);
        // 받아온 데이터값 삽입


        cancelBtn = findViewById(R.id.cancelBtn);
        okBtn = findViewById(R.id.okBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 데이터값 전달
                dismiss();
            }
        });
    }
}
