package com.exam.k_pop.activity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.k_pop.Artist;
import com.exam.k_pop.R;
import com.exam.k_pop.adapter.GalleryAdapter;
import com.exam.k_pop.model.Image;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ArrayList<Image> images;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        images = new ArrayList<>();
        mAdapter = new GalleryAdapter(getApplicationContext(), images);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
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
    }

    private void createArray() {
        int i = 0;
        ArrayList<Artist> artists = new ArrayList<>();
        AssetManager assetManager = getAssets();
        try {
            String[] groupName = assetManager.list("Groups");
            for (String s : groupName) {
                try {
                    String[] nameArtist = assetManager.list("Groups/" + s);
                    for (String folder : nameArtist) {
                        //Создание объекта
                        Log.i(s, "createArtist:" + i);
                        i++;
                        artists.add(new Artist(s, folder, assetManager.list("Groups/" + s + "/" + folder)));
                        images.add(new Image(artists.get(artists.size() - 1).getFolder()));
                    }
                } catch (IOException ignored) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}