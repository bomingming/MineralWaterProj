package kr.co.company.mineralwater;

import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;
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

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private RecyclerView recyclerView;
    private ArrayList<String> localDataSet;

    public class MyViewHolder extends RecyclerView.ViewHolder{ // static 임의로 없앤 상태

        private TextView textView;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.name_text_slide);
            imageView = itemView.findViewById(R.id.water_image_slide);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetatilActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
        }

        // getText 어쩌구? 추가 안 해도 오류 안 뜨나 시도하는 중
        public TextView getTextView(){
            return textView;
        }
    }

    public HomeAdapter(ArrayList<String> dataSet){
        localDataSet = dataSet; // Fragement에서 Adapter 호출 시 들어간 매개변수가 목록의 값
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_main, parent, false);
        HomeAdapter.MyViewHolder viewHolder = new HomeAdapter.MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(localDataSet.get(position)); // 예제에서 getName() 임의로 제거
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    // JSON 파싱 시도
    public String JSONLink(String url){
        String test = "시작";
        Log.e("JSONLink 메소드 시작", test);

        String receiveMsg = ""; // 초기화 필수
        //InputStream is = null;

        /*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); // 추가하지 않을 경우 서버에 URL로 접근 불가*/


        try {
            /*String test1 = "트라이 간드아";
            Log.e("시작했을까?", test1);*/

            InputStream is = new URL(url).openStream();

            /*String test2 = "잘 나옴!";
            Log.e("중간부분은?", test2);*/
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String str;
            StringBuffer buffer = new StringBuffer();

            while((str = rd.readLine()) != null){
                buffer.append(str);
                //Log.e("이 값은?", str);
            }
            receiveMsg = buffer.toString();
        }catch (IOException e){
            String hehe = "헐";
            Log.e("설마...? 캐치 오류?", hehe);
            e.printStackTrace();
        }
        //Log.e("리시브 메시지", receiveMsg);
        return receiveMsg; // JSON 데이터를 반환
    }

    String JSONParse(String jsonStr){
        String str = new String();
        //String waterName = ""; // JSON 데이터 넣을 문자열 변수 초기화 선언
        try{
            JSONArray jsonArray = new JSONArray(jsonStr);
            /*for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                waterName = jsonObject.getString("name");
            }*/
            JSONObject newjsonObject = jsonArray.getJSONObject(0);
            String newWaterName = newjsonObject.getString("name");
            str = newWaterName;

        }catch (JSONException e){
            e.printStackTrace();
        }
        Log.e("과연 str의 값이 나올까?", str);
        return str;
    }


}
