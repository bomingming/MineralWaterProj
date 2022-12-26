package kr.co.company.mineralwater;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import androidx.appcompat.widget.SearchView;

public class SearchFragment extends Fragment {

    private ArrayList<String> searchList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private SearchView searchView;

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

        // SearchView 사용
        searchView = (SearchView) v.findViewById(R.id.search_view); // searchView 객체 생성
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s) {
                // 입력받은 문자열 처리
                /*Log.e("서치리스트는?", searchList.toString());
                int i = searchList.indexOf(s);
                Log.e("i의 값", Integer.toString(i));*/

                for(int i=0; i<searchList.size(); i++){
                    if(searchList.get(i) != s){
                        searchList.set(i, "얍");

                    }
                    /*Log.e("searchList 값", searchList.get(i));
                    Log.e("type", searchList.get(i).getClass().getName());
                    Log.e("입력값", s);*/

                }
                Log.e("for문 테스트", searchList.toString());

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // 입력란의 문자열이 바뀔 때 처리
                return false;
            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
}