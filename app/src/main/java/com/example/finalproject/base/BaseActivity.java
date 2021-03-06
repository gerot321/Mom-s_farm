package com.example.finalproject.base;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.finalproject.R;
import com.example.finalproject.util.PreferenceUtil;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog dialog;
    protected Menu menu;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceUtil.setContext(this);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void setTitle(Toolbar toolbar, String title){
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.menu = menu;


        return super.onCreateOptionsMenu(menu);
    }
    public void showProgress() {
        if (mDialog == null) {
            mDialog = new AlertDialog.Builder(this)
                    .setView(R.layout.dialog_progress)
                    .create();
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.getWindow().setBackgroundDrawable(new BitmapDrawable());
        }
        mDialog.show();
    }
    public void disProgress() {
        BaseActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                if (mDialog != null) {
                    try {
                        if(mDialog.isShowing()){
                            mDialog.dismiss();
                        }
                    }catch (IllegalArgumentException e) {
                        // Do nothing.
                    } catch (Exception e){

                    }
                    mDialog = null;
                }
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();

    }

}
