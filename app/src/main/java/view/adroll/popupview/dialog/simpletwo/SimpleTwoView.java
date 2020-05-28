package view.adroll.popupview.dialog.simpletwo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import view.adroll.popupview.R;
import view.adroll.popupview.dialog.PopUpViewDialog;
import view.adroll.popupview.dialog.base.BasePopUpLayout;
import view.adroll.popupview.dialog.callback.PopUpViewTransferCallBack;
import view.adroll.popupview.dialog.simpletwo.adapter.SimpleTwoAdapter;

/**
 * Created by Adroll
 * on 2020/5/27
 * Description: 样式二的view
 * * * * * * * * * * * * * * * * * * * * * *
 * Thinking is more important than coding. *
 * * * * * * * * * * * * * * * * * * * * * *
 */
public class SimpleTwoView extends BasePopUpLayout {

    private PopUpViewTransferCallBack transferCallBack;

    private TextView tvTitle;
    private RecyclerView rv;

    private int row;

    private SimpleTwoAdapter adapter;

    private ValueAnimator contentGoneAnimator, contentVisibleAnimator, titleGoneAnimator, titleVisibleAnimator;

    public SimpleTwoView(Context context, PopUpViewTransferCallBack transferCallBack) {
        this(context, null, transferCallBack);
    }

    public SimpleTwoView(Context context, AttributeSet attrs, PopUpViewTransferCallBack transferCallBack) {
        this(context, attrs, 0, transferCallBack);
    }

    public SimpleTwoView(Context context, AttributeSet attrs, int defStyleAttr, PopUpViewTransferCallBack transferCallBack) {
        super(context, attrs, defStyleAttr);

        this.transferCallBack = transferCallBack;

        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.view_simple_two, this);
        tvTitle = findViewById(R.id.view_simple_two_tv_title);
        rv = findViewById(R.id.view_simple_two_rv);
    }

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

    private void initContentAnimator(){
        contentGoneAnimator = ValueAnimator.ofInt(rv.getMeasuredHeight(), 0);
        contentGoneAnimator.setDuration(PopUpViewDialog.POP_UP_ANIMATION_DURATION);
        contentGoneAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rv.getLayoutParams();
                params.height = (int) valueAnimator.getAnimatedValue();
                rv.setLayoutParams(params);
                rv.requestLayout();
            }
        });

        contentVisibleAnimator = ValueAnimator.ofInt(0, rv.getMeasuredHeight());
        contentVisibleAnimator.setDuration(PopUpViewDialog.POP_UP_ANIMATION_DURATION);
        contentVisibleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rv.getLayoutParams();
                params.height = (int) valueAnimator.getAnimatedValue();
                rv.setLayoutParams(params);
                rv.requestLayout();
            }
        });
        contentVisibleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                rv.requestFocus();
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

    private void initData(List<String> data) {
        adapter = new SimpleTwoAdapter(transferCallBack, data, row);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }

    public void setData(List<String> data, String title, int row){
        this.row = row;
        tvTitle.setText(title);

        initData(data);
    }

    /**
     * 设置栏目当前状态
     *
     * @param status：
     * 1 -> 消失 -> 在底部/顶部
     * 2 -> 底部/顶部 -> 焦点在子view，标题栏和子view都显示
     * 3 -> 聚焦 -> 在顶部
     * 4 -> 聚焦 -> 在底部
     * 5 -> 最后一个
     * 6 -> 底部/顶部 -> 消失
     */
    @Override
    public void setStatus(int status) {
        switch (status) {
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
                    rv.setVisibility(VISIBLE);
                    rv.post(new Runnable() {
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
                    rv.setVisibility(VISIBLE);
                    rv.post(new Runnable() {
                        @Override
                        public void run() {
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rv.getLayoutParams();
                            params.height = rv.getMeasuredHeight() + 50;
                            rv.setLayoutParams(params);

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
