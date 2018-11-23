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

import java.util.HashMap;
import java.util.List;

import bean.Type;

class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    private List<Type> mlist;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public ViewHolder(View view){
            super(view);
            imageView=view.findViewById(R.id.right_image);
            textView=view.findViewById(R.id.right_image_name);
        }
    }
    public GridAdapter(List<Type> types) {
        mlist=types;
    }

//    private static HashMap<Integer,GridAdapter> dict=new HashMap<>();
//    public static GridAdapter getAdapter(List<Type> types,int pos){
//        if(dict.get(pos)==null){
//            GridAdapter adapter=new GridAdapter(types);
//            dict.put(pos,adapter);
//        }
//        return dict.get(pos);
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.right_in_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        mContext=view.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(mlist.get(position).getImageName());
        Glide.with(mContext).load(mlist.get(position).getImageCode()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}
