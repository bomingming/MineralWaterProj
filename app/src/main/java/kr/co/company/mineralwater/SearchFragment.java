package kr.co.company.mineralwater;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import androidx.appcompat.widget.SearchView;

public class SearchFragment extends Fragment {

    private ArrayList<String> searchList = new ArrayList<>();
    private ArrayList<String> newSearchList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private SearchView searchView;

    private String selectData;
    public String selectName;
    public String selectSize;
    public ArrayList<String> locArr;
    public ArrayList<String> factory_name;
    public ArrayList<String> warning_stage;
    public String price = "";

    //private ImageView search_notice; // 검색 결과 없을 시 띄울 사진

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_menu2, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        adapter = new SearchAdapter(searchList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        // 받아온 JSON 데이터를 UI에 갱신하는 Thread
        new Thread() {
            @Override
            public void run() {
                searchList = adapter.JSONParse(adapter.JSONLink("https://wwater.xyz:4443/rjh/2.php"));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setSearchList(searchList);
                    }
                });
            }
        }.start();

        ImageView search_notcie = (ImageView)v.findViewById(R.id.search_notcie);

        // SearchView 사용
        searchView = (SearchView) v.findViewById(R.id.search_view); // searchView 객체 생성
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s) { // 입력받은 문자열 처리
                new Thread() {
                    @Override
                    public void run() {
                        searchList = adapter.JSONParse(adapter.JSONLink("https://wwater.xyz:4443/rjh/2.php"));
                        newSearchList.clear();
                        for(int i=0; i<searchList.size(); i++){
                            if(searchList.get(i).contains(s)){ // 검색창 입력값을 포함하는 값인지 확인
                                newSearchList.add(searchList.get(i));
                            }
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setSearchList(newSearchList);
                                if(newSearchList.isEmpty()){
                                    search_notcie.setVisibility(View.VISIBLE);
                                }else{
                                    search_notcie.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                }.start();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // 입력란의 문자열이 바뀔 때 처리
                return false;
            }
        });
        adapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View v, int position) {

                selectData = searchList.get(position);
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
}