package kr.co.company.mineralwater;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DetatilActivity extends AppCompatActivity {

    Dialog dialog; // 커스텀 다이얼로그

    // 상세 정보에 데이터 연결 시도 중
    public HomeAdapter adapter;
    public ArrayList<String> searchList = new ArrayList<>();
    public HomeFragment homeFragment;
    public String s;

    public String selectName;

    private TextView water_name;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);

        water_name = findViewById(R.id.water_name);

        // 액션바 타이틀 수정
        ActionBar ac = getSupportActionBar();
        ac.setTitle("제품 상세 정보");

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

        /*new Thread(){
            @Override
            public void run(){
                //searchList = adapter.JSONParse(adapter.JSONLink("https://wwater.xyz:4443/rjh/1-1.php"));
                //String testStr = adapter.JSONLink("https://wwater.xyz:4443/rjh/1-1.php");

                (DetatilActivity.this).runOnUiThread(new Runnable(){ // Activity 형식에 맞게 DetatilActivity.this로 수정
                    @Override
                    public void run(){
                        //adapter.setSearchList(searchList);
                    }
                });
            }
        }.start();*/

        //homeFragment.
        Intent intent = getIntent();
        water_name.setText(intent.getStringExtra("값 테스트"));
        adapter = new HomeAdapter(searchList);


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
