package view.adroll.popupview.dialog.simpletwo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import view.adroll.popupview.R;
import view.adroll.popupview.dialog.callback.PopUpViewTransferCallBack;
import view.adroll.popupview.dialog.simpletwo.viewholder.SimpleTwoViewHolder;

/**
 * Created by Adroll
 * on 2020/5/27
 * Description:
 * * * * * * * * * * * * * * * * * * * * * *
 * Thinking is more important than coding. *
 * * * * * * * * * * * * * * * * * * * * * *
 */
public class SimpleTwoAdapter extends RecyclerView.Adapter<SimpleTwoViewHolder> {

    private PopUpViewTransferCallBack transferCallBack;

    private List<String> data;
    private int row;

    public SimpleTwoAdapter(PopUpViewTransferCallBack transferCallBack, List<String> data, int row){
        this.transferCallBack = transferCallBack;
        this.data = data;
        this.row = row;
    }

    public PopUpViewTransferCallBack getTransferCallBack() {
        return transferCallBack;
    }

    public int getRow() {
        return row;
    }

    @NonNull
    @Override
    public SimpleTwoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_simple_two, parent, false);
        return new SimpleTwoViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleTwoViewHolder holder, int position) {
        holder.tv.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
