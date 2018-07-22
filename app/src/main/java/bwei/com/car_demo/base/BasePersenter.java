package bwei.com.car_demo.base;

import bwei.com.car_demo.model.CarModel;
import bwei.com.car_demo.view.CarView;

public abstract class BasePersenter<V extends IView>{
    protected V view;

    public BasePersenter(V view) {
        this.view = view;
        initModel();
    }

    protected abstract void initModel();
    void onDestroy(){
        view=null;

    }

}
