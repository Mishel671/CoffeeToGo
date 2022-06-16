package com.example.coffetogo.presentation.bottommenucontainer.catalog;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.coffetogo.data.database.AppDatabase;
import com.example.coffetogo.data.database.CartDao;
import com.example.coffetogo.data.database.CartDbModel;
import com.example.coffetogo.data.network.ApiFactory;
import com.example.coffetogo.data.network.ApiService;
import com.example.coffetogo.data.network.models.CatalogItem;
import com.example.coffetogo.data.network.models.CategoryItem;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CatalogViewModel extends AndroidViewModel {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final CartDao cartDao = AppDatabase.getInstance(getApplication()).cartDao();

    private final MutableLiveData<List<CategoryItem>> categoryList = new MutableLiveData<>();
    public LiveData<List<CategoryItem>> getCategoryList() {
        return categoryList;
    }

    private final MutableLiveData<Integer> cartItemCount = new MutableLiveData<>();
    public LiveData<Integer> getCartItemCount() {
        return cartItemCount;
    }

    private final MutableLiveData<List<CatalogItem>> catalogList = new MutableLiveData<>();
    public LiveData<List<CatalogItem>> getCatalogList() {
        return catalogList;
    }


    public CatalogViewModel(@NonNull Application application) {
        super(application);
        getCategories();
        getCartCount();
    }

    private void getCategories() {
        ApiService apiService = ApiFactory.getInstance().getApiService();
        Disposable disposable = apiService.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryItems -> categoryList.setValue(categoryItems));
        compositeDisposable.add(disposable);
    }

    private void getCartCount() {
        Disposable disposable = cartDao.getCartCount()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(cartCount -> cartItemCount.postValue(cartCount));
        compositeDisposable.add(disposable);
    }

    public void getDrinkByTag(String drinkName) {
        ApiService apiService = ApiFactory.getInstance().getApiService();
        Disposable disposable = apiService.getDrink(drinkName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        catalogItems ->
                                catalogList.setValue(catalogItems),
                        throwable ->
                                Log.d("ViewModelLog", throwable.toString())
                );
        compositeDisposable.add(disposable);

    }

    public void addCartItem(CatalogItem catalogItem) {
        Observable<CatalogItem> observable = Observable.just(catalogItem);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .subscribe(item -> {
                    Log.d("ViewModelLog", item.getName());
                    cartDao.insertCartItem(mapItemToDbModel(catalogItem));
                }, throwable ->
                        Log.d("ViewModelLog", throwable.toString()));
        compositeDisposable.add(disposable);
    }

    private CartDbModel mapItemToDbModel(CatalogItem catalogItem) {
        return new CartDbModel(
                catalogItem.getName(),
                catalogItem.getPrice(),
                1
        );
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
