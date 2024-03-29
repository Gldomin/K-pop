package com.star.k_pop.gallery.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.star.k_pop.R;
import com.star.k_pop.gallery.model.ImageGallery;

import java.util.ArrayList;

/**
 * Класс фрагмента типа страницы, который можно листать вправо, влево
 */
public class GallerySlideshowDialogFragment extends DialogFragment {

    private final String TAG = GallerySlideshowDialogFragment.class.getSimpleName();
    private ArrayList<ImageGallery> imageGalleries;
    private ViewPager viewPager;
    private TextView lblCount, lblTitle, lblDate;
    private int selectedPosition = 0;

    public static GallerySlideshowDialogFragment newInstance() {
        return new GallerySlideshowDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.gallery_fragment_image_slider, container, false);
        viewPager = v.findViewById(R.id.viewpager);
        lblCount = v.findViewById(R.id.lbl_count);
        lblTitle = v.findViewById(R.id.gallaryName);
        lblDate = v.findViewById(R.id.galleryText);
        imageGalleries = (ArrayList<ImageGallery>) getArguments().getSerializable("images");
        selectedPosition = getArguments().getInt("position");
        Log.e(TAG, "position: " + selectedPosition);
        Log.e(TAG, "images size: " + imageGalleries.size());
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        setCurrentItem(selectedPosition);
        return v;
    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    //	page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    @SuppressLint("DefaultLocale")
    private void displayMetaInfo(int position) {
        lblCount.setText(String.format("%d of %d", position + 1, imageGalleries.size()));
        ImageGallery imageGallery = imageGalleries.get(position);
        lblTitle.setText(imageGallery.getName());
        lblTitle.setText(imageGallery.getName());
        lblDate.setText(imageGallery.getGroup());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    //	adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        public MyViewPagerAdapter() {
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.gallery_image_fullscreen_preview, container, false);
            ImageView imageViewPreview = view.findViewById(R.id.image_preview);
            ImageGallery imageGallery = imageGalleries.get(position);
            Glide.with(getActivity()).load(imageGallery.getUri())
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewPreview);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return imageGalleries.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == ((View) obj);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
