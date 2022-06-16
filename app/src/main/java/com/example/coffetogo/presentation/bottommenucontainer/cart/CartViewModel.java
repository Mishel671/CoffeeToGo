package com.example.coffetogo.presentation.bottommenucontainer.cart;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.coffetogo.data.database.AppDatabase;
import com.example.coffetogo.data.database.CartDao;
import com.example.coffetogo.data.database.CartDbModel;
import com.example.coffetogo.data.database.CartItem;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CartViewModel extends AndroidViewModel {

    private final CartDao cartDao = AppDatabase.getInstance(getApplication()).cartDao();
    private final MutableLiveData<List<CartItem>> cartList = new MutableLiveData<>();

    public LiveData<List<CartItem>> getCartList() {
        return cartList;
    }

    private final MutableLiveData<Integer> resultCost = new MutableLiveData<>();

    public LiveData<Integer> getResultCost() {
        return resultCost;
    }

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public CartViewModel(@NonNull Application application) {
        super(application);
        getCartItems();
    }

    private void getCartItems() {
        Disposable disposable = cartDao.getCartList()
                .flatMap(cartDbModels ->
                        Flowable.fromIterable(cartDbModels)
                                .map(cartDbModel -> mapDbModelToItem(cartDbModel))
                                .toList()
                                .toFlowable()
                )
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(cartItems -> {
                    Log.d("CartLog", String.valueOf(cartItems.get(0).getItemCount()));
                    cartList.postValue(cartItems);
                    resultCost.postValue(countResult(cartItems));
                }, throwable -> {
                    Log.d("CartLog", throwable.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    private int countResult(List<CartItem> list) {
        int resultCost = 0;
        for (CartItem cartItem : list) {
            resultCost += cartItem.getCost() * cartItem.getItemCount();
        }
        return resultCost;
    }

    public void changeCountItem(CartItem oldItem, int deltaCount) {
        int newItemCount = oldItem.getItemCount() + deltaCount;
        CartItem cartItem = new CartItem(
                oldItem.getName(),
                oldItem.getCost(),
                newItemCount
        );
        Observable<CartItem> observable = Observable.just(cartItem);
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .subscribe(item -> {
                    if (item.getItemCount() <= 0) {
                        cartDao.deleteCartItem(item.getName());
                    } else {
                        cartDao.updateCartItem(mapItemToDbModel(item));
                    }
                }, throwable ->
                        Log.d("ViewModelLog", throwable.toString()));
        compositeDisposable.add(disposable);

    }

    private CartItem mapDbModelToItem(CartDbModel cartDbModel) {
        return new CartItem(
                cartDbModel.name,
                cartDbModel.cost,
                cartDbModel.itemCount
        );
    }

    private CartDbModel mapItemToDbModel(CartItem cartItem) {
        return new CartDbModel(
                cartItem.getName(),
                cartItem.getCost(),
                cartItem.getItemCount()
        );
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
