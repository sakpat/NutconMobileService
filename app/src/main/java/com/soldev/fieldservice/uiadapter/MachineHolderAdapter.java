package com.soldev.fieldservice.uiadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soldev.prod.mobileservice.R;
import com.soldev.fieldservice.dataentity.TaskDataMachine;

import java.util.List;

public class MachineHolderAdapter extends  RecyclerView.Adapter<MachineHolderAdapter.Holder> {
    private List<TaskDataMachine> taskDataMachines;
    public MachineHolderAdapter(List<TaskDataMachine> taskDataMachines) {
        this.taskDataMachines = taskDataMachines;
    }

    @NonNull
    @Override
    public MachineHolderAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_task_machine,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MachineHolderAdapter.Holder holder, int position) {

        holder.txtMachineRow.setText(Integer.toString(position+1));
        holder.txtMachineNo.setText(taskDataMachines.get(position).getCode());
        holder.txtMachineRemark.setText(taskDataMachines.get(position).getRemark());
    }

    @Override
    public int getItemCount() {
        if(taskDataMachines==null){
            return 0;
        }
        return taskDataMachines.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        public TextView txtMachineRow;
        public TextView txtMachineNo;
        public TextView txtMachineRemark;
        public Holder(@NonNull View itemView) {
            super(itemView);
            txtMachineRow = itemView.findViewById(R.id.txtMachineRow);
            txtMachineRow.setVisibility(View.GONE);
            txtMachineNo = itemView.findViewById(R.id.txtMachineNo);
            txtMachineRemark = itemView.findViewById(R.id.txtMachineRemark);
        }
    }


    public List<TaskDataMachine> getData(){
        return taskDataMachines;
    }


    public void removeItem(int position) {
        taskDataMachines.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(TaskDataMachine item, int position) {
        taskDataMachines.add(position, item);
        notifyItemInserted(position);
    }
}
