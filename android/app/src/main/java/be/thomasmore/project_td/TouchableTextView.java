package be.thomasmore.project_td;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

class TouchableTextView extends android.support.v7.widget.AppCompatTextView{

    public TouchableTextView(Context context)
    {
        super(context);
    }

    public TouchableTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public TouchableTextView(Context context, AttributeSet attrs, int defStyle)
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
