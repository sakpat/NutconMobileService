package com.soldev.fieldservice.uiadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.soldev.fieldservice.TaskFormCheckingListTabFragement;
import com.soldev.fieldservice.TaskFormEmployeeTabFragment;
import com.soldev.fieldservice.TaskFormEquipmentTabFragment;
import com.soldev.fieldservice.TaskFormPolishPerformTabFragment;
import com.soldev.prod.mobileservice.R;
import com.soldev.fieldservice.TaskFormCloseJobTabFragment;
import com.soldev.fieldservice.TaskFormPictureTabFragment;

public class TaskPolishJobViewPagerAdapter extends FragmentPagerAdapter {
    private SparseArray<Fragment> registeredFragments = new SparseArray<>();
    private Context mContext;
    private String taskId;
    public TaskPolishJobViewPagerAdapter(Context context, @NonNull FragmentManager fm, String taskID) {
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mContext = context;
        this.taskId = taskID;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TaskFormEquipmentTabFragment.newInstance(taskId);
            case 1:
                return TaskFormCheckingListTabFragement.newInstance(taskId);
            case 2:
                return TaskFormPolishPerformTabFragment.newInstance(taskId);
            case 3:
                return TaskFormEmployeeTabFragment.newInstance(taskId);
            case 4:
                return TaskFormPictureTabFragment.newInstance(taskId);
            case 5:
                return TaskFormCloseJobTabFragment.newInstance(taskId);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String[] titleText =  mContext.getResources().getStringArray(R.array.task_entry_polish_tab);
        return titleText[position];
    }

    @Override
    public int getCount() {
        return 7;
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
