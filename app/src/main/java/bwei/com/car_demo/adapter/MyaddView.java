package bwei.com.car_demo.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import bwei.com.car_demo.R;

public class MyaddView extends LinearLayout {
    @BindView(R.id.add_jia)
    TextView addJia;
    @BindView(R.id.add_num)
    TextView addNum;
    @BindView(R.id.add_jian)
    TextView addJian;
    private int number=1;
    public MyaddView(Context context) {
        this(context, null);
    }

    public MyaddView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.addview_layout, this);
        ButterKnife.bind(view);
    }

    @OnClick({R.id.add_jia, R.id.add_jian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_jia:
                ++number;
                addNum.setText(number+"");
                onNumBerChangListener.onNumBerChange(number);
                break;
            case R.id.add_jian:
                if (number>1){
                    --number;
                    addNum.setText(number+"");
                    onNumBerChangListener.onNumBerChange(number);
                }else{
                    Toast.makeText(getContext(),"不能小于1",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        addNum.setText(number+"");
    }
    OnNumBerChangListener onNumBerChangListener;
    public interface OnNumBerChangListener{
        void onNumBerChange(int num);
    }
    public void setOnNumBerChangListener(OnNumBerChangListener onNumBerChangListener){
        this.onNumBerChangListener=onNumBerChangListener;
    }

}
