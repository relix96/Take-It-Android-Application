package com.example.guanzhuli.icart.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.guanzhuli.icart.fragments.ItemDetailFragment;
import com.example.guanzhuli.icart.fragments.ItemListFragment;
import com.example.guanzhuli.icart.R;
import com.example.guanzhuli.icart.data.Item;
import com.example.guanzhuli.icart.utils.AppController;

import java.util.List;

/**
 * Created by Guanzhu Li on 1/1/2017.
 */
public class ItemGridAdapter extends RecyclerView.Adapter<GridViewHolder> {
    public static final String ITEM_ID = "id";
    public static final String ITEM_NAME = "name";
    public static final String ITEM_PRICE = "price";
    public static final String ITEM_PACKPRICE = "packPrice";
    public static final String ITEM_PORTION = "portion";
    public static final String ITEM_PORTIONPACK = "portionPack";
    public static final String ITEM_QUANTITY = "quantity";
    public static final String ITEM_QUANTITYPACK = "quantityPack";
    public static final String ITEM_MINQUANTITY = "minQuantity";
    public static final String ITEM_DES = "description";
    public static final String ITEM_IMAGEURL = "image_url";
    private Context mContext;
    private LayoutInflater inflater;
    private ImageLoader mImageLoader;
    private List<Item> mItemArrayList;

    public ItemGridAdapter(Context context, List<Item> objects) {
        this.mContext = context;
        mImageLoader = AppController.getInstance().getImageLoader();
        inflater = LayoutInflater.from(mContext);
        mItemArrayList = objects;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cardview_product_grid, parent, false);
        GridViewHolder viewHolder = new GridViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.mTextID.setText(mItemArrayList.get(position).getId());
        holder.mTextName.setText(mItemArrayList.get(position).getNomeProduto());
        holder.mTextPrice.setText(Double.toString(mItemArrayList.get(position).getPreco()));
        //holder.mImageView.setImageUrl(mItemArrayList.get(position).getImageurl(), mImageLoader);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(ITEM_ID, mItemArrayList.get(position).getId());
                bundle.putString(ITEM_NAME, mItemArrayList.get(position).getNomeProduto());
                bundle.putString(ITEM_PORTION, mItemArrayList.get(position).getPorcao());
                bundle.putString(ITEM_PORTIONPACK, mItemArrayList.get(position).getPorcaoPack());
                bundle.putDouble(ITEM_PRICE, mItemArrayList.get(position).getPreco());
                bundle.putDouble(ITEM_PACKPRICE, mItemArrayList.get(position).getPrecoPack());
                bundle.putDouble(ITEM_MINQUANTITY, mItemArrayList.get(position).getQuantidadeMinima());
                bundle.putString(ITEM_DES, mItemArrayList.get(position).getDescricao());
                //bundle.putString(ITEM_IMAGEURL, mItemArrayList.get(position).getImageurl());
                bundle.putInt(ITEM_QUANTITY, mItemArrayList.get(position).getQuantidadeMinima());
                bundle.putDouble(ITEM_PRICE, mItemArrayList.get(position).getPreco());
                itemDetailFragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.enter_from_left)
                        .replace(R.id.main_fragment_container, itemDetailFragment)
                        .addToBackStack(ItemListFragment.class.getName())
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemArrayList.size();
    }
}

class GridViewHolder extends RecyclerView.ViewHolder {
    NetworkImageView mImageView;
    TextView mTextID, mTextName, mTextPrice;
    public GridViewHolder(View itemView) {
        super(itemView);
        mImageView = (NetworkImageView) itemView.findViewById(R.id.item_grid_image);
        mTextID = (TextView) itemView.findViewById(R.id.item_grid_id);
        mTextName = (TextView) itemView.findViewById(R.id.item_grid_name);
        mTextPrice = (TextView) itemView.findViewById(R.id.item_grid_price);
    }
}