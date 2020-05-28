package view.adroll.popupview.dialog.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Adroll
 * on 2020/5/26
 * Description:
 * * * * * * * * * * * * * * * * * * * * * *
 * Thinking is more important than coding. *
 * * * * * * * * * * * * * * * * * * * * * *
 */
public abstract class BasePopUpLayout extends RelativeLayout {

    public BasePopUpLayout(Context context) {
        this(context, null);
    }

    public BasePopUpLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasePopUpLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract void setStatus(int status);
}
