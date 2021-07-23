package com.example.guanzhuli.icart.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.icu.math.BigDecimal;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.guanzhuli.icart.R;
import com.example.guanzhuli.icart.data.CompraData;
import com.example.guanzhuli.icart.data.CompraLinhaData;
import com.example.guanzhuli.icart.data.SPManipulation;
import com.example.guanzhuli.icart.utils.AppController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guanzhu Li on 1/3/2017.
 */
public class CartListAdapter extends RecyclerView.Adapter<CartListViewHolder>{

    private Context mContext;
    private Activity mActivity;
    private LayoutInflater inflater;
    private ImageLoader mImageLoader;
    private CompraData mCart;
   // private List<CompraLinhaData> products;
    //private DBManipulation mDBManipulation;
    private SPManipulation mSPManipulation;
    public CartListAdapter(Context context, CompraData objects, Activity activity) {
        this.mActivity = activity;
        this.mContext = context;
        mImageLoader = AppController.getInstance().getImageLoader();
        inflater = LayoutInflater.from(context);
        mCart = objects;
        mSPManipulation = SPManipulation.getInstance(context);
        String name = mSPManipulation.getName();
        String mobile = mSPManipulation.getMobile();
        //mDBManipulation = DBManipulation.getInstance(mContext, name + mobile);
    }

    @Override
    public CartListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.cardview_cart_item, parent, false);
        CartListViewHolder cartListViewHolder = new CartListViewHolder(v);
        return cartListViewHolder;
    }

    @Override
    public void onBindViewHolder(final CartListViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        try {
            Log.d("Cart:", mCart.toString());
            holder.mTextCartId.setText(mCart.getLinhas().get(position).getIdProduto().toString());
            holder.mTextCartquant.setText(Integer.toString(mCart.getLinhas().get(position).getQuantidade()));
            holder.mTextCartName.setText(mCart.getLinhas().get(position).getNomeProduto());
            holder.mTextCartPrice.setText(mCart.getLinhas().get(position).getPreco().toString());

        }
        catch (Exception e){
            Log.d("Error:", e.getMessage());
        }

        //holder.mImage.setImageUrl(mItemArrayList.get(position).getImageurl(), mImageLoader);
        holder.mButtonQuantAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = mCart.getLinhas().get(position).getQuantidadeMinima();
                temp++;
                if (temp > mCart.getLinhas().get(position).getQuantidadeMinima()) {
                    //is accept
                    Toast.makeText(mContext, "Exceeds the stock limit", Toast.LENGTH_LONG ).show();
                    return;
                }
                mCart.getLinhas().get(position).setQuantidadeMinima(temp);
                //mDBManipulation.update(mItemArrayList.get(position).getId().toString(), temp);
                notifyItemChanged(position, mCart);
                holder.mTextCartquant.setText(Integer.toString(temp));
                TextView mTextTotal = (TextView) mActivity.findViewById(R.id.cart_total);
                Double result = Double.parseDouble(mCart.getPrecoFinal().toString());
                result += Double.parseDouble(mCart.getLinhas().get(position).getPreco().toString());
                mTextTotal.setText(String.valueOf(result));
            }
        });
        holder.mButtonQuantMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = mCart.getLinhas().get(position).getQuantidadeMinima();
                if (temp <= 0) {
                    return;
                }
                --temp;
                TextView mTextTotal = (TextView) mActivity.findViewById(R.id.cart_total);
                Double result = Double.parseDouble(mTextTotal.getText().toString());
                result -= Double.parseDouble(mCart.getLinhas().get(position).getPreco().toString());
                mTextTotal.setText(String.valueOf(result));
                mCart.getLinhas().get(position).setQuantidadeMinima(temp);
                //mDBManipulation.update(mItemArrayList.get(position).getId().toString(), temp);
                notifyItemChanged(position, mCart);
                holder.mTextCartquant.setText(Integer.toString(temp));
            }
        });
        holder.mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                // restart the activity
                TextView mTextTotal = (TextView) mActivity.findViewById(R.id.cart_total);
                Double result =Double.parseDouble(mTextTotal.getText().toString());
                result -= mCart.getLinhas().get(position).getQuantidadeMinima() * Double.parseDouble(mCart.getLinhas().get(position).getPreco().toString());
                mTextTotal.setText(String.valueOf(result));
                //mDBManipulation.delete(mItemArrayList.get(position).getId().toString());
                mCart.getLinhas().remove(position);
                notifyItemRemoved(position);
                notifyItemChanged(position, mCart);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mCart.getLinhas().size();
    }
}

class CartListViewHolder extends RecyclerView.ViewHolder {
    NetworkImageView mImage;
    TextView mTextCartName, mTextCartId, mTextCartquant, mTextCartPrice;
    ImageButton mButtonQuantAdd, mButtonQuantMinus;
    ImageView mButtonDelete;

    public CartListViewHolder(View itemView) {
        super(itemView);
        mImage = (NetworkImageView) itemView.findViewById(R.id.cart_item_image);
        mTextCartName = (TextView) itemView.findViewById(R.id.cart_item_name);
        mTextCartId = (TextView) itemView.findViewById(R.id.cart_item_id);
        mTextCartquant = (TextView) itemView.findViewById(R.id.cart_item_number);
        mTextCartPrice = (TextView) itemView.findViewById(R.id.cart_item_price);
        mButtonQuantAdd = (ImageButton) itemView.findViewById(R.id.cart_quant_add);
        mButtonQuantMinus = (ImageButton) itemView.findViewById(R.id.cart_quant_minus);
        mButtonDelete = (ImageView) itemView.findViewById(R.id.cart_item_delete);
    }
}