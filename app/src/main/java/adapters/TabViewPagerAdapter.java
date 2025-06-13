package adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import fragments.ListsFragment;
import fragments.MoviesFragment;
import fragments.NewsFragment;

public class TabViewPagerAdapter extends FragmentStateAdapter {


    public TabViewPagerAdapter(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1: return new NewsFragment();
            case 2: return new ListsFragment();
            default: return new MoviesFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
