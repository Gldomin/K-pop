package com.star.k_pop.handlers;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class KpopEventHandler {

    public void setUpView (Activity activ, ImageView view, String imageName)
    {
        Glide.with(activ).load(Uri.parse("file:///android_asset/Groups/" + imageName))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transition(withCrossFade())
                .into(view);
    }

}
