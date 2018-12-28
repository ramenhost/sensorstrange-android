package io.picopalette.sensorstrange;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

public class KeyLogTextView extends AppCompatTextView {

    private static final String TAG = "KeyLog";

    public KeyLogTextView(Context context) {
        super(context);
        setKeyListener();
    }

    public KeyLogTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setKeyListener();
    }

    public KeyLogTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setKeyListener();
    }
    
    private void setKeyListener() {
        setFocusableInTouchMode(true); // allows the keyboard to pop up on
        // touch down

        setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    return false;
                }
                else if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    // Perform action on key press
                    Log.d(TAG, "ACTION_DOWN " + event.getDisplayLabel());
                    return true;
                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    // Perform action on key press
                    Log.d(TAG, "ACTION_UP " + event.getDisplayLabel());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        Log.d(TAG, "onTOUCH");
        if (event.getAction() == MotionEvent.ACTION_UP) {

            // show the keyboard so we can enter text
            InputMethodManager imm = (InputMethodManager) getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(this, InputMethodManager.SHOW_FORCED);
            }
        }
        return true;
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        Log.d(TAG, "onCreateInputConnection");

        BaseInputConnection fic = new BaseInputConnection(this, false);
        outAttrs.actionLabel = null;
        outAttrs.inputType = InputType.TYPE_NULL;
        outAttrs.imeOptions = EditorInfo.IME_ACTION_NEXT;
        return fic;
    }

}
