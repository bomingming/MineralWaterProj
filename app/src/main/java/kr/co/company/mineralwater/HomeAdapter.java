package kr.co.company.mineralwater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private RecyclerView recyclerView;
    private ArrayList<String> localDataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.name_text_slide);
            imageView = itemView.findViewById(R.id.water_image_slide);
        }

        // getText 어쩌구? 추가 안 해도 오류 안 뜨나 시도하는 중
        public TextView getTextView(){
            return textView;
        }
    }
    public HomeAdapter(ArrayList<String> dataSet){
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_main, parent, false);
        HomeAdapter.MyViewHolder viewHolder = new HomeAdapter.MyViewHolder(view);

        //return new MyViewHolder(view);

        // 코드가 이해가 안 돼서... viewHolder 선언 시켜놓고 반환 왜 안 하지? 그래서 시도해봄
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


}
