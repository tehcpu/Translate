package ru.tehcpu.translate.ui.component;

import android.content.Context;
import android.util.AttributeSet;

import ru.tehcpu.translate.core.Utils;

/**
 * Created by tehcpu on 4/23/17.
 */

public class SquareButton extends android.support.v7.widget.AppCompatButton {
    public SquareButton(Context context) {
        super(context);
    }

    public SquareButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = this.getLayout().getWidth()+ Utils.dpToPx(10);
        setMeasuredDimension(size, size);
    }
}
