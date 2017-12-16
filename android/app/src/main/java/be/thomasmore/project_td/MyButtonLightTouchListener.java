package be.thomasmore.project_td;

import android.view.MotionEvent;
import android.view.View;

class MyButtonLightTouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            view.setBackgroundResource(R.drawable.buttonshapelightclick);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            view.setBackgroundResource(R.drawable.buttonshapelight);
            view.performClick();
        }
        return true;
    }
}
