package bwei.com.car_demo.model;

import bwei.com.car_demo.bean.CarBean;
import bwei.com.car_demo.bean.DeleteBean;
import bwei.com.car_demo.bean.UpdataCartBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CarApi {
    @GET("product/getCarts")
    Observable<CarBean> CarLogin(@Query("Uid")Integer uid);

    @GET("product/deleteCart")
    Observable<DeleteBean>deletelogin(@Query("Uid")Integer Uid, @Query("Pid")Integer pid);
    @GET("product/updateCarts")
    Observable<UpdataCartBean>updateLogin(@Query("uid")Integer uid, @Query("sellerid") String sellerid, @Query("pid")Integer pid, @Query("num")Integer num, @Query("selected")Integer selected);
}
