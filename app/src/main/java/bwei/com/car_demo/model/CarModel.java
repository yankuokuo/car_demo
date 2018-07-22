package bwei.com.car_demo.model;

import bwei.com.car_demo.bean.CarBean;
import bwei.com.car_demo.bean.DeleteBean;
import bwei.com.car_demo.bean.UpdataCartBean;
import bwei.com.car_demo.utils.RetrofitManager;
import io.reactivex.Observable;

public class CarModel {
    public Observable<CarBean>CarLogin(Integer uid){
        return RetrofitManager.getDefault().Create(CarApi.class).CarLogin(uid);
    }
    public Observable<DeleteBean>deleteLogin(Integer uid, Integer pid){
        return RetrofitManager.getDefault().Create(CarApi.class).deletelogin(uid,pid);
    }
    public Observable<UpdataCartBean>updateLogin(Integer uid, String sellerid, Integer pid, Integer num, Integer selected){
        return RetrofitManager.getDefault().Create(CarApi.class).updateLogin(uid,sellerid,pid,num,selected);
    }
}
