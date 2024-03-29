package kr.co.company.mineralwater;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment implements View.OnClickListener{

    public ArrayList<String> searchList = new ArrayList<>(); // 생수 이름 리스트
    public ArrayList<String> imageList = new ArrayList<>(); // 생수 이미지 리스트
    private RecyclerView recyclerView;
    public HomeAdapter adapter;
    public RadioGroup radioGroup;

    // 지역 관련 객체
    private Button loc_button; // 직접 설정
    private Button GPS_button; // GPS로 찾기

    // GPS 활용
    private GpsTracker gpsTracker;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    public TextView myGPS_text;

    private String selectData; // 선택한 항목
    public String selectName; // 선택한 항목의 제품명
    public String selectSize; // 선택한 항목의 제품 용량
    
    // 상세 정보 데이터를 담을 변수들
    public ArrayList<String> locArr; // 공장 주소
    public ArrayList<String> factory_name; // 공장 이름
    public ArrayList<String> warning_stage; // 경고 단계
    public String price = ""; // 가격

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_menu1, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_main);
        recyclerView.setHasFixedSize(true);
        adapter = new HomeAdapter(searchList, imageList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        myGPS_text = (TextView) v.findViewById(R.id.myGPS_text);

        // GPS 기능 활용
        if(!checkLocationServicesStatus()){
            showDialogForLocationServiceSetting();
        }else{
            checkRunTimePermission();
        }

        //GPS로 지역 선택
        /*GPS_button = (Button)v.findViewById(R.id.GPS_button);
        GPS_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        GPS_button = (Button)v.findViewById(R.id.GPS_button);
        GPS_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                gpsTracker = new GpsTracker(getActivity().getApplicationContext());

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                String address = getCurrentAddress(latitude, longitude);
                myGPS_text.setText(address);
            }
        });

        new Thread(){
            @Override
            public void run(){
                searchList = adapter.JSONParse(adapter.JSONLink("https://wwater.xyz:4443/rjh/1-1.php"));
                imageList = adapter.JSONParseForImageHome(adapter.JSONLink("https://wwater.xyz:4443/rjh/1-1.php"));
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        adapter.setSearchList(searchList);
                        adapter.setImageList(imageList);
                    }
                });
            }
        }.start();
        
        // RadioGroup 객체 선언
        radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.radiobutton1){ // ~1L 버튼 누를 경우
                    new Thread(){
                        @Override
                        public void run(){
                            String location = ""; // 지역 필터링 파라미터의 초기값
                            if(myGPS_text.getText().toString().equals("선택 안 함")){
                                location = "";
                            }else{
                                location = myGPS_text.getText().toString();
                            }
                            searchList = adapter.JSONParse(adapter.JSONLink("https://wwater.xyz:4443/rjh/1-1.php?area="+location));
                            imageList = adapter.JSONParseForImageHome(adapter.JSONLink("https://wwater.xyz:4443/rjh/1-1.php?area="+location));
                            getActivity().runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                    adapter.setSearchList(searchList);
                                    adapter.setImageList(imageList);
                                }
                            });
                        }
                    }.start();
                }else if(i==R.id.radiobutton2){ // 1L~ 버튼 누를 경우
                    new Thread(){
                        @Override
                        public void run(){
                            String location = ""; // 지역 필터링 파라미터의 초기값
                            if(myGPS_text.getText().toString().equals("선택 안 함")){
                                location = "";
                            }else{
                                location = myGPS_text.getText().toString();
                            }
                            searchList = adapter.JSONParse(adapter.JSONLink("https://wwater.xyz:4443/rjh/1-2.php?area="+location));
                            imageList = adapter.JSONParseForImageHome(adapter.JSONLink("https://wwater.xyz:4443/rjh/1-2.php?area="+location));
                            getActivity().runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                    adapter.setSearchList(searchList);
                                    adapter.setImageList(imageList);
                                }
                            });
                        }
                    }.start();
                }
            }
        });

        /*myGPS_text = (TextView) v.findViewById(R.id.myGPS_text);
        //Log.e("Text 값 바뀌나", myGPS_text.toString());

        // GPS 이미지 버튼 클릭 이벤트
        ImageButton testButton = (ImageButton) v.findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("버튼 이벤트?", "발생!");
            }
        });*/

        // GPS 설정
        /*final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                String provider = location.getProvider();
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                double altitude = location.getAltitude();
                // myGPS_text.setText("서울특별시");
            }
        };*/

        // 지역 임의 선택
        loc_button = (Button)v.findViewById(R.id.loc_button);
        loc_button.setOnClickListener(this);



        // RecyclerView 항목 클릭 시 선택한 항목의 값 받아오기
        adapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                selectData = searchList.get(position); // "제품명 제품용량"
                String[] splitStr = selectData.split(" "); // 공백을 기준으로 문자열 나누기
                selectName = splitStr[0]; // 제품명 할당
                selectSize = splitStr[1]; // 제품 용량 할당
                returnName(); // 제품명 반환
                returnSize(); // 제품 용량 반환
                Intent intent = new Intent(v.getContext(), DetatilActivity.class);
                intent.putExtra("제품명", selectName);
                intent.putExtra("제품용량", selectSize);

                new Thread(){
                    @Override
                    public void run(){
                        // ml를 빈값으로 제거(L의 경우 . 제거)
                        selectSize = selectSize.replaceAll("[^0-d]","");
                        if(selectSize.contains("L")){ // L 단위인 경우 00을 추가
                            selectSize = selectSize.replaceAll("L","00");
                        }
                        locArr = adapter.JSONParseForLoc(adapter.JSONLink("https://wwater.xyz:4443/rjh/4.php?name="+selectName+"&capacity="+selectSize));
                        factory_name = adapter.JSONParseForFCName(adapter.JSONLink("https://wwater.xyz:4443/rjh/4.php?name="+selectName+"&capacity="+selectSize));
                        warning_stage = adapter.JSONParseForWarn(adapter.JSONLink("https://wwater.xyz:4443/rjh/4.php?name="+selectName+"&capacity="+selectSize));
                        price = adapter.JSONParseForPrice(adapter.JSONLink("https://wwater.xyz:4443/rjh/4.php?name="+selectName+"&capacity="+selectSize));

                        intent.putExtra("가격", price);
                        intent.putExtra("지역 정보", locArr);
                        intent.putExtra("공장 이름", factory_name);
                        intent.putExtra("경고 단계", warning_stage);

                        // 이미지 파싱
                        String imageTest = adapter.JSONParseForImage(adapter.JSONLink("https://wwater.xyz:4443/rjh/4.php?name="+selectName+"&capacity="+selectSize));
                        intent.putExtra("이미지", imageTest);

                        // 서버 연결을 위한 Thread 작업이 끝난 뒤 intent를 실행해야 변화된 값으로 setText를 할 수 있음
                        v.getContext().startActivity(intent);
                    }
                }.start();
            }
        });

        return v;
    }

    // 제품명을 반환하는 메소드
    public String returnName(){
        return selectName;
    }

    // 제품용량을 반환하는 메소드
    public String returnSize(){
        return selectSize;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loc_button: // 내 위치 설정 버튼 누를 시
                Bundle args = new Bundle();
                args.putString("key", "value");
                LocDialogFragment locDialogFragment = new LocDialogFragment();
                locDialogFragment.setArguments(args); // 데이터를 전달
                locDialogFragment.show(getActivity().getSupportFragmentManager(),"tag");

                getFragmentManager().executePendingTransactions(); // Dialog가 활성화되지 않은 상태이므로 getDialog()를 하기 위해 리스너 등록 전에 넣어주어야 하는 코드
                Button apply_btn = (Button)locDialogFragment.getDialog().findViewById(R.id.apply_btn);
                apply_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 다이얼로그 종료
                        locDialogFragment.dismiss();
                    }
                });

                locDialogFragment.setDialogResult(new LocDialogFragment.OnMyDialogResult() {
                    @Override
                    public void finish(String result) {
                        myGPS_text.setText(result);
                        new Thread(){
                            @Override
                            public void run(){
                                if(result.equals("선택 안 함")){
                                    searchList = adapter.JSONParse(adapter.JSONLink("https://wwater.xyz:4443/rjh/1-1.php"));
                                    imageList = adapter.JSONParseForImageHome(adapter.JSONLink("https://wwater.xyz:4443/rjh/1-1.php"));
                                }else{
                                    searchList = adapter.JSONParse(adapter.JSONLink("https://wwater.xyz:4443/rjh/1-1.php?area="+result));
                                    imageList = adapter.JSONParseForImageHome(adapter.JSONLink("https://wwater.xyz:4443/rjh/1-1.php?area="+result));
                                }
                                getActivity().runOnUiThread(new Runnable(){
                                    @Override
                                    public void run(){
                                        adapter.setSearchList(searchList);
                                        adapter.setImageList(imageList);
                                    }
                                });
                            }
                        }.start();
                    }
                });
                break;
        }
    }

    public boolean checkLocationServicesStatus(){
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /*@Override
    public void onRequestPermissionResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grandResults){
        if(permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length){
            boolean check_result = true;

            for(int result : grandResults){
                if(result != PackageManager.PERMISSION_GRANTED){
                    check_result = false;
                    break;
                }
            }
            if(check_result){

            }else{
                if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0]) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[1])){
                    Toast.makeText(getActivity(), "퍼미션이 거부되었습니다.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }*/

    void checkRunTimePermission(){
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);

        if(hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED){

        }else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])){
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }else{
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    public String getCurrentAddress(double latitude, double longitude){
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses;
        try{
            addresses = geocoder.getFromLocation(latitude, longitude, 7);
        }catch (IOException ioException){
            return "지오코더 서비스 사용 불가";
        }catch (IllegalArgumentException illegalArgumentException){
            return "잘못된 GPS 좌표";
        }
        if(addresses == null || addresses.size()==0){
            return "주소 미발견";
        }
        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";
    }

    private void showDialogForLocationServiceSetting(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("위치 서비스 활성화");
        builder.setMessage("GPS를 사용하기 위해서는 위치 서비스가 필요합니다.\n위치 설정을 변경해주세요!");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }

}