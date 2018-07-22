package bwei.com.car_demo.view;

import bwei.com.car_demo.base.IView;
import bwei.com.car_demo.bean.CarBean;
import bwei.com.car_demo.bean.DeleteBean;
import bwei.com.car_demo.bean.UpdataCartBean;

public interface CarView extends IView {
    void OnGetCarUssed(CarBean carBean);
    void OnDeleteUssed(DeleteBean deleteBean);
    void UpdateCartUssed(UpdataCartBean updataCartBean);
}
