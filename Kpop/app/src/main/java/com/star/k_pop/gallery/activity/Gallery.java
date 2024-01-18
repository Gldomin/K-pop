package com.star.k_pop.gallery.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.star.k_pop.R;
import com.star.k_pop.StartApplication.Importer;
import com.star.k_pop.gallery.adapter.GalleryAdapter;
import com.star.k_pop.gallery.fragment.GallerySlideshowDialogFragment;
import com.star.k_pop.gallery.model.ImageGallery;
import com.star.k_pop.helper.Theme;
import com.star.k_pop.model.Artist;
import com.star.k_pop.model.Bands;

import java.util.ArrayList;

//Класс для показа всех Артистов плиткой
public class Gallery extends AppCompatActivity {

    private ArrayList<ImageGallery> imageGalleries;
    Theme theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme = new Theme(this);
        theme.setThemeSecond();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        imageGalleries = new ArrayList<>();
        GalleryAdapter mAdapter = new GalleryAdapter(getApplicationContext(), imageGalleries);

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
                GallerySlideshowDialogFragment newFragment = GallerySlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        createArray();
    }

    private void createArray() {
        ArrayList<Bands> bands = Importer.getBands();
        for (Bands b: bands)
        {
            for(int i = 0; i < b.getNumberImage(); i++){
                imageGalleries.add(new ImageGallery(b.getName(), b.getFolder(i)));
            }
            ArrayList<Artist> artists = b.getArtists();
            for (Artist a : artists) {
                for (int i = 0; i < a.getCountImages(); i++) {
                    imageGalleries.add(new ImageGallery(a.getName(), a.getGroup(), a.getFolder(i)));
                }
            }
        }
    }
}