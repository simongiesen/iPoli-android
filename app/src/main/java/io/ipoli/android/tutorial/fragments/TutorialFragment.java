package io.ipoli.android.tutorial.fragments;


import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;

import io.ipoli.android.R;

public class TutorialFragment extends Fragment implements ISlideBackgroundColorHolder {
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String BACKGROUND = "background_color";
    private static final String SHOW_LOGO = "show_parrot";
    private String title;
    private int image;
    private String description;
    private int backgroundColor;
    private boolean showLogo;
    private int slideBackgroundColor;
    private View containerView;

    public TutorialFragment() {
    }

    public static TutorialFragment newInstance(String title, String description, @DrawableRes int image, @ColorRes int backgroundColor) {
        return newInstance(title, description, image, backgroundColor, true);
    }

    public static TutorialFragment newInstance(String title, String description, @DrawableRes int image, @ColorRes int backgroundColor, boolean showLogo) {
        TutorialFragment fragment = new TutorialFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(DESCRIPTION, description);
        args.putInt(IMAGE, image);
        args.putInt(BACKGROUND, backgroundColor);
        args.putBoolean(SHOW_LOGO, showLogo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
            description = getArguments().getString(DESCRIPTION);
            image = getArguments().getInt(IMAGE);
            backgroundColor = getArguments().getInt(BACKGROUND);
            showLogo = getArguments().getBoolean(SHOW_LOGO);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tutorial, container, false);
        containerView = v.findViewById(R.id.tutorial_container);
        TextView titleView = (TextView) v.findViewById(R.id.tutorial_title);
        TextView descView = (TextView) v.findViewById(R.id.tutorial_description);
        ImageView imageView = (ImageView) v.findViewById(R.id.tutorial_image);
        ImageView logoView = (ImageView) v.findViewById(R.id.tutorial_logo);

        if (showLogo) {
            logoView.setVisibility(View.VISIBLE);
        } else {
            logoView.setVisibility(View.GONE);
        }

        titleView.setText(title);
        descView.setText(description);
        imageView.setImageResource(image);
        return v;
    }

    @Override
    public int getDefaultBackgroundColor() {
        return ContextCompat.getColor(getContext(), backgroundColor);
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        containerView.setBackgroundColor(backgroundColor);
    }
}
