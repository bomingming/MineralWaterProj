package kr.co.company.mineralwater;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_search, parent, false);
        MyViewHolder viewHolder = new SearchAdapter.MyViewHolder(view);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(localDataSet.get(position)); // 예제에서 getName() 임의로 제거
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
