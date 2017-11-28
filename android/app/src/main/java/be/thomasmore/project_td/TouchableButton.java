package be.thomasmore.project_td;

import android.content.Context;
import android.util.AttributeSet;

class TouchableButton extends android.support.v7.widget.AppCompatButton {

    public TouchableButton(Context context)
    {
        super(context);
    }

    public TouchableButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public TouchableButton(Context context, AttributeSet attrs, int defStyle)
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
