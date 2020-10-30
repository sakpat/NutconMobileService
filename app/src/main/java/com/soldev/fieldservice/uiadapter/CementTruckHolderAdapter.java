package com.soldev.fieldservice.uiadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soldev.prod.mobileservice.R;
import com.soldev.fieldservice.dataentity.TaskDataConcreteTruck;

import java.util.List;

public class CementTruckHolderAdapter extends RecyclerView.Adapter<CementTruckHolderAdapter.Holder> {
    private List<TaskDataConcreteTruck> taskDataConcreteTrucks;
    private  Context context;
    public CementTruckHolderAdapter(Context context, List<TaskDataConcreteTruck> taskDataConcreteTrucks) {
        this.context = context;
        this.taskDataConcreteTrucks = taskDataConcreteTrucks;
    }

    @NonNull
    @Override
    public CementTruckHolderAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_task_cement_truck,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CementTruckHolderAdapter.Holder holder, int position) {
        TaskDataConcreteTruck taskDataConcreteTruck = taskDataConcreteTrucks.get(position);
        holder.txtTruckLicenseNo.setText(taskDataConcreteTruck.getLicensePlate());
        holder.txtCementTrcukAmount.setText(Double.toString(taskDataConcreteTruck.getAmount()));
        holder.txtStartTime.setText(taskDataConcreteTruck.getTimeStart());
        holder.txtEndTime.setText(taskDataConcreteTruck.getTimeEnd());
    }

    @Override
    public int getItemCount() {
        return taskDataConcreteTrucks.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        public TextView txtTruckLicenseNo;
        public TextView txtCementTrcukAmount;
        public TextView txtStartTime;
        public TextView txtEndTime;
        public Holder(@NonNull View itemView) {
            super(itemView);
            txtTruckLicenseNo = itemView.findViewById(R.id.txtTruckLicenseNo);
            txtCementTrcukAmount = itemView.findViewById(R.id.txtCementTrcukAmount);
            txtStartTime = itemView.findViewById(R.id.txtStartTime);
            txtEndTime = itemView.findViewById(R.id.txtEndTime);
        }
    }

    public void removeItem(int position) {
        taskDataConcreteTrucks.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(TaskDataConcreteTruck item, int position) {
        taskDataConcreteTrucks.add(position, item);
        notifyItemInserted(position);
    }
}
