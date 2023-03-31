package com.example.bookhotel;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyDecoration extends RecyclerView.ItemDecoration {
    int margin;

    public MyDecoration(int margin) {
        this.margin = margin;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.right = margin;
    }
}
