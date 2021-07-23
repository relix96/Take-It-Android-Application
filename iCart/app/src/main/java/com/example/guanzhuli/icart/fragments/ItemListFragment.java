package com.example.guanzhuli.icart.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.guanzhuli.icart.R;
import com.example.guanzhuli.icart.adapters.ItemGridAdapter;
import com.example.guanzhuli.icart.adapters.ItemListAdapter;
import com.example.guanzhuli.icart.data.Item;
import com.example.guanzhuli.icart.utils.AppController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.guanzhuli.icart.fragments.SubCategoryFragment.CATEGORY_NAME_KEY;

public class ItemListFragment extends Fragment {
    public static final String ITEMLIST_URL = "http://192.168.56.1:8080/product/category/";
    public static final String CATEGORY_NAME_KEY = "categoryName";
    private ImageButton mButtonListView, mButtonGridView;
    RecyclerView recyclerView;
    List<Item> itemList;
    String cName;

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(cName);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_product, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.product_container);
        Bundle bundle = this.getArguments();
        String url = null;
        if (bundle != null) {
            String categoryName = bundle.getString(CATEGORY_NAME_KEY, "-1");
            cName = categoryName;
            if (categoryName.equals("-1")) {
                Toast.makeText(getContext(),"CategoryName invalid", Toast.LENGTH_SHORT).show();
                return view;
            } else {
                url = ITEMLIST_URL + categoryName+"/NONE";
            }
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        itemList = new ArrayList<>();
                        try {
                            JSONArray items = new JSONArray(response);
                            for (int i = 0; i < items.length(); i++) {
                                JSONObject item = items.getJSONObject(i);
                                Integer id = item.getInt("id");
                                String name = item.getString("nomeProduto");
                                Double preco = item.getDouble("preco");
                                Double precoPack = item.getDouble("precoPack");
                                String porcao = item.getString("porcao");
                                String porcaoPack = item.getString("porcaoPack");
                                Integer qtdMinima = item.getInt("quantidadeMinima");
                                String description = item.getString("descricao");
                                String imageUrl = item.getString("id");
                                Integer idCategoria = item.getInt("idCategoria");
                                itemList.add(new Item(id, name, preco,precoPack,porcao,porcaoPack,qtdMinima,description,idCategoria));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        final ItemListAdapter adapter = new ItemListAdapter(getContext(), itemList);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);
                        //Layout manager for Recycler view
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "network error!", Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mButtonGridView = (ImageButton) getView().findViewById(R.id.product_gridview);
        mButtonGridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemGridAdapter adapter = new ItemGridAdapter(getContext(), itemList);
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            }
        });
        mButtonListView = (ImageButton) getView().findViewById(R.id.product_listview);
        mButtonListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemListAdapter adapter = new ItemListAdapter(getContext(), itemList);
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
                //Layout manager for Recycler view
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
    }
}