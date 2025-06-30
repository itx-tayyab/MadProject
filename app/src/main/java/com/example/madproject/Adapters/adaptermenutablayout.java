package com.example.madproject.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.madproject.Fragments.Menu;


public class adaptermenutablayout extends FragmentPagerAdapter {

    public adaptermenutablayout(@NonNull FragmentManager fm) {
        super(fm);
    }


    @Override

    @NonNull
    // @Override
    public Fragment getItem(int position) {
        if(position==0)
        {
            return new Menu();
        } else if (position==1)
        {
            return new Menu();
        }
        else return new Menu();
    }

    @Override
    public int getCount() {
        return 3;
    }

    /*@Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
        {
            return "Chats";
        } else if (position==1)
        {
            return "Status";
        }
        else  return "Calls";
    }*/
}
