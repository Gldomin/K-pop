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
import com.star.k_pop.helper.OptionsSet;
import com.star.k_pop.helper.Storage;
import com.star.k_pop.model.Artist;

import java.util.ArrayList;

//Класс для показа всех Артистов плиткой
public class Gallery extends AppCompatActivity {

    private ArrayList<ImageGallery> imageGalleries;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    final OptionsSet tempSettingsSet = new OptionsSet(false, false); //переменная для считывания состояния свиича на darkmod

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Storage storage2 = new Storage(this, "settings");
        tempSettingsSet.darkMode = storage2.getBoolean("darkMode"); //считываем состояние
        //теперь выбираем тему в зависимости от положения свича
        if (tempSettingsSet.darkMode) setTheme(R.style.AppTheme2);
        else setTheme(R.style.AppThemeLight);

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
        ArrayList<Artist> artists = Importer.getArtists();
        for (Artist a : artists) {
            imageGalleries.add(new ImageGallery(a.getName(), a.getGroup(), a.getFolder()));
        }
    }
}