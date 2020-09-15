package com.star.k_pop.gallery.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.star.k_pop.OnSwipeTouchListener;
import com.star.k_pop.R;
import com.star.k_pop.gallery.model.ImageGallery;

import java.util.ArrayList;

//Класс фрагмента типа страницы, который можно листать вправо, влево
public class SlideshowDialogFragment extends DialogFragment {
    private String TAG = SlideshowDialogFragment.class.getSimpleName();
    private ArrayList<ImageGallery> imageGalleries;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView lblCount, lblTitle, lblDate;
    private int selectedPosition = 0;

    static SlideshowDialogFragment newInstance() {
        SlideshowDialogFragment f = new SlideshowDialogFragment();
        return f;
    }

    public void bioPageTransition(View v)  //метод открывает bioPageTransition
    {
        Intent galaryBioPage = new Intent();
        galaryBioPage.setClass(getContext(), GalleryBioPage.class);
        startActivityForResult(galaryBioPage, 0);
    }


    @Override
    public void onPause() {
        getActivity().overridePendingTransition(R.anim.alpha_off, R.anim.bottom_off);
        super.onPause();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.gallery_fragment_image_slider, container, false);

        viewPager = v.findViewById(R.id.viewpager);
        lblCount = v.findViewById(R.id.lbl_count);
        lblTitle = v.findViewById(R.id.gallaryName);
        lblDate = v.findViewById(R.id.galleryText);

        imageGalleries = (ArrayList<ImageGallery>) getArguments().getSerializable("images");
        selectedPosition = getArguments().getInt("position");

        Log.e(TAG, "position: " + selectedPosition);
        Log.e(TAG, "images size: " + imageGalleries.size());

        LinearLayout Lnr = v.findViewById(R.id.gallaryFooterLayout);
        ImageButton imgBtn = v.findViewById(R.id.imageButtonArrow);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bioPageTransition(v);
            }
        });

        Lnr.setOnClickListener(new View.OnClickListener() { //обработка кнопки-стрелки
            @Override
            public void onClick(View arg0) {
                bioPageTransition(v);
            }
        });
        Lnr.setOnTouchListener(new OnSwipeTouchListener() { //обработка свайпов

            @Override

            public boolean onSwipeUp() {
                Toast.makeText(v.getContext(), "Свайп-вверх", Toast.LENGTH_LONG).show();
                bioPageTransition(v);
                return true;
            }

            public boolean onSwipeDown() {
                Toast.makeText(v.getContext(), "Свайп-вниз", Toast.LENGTH_LONG).show();
                return true;
            }

            public boolean onSwipeLeft() {
                Toast.makeText(v.getContext(), "Свайп-влево", Toast.LENGTH_LONG).show();
                return true;
            }

            public boolean onSwipeRight() {
                Toast.makeText(v.getContext(), "Свайп-вправо", Toast.LENGTH_LONG).show();
                return true;
            }

        });

        myViewPagerAdapter = new MyViewPagerAdapter();
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

    private void displayMetaInfo(int position) {
        lblCount.setText((position + 1) + " of " + imageGalleries.size());

        ImageGallery imageGallery = imageGalleries.get(position);
        lblTitle.setText(imageGallery.getName());
        lblDate.setText(imageGallery.getTimestamp());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    //	adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.gallery_image_fullscreen_preview, container, false);

            ImageView imageViewPreview = (ImageView) view.findViewById(R.id.image_preview);

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
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
