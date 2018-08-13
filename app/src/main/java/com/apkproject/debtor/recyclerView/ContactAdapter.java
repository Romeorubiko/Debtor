package com.apkproject.debtor.recyclerView;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkproject.debtor.R;
import com.apkproject.debtor.dataStructure.debts.Debt;
import com.apkproject.debtor.dataStructure.person.Contact;
import com.apkproject.debtor.dataStructure.person.User;
import com.apkproject.debtor.dataStructure.tools.Tool;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_CONTACT = 0;
    public static final int ITEM_TYPE_DATE = 1;

    Context context;
    List<Object> items;
    User me;

    public ContactAdapter(Context context, List<Object> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the custom layout
        RecyclerView.ViewHolder viewHolder = null;
        if(viewType == ITEM_TYPE_CONTACT) {//for contacts
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View row = layoutInflater.inflate(R.layout.row_contact_adapter, parent, false);
            viewHolder= new ViewHolderContact(row);
        }
        else if (viewType == ITEM_TYPE_DATE){//for dates
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View row = layoutInflater.inflate(R.layout.row_date, parent, false);
            viewHolder = new ViewHolderDate(row);
        }

        try {
            me = Tool.getCurrentUser(context);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return viewHolder;
    }
        @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        // Set item views based on views and data model

        //date row
        if(getItemViewType(position) == ITEM_TYPE_DATE){
            Calendar calendar = (Calendar)  items.get(position);
            String month= "";
            switch (calendar.get(Calendar.MONTH)){
                case Calendar.JANUARY:

                    break;
                case Calendar.FEBRUARY:
                    month = context.getString(R.string.january);
                    break;
                case Calendar.MARCH:
                    month = context.getString(R.string.february);
                    break;
                case Calendar.APRIL:
                    month = context.getString(R.string.april);
                    break;
                case Calendar.MAY:
                    month = context.getString(R.string.may);
                    break;
                case Calendar.JUNE:
                    month = context.getString(R.string.june);
                    break;
                case Calendar.JULY:
                    month = context.getString(R.string.july);
                    break;
                case Calendar.AUGUST:
                    month = context.getString(R.string.august);
                    break;
                case Calendar.SEPTEMBER:
                    month = context.getString(R.string.september);
                    break;
                case Calendar.OCTOBER:
                    month = context.getString(R.string.october);
                    break;
                case Calendar.NOVEMBER:
                    month = context.getString(R.string.november);
                    break;
                case Calendar.DECEMBER:
                    month = context.getString(R.string.december);
                    break;
            }

            ((ViewHolderDate)viewHolder).dateText.setText((month.toUpperCase()) +" - "+calendar.get(Calendar.YEAR));

        }

        //contact row
        else if(getItemViewType(position) == ITEM_TYPE_CONTACT) {
            User user = (User) items.get(position);
            ((ViewHolderContact) viewHolder).debt_date.setText(new SimpleDateFormat("dd/MM/yyyy").format(user.getDebtList().get(0).getLastUpdate().getTime()));

            ((ViewHolderContact) viewHolder).name.setText(user.getName());
            int c = user.getDebtList().size();
            if (c >= 1) {
                Debt d = user.getDebtList().get(0);
                ((ViewHolderContact) viewHolder).amount_one.setText(Tool.toCurrencyAndSymbol(d.getAmount(), d.getCurrency()));
                if (d.getTo().getName().equals(me.getName())) {
                    ((ViewHolderContact) viewHolder).amount_one.setTextColor(ContextCompat.getColor(context, R.color.colorBlue));
                }
                ((ViewHolderContact) viewHolder).reason_one.setText(d.getDescription());
            } else {
                ((ViewHolderContact) viewHolder).amount_one.setVisibility(View.INVISIBLE);
                ((ViewHolderContact) viewHolder).reason_one.setVisibility(View.INVISIBLE);
            }
            if (c >= 2) {
                Debt d = user.getDebtList().get(1);
                ((ViewHolderContact) viewHolder).amount_two.setText(Tool.toCurrencyAndSymbol(d.getAmount(), d.getCurrency()));
                if (d.getTo().getName().equals(me.getName())) {
                    ((ViewHolderContact) viewHolder).amount_two.setTextColor(ContextCompat.getColor(context, R.color.colorBlue));
                }
                ((ViewHolderContact) viewHolder).reason_two.setText(d.getDescription());
            } else {
                ((ViewHolderContact) viewHolder).amount_two.setVisibility(View.INVISIBLE);
                ((ViewHolderContact) viewHolder).reason_two.setVisibility(View.INVISIBLE);
            }
            if (c >= 3) {
                ((ViewHolderContact) viewHolder).plus.setText("+" + (c - 2) + " more");
            } else {
                ((ViewHolderContact) viewHolder).plus.setVisibility(View.INVISIBLE);
            }

            double balance = user.getBalance();
            if (balance > 0) {
                ((ViewHolderContact) viewHolder).total_amount.setText(Tool.toCurrencyAndSymbol(user.getBalance(), me.getCurrency()));
            } else {
                ((ViewHolderContact) viewHolder).total_amount.setText(Tool.toCurrencyAndSymbol(user.getBalance() * -1, me.getCurrency()));
                ((ViewHolderContact) viewHolder).total_amount.setTextColor(ContextCompat.getColor(context, R.color.colorBlue));
            }
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(items.get(position) instanceof User)return ITEM_TYPE_CONTACT;
        else if (items.get(position) instanceof Calendar) return ITEM_TYPE_DATE;
        return 99;
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolderContact extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView avatar;
        public TextView name, debt_date, amount_one, amount_two, reason_one, reason_two, plus, total_amount;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolderContact(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            avatar = (ImageView) itemView.findViewById(R.id.row_contact_adapter_user_avatar);
            name = (TextView) itemView.findViewById(R.id.row_contact_adapter_name);
            debt_date = (TextView) itemView.findViewById(R.id.row_contact_adapter_date);
            amount_one = (TextView) itemView.findViewById(R.id.row_contact_adapter_pay_one_amount);
            amount_two = (TextView) itemView.findViewById(R.id.row_contact_adapter_pay_two_amount);
            reason_one = (TextView) itemView.findViewById(R.id.row_contact_adapter_pay_one_reason);
            reason_two = (TextView) itemView.findViewById(R.id.row_contact_adapter_pay_two_reason);
            plus = (TextView) itemView.findViewById(R.id.row_contact_adapter_more);
            total_amount = (TextView) itemView.findViewById(R.id.row_contact_adapter_total_amount);
        }
    }

    public class ViewHolderDate extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView dateText;
        
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolderDate(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            dateText = (TextView) itemView.findViewById(R.id.row_date_text);
        }
    }
}
