package com.feicuiedu.eshop.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feicuiedu.eshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gqq on 2017/2/9.
 */

public class TestFragment extends Fragment {

    private static final String FRAGMENT_TEXT = "fragment_text";
    @BindView(R.id.text)
    TextView mText;


    // 对外提供一个创建方法：传递数据，根据切换的Fragment展示不同模块
    public static TestFragment getInstance(String text) {
        TestFragment fragment = new TestFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT_TEXT, text);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        ButterKnife.bind(this, view);

        String text = getArguments().getString(FRAGMENT_TEXT);
        mText.setText(text);
        return view;
    }

    public String getArgumentText() {
        return getArguments().getString(FRAGMENT_TEXT);
    }
}
