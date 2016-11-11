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

/**
 * Created by valmir.massoni on 07/11/2016.
 */

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.CustomViewHolder> {

    private List<Object> genericBills;
    private Context mContext;

    public BillsAdapter(Context cx) {
        this.mContext = cx;
    }

    public void setContentList(List<Object> list) {
        this.genericBills = list;
        notifyDataSetChanged();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_row_item, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Object genericBill = this.genericBills.get(position);
        int color;
        if(genericBill instanceof ClosedBill) {
            color = ContextCompat.getColor(mContext, R.color.colorClosedBill);
            ClosedBill currentClosedBill = (ClosedBill) genericBill;
            holder.tvMonth.setText(Integer.toString(currentClosedBill.getMonth()));
            holder.tvYear.setText(Integer.toString(currentClosedBill.getYear()));
            holder.tvValue.setText(String.format("R$ %.2f", currentClosedBill.getTotalValue()));
        }
        else {
            color = ContextCompat.getColor(mContext, R.color.colorOpenBill);
            OpenBill currentOpenBill = (OpenBill) genericBill;
            holder.tvMonth.setText(Integer.toString(currentOpenBill.getMonth()));
            holder.tvYear.setText(Integer.toString(currentOpenBill.getYear()));
            holder.tvValue.setText(String.format("R$ %.2f", currentOpenBill.getPartialValue()));
        }

        holder.tvValue.setTextColor(color);
        holder.tvMonth.setTextColor(color);
        holder.tvYear.setTextColor(color);
    }

    @Override
    public int getItemCount() {
        return (this.genericBills != null)? this.genericBills.size(): 0;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvMonth;
        protected TextView tvYear;
        protected TextView tvValue;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.tvMonth = (TextView) itemView.findViewById(R.id.tvMonth);
            this.tvYear = (TextView) itemView.findViewById(R.id.tvYear);
            this.tvValue = (TextView) itemView.findViewById(R.id.tvValue);
        }
    }
}