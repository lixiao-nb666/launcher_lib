package com.newbee.launcher_lib.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.newbee.bulid_lib.mybase.fragment.BaseFragmen_v4;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    private FragmentManager fm;
    private List<BaseFragmen_v4> list;

    public FragmentAdapter(FragmentManager fm, List<BaseFragmen_v4> list) {
        super(fm);
        this.fm = fm;
        this.list = list;
    }

    @Override
    public Fragment getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public List<BaseFragmen_v4> getList() {
        return list;
    }



}
