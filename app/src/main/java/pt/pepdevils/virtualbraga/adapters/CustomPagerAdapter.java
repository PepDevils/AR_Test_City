package pt.pepdevils.virtualbraga.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import pt.pepdevils.virtualbraga.R;
import pt.pepdevils.virtualbraga.model.City;

/**
 * Created by pfons on 01/12/2017.
 */

public class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<City> city;

    public CustomPagerAdapter(Context c,ArrayList<City> city) {
        mContext = c;
        this.city = city;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return city.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ( object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = itemView.findViewById(R.id.imageView);
        imageView.setImageResource(city.get(position).getDrawBack());

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
