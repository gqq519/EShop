package com.feicuiedu.eshop.feature.home;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.feicuiedu.eshop.R;
import com.feicuiedu.eshop.base.BaseFragment;
import com.feicuiedu.eshop.base.widgets.BannerAdapter;
import com.feicuiedu.eshop.base.widgets.BannerLayout;
import com.feicuiedu.eshop.base.wrapper.PtrWrapper;
import com.feicuiedu.eshop.base.wrapper.ToolbarWrapper;
import com.feicuiedu.eshop.network.EShopClient;
import com.feicuiedu.eshop.network.entity.Banner;
import com.feicuiedu.eshop.network.entity.HomeBannerRsp;
import com.feicuiedu.eshop.network.entity.HomeCategoryRsp;
import com.feicuiedu.eshop.network.entity.SimpleGoods;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by gqq on 2017/2/13.
 */

// 首页的Fragment

public class HomeFragment extends BaseFragment {

    @BindView(R.id.standard_toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.standard_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.list_home_goods)
    ListView mListHomeGoods;

    private ImageView[] mIvPromotes = new ImageView[4];
    private TextView mTvPromoteGoods;
    private HomeGoodsAdapter mGoodsAdapter;
    private Handler mHandler;
    private BannerAdapter<Banner> mBannerAdapter;
    private PtrWrapper mPtrWrapper;

    @Override
    protected int getContentViewLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

        // Toolbar
        new ToolbarWrapper(this).setCustomTitle(R.string.home_title);

        // 刷新
        // 刷新获取数据
        mPtrWrapper = new PtrWrapper(this) {
            @Override
            public void onRefresh() {
                // 刷新获取数据
                getHomeGoodsData();
            }
        };
        mPtrWrapper.postRefreshDelayed(50);

        mHandler = new Handler();

        mGoodsAdapter = new HomeGoodsAdapter();
        mListHomeGoods.setAdapter(mGoodsAdapter);

        // 商品的ListView的头布局:Banner+图片
        View view = LayoutInflater.from(getContext()).inflate(R.layout.partial_home_header, mListHomeGoods, false);

        BannerLayout bannerLayout = ButterKnife.findById(view, R.id.layout_banner);
        // TODO: 2017/2/14  数据绑定,暂时是默认图
        mBannerAdapter = new BannerAdapter<Banner>() {
            @Override
            protected void bind(ViewHolder holder, final Banner data) {
                // TODO: 2017/2/14  数据绑定,暂时是默认图
                holder.mImageBannerItem.setImageResource(R.drawable.image_holder_banner);
            }
        };
        bannerLayout.setAdapter(mBannerAdapter);

//        展示Banner，模拟数据
//        List<Banner> list = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            Banner banner = new Banner();
//            banner.setDesc("测试1");
//            list.add(banner);
//        }
//        mBannerAdapter.reset(list);

        mIvPromotes[0] = ButterKnife.findById(view, R.id.image_promote_one);
        mIvPromotes[1] = ButterKnife.findById(view, R.id.image_promote_two);
        mIvPromotes[2] = ButterKnife.findById(view, R.id.image_promote_three);
        mIvPromotes[3] = ButterKnife.findById(view, R.id.image_promote_four);

        mTvPromoteGoods = ButterKnife.findById(view, R.id.text_promote_goods);

        mListHomeGoods.addHeaderView(view);
    }

    private void getHomeGoodsData() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                EShopClient client = EShopClient.getInstance();

                try {
                    final HomeCategoryRsp categoryRsp = client.getHomeCategoryRsp();
                    final HomeBannerRsp homeBannerRsp = client.getHomeBannerRsp();

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // 拿到结果更新UI
                            if (categoryRsp.getStatus().isSucceed()) {
                                mGoodsAdapter.reset(categoryRsp.getData());
                            } else {
                                Toast.makeText(getContext(), categoryRsp.getStatus().getErrorDesc(), Toast.LENGTH_SHORT).show();
                            }
                            if (homeBannerRsp.getStatus().isSucceed()) {
                                mBannerAdapter.reset(homeBannerRsp.getData().getBanners());
                                // 设置促销的商品
                                setPromoteGoods(homeBannerRsp.getData().getGoodsList());
                            }
                            mPtrWrapper.stopRefresh();
                        }
                    });

                } catch (final IOException e) {
                    mHandler.post(new Runnable() {
                        @Override public void run() {
                            // 显示错误提示
                            Toast.makeText(getContext(),
                                    e.getMessage(),
                                    Toast.LENGTH_SHORT)
                                    .show();
                            mPtrWrapper.stopRefresh();
                        }
                    });
                }
            }
        }).start();
    }

    private void setPromoteGoods(List<SimpleGoods> list) {
        mTvPromoteGoods.setVisibility(View.VISIBLE);

        for (int i = 0; i < mIvPromotes.length; i++) {
            mIvPromotes[i].setVisibility(View.VISIBLE);
            final SimpleGoods simpleGoods = list.get(i);

            // TODO: 2017/2/14 图片展示
            mIvPromotes[i].setImageResource(R.drawable.image_holder_goods);

            mIvPromotes[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), simpleGoods.getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
