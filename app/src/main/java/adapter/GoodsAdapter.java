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

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder>{
    private List<Goods> mGoodsList;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titleText;
        TextView priceText;
        public ViewHolder(View view){
            super(view);
            imageView=view.findViewById(R.id.goods_image);
            titleText=view.findViewById(R.id.goods_title);
            priceText=view.findViewById(R.id.goods_price);
        }
    }
    public GoodsAdapter(List<Goods> mlist){
        mGoodsList=mlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        context=view.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageCode="file:///android_asset/"+mGoodsList.get(position).getImageCode()+".JPG";
        Glide.with(context).load(imageCode).into(holder.imageView);
        holder.titleText.setText(mGoodsList.get(position).getGoodsTitle());
        holder.priceText.setText("¥"+mGoodsList.get(position).getGoodsPrice());
    }

    @Override
    public int getItemCount() {
        return mGoodsList.size();
    }
}
