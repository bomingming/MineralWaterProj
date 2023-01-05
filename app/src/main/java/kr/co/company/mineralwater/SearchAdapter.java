package kr.co.company.mineralwater;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>{

    private ArrayList<String> localDataSet;

    // 뷰 홀더 클래스
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_search_list);
            imageView = itemView.findViewById(R.id.water_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetatilActivity.class);
                    intent.putExtra("값 테스트", "검색창과 연결 성공");
                    view.getContext().startActivity(intent);
                }
            });
        }
        public TextView getTextView(){
            return textView;
        }

    }

    // 생성자(생성자를 통해서 데이터 전달 받음)
    public SearchAdapter(ArrayList<String> dataSet){
        localDataSet = dataSet;
    } // Fragment에서 Adapter 호출 시 들어간 매개변수가 목록의 값

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_search, parent, false);
        MyViewHolder viewHolder = new SearchAdapter.MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(localDataSet.get(position)); // 예제에서 getName() 임의로 제거
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
    
    // RecyclerView에 들어갈 searchList의 값을 변경하는 메소드
    public void setSearchList(ArrayList<String> searchList){
        this.localDataSet = searchList;
        notifyDataSetChanged();
    }

    public String JSONLink(String url){

        String receiveMsg = ""; // 초기화 필수

        try {
            InputStream is = new URL(url).openStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String str;
            StringBuffer buffer = new StringBuffer();

            while((str = rd.readLine()) != null){
                buffer.append(str);
            }
            receiveMsg = buffer.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return receiveMsg; // JSON 데이터를 반환
    }

    // 받아온 JSON 데이터를 ArrayList로 변환
    ArrayList<String> JSONParse(String jsonStr){
        ArrayList<String> DataArray = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(jsonStr);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String waterName = jsonObject.getString("name"); // 제품명 문자열로 파싱
                String waterMl = jsonObject.getString("capacity"); // 제품 용량 문자열로 파싱
                if(Integer.parseInt(waterMl) >= 1000){
                    double waterMlInt = Integer.parseInt(waterMl);
                    waterMlInt = waterMlInt/1000;
                    waterMl = Double.toString(waterMlInt);
                    String waterData = waterName+" "+waterMl+"L"; // '제품명, 제품용량ml' 형식으로 문자열 저장
                    DataArray.add(waterData);
                }else{
                    String waterData = waterName+" "+waterMl+"ml"; // '제품명, 제품용량ml' 형식으로 문자열 저장
                    DataArray.add(waterData);
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return DataArray;
    }
}
