package sugarsinitiative.talks;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CategoryAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[]{"Latest", "Business", "Politics", " Sports", "Technology"};
    private Context context;

    public CategoryAdapter(FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new LatestFragment();
        } else if (position == 1) {
            return new BusinessFragment();
        } else if (position == 2) {
            return new PoliticsFragment();
        } else if (position == 3) {
            return new SportsFragment();
        } else {
            return new TechnologyFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //generate title based on item position
        return tabTitles[position];
    }
}

