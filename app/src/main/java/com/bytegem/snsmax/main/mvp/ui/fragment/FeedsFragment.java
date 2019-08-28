package com.bytegem.snsmax.main.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.feed.FeedBean;
import com.bytegem.snsmax.main.mvp.contract.FeedsContract;
import com.bytegem.snsmax.main.mvp.ui.activity.AddressSelectActivity;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedsAdapter;
import com.bytegem.snsmax.main.mvp.ui.dialog.CommitFeedCommentBottomSheetDialog;
import com.bytegem.snsmax.main.mvp.ui.view.HomeBannerView;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerFeedsComponent;
import com.bytegem.snsmax.main.mvp.presenter.FeedsPresenter;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/05/2019 11:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class FeedsFragment extends BaseFragment<FeedsPresenter> implements FeedsContract.View, View.OnClickListener {
    int type;
    @Inject
    FeedsAdapter adapter;
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.address)
    FrameLayout address;
    @BindView(R.id.city)
    TextView city;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetDialog commitBottomSheetDialog;
    FeedBean feedBean;
    int groupId;

    @OnClick({R.id.address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.address:
                launchActivity(new Intent(getContext(), AddressSelectActivity.class));
                break;
            case R.id.to_commit:
                bottomSheetDialog.dismiss();
                commitBottomSheetDialog.show();
                break;
            case R.id.to_report:
                showMessage("举报");
                bottomSheetDialog.dismiss();
                break;
            case R.id.cancel:
                bottomSheetDialog.dismiss();
                break;
        }
    }

    public static FeedsFragment newInstance(int type) {
        FeedsFragment fragment = new FeedsFragment();
        fragment.setType(type);
        return fragment;
    }

    public static FeedsFragment newInstance(int type, int groupId) {
        FeedsFragment fragment = new FeedsFragment();
        fragment.setType(type);
        fragment.setGroupId(groupId);
        return fragment;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFeedsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_community_post_list, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initList();
        initBottomSheetDialog();
        initCommitBottomSheetDialog();
    }

    private void initList() {
        if (adapter == null) adapter = new FeedsAdapter();
        adapter.setListener(mPresenter, mPresenter, mPresenter);
        if (type == 0) {
            address.setVisibility(View.GONE);
            HomeBannerView homeBannerView = new HomeBannerView(getContext());
            ArrayList<String> list = new ArrayList<>();
            list.add("https://img.51miz.com/Element/00/59/09/60/73f61abe_E590960_d62b7177.jpg!/quality/90/unsharp/true/compress/true/format/jpg/fh/630");
            list.add("https://img.51miz.com/Element/00/59/33/95/b432a37c_E593395_c31bd364.jpg!/quality/90/unsharp/true/compress/true/format/jpg/fh/630");
            list.add("https://img.51miz.com/Element/00/59/33/30/632a7837_E593330_408dd1bd.jpg!/quality/90/unsharp/true/compress/true/format/jpg/fh/630");
            list.add("https://img.51miz.com/Element/00/59/51/04/bf708b6a_E595104_b0011444.jpg!/quality/90/unsharp/true/compress/true/format/jpg/fh/630");
            homeBannerView.showBanner(list);
            adapter.setHeaderView(homeBannerView.getView());
        } else if (type == 1)
            address.setVisibility(View.VISIBLE);
        else if (type == 3) {//圈子动态
            address.setVisibility(View.GONE);
            mPresenter.setGroupId(groupId);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(mPresenter);
        adapter.setOnItemChildClickListener(mPresenter);
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                if (type == 3)
                    mPresenter.getGroupFeedList(false);
                else
                    mPresenter.getList(false);
            }

            @Override
            public void onLoadmore() {
                if (type == 3)
                    mPresenter.getGroupFeedList(true);
                else
                    mPresenter.getList(true);
            }
        });
        mPresenter.setType(type, false);
    }

    private void initCommitBottomSheetDialog() {
        commitBottomSheetDialog = new CommitFeedCommentBottomSheetDialog(getContext()
                , this
                , new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {//发送按键action
                    if (textView != null) {
                        String content = textView.getText().toString();
                        if (content.isEmpty()) {
                            textView.setError("请输入评论内容");
                            return true;
                        }
                        mPresenter.commit(feedBean.getId(), content);
                        textView.setText("");
                        commitBottomSheetDialog.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void initBottomSheetDialog() {
        bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.dialog_community_commit);
        bottomSheetDialog.getDelegate().findViewById(R.id.design_bottom_sheet)
                .setBackgroundColor(getResources().getColor(R.color.albumTransparent));
        bottomSheetDialog.findViewById(R.id.to_commit).setOnClickListener(this);
        bottomSheetDialog.findViewById(R.id.to_report).setOnClickListener(this);
        bottomSheetDialog.findViewById(R.id.cancel).setOnClickListener(this);
    }

    public void changeCity() {
        if (MApplication.location != null)
            city.setText(MApplication.location.getCity());
    }

    public ArrayList<String> getList(int number) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < number; i++)
            arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560157380928&di=a5fcba2094b5d96612a2a77b4873115e&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F9b671d17b52639d35e7c76c23f79fbabebe769d43140-xjB5Tw_fw658");
        return arrayList;
    }

    public void onFinishFreshAndLoad() {
        springView.onFinishFreshAndLoad();
    }

    @Override
    public void toComment(FeedBean feedBean) {
        this.feedBean = feedBean;
        commitBottomSheetDialog.show();
    }

    @Override
    public void showMore(FeedBean feedBean) {
        this.feedBean = feedBean;
        bottomSheetDialog.show();
    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }
}
