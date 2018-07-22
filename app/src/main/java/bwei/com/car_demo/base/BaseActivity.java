package bwei.com.car_demo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity<P extends BasePersenter> extends AppCompatActivity{
   protected  P presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideLayoutId());
        ButterKnife.bind(this);
        initView();
        initListener();
        presenter=proderPersenter();
    }
    protected abstract P proderPersenter();

    protected abstract void initListener();

    protected abstract void initView();

    protected abstract int provideLayoutId();

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
