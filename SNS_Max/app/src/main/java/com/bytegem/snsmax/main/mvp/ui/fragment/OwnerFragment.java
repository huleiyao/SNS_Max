package com.bytegem.snsmax.main.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bytegem.snsmax.main.mvp.ui.activity.OwnerHomeActivity;
import com.bytegem.snsmax.main.mvp.ui.activity.OwnerQRCodeActivity;
import com.bytegem.snsmax.main.mvp.ui.activity.SettingsActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerOwnerComponent;
import com.bytegem.snsmax.main.mvp.contract.OwnerContract;
import com.bytegem.snsmax.main.mvp.presenter.OwnerPresenter;

import com.bytegem.snsmax.R;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/10/2019 16:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class OwnerFragment extends BaseFragment<OwnerPresenter> implements OwnerContract.View {
    private static OwnerFragment instance;

    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.user_content)
    TextView user_content;
    @BindView(R.id.user_zan_count)
    TextView user_zan_count;
    @BindView(R.id.user_follow_count)
    TextView user_follow_count;
    @BindView(R.id.user_fans_count)
    TextView user_fans_count;

    @BindView(R.id.user_cover)
    ImageView user_cover;


    @OnClick({R.id.setting, R.id.owner_qrcode, R.id.scan, R.id.user_cover
            , R.id.owner_group, R.id.owner_favorites, R.id.community_honor, R.id.owner_treasure
            , R.id.owner_drafts, R.id.owner_share, R.id.help_or_feedback, R.id.owner})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting:
                launchActivity(new Intent(getContext(), SettingsActivity.class));
                break;
            case R.id.owner_qrcode:
                launchActivity(new Intent(getContext(), OwnerQRCodeActivity.class));
                break;
            case R.id.scan:
                showMessage("我的迹记");
                break;
            case R.id.user_cover:
                launchActivity(new Intent(getContext(), OwnerHomeActivity.class).putExtra(OwnerHomeActivity.ISME, true));
                break;
            case R.id.owner:
                showMessage("我的圈子");
                break;
            case R.id.owner_group:
                showMessage("去扫码");
                break;
            case R.id.owner_favorites:
                showMessage("我的收藏");
                break;
            case R.id.community_honor:
                showMessage("社区荣誉");
                break;
            case R.id.owner_treasure:
                showMessage("财富积分");
                break;
            case R.id.owner_drafts:
                showMessage("草稿箱");
                break;
            case R.id.owner_share:
                showMessage("邀请分享");
                break;
            case R.id.help_or_feedback:
                showMessage("帮助与反馈");
                break;

        }
    }

    public static OwnerFragment newInstance() {
        if (instance == null)
            instance = new OwnerFragment();
        return instance;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerOwnerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_owner, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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