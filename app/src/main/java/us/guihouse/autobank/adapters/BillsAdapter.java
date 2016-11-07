package us.guihouse.autobank.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import us.guihouse.autobank.R;
import us.guihouse.autobank.bean.Bill;

/**
 * Created by valmir.massoni on 07/11/2016.
 */

public class BillsAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private List<Bill> bills;
    private Context mContext;

    public BillsAdapter(Context cx, List<Bill> bills) {
        this.bills = bills;
        this.mContext = cx;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_row_item, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
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