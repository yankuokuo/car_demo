package bwei.com.car_demo.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import bwei.com.car_demo.R;
import bwei.com.car_demo.adapter.CarAdapter;
import bwei.com.car_demo.base.BaseActivity;
import bwei.com.car_demo.bean.CarBean;
import bwei.com.car_demo.bean.DeleteBean;
import bwei.com.car_demo.bean.UpdataCartBean;
import bwei.com.car_demo.persenter.CarPersenter;

public class MainActivity extends BaseActivity<CarPersenter> implements CarView {
    @BindView(R.id.main_back)
    TextView mainBack;
    @BindView(R.id.main_prent_view)
    ExpandableListView mainPrentView;
    @BindView(R.id.main_checkbox)
    CheckBox mainCheckbox;
    @BindView(R.id.main_zongjia)
    TextView mainZongjia;
    @BindView(R.id.main_quzhifu)
    Button mainQuzhifu;
    private CarAdapter carAdapter;
    private CarPersenter carPersenter;
    private List<CarBean.DataBean> list;

    @Override
    protected CarPersenter proderPersenter() {
        carPersenter = new CarPersenter(this);
        carPersenter.GetCarLogin(14763);
        return carPersenter;
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void OnGetCarUssed(final CarBean carBean) {
        list = carBean.getData();
        carAdapter = new CarAdapter(list, MainActivity.this);
        carAdapter.SetOnCheckChangeListener(new CarAdapter.OnCheckChangLister() {
            @Override
            public void OnPrentCheck(int i) {
                boolean ischeak = carAdapter.ischeak(i);
                carAdapter.Allchildaddcheckstatus(i,!ischeak);
                carAdapter.notifyDataSetChanged();
                List<CarBean.DataBean.ListBean> data = MainActivity.this.list.get(i).getList();
                for (int j = 0; j <data.size() ; j++) {
                    int pid = data.get(j).getPid();
                    int num = data.get(j).getNum();
                    int selected = data.get(j).getSelected();
                    presenter.UpdateLogin(14763,list.get(i).getSellerid(),pid,num,selected);
                }
                refresh();
            }

            @Override
            public void OnChildCheck(int i, int i1) {
                carAdapter.childcheckcheck(i,i1);
                carAdapter.notifyDataSetChanged();
                presenter.UpdateLogin(14763, list.get(i).getSellerid(), list.get(i).getList().get(i1).getPid(), list.get(i).getList().get(i1).getNum(), list.get(i).getList().get(i1).getSelected());
                refresh();
            }

            @Override
            public void OnNumChange(int i, int i1, int num) {
                carAdapter.changNumBarstatus(i,i1,num);
                carAdapter.notifyDataSetChanged();
                presenter.UpdateLogin(14763, list.get(i).getSellerid(), list.get(i).getList().get(i1).getPid(), list.get(i).getList().get(i1).getNum(), list.get(i).getList().get(i1).getSelected());
                refresh();
            }

            @Override
            public void OnDeleteChange(int pid) {
                presenter.DeleteLogin(14763,pid);
            }
        });
        mainPrentView.setAdapter(carAdapter);
        for (int i = 0; i < list.size(); i++) {
            mainPrentView.expandGroup(i);
        }
        refresh();

    }

    @Override
    public void OnDeleteUssed(DeleteBean deleteBean) {
        if (deleteBean.getCode().equals("0")){
            Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
            presenter.GetCarLogin(14763);
            carAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(MainActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void UpdateCartUssed(UpdataCartBean updataCartBean) {

    }
    private void refresh(){
        boolean allPrentSelected = carAdapter.isAllPrentSelected();
        mainCheckbox.setChecked(allPrentSelected);
        float allprice = carAdapter.Allprice();
        mainZongjia.setText("总价："+allprice);
        int allnumbar = carAdapter.allNumbar();
        mainQuzhifu.setText("去结算（"+allnumbar+")");
    }
    @OnClick(R.id.main_checkbox)
    public void onViewClicked() {
        boolean allPrentSelected = carAdapter.isAllPrentSelected();
        carAdapter.setAllproductstatus(!allPrentSelected);
        carAdapter.notifyDataSetChanged();
        refresh();
    }
}
