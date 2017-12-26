package com.example.android.choose_an_excel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ROHAN on 17-12-2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    ArrayList<CustomClass> detail=new ArrayList<>();
    Context ctx;
    public Adapter (ArrayList<CustomClass>detail,Context  ctx)
    {
        this.detail=detail;
        this.ctx=ctx;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_displayer,parent,false);
        Adapter.ViewHolder detailview=new Adapter.ViewHolder(view,ctx,detail);
        return detailview;

    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        CustomClass obj=detail.get(position);
        holder.name.setText(obj.getName());
        holder.rollno.setText(obj.getRoll());
    }

    @Override
    public int getItemCount() {
        return detail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ArrayList<CustomClass> detail=new ArrayList<>();
        TextView name;
        TextView rollno;
        Context ctx;
        public ViewHolder(View itemView,Context ctx,ArrayList<CustomClass> detail) {
            super(itemView);
            this.ctx=ctx;
            this.detail=detail;
            name=(TextView)itemView.findViewById(R.id.name);
            rollno=(TextView)itemView.findViewById(R.id.roll);
        }
    }
}
