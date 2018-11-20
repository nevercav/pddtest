package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pddtest.android.R;
import com.pddtest.android.ShowLunboActivity;

import java.util.List;

import bean.Channel;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {
    private List<Channel> mChannelList;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView channelImage1;
        TextView channelName1;
        ImageView channelImage2;
        TextView channelName2;
        LinearLayout layout1;
        LinearLayout layout2;
        public ViewHolder(View view){
            super(view);
            channelImage1=view.findViewById(R.id.channel_image1);
            channelName1=view.findViewById(R.id.channel_name1);
            channelImage2=view.findViewById(R.id.channel_image2);
            channelName2=view.findViewById(R.id.channel_name2);
            layout1=view.findViewById(R.id.item1);
            layout2=view.findViewById(R.id.item2);
        }
    }
    public ChannelAdapter(List<Channel> list){
        mChannelList=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Intent intent=new Intent(v.getContext(), ShowLunboActivity.class);
                intent.putExtra("imageurl",mChannelList.get(position).getImageId1());
                intent.putExtra("testtext",mChannelList.get(position).getName1());
                v.getContext().startActivity(intent);
            }
        });
        holder.layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Intent intent=new Intent(v.getContext(), ShowLunboActivity.class);
                intent.putExtra("imageurl",mChannelList.get(position).getImageId2());
                intent.putExtra("testtext",mChannelList.get(position).getName2());
                v.getContext().startActivity(intent);
            }
        });
        mContext=view.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Channel channel=mChannelList.get(position);
        holder.channelName1.setText(channel.getName1());
        //holder.channelImage1.setImageResource(channel.getImageId1());
        Glide.with(mContext).load(channel.getImageId1()).into(holder.channelImage1);
        holder.channelName2.setText(channel.getName2());
        //holder.channelImage2.setImageResource(channel.getImageId2());
        Glide.with(mContext).load(channel.getImageId2()).into(holder.channelImage2);
    }

    @Override
    public int getItemCount() {
        return mChannelList.size();
    }
}
