package jsj.mjc.hobbybook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MRealtimeBookReportSearch extends AppCompatActivity {

    ImageView backBtn, search;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m_book_report_search);

        backBtn = findViewById(R.id.backBtn);
        search = findViewById(R.id.search);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MRealtimeBookReport.class);
                startActivity(i);
            }
        });


    }
}
