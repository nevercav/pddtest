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

public class HistorySearchAdapter extends RecyclerView.Adapter<HistorySearchAdapter.ViewHolder> implements View.OnClickListener{
    private OnLeftRecyclerviewItemClickListener listener;
    private List<String> mlist;



    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        public ViewHolder(View view){
            super(view);
            textView=view.findViewById(R.id.history_tag_name);
        }
    }
    public HistorySearchAdapter(List<String> historyList,OnLeftRecyclerviewItemClickListener leftRecyclerviewItemClickListener){
        mlist=historyList;
        listener = leftRecyclerviewItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_search_item,parent,false);
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
        listener.onItemClickListener(v,(int)v.getTag(),1);
    }
}
