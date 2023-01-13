package kr.co.company.mineralwater;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DetatilActivity extends AppCompatActivity {

    Dialog dialog; // 커스텀 다이얼로그

    public HomeAdapter adapter;
    public ArrayList<String> searchList = new ArrayList<>();
    public HomeFragment homeFragment;

    // 상세 정보
    private TextView water_name;
    private TextView water_size;
    private TextView water_price;
    private TextView water_detail_info;
    private TextView water_detail_info2;
    private TextView water_detail_info3;
    private TextView water_detail_info4;
    private TextView water_detail_info5;
    private TextView water_detail_info6;
    private TextView water_detail_info7;
    private TextView water_detail_info8;

    // 경고 단계
    private TextView water_warning;
    private TextView water_warning2;
    private TextView water_warning3;
    private TextView water_warning4;
    private TextView water_warning5;
    private TextView water_warning6;
    private TextView water_warning7;
    private TextView water_warning8;

    private String[] infoArr = {"", "", "", "","", "", "", ""}; // 공장 주소 담을 문자열 배열
    private String[] fcArr = {"", "", "", "","", "", "", ""}; // 공장 이름 담을 문자열 배열
    private String[] stageArr = {"", "", "", "","", "", "", ""}; // 경고 단계 담을 문자열 배열

    private ImageView mark_image; // 위험 단계 마크
    private ImageView mark_image2; // 보통 단계 마크
    private ImageView mark_image3; // 안전 단계 마크

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);

        // 경고 단계 마크 View 객체 연결
        mark_image = findViewById(R.id.mark_image);
        mark_image2 = findViewById(R.id.mark_image2);
        mark_image3 = findViewById(R.id.mark_image3);

        // 액션바 타이틀 수정
        ActionBar ac = getSupportActionBar();
        ac.setTitle("제품 상세 정보");

        // 커스텀 다이얼로그 구현
        dialog = new Dialog(DetatilActivity.this); // Dialog 초기화
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog.setContentView(R.layout.popup_warning); // xml 파일과 연동

        // 버튼 : 커스텀 다이얼로그 띄우기
        mark_image.setOnClickListener(new View.OnClickListener(){ // 위험 마크

            @Override
            public void onClick(View view) {
                showMyDialog();
            }
        });

        mark_image2.setOnClickListener(new View.OnClickListener(){ // 불안 마크

            @Override
            public void onClick(View view) {
                showMyDialog();
            }
        });

        mark_image3.setOnClickListener(new View.OnClickListener(){ // 안전 마크

            @Override
            public void onClick(View view) {
                showMyDialog();
            }
        });

        //homeFragment
        Intent intent = getIntent();

        water_name = findViewById(R.id.water_name); // 제품명 객체
        water_size = findViewById(R.id.water_size); // 용량 객체
        water_price = findViewById(R.id.water_price); // 가격 객체
        // 제조 공장 객체
        water_detail_info = findViewById(R.id.water_detail_info);
        water_detail_info2 = findViewById(R.id.water_detail_info2);
        water_detail_info3 = findViewById(R.id.water_detail_info3);
        water_detail_info4 = findViewById(R.id.water_detail_info4);
        water_detail_info5 = findViewById(R.id.water_detail_info5);
        water_detail_info6 = findViewById(R.id.water_detail_info6);
        water_detail_info7 = findViewById(R.id.water_detail_info7);
        water_detail_info8 = findViewById(R.id.water_detail_info8);
        // 경고 단계 객체
        water_warning = findViewById(R.id.water_warning);
        water_warning2 = findViewById(R.id.water_warning2);
        water_warning3 = findViewById(R.id.water_warning3);
        water_warning4 = findViewById(R.id.water_warning4);
        water_warning5 = findViewById(R.id.water_warning5);
        water_warning6 = findViewById(R.id.water_warning6);
        water_warning7 = findViewById(R.id.water_warning7);
        water_warning8 = findViewById(R.id.water_warning8);

        // 상세 정보 파싱
        ArrayList<String> locArr = intent.getStringArrayListExtra("지역 정보");

        for(int i=0; i<locArr.size(); i++){
            infoArr[i] = locArr.get(i);
        }

        ArrayList<String> factory_name = intent.getStringArrayListExtra("공장 이름");
        for(int i=0; i<factory_name.size(); i++){
            fcArr[i] = factory_name.get(i);
        }

        ArrayList<String> warning_stage = intent.getStringArrayListExtra("경고 단계");
        int max = 0;
        for(int i=0; i<warning_stage.size(); i++){
            switch (warning_stage.get(i)){
                case "0":
                    stageArr[i] = "공장 경고 단계 : 안전";
                    break;
                case "1":
                    stageArr[i] = "공장 경고 단계 : 보통";
                    break;
                case "2":
                    stageArr[i] = "공장 경고 단계 : 위험";
                    break;
            }
            if(Integer.parseInt(warning_stage.get(i))>max){
                max = Integer.parseInt(warning_stage.get(i));
            }
        }

        if (max == 0){
            mark_image3.setVisibility(View.VISIBLE);
        }else if(max==1){
            mark_image2.setVisibility(View.VISIBLE);
        }else{
            mark_image.setVisibility(View.VISIBLE);
        }

        water_name.setText("제품명 : " +intent.getStringExtra("제품명"));
        water_size.setText("용량 : " +intent.getStringExtra("제품용량"));
        water_price.setText("가격 : "+intent.getStringExtra("가격")+"원");

        water_detail_info.setText(fcArr[0]+"\n"+infoArr[0]+"");
        water_detail_info2.setText(fcArr[1]+"\n"+infoArr[1]+"");
        water_detail_info3.setText(fcArr[2]+"\n"+infoArr[2]+"");
        water_detail_info4.setText(fcArr[3]+"\n"+infoArr[3]+"");
        water_detail_info5.setText(fcArr[4]+"\n"+infoArr[4]+"");
        water_detail_info6.setText(fcArr[5]+"\n"+infoArr[5]+"");
        water_detail_info7.setText(fcArr[6]+"\n"+infoArr[6]+"");
        water_detail_info8.setText(fcArr[7]+"\n"+infoArr[7]+"");

        water_warning.setText(stageArr[0]);
        water_warning2.setText(stageArr[1]);
        water_warning3.setText(stageArr[2]);
        water_warning4.setText(stageArr[3]);
        water_warning5.setText(stageArr[4]);
        water_warning6.setText(stageArr[5]);
        water_warning7.setText(stageArr[6]);
        water_warning8.setText(stageArr[7]);



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
