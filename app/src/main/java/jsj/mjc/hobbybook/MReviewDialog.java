package jsj.mjc.hobbybook;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MReviewDialog extends Dialog {

    private Context mContext;

    private ImageView backBtn;
    TextView upload;
    RatingBar stars;
    EditText edt;
    String str;

    public MReviewDialog(Context context) {
        super(context);
        mContext = context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_review_dialog);

        backBtn = findViewById(R.id.backBtn);
        upload = findViewById(R.id.upload);
        stars = findViewById(R.id.stars);
        edt = findViewById(R.id.edt);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dismiss();
            }
        });

        stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //별개수에 따른 별 한칸당 평균
                float st = 10.0f/ratingBar.getNumStars();

                //String 객체를 이용해서 구한 평균값을 소수점 한자리로 표현..
                str = String.format("%.1f",(st * rating) );
            }
        });
    }
}
