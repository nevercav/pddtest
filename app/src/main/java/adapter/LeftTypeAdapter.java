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

public class LeftTypeAdapter extends RecyclerView.Adapter<LeftTypeAdapter.ViewHolder> implements View.OnClickListener {
    private OnLeftRecyclerviewItemClickListener listener;
    private List<String> mlist;
    private int selection=0;


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.left_type);
        }
    }

    public LeftTypeAdapter(List<String> mTypeList, OnLeftRecyclerviewItemClickListener leftRecyclerviewItemClickListener) {
        mlist = mTypeList;
        listener = leftRecyclerviewItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LeftTypeAdapter.ViewHolder holder, int position) {
        holder.textView.setText(mlist.get(position));
        holder.itemView.setTag(position);
        if(selection==position){
            holder.itemView.setSelected(true);
        }else{
            holder.itemView.setSelected(false);
        }
    }


    @Override
    public int getItemCount() {
        return mlist.size();
    }

    @Override
    public void onClick(View v) {
        listener.onItemClickListener(v,(int)v.getTag());
    }
    public void setSelection(int p){
        int preselection=selection;
        selection=p;
        notifyItemChanged(preselection);
        notifyItemChanged(selection);
        //notifyDataSetChanged();
    }
}