package bwei.com.car_demo.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.datasource.ListDataSource;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import bwei.com.car_demo.R;
import bwei.com.car_demo.bean.CarBean;

public class CarAdapter extends BaseExpandableListAdapter {
    private List<CarBean.DataBean> list;
    private Context context;
    public CarAdapter(List<CarBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return list.get(i).getList().size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
        PrentHolder holder;
        if (view==null){
            view = View.inflate(context, R.layout.prent_item, null);
            holder=new PrentHolder(view);
            view.setTag(holder);
        }else{
            holder= (PrentHolder) view.getTag();
        }
        holder.prentName.setText(list.get(i).getSellerName());
        boolean ischeak=ischeak(i);
        holder.prentCheckbox.setChecked(ischeak);
        holder.prentCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCheckChangLister!=null){
                    onCheckChangLister.OnPrentCheck(i);
                }
            }
        });
        return view;
    }
    //判断商家的选中状态
    public boolean ischeak(int i){
        CarBean.DataBean dataBean = list.get(i);
        List<CarBean.DataBean.ListBean> list = dataBean.getList();
        for (CarBean.DataBean.ListBean list1:list) {
            if (list1.getSelected()==0){
                return false;
            }
        }
        return true;
    }
    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildHolder holder;
        if (view==null){
            view = View.inflate(context, R.layout.child_item, null);
        holder=new ChildHolder(view);
        view.setTag(holder);
        }else{
            holder= (ChildHolder) view.getTag();
        }
        final List<CarBean.DataBean.ListBean> list = this.list.get(i).getList();
        CarBean.DataBean.ListBean listBean = list.get(i1);
        holder.childName.setText(listBean.getTitle());
        holder.childTime.setText(listBean.getCreatetime()+"");
        holder.childPrice.setText("￥"+listBean.getPrice()+"");
        holder.childNum.setNumber(listBean.getNum());
        holder.childCheckbox.setChecked(listBean.getSelected()==1);
        String[] split = listBean.getImages().split("\\|");
        Uri uri = Uri.parse(split[0]);
        holder.childImage.setImageURI(uri);
        holder.childCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCheckChangLister!=null){
                    onCheckChangLister.OnChildCheck(i,i1);
                }
            }
        });
        holder.childNum.setOnNumBerChangListener(new MyaddView.OnNumBerChangListener() {
            @Override
            public void onNumBerChange(int num) {
                if (onCheckChangLister!=null){
                    onCheckChangLister.OnNumChange(i,i1,num);
                }
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pid = list.get(i1).getPid();
                onCheckChangLister.OnDeleteChange(pid);
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
    //判断所有商品是否被选中
    public boolean isAllPrentSelected(){
        for (int i = 0; i <list.size() ; i++) {
            List<CarBean.DataBean.ListBean> list = this.list.get(i).getList();
            for (int j = 0; j <list.size() ; j++) {
                if (list.get(j).getSelected()==0){
                    return false;
                }
            }
        }
        return true;
    }
    //商品点击时
    public void childcheckcheck(int i,int i1){
        CarBean.DataBean dataBean = list.get(i);
        List<CarBean.DataBean.ListBean> list = dataBean.getList();
        CarBean.DataBean.ListBean listBean = list.get(i1);
        listBean.setSelected(listBean.getSelected()==0?1:0);
    }
    //设置所有商品状态
    public void setAllproductstatus(boolean b){
        for (int i = 0; i <list.size() ; i++) {
            List<CarBean.DataBean.ListBean> list = this.list.get(i).getList();
            for (int j = 0; j <list.size() ; j++) {
                list.get(j).setSelected(b?1:0);
            }
        }
    }
    //当商家点击时
    public void Allchildaddcheckstatus(int i,boolean b){
        CarBean.DataBean dataBean = list.get(i);
        List<CarBean.DataBean.ListBean> list = dataBean.getList();
        for (int j = 0; j <list.size() ; j++) {
            CarBean.DataBean.ListBean listBean = list.get(j);
            listBean.setSelected(b?1:0);
        }
    }
    //计算总价
    public float Allprice(){
        float allprice=0;
        for (int i = 0; i <list.size(); i++) {
            List<CarBean.DataBean.ListBean> list = this.list.get(i).getList();
            for (int j = 0; j <list.size() ; j++) {
                if (list.get(j).getSelected()==1){
                    float price = list.get(j).getPrice();
                    int num = list.get(j).getNum();
                    allprice+=price*num;
                }
            }
        }
        return allprice;
    }
    //结算
    public int allNumbar(){
        int tonumbar=0;
        for (int i = 0; i <list.size() ; i++) {
            List<CarBean.DataBean.ListBean> list = this.list.get(i).getList();
            for (int j = 0; j <list.size() ; j++) {
                if (list.get(j).getSelected()==1){
                    int num = list.get(j).getNum();
                    tonumbar+=num;
                }
            }
        }
        return tonumbar;
    }
    //加减器改变数量
    public void changNumBarstatus(int i,int i1,int num){
        CarBean.DataBean dataBean = list.get(i);
        List<CarBean.DataBean.ListBean> list = dataBean.getList();
        CarBean.DataBean.ListBean listBean = list.get(i1);
        listBean.setNum(num);
    }
    class PrentHolder {
        @BindView(R.id.prent_checkbox)
        CheckBox prentCheckbox;
        @BindView(R.id.prent_name)
        TextView prentName;

        PrentHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ChildHolder {
        @BindView(R.id.child_checkbox)
        CheckBox childCheckbox;
        @BindView(R.id.child_image)
        SimpleDraweeView childImage;
        @BindView(R.id.child_name)
        TextView childName;
        @BindView(R.id.child_time)
        TextView childTime;
        @BindView(R.id.child_price)
        TextView childPrice;
        @BindView(R.id.child_num)
        MyaddView childNum;
        @BindView(R.id.child_button)
        Button button;
        ChildHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    OnCheckChangLister onCheckChangLister;
    public interface OnCheckChangLister{
        void OnPrentCheck(int i);
        void OnChildCheck(int i,int i1);
        void OnNumChange(int i,int i1,int num);
        void OnDeleteChange(int pid);
    }
    public void SetOnCheckChangeListener(OnCheckChangLister onCheckChangLister){
        this.onCheckChangLister=onCheckChangLister;
    }
}
