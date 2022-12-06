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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;

import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;


public class HomeFragment extends Fragment {

    public ArrayList<String> searchList = new ArrayList<>(); // 생수 이름 리스트
    private RecyclerView recyclerView;
    public HomeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_menu1, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_main);
        recyclerView.setHasFixedSize(true);
        adapter = new HomeAdapter(searchList);

        //myData();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        /*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); // 추가하지 않을 경우 서버에 URL로 접근 불가*/

        /*new Thread(()->{
            searchList = adapter.JSONParse(adapter.JSONLink("https://dict.asuscomm.com:4443/rjh/"));
            Log.e("searchList 값", searchList.toString());
        }).start();*/

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                //searchList = adapter.JSONParse(adapter.JSONLink("https://dict.asuscomm.com:4443/rjh/"));
                myData();

            }
        }).start();*/

        // 화면 출력 시도
            /*NewThread newThread = new NewThread();
            newThread.start();
            Log.e("메인 서치값",searchList.toString());*/

//        NewThread newThread = new NewThread();
//        newThread.start();
        Log.e("메인 서치값",searchList.toString());

        myData();
        return v;
    }

//   @Override
//    public void onCreate(@Nullable Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        Data();
//    }
//    private void Data(){
//        searchList = adapter.JSONParse(adapter.JSONLink("https://dict.ausucomm.com:4443/rjh/"));
//    }

//    class NewThread extends Thread{
//        HomeAdapter homeAdapter;
//        ArrayList<String> tt;
//        public void run(){
//            tt = adapter.JSONParse(adapter.JSONLink("https://dict.asuscomm.com:4443/rjh/"));
//
//            Message message = mmHandler.obtainMessage();
//            Bundle bundle = new Bundle();
//            bundle.putStringArrayList("water", tt);
//            message.setData(bundle);
//            mmHandler.sendMessage(message);
//
//        }
//    }
//
//    class mHandle extends Handler{
//
//        @Override
//        public void handleMessage(@NonNull Message message){
//            super.handleMessage(message);
//
//            Bundle bundle = message.getData();
//            searchList = bundle.getStringArrayList("water");
//            Log.e("핸들러 결과", searchList.toString());
//
//
//        }
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    private void myData(){

        new Thread(()->{
            searchList.add(new String("목록 1"));
            searchList.add(new String("목록 2"));
            searchList.add(new String("목록 3"));
            searchList.add(new String("목록 4"));
            //Log.e("searchList 첫번째 값", searchList.toString());

            //searchList = adapter.JSONParse(adapter.JSONLink("https://dict.asuscomm.com:4443/rjh/"));
            //searchList = adapter.JSONParse(adapter.JSONLink("https://dict.asuscomm.com:4443/rjh/"));

            Log.e("searchList 값?", searchList.toString());
        }).start();
        /*searchList = adapter.JSONParse(adapter.JSONLink("https://dict.asuscomm.com:4443/rjh/"));
        Log.e("searchList 값?", searchList.toString());*/
    }



}
