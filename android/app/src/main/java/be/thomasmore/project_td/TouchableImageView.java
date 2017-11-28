package be.thomasmore.project_td;

import android.content.Context;
import android.util.AttributeSet;

class TouchableImageView extends android.support.v7.widget.AppCompatImageView{

    public TouchableImageView(Context context)
    {
        super(context);
    }

    public TouchableImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public TouchableImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean performClick() {
        // do what you want
        super.performClick();
        return true;
    }
}
