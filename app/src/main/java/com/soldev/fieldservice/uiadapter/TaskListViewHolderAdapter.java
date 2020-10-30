package com.soldev.fieldservice.uiadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.soldev.fieldservice.dataentity.TaskDataEntry;
import com.soldev.fieldservice.utilities.AppConstant;
import com.soldev.fieldservice.utilities.AppPreference;
import com.soldev.fieldservice.utilities.AppUitility;
import com.soldev.prod.mobileservice.R;

import java.util.List;

public class TaskListViewHolderAdapter extends RecyclerView.Adapter<TaskListViewHolderAdapter.Holder>{
    private List<TaskDataEntry> taskDataEntries;
    private Context context;
    public TaskListViewHolderAdapter(Context context, List<TaskDataEntry> taskDataEntries) {
        this.context = context;
        this.taskDataEntries = taskDataEntries;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_task,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        final TaskDataEntry taskDataEntry = taskDataEntries.get(position);
        holder.txttaskTypeDesc.setText(taskDataEntry.getTaskType());
        holder.txttaskNoDesc.setText(taskDataEntry.getTaskNo().trim());
        holder.txtJobNoDesc.setText(taskDataEntry.getJobNo().trim());
        String taskDate = AppUitility.convertDateObjToString(AppUitility.formatddMMyy, AppUitility.convertStringToDateObj(AppUitility.formatddMMyy,taskDataEntry.getTaskDate()));
        holder.txtDateDesc.setText(taskDate);
        holder.txtCustomerNameDesc.setText(taskDataEntry.getCustomerName());
        holder.txtCustomerLocationDesc.setText(taskDataEntry.getLocation());
        holder.txtCustomerProjectDesc.setText(taskDataEntry.getProjectName());
        if(AppPreference.getUserRole(context).equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {
            if (taskDataEntry.getTaskStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_NEW)) {
                holder.imgStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.colorYellow));
            } else if (taskDataEntry.getTaskStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_PROGRESS)) {
                holder.imgStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlue));
            } else {
                holder.imgStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.colorLightGreen));
            }
        } else {
            if (taskDataEntry.getQcStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_NEW)) {
                holder.imgStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.colorYellow));
            } else if (taskDataEntry.getQcStatus().equalsIgnoreCase(AppConstant.TASK_STATUS_PROGRESS)) {
                holder.imgStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlue));
            } else {
                holder.imgStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.colorLightGreen));
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onContactClick != null) {
                    onContactClick.onContactClick(position,taskDataEntry);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.taskDataEntries.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView txttaskTypeDesc;
        TextView txttaskNoDesc;
        TextView txtDateDesc;
        TextView txtCustomerNameDesc;
        TextView txtCustomerLocationDesc;
        TextView txtCustomerProjectDesc;
        TextView txtJobNoDesc;
        ImageView imgStatus;
        public Holder(@NonNull View itemView) {
            super(itemView);
            txttaskTypeDesc = itemView.findViewById(R.id.txttaskTypeDesc);
            txttaskNoDesc = itemView.findViewById(R.id.txttaskNoDesc);
            txtJobNoDesc = itemView.findViewById(R.id.txtJobNoDesc);
            txtDateDesc = itemView.findViewById(R.id.txtDateDesc);
            txtCustomerNameDesc = itemView.findViewById(R.id.txtCustomerNameDesc);
            txtCustomerLocationDesc = itemView.findViewById(R.id.txtCustomerLocationDesc);
            txtCustomerProjectDesc  = itemView.findViewById(R.id.txtCustomerProjectDesc);
            imgStatus = itemView.findViewById(R.id.imgStatus);
        }
    }

    public interface OnContactClick {
        public void onContactClick(int position, TaskDataEntry taskDataEntry);
    }

    OnContactClick onContactClick;

    public void setOnContactClick(OnContactClick onContactClick) {
        this.onContactClick = onContactClick;
    }
}
