package com.example.guanzhuli.icart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.guanzhuli.icart.adapters.CartListAdapter;
import com.example.guanzhuli.icart.data.Category;
import com.example.guanzhuli.icart.data.CompraData;
import com.example.guanzhuli.icart.data.CompraLinhaData;
import com.example.guanzhuli.icart.data.Item;
import com.example.guanzhuli.icart.data.SPManipulation;
import com.example.guanzhuli.icart.data.ShoppingCartList;
import com.example.guanzhuli.icart.service.CartService;
import com.example.guanzhuli.icart.utils.API;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private TextView mTextTotal;
    private Button mButtonContinue, mButtonCheckout;
    private RecyclerView recyclerView;

    private SPManipulation mSPManipulation;
    private ShoppingCartList itemList;
    private CompraData carrinho;
    private List<CompraLinhaData> compraLinha;
    private CartService cartService;
    private String hashcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = (RecyclerView) findViewById(R.id.cart_container);
        mSPManipulation = SPManipulation.getInstance(this);
        hashcode =  mSPManipulation.getHashcode();
        getCart();
         showCart();
        //mDBManipulation = DBManipulation.getInstance(this, name + mobile);
        itemList = ShoppingCartList.getInstance();
        itemList.clear();
        //itemList.addAll( mDBManipulation.selectAll());
        //itemList = mDBManipulation.selectAll();


        // Button
        mButtonContinue = (Button) findViewById(R.id.cart_continue);
        mButtonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mButtonCheckout = (Button) findViewById(R.id.cart_checkout);
        mButtonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(intent);

            }
        });
        //Set Total
        mTextTotal = (TextView) findViewById(R.id.cart_total);
        mTextTotal.setText(String.valueOf(calculateTotal(itemList)));
    }



    private double calculateTotal(List<Item> itemList) {
        double result = 0;
        for (int i = 0; i < itemList.size(); i++) {
            //result += itemList.get(i).getPrice() * itemList.get(i).getQuantity();
        }
        return result;
    }

    private void showCart(){
        CartListAdapter cartListAdapter = new CartListAdapter(CartActivity.this, carrinho, this);
        recyclerView.setAdapter(cartListAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
    }

    private void getCart(){
        carrinho = new CompraData();
        compraLinha = new ArrayList<>();
        cartService = API.getCartService();
        Call<ResponseBody> call = cartService.findCart(this.hashcode);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if(response.isSuccessful()){
                        JSONObject cart = new JSONObject(response.body().string());
                        JSONArray item = cart.getJSONArray("linhas");
                        Integer id = cart.getInt("id");
                        Integer idCliente = cart.getInt("idCliente");
                        Double precoFinalCart = cart.getDouble("precoFinal");
                        Double precoTotalCart = cart.getDouble("precoTotal");
                        Double taxaTotalCart = cart.getDouble("taxaTotal");
                        for(int i = 0; i < item.length(); i++) {
                            JSONObject product = item.getJSONObject(i);
                            Integer idProduto = product.getInt("idProduto");
                            String nomeProduto = product.getString("nomeProduto");
                            Double preco = product.getDouble("preco");
                            Double precoPack = product.getDouble("precoPack");
                            String porcao = product.getString("porcao");
                            String porcaoPack = product.getString("porcaoPack");
                            Integer quantidade = product.getInt("quantidade");
                            Integer quantidadePack = product.getInt("quantidadePack");
                            Integer quantidadeMinima = product.getInt("quantidadeMinima");
                            Integer versao = product.getInt("versao");
                            String descricao = product.getString("descricao");
                            //Integer idCarrinho = product.getInt("idCarrinho");
                            Double precoFinal = product.getDouble("precoFinal");
                            Double precoTotal = product.getDouble("precoTotal");
                            Double taxaTotal = product.getDouble("taxaTotal");
                            Double precoIVA = product.getDouble("precoIVA");
                            Double precoPackIVA = product.getDouble("precoPackIVA");
                            CompraLinhaData linha = new CompraLinhaData(idProduto, versao, nomeProduto, preco,  precoPack, porcao, porcaoPack, quantidadeMinima, quantidade, quantidadePack,  descricao );
                            compraLinha.add(linha);
                        }
                        carrinho.setLinhas(compraLinha);
                        carrinho.setPrecoFinal(precoFinalCart);
                        carrinho.setPrecoTotal(precoTotalCart);
                        carrinho.setTaxaTotal(taxaTotalCart);
                        carrinho.setId(id);
                        carrinho.setIdCliente(idCliente);
                        showCart();
                    }
                    else
                        Log.d("Error: ", response.errorBody().string());
                    }catch (Exception e){
                        Log.d("Error do server: ", e.getMessage());
                    }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
