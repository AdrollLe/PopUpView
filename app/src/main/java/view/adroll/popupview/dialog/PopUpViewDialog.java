package view.adroll.popupview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import view.adroll.popupview.R;
import view.adroll.popupview.dialog.base.BasePopUpLayout;
import view.adroll.popupview.dialog.callback.PopUpViewTransferCallBack;
import view.adroll.popupview.dialog.simpleone.SimpleOneView;
import view.adroll.popupview.dialog.simpletwo.SimpleTwoView;

/**
 * Created by Adroll
 * on 2020/5/25
 * Description: 仿酷喵弹出选项弹窗
 * * * * * * * * * * * * * * * * * * * * * *
 * Thinking is more important than coding. *
 * * * * * * * * * * * * * * * * * * * * * *
 */
public class PopUpViewDialog extends Dialog implements PopUpViewTransferCallBack {

    private LinearLayout llContainer;

    private SimpleOneView firstLine = null, thirdLine = null, fourthLine = null;
    private SimpleTwoView secondLine = null, fifthLine = null;

    // 上下按键动画是否结束
    private volatile boolean isAnimationOver = true;

    // 是否初始化
    private boolean isInit = true;
    // 一共有多少行
    private int lineCount = 0;
    public static final int POP_UP_ANIMATION_DURATION = 250;

    private List<BasePopUpLayout> viewList = new ArrayList<>();

    public PopUpViewDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pop_up_view);

        initView();
        initData();
    }

    private void initView() {
        llContainer = findViewById(R.id.dialog_pop_up_view_ll_container);

        firstLine = new SimpleOneView(getContext(), this);
        secondLine = new SimpleTwoView(getContext(), this);
        thirdLine = new SimpleOneView(getContext(), this);
        fourthLine = new SimpleOneView(getContext(), this);
        fifthLine = new SimpleTwoView(getContext(), this);
    }

    private void initData() {
        // 第一行数据
        List<String> firstList = new LinkedList<>();
        firstList.add("11111");
        firstList.add("22222");
        firstList.add("33333");
        firstList.add("44444");

        firstLine.setData(firstList, "第一行", ++lineCount);
        viewList.add(firstLine);

        // 第二行数据
        List<String> secondList = new LinkedList<>();
        secondList.add("1111");
        secondList.add("2222");
        secondList.add("3333");
        secondLine.setData(secondList, "第二行", ++lineCount);
        viewList.add(secondLine);

        // 第三行数据
        List<String> thirdList = new LinkedList<>();
        thirdList.add("55555");
        thirdList.add("66666");
        thirdList.add("77777");
        thirdList.add("88888");
        thirdList.add("99999");

        thirdLine.setData(thirdList, "第三行", ++lineCount);
        viewList.add(thirdLine);

        // 第四行数据
        List<String> fourthList = new LinkedList<>();
        fourthList.add("00000");
        fourthList.add("00000");

        fourthLine.setData(fourthList, "第四行", ++lineCount);
        viewList.add(fourthLine);

        // 第五行数据
        List<String> fifthList = new LinkedList<>();
        fifthList.add("4444");
        fifthList.add("5555");
        fifthList.add("6666");
        fifthList.add("7777");
        fifthList.add("8888");
        fifthLine.setData(fifthList, "第五行", ++lineCount);
        viewList.add(fifthLine);

        /**
         * 第一次显示每行显示逻辑
         */
        thirdLine.setVisibility(View.GONE);
        fourthLine.setVisibility(View.GONE);
        fifthLine.setVisibility(View.GONE);

        llContainer.addView(firstLine);
        llContainer.addView(secondLine);
        llContainer.addView(thirdLine);
        llContainer.addView(fourthLine);
        llContainer.addView(fifthLine);
    }

    @Override
    public void show() {
        super.show();

        if (isInit){
            isInit = false;
            firstLine.setStatus(2);
            secondLine.setStatus(1);
        }
    }

    @Override
    public void moveUp(int currentIndex) {
        if (!isAnimationOver){
            return;
        }

        isAnimationOver = false;
        onMoveUp(currentIndex);
    }

    @Override
    public void moveDown(int currentIndex) {
        if (!isAnimationOver){
            return;
        }

        isAnimationOver = false;
        onMoveDown(currentIndex);
    }

    @Override
    public void dismiss() {
        super.dismiss();

        isAnimationOver = true;
    }

    @Override
    public void animationOver() {
        isAnimationOver = true;
    }

    private void onMoveUp(int currentIndex) {
        if (lineCount == currentIndex && lineCount > 3) {
            viewList.get(currentIndex - 1).setStatus(4);
            viewList.get(currentIndex - 2).setStatus(2);
            if (viewList.get(currentIndex - 4).getVisibility() == View.GONE){
                viewList.get(currentIndex - 4).setVisibility(View.VISIBLE);
            }
            viewList.get(currentIndex - 4).setStatus(1);
        } else if (lineCount == currentIndex){
            viewList.get(currentIndex - 1).setStatus(4);
            viewList.get(currentIndex - 2).setStatus(2);
        } else if (currentIndex > 1 && currentIndex <= 3 && lineCount >= 3) {
            viewList.get(currentIndex).setStatus(6);
            viewList.get(currentIndex - 1).setStatus(4);
            viewList.get(currentIndex - 2).setStatus(2);
        } else if (currentIndex == 1) {
            dismiss();
        } else if (lineCount <= 3){
            viewList.get(currentIndex - 1).setStatus(4);
            viewList.get(currentIndex - 2).setStatus(2);
        } else {
            viewList.get(currentIndex).setStatus(6);
            viewList.get(currentIndex - 1).setStatus(4);
            viewList.get(currentIndex - 2).setStatus(2);
            if (viewList.get(currentIndex - 4).getVisibility() == View.GONE){
                viewList.get(currentIndex - 4).setVisibility(View.VISIBLE);
            }
            viewList.get(currentIndex - 4).setStatus(1);
        }
    }

    private void onMoveDown(int currentIndex) {
        if (currentIndex == lineCount) {
            isAnimationOver = true;
        } else if ((currentIndex == 1 && lineCount > 2) || (currentIndex == 2 && lineCount > 3)) {
            viewList.get(currentIndex - 1).setStatus(3);
            viewList.get(currentIndex).setStatus(2);
            if (viewList.get(currentIndex + 1).getVisibility() == View.GONE){
                viewList.get(currentIndex + 1).setVisibility(View.VISIBLE);
            }
            viewList.get(currentIndex + 1).setStatus(1);
        } else if (currentIndex == 1 || currentIndex == 2){
            viewList.get(currentIndex - 1).setStatus(3);
            viewList.get(currentIndex).setStatus(5);
        } else if ((lineCount - currentIndex == 1) && lineCount > 3) {
            viewList.get(currentIndex - 3).setStatus(6);
            viewList.get(currentIndex - 1).setStatus(3);
            viewList.get(currentIndex).setStatus(5);
        } else if (lineCount - currentIndex == 1){
            viewList.get(currentIndex - 1).setStatus(3);
            viewList.get(currentIndex).setStatus(5);
        } else {
            viewList.get(currentIndex - 3).setStatus(6);
            viewList.get(currentIndex - 1).setStatus(3);
            viewList.get(currentIndex).setStatus(2);
            if (viewList.get(currentIndex + 1).getVisibility() == View.GONE){
                viewList.get(currentIndex + 1).setVisibility(View.VISIBLE);
            }
            viewList.get(currentIndex + 1).setStatus(1);
        }
    }
}
