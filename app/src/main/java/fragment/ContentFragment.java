package fragment;

import android.view.View;

import com.example.acer.zhbj.R;

/**
 * Created by acer on 2017/7/21.
 */

public class ContentFragment extends BaseFragment {
    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);
        return view;
    }

    @Override
    public void initData() {

    }
}
