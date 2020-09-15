//Класс для работы свайпов у элементов
package com.exam.k_pop;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnSwipeTouchListener implements OnTouchListener {

    private final GestureDetector gestureDetector = new GestureDetector(new GestureListener());
    float dX, dY;
    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        return true;
    }


    private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            result = onSwipeRight();
                        } else {
                            result = onSwipeLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            result = onSwipeDown();
                        } else {
                            result = onSwipeUp();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public boolean onSwipeRight() {
        return false;
    }

    public boolean onSwipeLeft() {
        return false;
    }

    public boolean onSwipeUp() {
        return false;
    }

    public boolean onSwipeDown() {
        return false;
    }
}

///Пример использования кода
/*
    background.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            toggleSomething();
        }
    });
    background.setOnTouchListener(new OnSwipeTouchListener() {
        public boolean onSwipeTop() {
            Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
            return true;
        }
        public boolean onSwipeRight() {
            Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
            return true;
        }
        public boolean onSwipeLeft() {
            Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            return true;
        }
        public boolean onSwipeBottom() {
            Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            return true;
        }
    });
 */