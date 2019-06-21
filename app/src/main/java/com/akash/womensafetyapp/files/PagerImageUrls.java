package com.akash.womensafetyapp.files;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.akash.womensafetyapp.R;
import com.squareup.picasso.Picasso;

public class PagerImageUrls extends PagerAdapter {

    private Context context;
    private String[] imagesUrls;

    public PagerImageUrls(Context context, String[] imagesUrls) {
        this.context = context;
        this.imagesUrls = imagesUrls;
    }


    @Override
    public int getCount() {
        return imagesUrls.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setPadding(0 , 10 , 0 , 10);
        Picasso.get().load(imagesUrls[position])
                .placeholder(R.drawable.placeholder).error(R.drawable.error).into(imageView);

        container.addView(imageView);

        return imageView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
