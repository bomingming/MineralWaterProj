package kr.co.company.mineralwater;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.Observable;


public class HomeFragment extends Fragment {

    public ArrayList<String> searchList = new ArrayList<>(); // 생수 이름 리스트
    private RecyclerView recyclerView;
    public HomeAdapter adapter;
    public RadioGroup radioGroup;

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
                            searchList = adapter.JSONParse(adapter.JSONLink("https://wwater.xyz:4443/rjh/1-1.php"));
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
                            searchList = adapter.JSONParse(adapter.JSONLink("https://wwater.xyz:4443/rjh/1-2.php"));
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
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
}


