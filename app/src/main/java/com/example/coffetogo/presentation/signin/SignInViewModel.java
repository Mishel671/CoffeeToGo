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


    private MutableLiveData<Boolean> successLogin= new MutableLiveData<>();
    public LiveData<Boolean> getSuccessLogin() {
        return successLogin;
    }
    public void clearSuccessLogin() {
        successLogin.setValue(false);
    }

    private MutableLiveData<String> error = new MutableLiveData<>();
    public LiveData<String> getError() {
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
                            AppPreferences.getInstance().setMail(userInfo.getMail());
                            AppPreferences.getInstance().setLogin(userInfo.getLogin());
                            successLogin.setValue(true);
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
        if (disposable != null) {
            disposable.dispose();
        }
        super.onCleared();
    }

    public SignInViewModel(@NonNull Application application) {
        super(application);
    }
}
