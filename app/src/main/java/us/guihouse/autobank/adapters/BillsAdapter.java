package us.guihouse.autobank.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import us.guihouse.autobank.R;
import us.guihouse.autobank.bean.Bill;
import us.guihouse.autobank.bean.ClosedBill;
import us.guihouse.autobank.bean.OpenBill;
import us.guihouse.autobank.callbacks.BillsCallback;

/**
 * Created by valmir.massoni on 07/11/2016.
 */

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.CustomViewHolder> {

    private List<Object> genericBills;
    private Context mContext;
    private BillsCallback callback;

    public BillsAdapter(Context cx, BillsCallback callback) {
        this.mContext = cx;
        this.callback = callback;
    }

    public void setContentList(List<Object> list) {
        this.genericBills = list;
        notifyDataSetChanged();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_row_item, parent, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Object genericBill = this.genericBills.get(position);
        int color;
        if(genericBill instanceof ClosedBill) {
            ClosedBill currentClosedBill = (ClosedBill) genericBill;
            color = ContextCompat.getColor(mContext, R.color.colorClosedBillNotPaid);
            if(currentClosedBill.getPaidValue().compareTo(currentClosedBill.getMinValue()) >= 0) {
                color = ContextCompat.getColor(mContext, R.color.colorClosedBillPaid);
            }
            holder.tvMonthYear.setText(String.format("%02d", currentClosedBill.getMonth()) + "/" + currentClosedBill.getYear());
            holder.tvValue.setText(String.format("R$ %.2f", currentClosedBill.getTotalValue()));
        }
        else {
            color = ContextCompat.getColor(mContext, R.color.colorOpenBill);
            OpenBill currentOpenBill = (OpenBill) genericBill;
            holder.tvMonthYear.setText(String.format("%02d", currentOpenBill.getMonth()) + "/" + currentOpenBill.getYear());
            holder.tvValue.setText(String.format("R$ %.2f", currentOpenBill.getPartialValue()));
        }
        holder.tvValue.setTextColor(color);
    }

    @Override
    public int getItemCount() {
        return (this.genericBills != null)? this.genericBills.size(): 0;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView tvMonthYear;
        protected TextView tvValue;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.tvMonthYear = (TextView) itemView.findViewById(R.id.tvMonthYear);
            this.tvValue = (TextView) itemView.findViewById(R.id.tvValue);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            callback.onRowClick(((Bill)genericBills.get(this.getAdapterPosition())).getId());
        }
    }
}