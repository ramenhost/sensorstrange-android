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

import io.picopalette.sensorstrange.helpers.Logger;

import static android.content.Context.MODE_PRIVATE;

public class KeyLogTextView extends AppCompatTextView {


    private String[] words={"BATMAN","CYBER","CACHE","WELCOME","CRICKET","PRIZE","ANDROID"};
    private int currentWord=-1;
    private int charCount;
    private int wordCount;
    private static final String TAG = "KeyLog";
    public static final String FILE_NAME="SharedPrefFile";
    SharedPreferences sharedPreferences = getContext().getSharedPreferences(FILE_NAME, MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
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

    public void setLogger(Logger logger)
    {
        this.kLogger = logger;
        charCount = 0;
        wordCount = 0;
        currentWord = sharedPreferences.getInt("currentWord",0);
        setText(words[currentWord]);
    } // session starts here

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

                    if(words[currentWord].charAt(charCount)==event.getDisplayLabel()){
                        charCount++;
                        if(charCount==words[currentWord].length()){
                            wordCount++;
                            charCount=0;
                            setText(words[currentWord]+" "+wordCount);
                            if(wordCount==50){
                                wordCount=0;
                                currentWord++;
                                if(currentWord==words.length){
                                    setText("Words Completed - Stop Logging");
                                    editor.putInt("currentWord",0);
                                    editor.commit();
                                }else {
                                    editor.putInt("currentWord",currentWord);
                                    editor.commit();
                                    setText(words[currentWord]);
                                }
                            }
                        }
                    }else{
                        Toast.makeText(getContext(),"Typo.. Retype the word",Toast.LENGTH_SHORT).show();
                        charCount=0;
                    }
                    //Log.d(TAG,  log);
                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    log = (long)(SystemClock.elapsedRealtimeNanos()*NS2MS) + "," + "ACTION_UP," + event.getDisplayLabel();

                    //Log.d(TAG, log);
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
            imm = (InputMethodManager) getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(this, InputMethodManager.SHOW_FORCED);
            }
        }
        return true;
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
