package jsj.mjc.hobbybook;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReportDialog extends Dialog{
    private Context mContext;

    private Button cancel_btn, ok_btn;
    static int i = 0;
    static final int cNum = 5;
    RadioGroup radioGroup;
    RadioButton [] radioButtons = new RadioButton[cNum];
    static int [] rbtnIds = new int[cNum];
    public String userId;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                        db.collection("member").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot doc = task.getResult();
                                    int report_c = doc.getLong("report_c").intValue() + 1;
                                    db.collection("member").document(userId).update("report_c", report_c).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(getContext().getResources().getString(R.string.logTag), getContext().getResources().getString(R.string.dataUpdateError) + e);
                                        }
                                    });
                                }
                            }
                        });
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
