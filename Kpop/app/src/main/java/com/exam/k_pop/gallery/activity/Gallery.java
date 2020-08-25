package com.exam.k_pop.gallery.activity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.k_pop.Artist;
import com.exam.k_pop.OnSwipeTouchListener;
import com.exam.k_pop.R;
import com.exam.k_pop.StartApplication.Importer;
import com.exam.k_pop.gallery.adapter.GalleryAdapter;
import com.exam.k_pop.gallery.model.ImageGallery;

import java.io.IOException;
import java.util.ArrayList;

//Класс для показа всех Артистов плиткой
public class Gallery extends AppCompatActivity {

    private String TAG = Gallery.class.getSimpleName();
    private ArrayList<ImageGallery> imageGalleries;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {






        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        imageGalleries = new ArrayList<>();
        mAdapter = new GalleryAdapter(getApplicationContext(), imageGalleries);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", imageGalleries);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        createArray();

/*
     */
    }

    private void createArray() {
        ArrayList<Artist> artists = Importer.getArtists();
        for(Artist a : artists){
            imageGalleries.add(new ImageGallery(a.getFolder()));
        }
    }
}