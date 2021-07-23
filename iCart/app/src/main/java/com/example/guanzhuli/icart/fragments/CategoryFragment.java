package com.example.guanzhuli.icart.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;

import com.example.guanzhuli.icart.MainActivity;
import com.example.guanzhuli.icart.R;
import com.example.guanzhuli.icart.adapters.CategoryAdapter;
import com.example.guanzhuli.icart.data.Category;
import com.example.guanzhuli.icart.service.CategoryService;
import com.example.guanzhuli.icart.utils.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class CategoryFragment extends Fragment {
    public static final String CATEGORY_ID_KEY = "categoryID";
    public static final String CATEGORY_NAME_KEY = "categoryName";
    private String hashcode;

    ArrayList<HashMap<String,String>> categorias;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        MainActivity main = (MainActivity)getActivity();
        hashcode = main.getHashcode();

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Categoria");
        MainActivity main = (MainActivity)getActivity();
        hashcode = main.getHashcode();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_category, container, false);
        categorias = new ArrayList<>();

        try {
            Log.d("Error",hashcode);
        }catch (Exception e){
            Log.d("Error",e.toString());
        }

        try {
            getCategories();
        } catch (Exception e) {
            Log.d("Error: ", e.toString());
        }

        return v;
    }


    public void getCategories(){
        CategoryService categoryService;
        categoryService = API.getCategoryService();
        Call<ResponseBody> call = categoryService.listCategories(hashcode);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    List<Category> categoryList = new ArrayList<>();
                    try {
                        JSONArray categories = new JSONArray(response.body().string());
                        for(int i = 0; i < categories.length(); i++) {
                            JSONObject item = categories.getJSONObject(i);
                            Integer id = item.getInt("id");
                            String name = item.getString("nome_categoria");
                            String description = item.getString("descricao");
                            String imageUrl = item.getString("id");
                            categoryList.add(new Category(imageUrl, name, id, description));
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                    final CategoryAdapter adapter = new CategoryAdapter(getContext(), R.layout.category_listview_item, categoryList);
                    ListView listView = (ListView) getView().findViewById(R.id.product_list);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Category category = (Category) adapterView.getItemAtPosition(i);
                            ItemListFragment productFragment = new ItemListFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString(CATEGORY_NAME_KEY, category.getName());
                            productFragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                                    .replace(R.id.main_fragment_container, productFragment)
                                    .addToBackStack(ItemListFragment.class.getName())
                                    .commit();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
