package jsj.mjc.hobbybook;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ReportDialog extends Dialog{
    private Context mContext;

    private Button cancel_btn, ok_btn;
    static int i = 0;
    static final int cNum = 5;
    RadioGroup radioGroup;
    RadioButton [] radioButtons = new RadioButton[cNum];
    static int [] rbtnIds = new int[cNum];
    public ReportDialog(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_report);

        cancel_btn = findViewById(R.id.report_cancel_btn);
        ok_btn = findViewById(R.id.report_ok_btn);
        ok_btn.setClickable(false);

        radioGroup = findViewById(R.id.report_radioG);

        for(i = 0; i < cNum; i++) {
            radioButtons[i] = findViewById(R.id.report_rbtn_c0 + i);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                ok_btn.setClickable(true);
                ok_btn.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.darkGreen));
                ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "신고가 성공적으로 접수됐습니다.", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                });
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
