package com.soldev.fieldservice.uiadapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.soldev.fieldservice.TaskListFragment;
import com.soldev.prod.mobileservice.R;
import com.soldev.fieldservice.TaskHistoryFragment;

public class TaskListTabViewPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    public TaskListTabViewPagerAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TaskListFragment.newInstance(null,null);
            case 1:
                return TaskHistoryFragment.newInstance(null,null);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String[] titleText =  mContext.getResources().getStringArray(R.array.main_activity_tab);
        return titleText[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
