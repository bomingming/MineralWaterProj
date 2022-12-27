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
            textView = itemView.findViewById(R.id.rank_name); // 과연 목록이 나올까 랭킹이 나올까
            imageView = itemView.findViewById(R.id.rank_image);
            rank_number = itemView.findViewById(R.id.rank_number); // 순위

            // 항목 클릭 시 상세정보 화면으로 이동
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetatilActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
        }
        public TextView getTextView(){
            return textView;
        }
    }
    // 랭킹 숫자 구현 시도
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
        holder.rank_number.setText(numbDataSet.get(position)); // 랭킹 숫자... 이렇게 추가해도 될까?
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
                String str2 = jsonObject.getString("name");
                DataArray.add(str2);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return DataArray;
    }
}
