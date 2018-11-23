package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pddtest.android.R;

import java.util.List;

import listener.OnLeftRecyclerviewItemClickListener;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements View.OnClickListener{
    private OnLeftRecyclerviewItemClickListener listener;
    private List<String> mlist;
    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public ViewHolder(View view){
            super(view);
            textView=view.findViewById(R.id.search_tag_name);
        }
    }
    public SearchAdapter(List<String> mTagList,OnLeftRecyclerviewItemClickListener leftRecyclerviewItemClickListener){
        mlist=mTagList;
        listener = leftRecyclerviewItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(mlist.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    @Override
    public void onClick(View v) {
        listener.onItemClickListener(v,(int)v.getTag(),2);
    }
}
