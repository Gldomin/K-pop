package com.star.k_pop.helper;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

public class Evaluate {

    public Intent rateApp(String packageName)
    {
        Intent rateIntent = null;
        try
        {
            rateIntent = rateIntentForUrl(String.format("market://details?id=%s", packageName));
        }
        catch (ActivityNotFoundException e)
        {
            rateIntent = rateIntentForUrl(String.format("https://play.google.com/store/apps/details?id=%s", packageName));
        }
        return rateIntent;
    }

    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        intent.addFlags(flags);
        return intent;
    }

}
