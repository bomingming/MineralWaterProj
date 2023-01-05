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

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.MyViewHolder>{

    private ArrayList<String> localDataSet;
    private ArrayList<String> numbDataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private TextView rank_number;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rank_name); // 제품명
            imageView = itemView.findViewById(R.id.rank_image); // 제품 사진
            rank_number = itemView.findViewById(R.id.rank_number); // 순위

            // 항목 클릭 시 상세정보 화면으로 이동
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetatilActivity.class);
                    intent.putExtra("값 테스트", "랭킹 화면과 연결 성공");
                    view.getContext().startActivity(intent);
                }
            });
        }
        public TextView getTextView(){
            return textView;
        }
    }

    public RankAdapter(ArrayList<String> dataSet, ArrayList<String> numSet){
        localDataSet = dataSet;
        numbDataSet = numSet;
    }
    
    // Fragment에서 recyclerView를 채울 데이터(ArrayList) - 원본
    // public RankAdapter(ArrayList<String> dataSet){localDataSet = dataSet;}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_rank, parent, false);
        RankAdapter.MyViewHolder viewHolder = new RankAdapter.MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RankAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(localDataSet.get(position)); // 예제에서 getName() 임의로 제거
        holder.rank_number.setText(numbDataSet.get(position)); // 랭킹 숫자
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void setSearchList(ArrayList<String> searchList){
        this.localDataSet = searchList;
        notifyDataSetChanged();
    }

    // JSON Link로부터 데이터를 받아오는 메소드
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
