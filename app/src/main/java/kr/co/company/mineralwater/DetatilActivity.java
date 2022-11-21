package kr.co.company.mineralwater;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DetatilActivity extends AppCompatActivity {

    Dialog dialog; // 커스텀 다이얼로그

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);

        // 커스텀 다이얼로그 구현
        dialog = new Dialog(DetatilActivity.this); // Dialog 초기화
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog.setContentView(R.layout.popup_warning); // xml 파일과 연동

        // 버튼 : 커스텀 다이얼로그 띄우기
        findViewById(R.id.mark_image).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                showMyDialog();
            }
        });
    }

    // 커스텀 다이얼로그 디자인하는 함수
    public void showMyDialog(){
        dialog.show(); // 다이얼로그 띄우기

        Button popup_btn = dialog.findViewById(R.id.popup_btn);
        popup_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
