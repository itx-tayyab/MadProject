package com.example.madproject.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class adaptertablayout extends FragmentPagerAdapter {

    public adaptertablayout(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @NonNull
   // @Override
    /*public Fragment getItem(int position) {
        if(position==0)
        {
            return new OneFragment();
        } else if (position==1)
        {
            return new TwoFragment();
        }
        else return new ThirdFragment();
    }
*/
    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
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
    }
}
