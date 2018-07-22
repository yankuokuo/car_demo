package bwei.com.car_demo.persenter;

import bwei.com.car_demo.base.BasePersenter;
import bwei.com.car_demo.bean.CarBean;
import bwei.com.car_demo.bean.DeleteBean;
import bwei.com.car_demo.bean.UpdataCartBean;
import bwei.com.car_demo.model.CarModel;
import bwei.com.car_demo.view.CarView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CarPersenter extends BasePersenter<CarView>{

    private CarModel carModel;

    public CarPersenter(CarView view) {
        super(view);
    }

    @Override
    protected void initModel() {
        carModel = new CarModel();
    }
   public void GetCarLogin(Integer uid){
        carModel.CarLogin(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CarBean>() {
                    @Override
                    public void accept(CarBean carBean) throws Exception {
                        view.OnGetCarUssed(carBean);
                    }
                });
   }
   public void DeleteLogin(Integer uid, Integer pid){
        carModel.deleteLogin(uid,pid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DeleteBean>() {
                    @Override
                    public void accept(DeleteBean deleteBean) throws Exception {
                        view.OnDeleteUssed(deleteBean);
                    }
                });
   }
   public void UpdateLogin(Integer uid, String sellerid, Integer pid, Integer num, Integer selected){
        carModel.updateLogin(uid,sellerid,pid,num,selected)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UpdataCartBean>() {
                    @Override
                    public void accept(UpdataCartBean updataCartBean) throws Exception {
                        view.UpdateCartUssed(updataCartBean);
                    }
                });
   }
}
