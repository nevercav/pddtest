package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pddtest.android.R;

import java.util.List;

import bean.Type;

public class RightOutAdapter extends RecyclerView.Adapter<RightOutAdapter.ViewHolder>{
    private List<List<Type>> mSrcList;
    private List<String> mTypeList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        RecyclerView rightInRecycler;
        public ViewHolder(View view){
            super(view);
            textView=view.findViewById(R.id.right_type);
            rightInRecycler=view.findViewById(R.id.right_in_recycler);
            GridLayoutManager layoutManager=new GridLayoutManager(view.getContext(),3);
            layoutManager.setAutoMeasureEnabled(true);
            rightInRecycler.setLayoutManager(layoutManager);
        }
    }
    public RightOutAdapter(List<List<Type>> mSrcList,List<String> mTypeList){
        this.mSrcList=mSrcList;
        this.mTypeList=mTypeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.right_out_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(mTypeList.get(position));
       if(holder.rightInRecycler.getAdapter()==null) {
            //if(holder.rightInRecycler.getAdapter()!=GridAdapter.getAdapter(mSrcList.get(position),position));
            ////holder.rightInRecycler.setAdapter(GridAdapter.getAdapter(mSrcList.get(position),position));
           holder.rightInRecycler.setAdapter(new GridAdapter(mSrcList.get(position)));
           //GridAdapter adapter=new GridAdapter(mSrcList.get(position));
           //holder.rightInRecycler.setAdapter(adapter);
           //holder.rightInRecycler.setTag(adapter);
       }

    }

    @Override
        public int getItemCount() {
            return mTypeList.size();
        }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
