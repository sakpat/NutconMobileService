package com.soldev.fieldservice.uiadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.soldev.fieldservice.TaskFormContactFragment;
import com.soldev.fieldservice.TaskFormEquipmentTabFragment;
import com.soldev.fieldservice.TaskFormPerformDocumentTabFragment;
import com.soldev.fieldservice.TaskFormPerformQCFragment;
import com.soldev.prod.mobileservice.R;
import com.soldev.fieldservice.TaskFormCheckingListTabFragement;
import com.soldev.fieldservice.TaskFormCloseJobTabFragment;
import com.soldev.fieldservice.TaskFormEmployeeTabFragment;
import com.soldev.fieldservice.TaskFormExtraFeeTabFragment;
import com.soldev.fieldservice.TaskFormPictureTabFragment;
import com.soldev.fieldservice.TaskFormPolishPerformTabFragment;
import com.soldev.fieldservice.TaskFormPumpPerformTabFragment;
import com.soldev.fieldservice.utilities.AppConstant;

public class TaskTabsViewPagerAdapter extends FragmentPagerAdapter {
    private SparseArray<Fragment> registeredFragments = new SparseArray<>();
    private Context mContext;
    private String taskId;
    private String taskTypeGroup;
    private String formType;
    public TaskTabsViewPagerAdapter(Context context, @NonNull FragmentManager fm, String taskID, String taskTypeGroup,String formType) {
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mContext = context;
        this.taskId = taskID;
        this.taskTypeGroup = taskTypeGroup;
        this.formType = formType;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(formType.equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {
            if (taskTypeGroup.equalsIgnoreCase(AppConstant.TASK_TYPE_PUMP)) {
                switch (position) {
                    case 0:
                        return TaskFormContactFragment.newInstance();
                    case 1:
                        return TaskFormEquipmentTabFragment.newInstance(taskId);
                    case 2:
                        return TaskFormCheckingListTabFragement.newInstance(taskId);
                    case 3:
                        return TaskFormPumpPerformTabFragment.newInstance(taskId);
                    case 4:
                        return TaskFormExtraFeeTabFragment.newInstance(taskId);
                    case 5:
                        return TaskFormEmployeeTabFragment.newInstance(taskId);
                    case 6:
                        return TaskFormPictureTabFragment.newInstance(taskId);
                    case 7:
                        return TaskFormCloseJobTabFragment.newInstance(taskId);
                    default:
                        return null;

                }
            } else if (taskTypeGroup.equalsIgnoreCase(AppConstant.TASK_TYPE_POLISHING) ||
                    taskTypeGroup.equalsIgnoreCase(AppConstant.TASK_TYPE_SMOTH) ||
                    taskTypeGroup.equalsIgnoreCase(AppConstant.TASK_TYPE_OTHER)) {
                switch (position) {
                    case 0:
                        return TaskFormContactFragment.newInstance();
                    case 1:
                        return TaskFormEquipmentTabFragment.newInstance(taskId);
                    case 2:
                        return TaskFormCheckingListTabFragement.newInstance(taskId);
                    case 3:
                        return TaskFormPolishPerformTabFragment.newInstance(taskId);
                    case 4:
                        return TaskFormEmployeeTabFragment.newInstance(taskId);
                    case 5:
                        return TaskFormPictureTabFragment.newInstance(taskId);
                    case 6:
                        return TaskFormCloseJobTabFragment.newInstance(taskId);
                    default:
                        return null;

                }
            } else if (taskTypeGroup.equalsIgnoreCase(AppConstant.TASK_TYPE_DOCUMENT)) {
                switch (position) {
                    case 0:
                        return TaskFormPerformDocumentTabFragment.newInstance(taskId);
                    case 1:
                        return TaskFormPictureTabFragment.newInstance(taskId);
                    default:
                        return null;
                }
            } else {
                return null;
            }
        } else if(formType.equalsIgnoreCase(AppConstant.USER_QC_ROLE)){
            switch (position) {
                case 0:
                    return TaskFormPerformQCFragment.newInstance(taskId);
                case 1:
                    return TaskFormPictureTabFragment.newInstance(taskId);
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(formType.equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {
            if (taskTypeGroup.equalsIgnoreCase(AppConstant.TASK_TYPE_PUMP)) {
                String[] titleText = mContext.getResources().getStringArray(R.array.task_entry_pump_tab);
                return titleText[position];
            } else if (taskTypeGroup.equalsIgnoreCase(AppConstant.TASK_TYPE_POLISHING) ||
                    taskTypeGroup.equalsIgnoreCase(AppConstant.TASK_TYPE_SMOTH) ||
                    taskTypeGroup.equalsIgnoreCase(AppConstant.TASK_TYPE_OTHER)) {
                String[] titleText = mContext.getResources().getStringArray(R.array.task_entry_polish_tab);
                return titleText[position];
            } else if (taskTypeGroup.equalsIgnoreCase(AppConstant.TASK_TYPE_DOCUMENT)) {
                String[] titleText = mContext.getResources().getStringArray(R.array.task_entry_document_tab);
                return titleText[position];
            }
        } else if(formType.equalsIgnoreCase(AppConstant.USER_QC_ROLE)) {
            String[] titleText = mContext.getResources().getStringArray(R.array.task_qc_tab);
            return titleText[position];
        }
        String[] titleText = mContext.getResources().getStringArray(R.array.task_entry_document_tab);
        return titleText[position];
    }

    @Override
    public int getCount() {
        if(formType.equalsIgnoreCase(AppConstant.USER_MOBILE_ROLE)) {
            if (taskTypeGroup.equalsIgnoreCase(AppConstant.TASK_TYPE_PUMP)) {
                return 8;
            } else if (taskTypeGroup.equalsIgnoreCase(AppConstant.TASK_TYPE_POLISHING) ||
                    taskTypeGroup.equalsIgnoreCase(AppConstant.TASK_TYPE_SMOTH) ||
                    taskTypeGroup.equalsIgnoreCase(AppConstant.TASK_TYPE_OTHER)) {
                return 7;
            } else if (taskTypeGroup.equalsIgnoreCase(AppConstant.TASK_TYPE_DOCUMENT)) {
                return 2;
            } else {
                return 0;
            }
        } else if(formType.equalsIgnoreCase(AppConstant.USER_QC_ROLE)){
            return 2;
        } else {
            return 0;
        }
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }


}
