package com.soldev.fieldservice.uiadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soldev.fieldservice.dataentity.Employee;
import com.soldev.prod.mobileservice.R;

import java.util.List;

public class EmployeeHolderAdapter extends RecyclerView.Adapter<EmployeeHolderAdapter.Holder> {
    private Context context;
    private List<Employee> employees;
    public EmployeeHolderAdapter(Context context, List<Employee> employees) {
        this.context = context;
        this.employees = employees;
    }

    @NonNull
    @Override
    public EmployeeHolderAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_task_employee,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeHolderAdapter.Holder holder, int position) {
        Employee employee = employees.get(position);
        holder.txtEmployee.setText(employee.getEmpID()+" "+employee.getEmpName());
        holder.txtStartTime.setText(employee.getTimeStart());
        holder.txtEndTime.setText(employee.getTimeEnd());
        if(employee.getAbsent()==null||employee.getAbsent().equalsIgnoreCase("N")){
            holder.llDisplayTime.setVisibility(View.VISIBLE);
            holder.txtAbsent.setVisibility(View.GONE);
        } else {
            holder.llDisplayTime.setVisibility(View.GONE);
            holder.txtAbsent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        public TextView txtEmployee;
        public TextView txtStartTime;
        public TextView txtEndTime;
        public LinearLayout llDisplayTime;
        public TextView txtAbsent;
        public Holder(@NonNull View itemView) {
            super(itemView);
            llDisplayTime = itemView.findViewById(R.id.llDisplayTime);
            txtEmployee = itemView.findViewById(R.id.txtEmployee);
            txtStartTime = itemView.findViewById(R.id.txtStartTime);
            txtEndTime = itemView.findViewById(R.id.txtEndTime);
            txtAbsent = itemView.findViewById(R.id.txtAbsent);
        }
    }

    public void removeItem(int position) {
        employees.remove(position);
        notifyItemRemoved(position);
    }
}
