package view.adroll.popupview.dialog.simpleone;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import view.adroll.popupview.R;
import view.adroll.popupview.dialog.PopUpViewDialog;
import view.adroll.popupview.dialog.base.BasePopUpLayout;
import view.adroll.popupview.dialog.callback.PopUpViewTransferCallBack;

/**
 * Created by Adroll
 * on 2020/5/25
 * Description: 样式一的view
 * * * * * * * * * * * * * * * * * * * * * *
 * Thinking is more important than coding. *
 * * * * * * * * * * * * * * * * * * * * * *
 */
public class SimpleOneView extends BasePopUpLayout {

    private TextView tvTitle;
    private LinearLayout llContainer;

    private PopUpViewTransferCallBack transferCallBack;

    private ValueAnimator contentGoneAnimator, contentVisibleAnimator, titleGoneAnimator, titleVisibleAnimator;

    /**
     * 位于菜单栏所在行数
     */
    private int row;

    public SimpleOneView(Context context, PopUpViewTransferCallBack transferCallBack) {
        this(context, null, transferCallBack);
    }

    public SimpleOneView(Context context, AttributeSet attributeSet, PopUpViewTransferCallBack transferCallBack) {
        this(context, attributeSet, 0, transferCallBack);
    }

    public SimpleOneView(Context context, AttributeSet attributeSet, int defStyleAttr, PopUpViewTransferCallBack transferCallBack) {
        super(context, attributeSet, defStyleAttr);

        this.transferCallBack = transferCallBack;

        initView();
    }

    private void initView(){
        inflate(getContext(), R.layout.view_simple_one, this);
        tvTitle = findViewById(R.id.view_simple_one_tv_title);
        llContainer = findViewById(R.id.view_simple_one_ll_container);
    }

    /**
     * 初始化标题动画
     */
    private void initTitleAnimator(){
        titleGoneAnimator = ValueAnimator.ofInt(tvTitle.getMeasuredHeight(), 0);
        titleGoneAnimator.setDuration(PopUpViewDialog.POP_UP_ANIMATION_DURATION);
        titleGoneAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ViewGroup.LayoutParams params = tvTitle.getLayoutParams();
                params.height = (Integer) valueAnimator.getAnimatedValue();
                tvTitle.setLayoutParams(params);
                tvTitle.requestLayout();
            }
        });

        titleVisibleAnimator = ValueAnimator.ofInt(0, tvTitle.getMeasuredHeight());
        titleVisibleAnimator.setDuration(PopUpViewDialog.POP_UP_ANIMATION_DURATION);
        titleVisibleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ViewGroup.LayoutParams params = tvTitle.getLayoutParams();
                params.height = (Integer) valueAnimator.getAnimatedValue();
                tvTitle.setLayoutParams(params);
                tvTitle.requestLayout();
            }
        });
    }

    /**
     * 初始化标题下的内容动画
     */
    private void initContentAnimator(){
        contentGoneAnimator = ValueAnimator.ofInt(llContainer.getMeasuredHeight(), 0);
        contentGoneAnimator.setDuration(PopUpViewDialog.POP_UP_ANIMATION_DURATION);
        contentGoneAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llContainer.getLayoutParams();
                params.height = (Integer) valueAnimator.getAnimatedValue();
                llContainer.setLayoutParams(params);
                llContainer.requestLayout();
            }
        });

        contentVisibleAnimator = ValueAnimator.ofInt(0, llContainer.getMeasuredHeight());
        contentVisibleAnimator.setDuration(PopUpViewDialog.POP_UP_ANIMATION_DURATION);
        contentVisibleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llContainer.getLayoutParams();
                params.height = (Integer) valueAnimator.getAnimatedValue();
                llContainer.setLayoutParams(params);
                llContainer.requestLayout();
            }
        });
        contentVisibleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                llContainer.requestFocus();
                transferCallBack.animationOver();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                transferCallBack.animationOver();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void setData(List data, String title, int row){
        this.row = row;
        tvTitle.setText(title);

        initData(data);
    }

    private void initData(List<String> data){
        for (int i=0; i < data.size(); i++){
            String model = data.get(i);
            SimpleOneSubView subView = new SimpleOneSubView(getContext());
            subView.setClipChildren(false);
            subView.setParams(transferCallBack, row, i, model);
            llContainer.addView(subView);
        }
    }

    /**
     * 设置栏目当前状态
     * @param status：
     *              1 -> 消失 -> 在底部/顶部
     *              2 -> 底部/顶部 -> 焦点在子view，标题栏和子view都显示
     *              3 -> 聚焦 -> 在顶部
     *              4 -> 聚焦 -> 在底部
     *              5 -> 最后一个
     *              6 -> 底部/顶部 -> 消失
     */
    @Override
    public void setStatus(int status){
        switch (status){
            case 1:
                if (titleVisibleAnimator == null){
                    tvTitle.post(new Runnable() {
                        @Override
                        public void run() {
                            initTitleAnimator();
                            titleVisibleAnimator.start();
                        }
                    });
                }else {
                    titleVisibleAnimator.start();
                }
                break;
            case 2:
                tvTitle.setTextSize(18f);
                tvTitle.setTextColor(getContext().getResources().getColor(R.color.color_blue));
                tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                if (contentVisibleAnimator == null){
                    llContainer.setVisibility(VISIBLE);
                    llContainer.post(new Runnable() {
                        @Override
                        public void run() {
                            initContentAnimator();
                            contentVisibleAnimator.start();
                        }
                    });
                }else {
                    contentVisibleAnimator.start();
                }

                if (row == 1 && titleVisibleAnimator == null){
                    tvTitle.post(new Runnable() {
                        @Override
                        public void run() {
                            initTitleAnimator();
                            titleVisibleAnimator.start();
                        }
                    });
                }
                break;
            case 3:
            case 4:
                tvTitle.setTextSize(15f);
                tvTitle.setTextColor(getContext().getResources().getColor(R.color.color_alpha_60_white));
                tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                contentGoneAnimator.start();
                break;
            case 5:
                tvTitle.setTextSize(18f);
                tvTitle.setTextColor(getContext().getResources().getColor(R.color.color_blue));
                tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                if (contentVisibleAnimator == null){
                    llContainer.setVisibility(VISIBLE);
                    llContainer.post(new Runnable() {
                        @Override
                        public void run() {
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llContainer.getLayoutParams();
                            params.height = llContainer.getMeasuredHeight() + 50;
                            llContainer.setLayoutParams(params);

                            initContentAnimator();
                            contentVisibleAnimator.start();
                        }
                    });
                }else {
                    contentVisibleAnimator.start();
                }
                break;
            case 6:
                titleGoneAnimator.start();
                break;
            default:
                break;
        }
    }
}
