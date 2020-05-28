package view.adroll.popupview.dialog.simpletwo.viewholder;

import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import view.adroll.popupview.AnimationUtil;
import view.adroll.popupview.R;
import view.adroll.popupview.dialog.simpletwo.adapter.SimpleTwoAdapter;

/**
 * Created by Adroll
 * on 2020/5/27
 * Description:
 * * * * * * * * * * * * * * * * * * * * * *
 * Thinking is more important than coding. *
 * * * * * * * * * * * * * * * * * * * * * *
 */
public class SimpleTwoViewHolder extends RecyclerView.ViewHolder implements View.OnKeyListener, View.OnFocusChangeListener {

    private SimpleTwoAdapter adapter;

    private LinearLayout ll;
    public TextView tv;

    public SimpleTwoViewHolder(View parent, SimpleTwoAdapter adapter) {
        super(parent);

        this.adapter = adapter;

        ll = parent.findViewById(R.id.view_item_simple_two_ll);
        tv = parent.findViewById(R.id.view_item_simple_two_tv);

        ll.setOnKeyListener(this);
        ll.setOnFocusChangeListener(this);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            adapter.getTransferCallBack().moveUp(adapter.getRow());
            return true;
        } else if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            adapter.getTransferCallBack().moveDown(adapter.getRow());
            return true;
        }

        return false;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b){
            AnimationUtil.enlarge(view, 1.2f);
            tv.setTextColor(itemView.getContext().getResources().getColor(R.color.white));
        }else {
            AnimationUtil.shrink(view, 1.2f);
            tv.setTextColor(itemView.getContext().getResources().getColor(R.color.color_blue));
        }
    }
}
