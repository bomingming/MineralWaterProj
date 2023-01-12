package kr.co.company.mineralwater;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import org.reactivestreams.Subscriber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class HomeFragment extends Fragment implements View.OnClickListener{

    public ArrayList<String> searchList = new ArrayList<>(); // 생수 이름 리스트
    private RecyclerView recyclerView;
    public HomeAdapter adapter;
    public RadioGroup radioGroup;

    //지역 관련 객체
    private Button loc_button;

    private ImageView gps_mark; // GPS 이미지 뷰
    private ImageButton testButton; // 임의로 만들어 본 이미지 버튼
    public TextView myGPS_text;

    private String selectData; // 선택한 항목
    public String selectName; // 선택한 항목의 제품명
    public String selectSize; // 선택한 항목의 제품 용량
    
    // 상세 정보 데이터를 담을 변수들
    public ArrayList<String> locArr;
    public ArrayList<String> factory_name;
    public String warning_stage = "";
    public String price = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_menu1, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_main);
        recyclerView.setHasFixedSize(true);
        adapter = new HomeAdapter(searchList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        myGPS_text = (TextView) v.findViewById(R.id.myGPS_text);

        new Thread(){
            @Override
            public void run(){
                searchList = adapter.JSONParse(adapter.JSONLink("https://wwater.xyz:4443/rjh/1-1.php"));
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        adapter.setSearchList(searchList);
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
                            getActivity().runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                    adapter.setSearchList(searchList);
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
                            getActivity().runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                    adapter.setSearchList(searchList);
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
                        selectSize = selectSize.replaceAll("[^0-d]","");
                        locArr = adapter.JSONParseForLoc(adapter.JSONLink("https://wwater.xyz:4443/rjh/4.php?name="+selectName+"&capacity="+selectSize));
                        factory_name = adapter.JSONParseForFCName(adapter.JSONLink("https://wwater.xyz:4443/rjh/4.php?name="+selectName+"&capacity="+selectSize));
                        /*warning_stage = adapter.JSONParseForWarn(adapter.JSONLink("https://wwater.xyz:4443/rjh/4.php?name="+selectName+"&capacity="+selectSize));*/
                        price = adapter.JSONParseForPrice(adapter.JSONLink("https://wwater.xyz:4443/rjh/4.php?name="+selectName+"&capacity="+selectSize));
                        
                        intent.putExtra("가격", price);
                        intent.putExtra("지역 정보", locArr);
                        intent.putExtra("공장 이름", factory_name);

                        // 서버 연결을 위한 Thread 작업이 끝난 뒤 intent를 실행해야 변화된 값으로 setText를 할 수 있음
                        v.getContext().startActivity(intent);
                        getActivity().runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                //adapter.setSearchList(searchList);
                            }
                        });
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
                                }else{
                                    searchList = adapter.JSONParse(adapter.JSONLink("https://wwater.xyz:4443/rjh/1-1.php?area="+result));
                                }
                                getActivity().runOnUiThread(new Runnable(){
                                    @Override
                                    public void run(){
                                        adapter.setSearchList(searchList);
                                    }
                                });
                            }
                        }.start();
                    }
                });
                break;
        }
    }
}