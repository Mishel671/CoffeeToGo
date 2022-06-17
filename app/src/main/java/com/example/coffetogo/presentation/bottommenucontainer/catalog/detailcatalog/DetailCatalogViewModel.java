package com.example.coffetogo.presentation.bottommenucontainer.catalog.detailcatalog;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.coffetogo.data.database.AppDatabase;
import com.example.coffetogo.data.database.CartDao;
import com.example.coffetogo.data.database.CartDbModel;
import com.example.coffetogo.data.network.models.CatalogItem;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;

public class DetailCatalogViewModel extends AndroidViewModel {

    private final CartDao cartDao = AppDatabase.getInstance(getApplication()).cartDao();
    private final MutableLiveData<Unit> finishScreen = new MutableLiveData<>();

    public LiveData<Unit> getFinishScreen() {
        return finishScreen;
    }

    private CatalogItem catalogItem;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public DetailCatalogViewModel(@NonNull Application application) {
        super(application);
    }

    public void addCartItem(CatalogItem catalogItem) {
        this.catalogItem = catalogItem;
        Observable<CatalogItem> observable = Observable.just(catalogItem);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .subscribe(item -> {
                    Log.d("ViewModelLog", item.getName());
                    cartDao.insertCartItem(mapCatalogItemToCartDbModel(catalogItem, 1));
                }, throwable ->
                        Log.d("ViewModelLog", throwable.toString()));
        compositeDisposable.add(disposable);
    }

    public void saveResult(int itemCount) {
        if (itemCount > 0) {
            Observable<CatalogItem> observable = Observable.just(catalogItem);
            Disposable disposable = observable.subscribeOn(Schedulers.io())
                    .subscribe(item -> {
                        Log.d("ViewModelLog", item.getName());
                        cartDao.insertCartItem(mapCatalogItemToCartDbModel(catalogItem, itemCount));
                    }, throwable ->
                            Log.d("ViewModelLog", throwable.toString()));
            compositeDisposable.add(disposable);
        } else {
            Observable<String> observable = Observable.just(catalogItem.getName());
            Disposable disposable = observable.subscribeOn(Schedulers.io())
                    .subscribe(name -> cartDao.deleteCartItem(name));
            compositeDisposable.add(disposable);

        }
    }

    private CartDbModel mapCatalogItemToCartDbModel(CatalogItem catalogItem, int itemCont) {
        return new CartDbModel(
                catalogItem.getName(),
                catalogItem.getPrice(),
                itemCont
        );
    }


    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
