package kr.co.company.mineralwater;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DetatilActivity extends AppCompatActivity {

    Dialog dialog; // 커스텀 다이얼로그

    public HomeAdapter adapter;
    public ArrayList<String> searchList = new ArrayList<>();
    public ArrayList<String> imageList = new ArrayList<>();
    public HomeFragment homeFragment;

    // 상세 정보
    private TextView water_name;
    private TextView water_size;
    private TextView water_price;

    private TextView water_detail_info, water_detail_info2, water_detail_info3, water_detail_info4, water_detail_info5, water_detail_info6, water_detail_info7, water_detail_info8;

    // 경고 단계
    private TextView water_warning, water_warning2, water_warning3, water_warning4, water_warning5, water_warning6, water_warning7, water_warning8;

    // 공장 별 경고 단계
    private ImageView grade1_1, grade1_2, grade1_3;
    private ImageView grade2_1, grade2_2, grade2_3;
    private ImageView grade3_1, grade3_2, grade3_3;
    private ImageView grade4_1, grade4_2, grade4_3;
    private ImageView grade5_1, grade5_2, grade5_3;
    private ImageView grade6_1, grade6_2, grade6_3;
    private ImageView grade7_1, grade7_2, grade7_3;
    private ImageView grade8_1, grade8_2, grade8_3;

    // 공장 분할선
    private View line1, line2, line3, line4, line5, line6, line7, line8;

    private String[] infoArr = {"", "", "", "","", "", "", ""}; // 공장 주소 담을 문자열 배열
    private String[] fcArr = {"", "", "", "","", "", "", ""}; // 공장 이름 담을 문자열 배열
    private String[] stageArr = {"", "", "", "","", "", "", ""}; // 경고 단계 담을 문자열 배열

    private ImageView mark_image; // 위험 단계 마크
    private ImageView mark_image2; // 보통 단계 마크
    private ImageView mark_image3; // 안전 단계 마크

    private ImageView detail_image; // 이미지 파싱 시도

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

        // 상세 정보 별 등급 마크
        grade1_1 = findViewById(R.id.grade1_1);
        grade1_2 = findViewById(R.id.grade1_2);
        grade1_3 = findViewById(R.id.grade1_3);

        grade2_1 = findViewById(R.id.grade2_1);
        grade2_2 = findViewById(R.id.grade2_2);
        grade2_3 = findViewById(R.id.grade2_3);

        grade3_1 = findViewById(R.id.grade3_1);
        grade3_2 = findViewById(R.id.grade3_2);
        grade3_3 = findViewById(R.id.grade3_3);

        grade4_1 = findViewById(R.id.grade4_1);
        grade4_2 = findViewById(R.id.grade4_2);
        grade4_3 = findViewById(R.id.grade4_3);

        grade5_1 = findViewById(R.id.grade5_1);
        grade5_2 = findViewById(R.id.grade5_2);
        grade5_3 = findViewById(R.id.grade5_3);

        grade6_1 = findViewById(R.id.grade6_1);
        grade6_2 = findViewById(R.id.grade6_2);
        grade6_3 = findViewById(R.id.grade6_3);

        grade7_1 = findViewById(R.id.grade7_1);
        grade7_2 = findViewById(R.id.grade7_2);
        grade7_3 = findViewById(R.id.grade7_3);

        grade8_1 = findViewById(R.id.grade8_1);
        grade8_2 = findViewById(R.id.grade8_2);
        grade8_3 = findViewById(R.id.grade8_3);

        ImageView[] grade_safe = {grade1_1, grade2_1, grade3_1, grade4_1, grade5_1, grade6_1, grade7_1, grade8_1};
        ImageView[] grade_middle = {grade1_2, grade2_2,grade3_2,grade4_2,grade5_2,grade6_2,grade7_2,grade8_2};
        ImageView[] grade_dangerous = {grade1_3, grade2_3,grade3_3,grade4_3,grade5_3,grade6_3,grade7_3,grade8_3};

        // 공장 분할선 객체 할당
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);
        line4 = findViewById(R.id.line4);
        line5 = findViewById(R.id.line5);
        line6 = findViewById(R.id.line6);
        line7 = findViewById(R.id.line7);
        line8 = findViewById(R.id.line8);

        View[] lineArr = {line1, line2, line3, line4, line5, line6, line7, line8};

        detail_image = findViewById(R.id.detail_image); // 이미지 객체 선언

        // 상세 정보 파싱
        ArrayList<String> locArr = intent.getStringArrayListExtra("지역 정보");

        for(int i=0; i<locArr.size(); i++){
            infoArr[i] = "("+locArr.get(i)+")";
        }

        ArrayList<String> factory_name = intent.getStringArrayListExtra("공장 이름");
        for(int i=0; i<factory_name.size(); i++){
            fcArr[i] = factory_name.get(i);
        }

        ArrayList<String> warning_stage = intent.getStringArrayListExtra("경고 단계");
        int max = 0;
        for(int i=0; i<warning_stage.size(); i++){
            stageArr[i] = "공장 경고 단계";
            lineArr[i].setVisibility(View.VISIBLE);
            switch (warning_stage.get(i)){
                case "0": // 안전
                    grade_safe[i].setVisibility(View.VISIBLE);
                    break;
                case "1": // 보통
                    grade_middle[i].setVisibility(View.VISIBLE);
                    break;
                case "2": // 위험
                    grade_dangerous[i].setVisibility(View.VISIBLE);
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

        water_detail_info.setText(fcArr[0]+"\n"+infoArr[0]);
        water_detail_info2.setText(fcArr[1]+"\n"+infoArr[1]);
        water_detail_info3.setText(fcArr[2]+"\n"+infoArr[2]);
        water_detail_info4.setText(fcArr[3]+"\n"+infoArr[3]);
        water_detail_info5.setText(fcArr[4]+"\n"+infoArr[4]);
        water_detail_info6.setText(fcArr[5]+"\n"+infoArr[5]);
        water_detail_info7.setText(fcArr[6]+"\n"+infoArr[6]);
        water_detail_info8.setText(fcArr[7]+"\n"+infoArr[7]);

        water_warning.setText(stageArr[0]);
        water_warning2.setText(stageArr[1]);
        water_warning3.setText(stageArr[2]);
        water_warning4.setText(stageArr[3]);
        water_warning5.setText(stageArr[4]);
        water_warning6.setText(stageArr[5]);
        water_warning7.setText(stageArr[6]);
        water_warning8.setText(stageArr[7]);
        
        String imageCode = intent.getStringExtra("이미지"); // 선택한 항목의 이미지 코드

        // 이미지 코드(문자열)에서 알맞은 형식으로 문자열 변환
        imageCode = imageCode.replaceAll("img id=\\\"image_size\\\" src=\\\"data:image/jpeg;base64,","");
        imageCode = imageCode.replaceAll("\"/>","");

        // byte 배열로 변환 후 비트맵으로 변환하여 이미지 바꾸어줌
        byte[] encodeByte = Base64.decode(imageCode, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        bitmap = Bitmap.createScaledBitmap(bitmap, 600, 600, true);
        detail_image.setImageBitmap(bitmap);

        adapter = new HomeAdapter(searchList, imageList);
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
