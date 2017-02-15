package com.feicuiedu.eshop.base.widgets;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feicuiedu.eshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gqq on 2017/2/15.
 */

// 自定义搜索框
public class SimpleSearchView extends LinearLayout implements TextView.OnEditorActionListener, TextWatcher {


    @BindView(R.id.button_search)
    ImageButton mBtnSearch;
    @BindView(R.id.edit_query)
    EditText mEtQuery;
    @BindView(R.id.button_clear)
    ImageButton mBtnClear;

    private OnSearchListener mOnSearchListener;

    public SimpleSearchView(Context context) {
        super(context);
        init(context);
    }

    public SimpleSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SimpleSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_simple_search_view, this);
        ButterKnife.bind(this);

        mEtQuery.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEtQuery.setOnEditorActionListener(this);
        mEtQuery.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        mEtQuery.addTextChangedListener(this);
    }

    @OnClick({R.id.button_search, R.id.button_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_search:
                // 搜索：执行搜索的操作
                search();
                break;
            case R.id.button_clear:
                mEtQuery.setText(null);
                break;
        }
    }

    public void setOnSearchListener(OnSearchListener listener){
        mOnSearchListener = listener;
    }

    // 搜索
    private void search() {
        String query = mEtQuery.getText().toString();
        // 具体根据文本做什么搜索的工作，由使用者来决定，所以可以对外提供一个接口，设置搜索的工作
        if (mOnSearchListener!=null){
            mOnSearchListener.search(query);
        }
        // 关闭软键盘
        closeKeyBoard();
    }

    private void closeKeyBoard() {
        mEtQuery.clearFocus();

        // 隐藏软键盘
        InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(mEtQuery.getWindowToken(),0);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId==EditorInfo.IME_ACTION_SEARCH){
            search();
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        // 主要是为了处理清除按钮的展示和隐藏
        String query = mEtQuery.getText().toString();
        int visibility = TextUtils.isEmpty(query) ? View.INVISIBLE : View.VISIBLE;
        mBtnClear.setVisibility(visibility);
    }

    public interface OnSearchListener{
        void search(String text);
    }
}
