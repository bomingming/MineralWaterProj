package kr.co.company.mineralwater;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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
    private ArrayList<String> imageDataSet;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private TextView rank_number;
        private ImageView imageView;
        private RankFragment rankFragment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rank_name); // 제품명
            imageView = itemView.findViewById(R.id.rank_image); // 제품 사진
            rank_number = itemView.findViewById(R.id.rank_number); // 순위

            rankFragment = new RankFragment();

            // 항목 클릭 시 상세정보 화면으로 이동
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

    public RankAdapter(ArrayList<String> dataSet, ArrayList<String> numSet, ArrayList<String> imagedataset){
        localDataSet = dataSet;
        numbDataSet = numSet;
        imageDataSet = imagedataset;
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
        //생수 이미지 출력
        ArrayList<Bitmap> bitArr = new ArrayList<>(); // 비트맵 보관할 ArrayList
        for(int i=0; i<imageDataSet.size(); i++){
            byte[] encodeByte = Base64.decode(imageDataSet.get(i), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            bitmap = Bitmap.createScaledBitmap(bitmap, 600, 600, true);
            bitArr.add(bitmap); // 이미지 코드를 비트맵으로 변환한 후 bitArr에 보관
        }
        holder.imageView.setImageBitmap(bitArr.get(position)); // 생수 이미지 출력
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void setSearchList(ArrayList<String> searchList){
        this.localDataSet = searchList;
        notifyDataSetChanged();
    }

    public void setImageList(ArrayList<String> imageList){
        this.imageDataSet = imageList;
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

    // 홈 화면에 이미지 파싱
    ArrayList<String> JSONParseForImageHome(String jsonStr){
        ArrayList<String> imageArr = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(jsonStr);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String imageCode = jsonObject.getString("image");

                imageCode = imageCode.replaceAll("img id=\\\"image_size\\\" src=\\\"data:image/jpeg;base64,","");
                imageCode = imageCode.replaceAll("\"/>","");

                imageArr.add(imageCode);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return imageArr;
    }
}
