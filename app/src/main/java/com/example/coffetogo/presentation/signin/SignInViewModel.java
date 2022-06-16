package com.example.coffetogo.presentation.signin;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.coffetogo.R;
import com.example.coffetogo.data.network.ApiFactory;
import com.example.coffetogo.data.network.ApiService;
import com.example.coffetogo.data.AppPreferences;
import com.example.coffetogo.data.network.models.AuthInfo;
import com.example.coffetogo.data.network.models.UserInfo;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;

public class SignInViewModel extends AndroidViewModel {

    private Disposable disposable;


    private MutableLiveData<Unit> successLogin;

    private MutableLiveData<String> error;

    public LiveData<Unit> getSuccessLogin() {
        if (successLogin == null) {
            successLogin = new MutableLiveData<>();
        }
        return successLogin;
    }

    public LiveData<String> getError() {
        if (error == null) {
            error = new MutableLiveData<>();
        }
        return error;
    }

    public void signIn(String login, String password) {
        if (!login.isEmpty() && !password.isEmpty()) {
            ApiService apiService = ApiFactory.getInstance().getApiService();
            AuthInfo authInfo = new AuthInfo(login.trim(), password.trim());
            disposable = apiService.login(authInfo)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<UserInfo>() {
                        @Override
                        public void accept(UserInfo userInfo) throws Exception {
                            AppPreferences.getInstance().setToken(userInfo.getToken());
                            successLogin.setValue(Unit.INSTANCE);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            error.setValue(throwable.getMessage());
                        }
                    });
        } else {
            error.setValue(getApplication().getString(R.string.empty_error));
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public SignInViewModel(@NonNull Application application) {
        super(application);
    }
}
