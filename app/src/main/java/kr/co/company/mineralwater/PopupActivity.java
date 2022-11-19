package kr.co.company.mineralwater;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class PopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_warning);

        findViewById(R.id.popup_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 코드가 말을 안 들어요
                finish();
                Intent intent = new Intent(PopupActivity.this, PopupActivity.class);
                startActivity(intent);
            }
        });
    }
}
