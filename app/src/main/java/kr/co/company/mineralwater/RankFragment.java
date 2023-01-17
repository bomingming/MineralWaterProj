package kr.co.company.mineralwater;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RankFragment extends Fragment {

    private ArrayList<String> rankList = new ArrayList<>();
    private ArrayList<String> rankNum = new ArrayList<>();
    private RecyclerView recyclerView;
    private RankAdapter adapter;

    private String selectData;
    public String selectName;
    public String selectSize;
    public ArrayList<String> locArr;
    public ArrayList<String> factory_name;
    public ArrayList<String> warning_stage;
    public String price = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_menu3, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_rank);
        recyclerView.setHasFixedSize(true);
        adapter = new RankAdapter(rankList,rankNum);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        // 받아온 JSON 데이터를 UI에 갱신하는 Thread
        new Thread(){
            @Override
            public void run(){
                rankList = adapter.JSONParse(adapter.JSONLink("https://wwater.xyz:4443/rjh/3.php"));
                int num = 0;
                for(int i=0; i<rankList.size(); i++){
                    num++;
                    rankNum.add(Integer.toString(num));
                }
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        adapter.setSearchList(rankList);
                    }
                });
            }
        }.start();

        adapter.setOnItemClickListener(new RankAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View v, int position) {

                selectData = rankList.get(position);
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
