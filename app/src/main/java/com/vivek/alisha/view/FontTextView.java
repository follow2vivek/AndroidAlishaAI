package com.vivek.alisha.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.vivek.alisha.R;

/**
 * Created by Vivek on 13-09-2017.
 */

public class FontTextView extends AppCompatTextView {

    public FontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public FontTextView(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FontView);
            String fontName = a.getString(R.styleable.FontView_font);

            try {
                if (fontName != null) {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                    setTypeface(myTypeface);
                }else{
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + getResources().getString(R.string.font_mi));
                    setTypeface(myTypeface);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            a.recycle();
        }
    }
}