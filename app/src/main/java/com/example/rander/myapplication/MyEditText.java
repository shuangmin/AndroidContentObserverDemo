package com.example.rander.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * Created by rander on 17-4-24.
 */

public class MyEditText extends EditText {
    public MyEditText(Context context) {
        this(context,null);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setCustomSelectionActionModeCallback(mNoSelectionActionModeCallback);
    }

    private final ActionMode.Callback mNoSelectionActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Prevents the selection action mode on double tap.
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
    };


}
