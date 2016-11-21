package us.guihouse.autobank.adapters;

import android.app.Application;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import us.guihouse.autobank.R;
import us.guihouse.autobank.bean.InterestRate;
import us.guihouse.autobank.bean.Payment;
import us.guihouse.autobank.bean.Purchase;
import us.guihouse.autobank.bean.Reversal;
import us.guihouse.autobank.bean.auxiliar.GenericFinantialStatements;

/**
 * Created by valmir.massoni on 21/11/2016.
 */

public class FinantialStatementAdapter  extends RecyclerView.Adapter {

    private ArrayList<Object> objectsData;

    class GenericViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvTitle;
        protected TextView tvDate;
        protected TextView tvValue;

        public GenericViewHolder(View itemView) {
            super(itemView);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            this.tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            this.tvValue = (TextView) itemView.findViewById(R.id.tvValue);
        }
    }

    class PurchaseViewHolder extends RecyclerView.ViewHolder {
        protected ImageView ivCategory;
        protected TextView tvDate;
        protected TextView tvEstablishment;
        protected TextView tvValue;
        protected TextView tvInstallments;

        public PurchaseViewHolder(View itemView) {
            super(itemView);
            this.ivCategory = (ImageView) itemView.findViewById(R.id.ivCategory);
            this.tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            this.tvEstablishment = (TextView) itemView.findViewById(R.id.tvEstablishment);
            this.tvValue = (TextView) itemView.findViewById(R.id.tvValue);
            this.tvInstallments = (TextView) itemView.findViewById(R.id.tvInstallments);
        }
    }

    public void setObjectsData(ArrayList<Object> objectsData) {
        this.objectsData = objectsData;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if(objectsData.get(position) instanceof Purchase)
            return 0;

        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        if(viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_finantial_statement_row, parent, false);
            return new PurchaseViewHolder(view);
        }

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.generic_finantial_statement_row, parent, false);
        return new GenericViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object genericObj = objectsData.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if(genericObj instanceof Purchase ){
            PurchaseViewHolder purchaseHolder = (PurchaseViewHolder) holder;
            Purchase currentPurchase = (Purchase) genericObj;

            purchaseHolder.tvDate.setText(dateFormat.format(currentPurchase.getProcessDate()));
            purchaseHolder.tvEstablishment.setText(currentPurchase.getEstablishmentName());
            purchaseHolder.tvValue.setText(String.format("R$%.2f", currentPurchase.getPurchaseValue()));
            purchaseHolder.tvInstallments.setText(currentPurchase.getInstallmentSequential() + "/" + currentPurchase.getInstallmentsAmount());
            return;
        }
        GenericViewHolder genericHolder = (GenericViewHolder) holder;
        int color = ContextCompat.getColor(genericHolder.tvTitle.getContext(), R.color.colorClosedBillPaid);
        if(genericObj instanceof Payment) {
            Payment payment = (Payment) genericObj;
            genericHolder.tvTitle.setText(genericHolder.tvTitle.getContext().getResources().getString(R.string.payment));
            genericHolder.tvDate.setText(dateFormat.format(payment.getProcessDate()));
            genericHolder.tvValue.setText(String.format("R$%.2f", payment.getPaymentValue()));
        } else if(genericObj instanceof Reversal) {
            Reversal reversal = (Reversal) genericObj;
            genericHolder.tvTitle.setText(genericHolder.tvTitle.getContext().getResources().getString(R.string.payment));
            genericHolder.tvDate.setText(dateFormat.format(reversal.getProcessDate()));
            genericHolder.tvValue.setText(String.format("R$%.2f", reversal.getReversalValue()));
        } else {
            InterestRate interestRate = (InterestRate) genericObj;
            color = ContextCompat.getColor(genericHolder.tvTitle.getContext(), R.color.colorClosedBillNotPaid);
            genericHolder.tvTitle.setText(genericHolder.tvTitle.getContext().getResources().getString(R.string.payment));
            genericHolder.tvDate.setText(dateFormat.format(interestRate.getProcessDate()));
            genericHolder.tvValue.setText(String.format("R$%.2f", interestRate.getInterestRateValue()));
        }

        genericHolder.tvTitle.setTextColor(color);
        genericHolder.tvValue.setTextColor(color);
    }

    @Override
    public int getItemCount() {
        return objectsData != null ? objectsData.size() : 0;
    }
}
