package io.picopalette.sensorstrange.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
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
import android.widget.Toast;

import java.util.Random;

import io.picopalette.sensorstrange.helpers.Logger;

import static android.content.Context.MODE_PRIVATE;

public class KeyLogTextView extends AppCompatTextView {

    private InputMethodManager imm;
    private static final float NS2MS = 1.0f / 1000000.0f;
    private Logger kLogger;

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

    public void setLogger(Logger logger) {
        this.kLogger = logger;
    }

    private void setKeyListener() {
        setFocusableInTouchMode(true);

        setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
            String log = "";
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                return false;
            }
            else if (event.getAction() == KeyEvent.ACTION_DOWN) {
                log = (long)(SystemClock.elapsedRealtimeNanos()*NS2MS) + "," + "ACTION_DOWN," + event.getDisplayLabel();
            } else if (event.getAction() == KeyEvent.ACTION_UP) {
                log = (long)(SystemClock.elapsedRealtimeNanos()*NS2MS) + "," + "ACTION_UP," + event.getDisplayLabel();
            }
            if(kLogger != null && !log.matches("")) {
                kLogger.appendLog(log);
                return true;
            }
            return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            // show the keyboard so we can enter text
            showKeyboard();
        }
        return true;
    }

    public void showKeyboard() {
        imm = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(this, InputMethodManager.SHOW_FORCED);
        }
    }

    public void hideKeyboard() {
        if(imm != null) {
            imm.hideSoftInputFromWindow(getWindowToken(), 0);
        }
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        BaseInputConnection fic = new BaseInputConnection(this, false);
        outAttrs.actionLabel = null;
        outAttrs.inputType = InputType.TYPE_NULL;
        outAttrs.imeOptions = EditorInfo.IME_ACTION_NEXT;
        return fic;
    }

}
