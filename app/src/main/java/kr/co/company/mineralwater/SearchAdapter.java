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
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        private SearchFragment searchFragment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_search_list);
            imageView = itemView.findViewById(R.id.water_image);

            searchFragment = new SearchFragment();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 클릭된 아이템의 위치 가져오기
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        // 클릭 이벤트

                        // 리스너 객체의 메소드 호출
                        if(mListener != null){
                            mListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
        public TextView getTextView(){
            return textView;
        }

    }

    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null;

    // OnItemClickListener 리스너 객체 참조를 Adapter에 전달하는 메소드
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
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
    // 상세 정보를 위한 메소드
    // 제조 공장 파싱
    ArrayList<String> JSONParseForFCName(String jsonStr){
        ArrayList<String> FCNameArray = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(jsonStr);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String FCName = jsonObject.getString("factory_name"); // 제조 공장 문자열로 파싱
                FCNameArray.add(FCName);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return FCNameArray;
    }

    // 공장 주소 파싱
    ArrayList<String> JSONParseForLoc(String jsonStr){
        ArrayList<String> locationArray = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(jsonStr);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String location = jsonObject.getString("location"); // 공장 주소 문자열로 파싱
                locationArray.add(location);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return locationArray;
    }

    // 가격 파싱
    String JSONParseForPrice(String jsonStr){
        ArrayList<String> priceArray = new ArrayList<>();
        String price = new String();
        try{
            JSONArray jsonArray = new JSONArray(jsonStr);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            price = jsonObject.getString("price");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return price;
    }

    // 경고 단계 파싱
    ArrayList<String> JSONParseForWarn(String jsonStr){
        ArrayList<String> warnArray = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(jsonStr);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String warning = jsonObject.getString("warning_stage"); // 경고 단계 문자열로 파싱
                warnArray.add(warning);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return warnArray;
    }

    // 이미지 파싱
    String JSONParseForImage(String jsonStr){
        //ArrayList<String> imageArray = new ArrayList<>();
        String image = new String();
        try{
            JSONArray jsonArray = new JSONArray(jsonStr);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            image = jsonObject.getString("image");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return image;
    }
}
