package be.thomasmore.project_td;

import android.view.MotionEvent;
import android.view.View;

class MyButtonTouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
            view.setBackgroundResource(R.drawable.buttonshapeclick);
        } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
            view.setBackgroundResource(R.drawable.buttonshape);
            view.performClick();
        }
        return true;
    }
}
