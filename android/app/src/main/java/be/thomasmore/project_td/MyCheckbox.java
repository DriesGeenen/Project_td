package be.thomasmore.project_td;

import android.content.Context;
import android.util.AttributeSet;

public class MyCheckbox extends android.support.v7.widget.AppCompatCheckBox {

    public MyCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public void setChecked(boolean t){
        if(t)
        {
            this.setBackgroundResource(R.drawable.select);
        }
        else
        {
            this.setBackgroundResource(R.drawable.deselect);
        }
        super.setChecked(t);
    }
}