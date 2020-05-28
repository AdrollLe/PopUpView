package view.adroll.popupview.dialog.simpleone;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import view.adroll.popupview.AnimationUtil;
import view.adroll.popupview.R;
import view.adroll.popupview.dialog.PopUpViewDialog;
import view.adroll.popupview.dialog.callback.PopUpViewTransferCallBack;

/**
 * Created by Adroll
 * on 2020/5/25
 * Description: 样式一的子view
 * * * * * * * * * * * * * * * * * * * * * *
 * Thinking is more important than coding. *
 * * * * * * * * * * * * * * * * * * * * * *
 */
public class SimpleOneSubView extends RelativeLayout implements View.OnFocusChangeListener, View.OnKeyListener {

    private TextView tv;
    private View view;

    // 当前所在行数
    private int row;

    private PopUpViewTransferCallBack transferCallBack;

    public SimpleOneSubView(Context context) {
        this(context, null);
    }

    public SimpleOneSubView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SimpleOneSubView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);

        initView();
        initListener();
    }

    private void initView(){
        inflate(getContext(), R.layout.view_simple_one_sub, this);
        tv = findViewById(R.id.view_simple_one_sub_tv);
        view = findViewById(R.id.view_simple_one_sub_view_bg);
    }

    private void initListener(){
        view.setOnFocusChangeListener(this);
        view.setOnKeyListener(this);
    }

    private void initData(String text){
        tv.setText(text);
    }

    public void setParams(PopUpViewTransferCallBack transferCallBack, int row, String text){
        this.transferCallBack = transferCallBack;
        this.row = row;

        initData(text);
    }

    /**
     * 首次延迟放大，否则会出现放大被截取效果
     */
    public void initEnlarge(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onFocusChange(view, true);
            }
        }, PopUpViewDialog.POP_UP_ANIMATION_DURATION + 50);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            AnimationUtil.enlarge(v, 1.2f);
            tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv.setTextColor(getContext().getResources().getColor(R.color.white));
        }else {
            AnimationUtil.shrink(v, 1.2f);
            tv.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tv.setTextColor(getContext().getResources().getColor(R.color.color_blue));
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN  && keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
            transferCallBack.moveDown(row);
            return true;
        } else if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_UP){
            transferCallBack.moveUp(row);
            return true;
        }
        return false;
    }
}
