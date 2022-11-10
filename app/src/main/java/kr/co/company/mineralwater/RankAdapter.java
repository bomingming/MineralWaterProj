package kr.co.company.mineralwater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.MyViewHolder>{

    private ArrayList<String> localDataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_search_list); // 과연 목록이 나올까 랭킹이 나올까
            imageView = itemView.findViewById(R.id.water_image);
        }
        public TextView getTextView(){
            return textView;
        }
    }

    public RankAdapter(ArrayList<String> dataSet){
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public RankAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_search, parent, false);
        RankAdapter.MyViewHolder viewHolder = new RankAdapter.MyViewHolder(view);
        return new RankAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(localDataSet.get(position)); // 예제에서 getName() 임의로 제거
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
