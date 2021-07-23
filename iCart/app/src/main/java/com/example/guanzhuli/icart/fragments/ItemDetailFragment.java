package com.example.guanzhuli.icart.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.guanzhuli.icart.CheckoutActivity;
import com.example.guanzhuli.icart.MainActivity;
import com.example.guanzhuli.icart.R;
import com.example.guanzhuli.icart.data.CompraData;
import com.example.guanzhuli.icart.data.CompraLinhaData;
import com.example.guanzhuli.icart.data.SPManipulation;
import com.example.guanzhuli.icart.data.ShoppingCartList;
import com.example.guanzhuli.icart.service.CartService;
import com.example.guanzhuli.icart.utils.API;
import com.example.guanzhuli.icart.utils.AppController;

import java.math.BigDecimal;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.guanzhuli.icart.adapters.ItemGridAdapter.*;

/**
 * Created by Guanzhu Li on 1/2/2017.
 */
public class ItemDetailFragment extends Fragment {
    private NetworkImageView mImageView;
    private TextView mTextName, mTextId, mTextPrice, mTextDescription, mTextQuant;
    private ImageButton mButtonQuantAdd, mButtonQuantMinus;
    private Button mButtonAddCart, mButtonChceckout;
    private ImageLoader mImageLoader;
    private AppController mController;
    private SPManipulation mSPManipulation;
    private ShoppingCartList mCartList;
    private CartService cartService;
    private String hashcode;
    private Integer qtdP;
    private MainActivity main;
    private Bundle bundle;
    private CompraLinhaData productCart;
    private CompraData carrinho;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mController = AppController.getInstance();
        mImageLoader = mController.getImageLoader();
        mSPManipulation = SPManipulation.getInstance(context);
        mCartList = ShoppingCartList.getInstance();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Product Detail");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);
        mTextId = (TextView) view.findViewById(R.id.item_details_id);
        mTextName = (TextView) view.findViewById(R.id.item_details_name);
        mTextPrice = (TextView) view.findViewById(R.id.item_details_price);
        mTextDescription = (TextView) view.findViewById(R.id.item_details_description);
        mImageView = (NetworkImageView)view.findViewById(R.id.item_details_image);
        main = (MainActivity)getActivity();
        hashcode = main.getHashcode();
        getBundleData();
        setTextViewData();
        return view;
    }

    private void setTextViewData() {
        mTextName.setText(productCart.getNomeProduto());
        mTextDescription.setText("Description: " + productCart.getDescricao());
        mTextPrice.setText("Price: " + Double.parseDouble(productCart.getPreco().toString())+" €");
        //mImageView.setImageUrl(mItem.getImageurl(), mImageLoader);
    }

    private void getBundleData() {
        bundle = this.getArguments();
        if (bundle != null) {
            productCart = new CompraLinhaData();
            carrinho = new CompraData();
            productCart.setIdProduto(bundle.getInt(ITEM_ID));
            productCart.setNomeProduto(bundle.getString(ITEM_NAME));
            productCart.setPorcao(bundle.getString(ITEM_PORTION));
            productCart.setPorcaoPack(bundle.getString(ITEM_PORTIONPACK));
            productCart.setPreco(BigDecimal.valueOf(bundle.getDouble(ITEM_PRICE)));
            productCart.setPrecoPack(BigDecimal.valueOf(bundle.getDouble(ITEM_PACKPRICE)));
            productCart.setQuantidadeMinima(bundle.getInt(ITEM_MINQUANTITY));
            productCart.setDescricao(bundle.getString(ITEM_DES));
            //mItem.setImageurl(bundle.getString(ITEM_IMAGEURL));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mButtonQuantAdd = (ImageButton) getView().findViewById(R.id.button_quantity_add);
        mButtonQuantMinus = (ImageButton) getView().findViewById(R.id.button_quantity_minus);
        mTextQuant = (TextView) getView().findViewById(R.id.item_details_quant);
        mTextQuant.setText(String.valueOf(productCart.getQuantidadeMinima()));
        mButtonQuantAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quant  = Integer.valueOf(mTextQuant.getText().toString());
                mTextQuant.setText(String.valueOf(quant + 1));
            }
        });
        mButtonQuantMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quant  = Integer.valueOf(mTextQuant.getText().toString());
                if (quant > productCart.getQuantidadeMinima()) {
                    mTextQuant.setText(String.valueOf(quant - 1));
                } else {
                    Toast.makeText(getContext(), "Quantidade minima atingida! ", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        mButtonAddCart = (Button) getView().findViewById(R.id.add_cart);

        mButtonAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartService = API.getCartService();
                productCart.setQuantidade(Integer.valueOf(mTextQuant.getText().toString()));
                Log.d("Linha: ",productCart.toString());
                Call<ResponseBody> call = cartService.addCart(productCart,hashcode);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            try{
                                Integer qtdP =  Integer.valueOf(mTextQuant.getText().toString());
                                if (qtdP >= productCart.getQuantidadeMinima()) {
                                    main.getCountProducts();
                                    Toast.makeText(getContext(),productCart.getNomeProduto()+" adicionado ao carrinho!",Toast.LENGTH_SHORT).show();

                                }
                                else{
                                    Toast.makeText(getContext(),"Quantidade incorreta!",Toast.LENGTH_SHORT).show();
                                }

                            }catch (Exception e){
                                Log.d("Error: ",e.getMessage());
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
        mButtonChceckout = (Button) getView().findViewById(R.id.checkout);
        mButtonChceckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mItem.setQuantity(Integer.valueOf(mTextQuant.getText().toString()));
                // mCartList.add(mItem);
                Intent i = new Intent(getContext(), CheckoutActivity.class);
                i.putExtra("SingleItem", true);
                i.putExtra("CheckoutItem", productCart);
                startActivity(i);
            }
        });
    }


}
