package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pddtest.android.R;

import java.util.List;

import bean.Goods;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {
    private List<Goods> mlist;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titleText;
        TextView priceText;
        public ViewHolder(View view){
            super(view);
            imageView=view.findViewById(R.id.recom_image);
            titleText=view.findViewById(R.id.recom_title);
            priceText=view.findViewById(R.id.recom_price);
        }
    }
    public RecommendAdapter(List<Goods> list){
        mlist=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        mContext=view.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageCode="file:///android_asset/"+mlist.get(position).getImageCode()+".JPG";
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(mContext).load(imageCode).into(holder.imageView);
        holder.titleText.setText(mlist.get(position).getGoodsTitle());
        holder.priceText.setText("¥"+mlist.get(position).getGoodsPrice());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}
