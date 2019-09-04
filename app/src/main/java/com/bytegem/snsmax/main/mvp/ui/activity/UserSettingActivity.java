package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.bytegem.snsmax.R;
import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.user.DATAUser;
import com.bytegem.snsmax.main.app.config.UserService;
import com.bytegem.snsmax.main.app.mvc.bean.AreaBean;
import com.bytegem.snsmax.main.app.mvc.utils.GetJsonUtil;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.HttpMvcHelper;
import com.bytegem.snsmax.main.app.utils.UserInfoUtils;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.di.component.DaggerUserSettingComponent;
import com.bytegem.snsmax.main.mvp.contract.UserSettingContract;
import com.bytegem.snsmax.main.mvp.presenter.UserSettingPresenter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/20/2019 10:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class UserSettingActivity extends BaseActivity<UserSettingPresenter> implements UserSettingContract.View, View.OnClickListener {
    @BindView(R.id.user_setting_more)
    TextView btnMore;
    @BindView(R.id.user_setting_title)
    TextView txtTitle;
    @BindView(R.id.user_setting_back)
    RelativeLayout btnBack;
    @BindView(R.id.change_user_cover)
    FrameLayout btnUserCover;
    @BindView(R.id.user_setting_cover)
    ImageView imCover;
    @BindView(R.id.user_setting_sex)
    LinearLayout btnSex;
    @BindView(R.id.user_setting_location)
    LinearLayout btnLocation;
    @BindView(R.id.user_setting_school)
    LinearLayout btnSchool;
    @BindView(R.id.user_setting_desc)
    LinearLayout btnDesc;
    @BindView(R.id.user_setting_industry)
    LinearLayout btnIndustry;
    @BindView(R.id.user_nickName)
    EditText edtNickName;
    @BindView(R.id.user_location)
    TextView txtLocation;
    @BindView(R.id.user_sex)
    TextView txtSex;
    @BindView(R.id.user_school)
    TextView txtSchool;
    @BindView(R.id.user_desc)
    TextView txtDesc;
    @BindView(R.id.user_industry)
    TextView txtIndustry;
    @Inject
    RxErrorHandler mErrorHandler;
    BottomSheetDialog changeUserCoverBottomSheetDialog;
    BottomSheetDialog changeUserSexBottomSheetDialog;
    private String strNickName = "";
    private Context context;
    private Intent intent = new Intent();
    //  省
    private List<AreaBean> options1Items = new ArrayList<>();
    //  市
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    //  区
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private DATAUser userInfo;
    private String strType = null;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserSettingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        context = this;
        return R.layout.activity_user_setting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        userInfo = UserInfoUtils.getUserInfo(HttpMvcHelper.getGson());
        setUserDetails();
        initCommitBottomSheetCoverDialog();
        initCommitBottomSheetUserDialog();
        txtTitle.setText("编辑个人资料");
        btnMore.setText("保存");
        btnMore.setTextColor(getResources().getColor(R.color.color_5e6ce7));
        setListener();
        edtNickName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    edtNickName.clearFocus();
                    strNickName = edtNickName.getText().toString().trim();
                    HttpMvcHelper.obtainRetrofitService(UserService.class)
                            .updateUserData(MApplication.getTokenOrType(), RequestBody.create(
                                    MediaType.parse("application/json; charset=utf-8"),
                                    M.getMapString("name", strNickName)))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(suc -> {
                            }, err -> {
                                if (err.toString().contains("Null is not a valid element")) {
                                    userInfo.getData().setName(edtNickName.getText().toString());
                                }
                            });
                    return true;
                }
                return false;
            }
        });
    }

    private void setListener() {
        btnMore.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnUserCover.setOnClickListener(this);
        btnSex.setOnClickListener(this);
        btnLocation.setOnClickListener(this);
        btnSchool.setOnClickListener(this);
        btnDesc.setOnClickListener(this);
        btnIndustry.setOnClickListener(this);
    }


    private void setUserDetails() {
        if (userInfo != null && userInfo.getData() != null) {
            edtNickName.setText(mPresenter.isNull(userInfo.getData().getName()) ? "--" : userInfo.getData().getName());
            String sex = mPresenter.isNull(userInfo.getData().getSex()) || userInfo.getData().getSex().equals("unknown") ? "保密" : userInfo.getData().getSex();
            txtSex.setText("保密".equals(sex) ? sex : "man".equals(sex) ? "男" : "女");
            txtLocation.setText(mPresenter.isNull(userInfo.getData().getLocation()) ? "--" : userInfo.getData().getLocation());
            txtSchool.setText(mPresenter.isNull(userInfo.getData().getSchool()) ? "--" : userInfo.getData().getSchool());
            txtDesc.setText(mPresenter.isNull(userInfo.getData().getBio()) ? "--" : userInfo.getData().getBio());
            txtIndustry.setText(mPresenter.isNull(userInfo.getData().getTrade()) ? "--" : userInfo.getData().getTrade());
        }
        if (userInfo.getData().getAvatar() == null || userInfo.getData().getAvatar().isEmpty()) {
            GlideLoaderUtil.LoadCircleImage(this, R.drawable.ic_deskicon, imCover);
        } else {
            GlideLoaderUtil.LoadCircleImage(this, Utils.checkUrl(userInfo.getData().getAvatar()), imCover);
        }
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
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_setting_back:
                finish();
                break;
            case R.id.change_user_cover:
                changeUserCoverBottomSheetDialog.show();
                break;
            case R.id.tv_take_photo:
                changeUserCoverBottomSheetDialog.dismiss();
                ImagePicker.getInstance().setCrop(true);
                ImagePicker.getInstance().takePicture(this, ImagePicker.REQUEST_CODE_TAKE);
                break;
            case R.id.tv_take_pic:
                changeUserCoverBottomSheetDialog.dismiss();
                intent = new Intent(context, ImageGridActivity.class);
                startActivityForResult(intent, 801);
                break;
            case R.id.tv_cancel:
                changeUserCoverBottomSheetDialog.dismiss();
                break;
            case R.id.user_setting_location:
                parseData();
                break;
            case R.id.user_setting_sex:
                changeUserSexBottomSheetDialog.show();
                break;
            case R.id.tv_take_man:
                changeUserSexBottomSheetDialog.dismiss();
                txtSex.setText("男");
                userInfo.getData().setSex("man");
                break;
            case R.id.tv_take_woman:
                changeUserSexBottomSheetDialog.dismiss();
                txtSex.setText("女");
                userInfo.getData().setSex("woman");
                break;
            case R.id.tv_secret:
                changeUserSexBottomSheetDialog.dismiss();
                txtSex.setText("保密");
                userInfo.getData().setSex("unknown");
                break;
            case R.id.user_setting_school:
                strType = "学院编辑";
                intent.setClass(context, UserUpdateActivity.class);
                intent.putExtra("strType", strType);
                startActivity(intent);
                break;
            case R.id.user_setting_desc:
                strType = "简介编辑";
                intent.setClass(context, UserUpdateActivity.class);
                intent.putExtra("strType", strType);
                startActivity(intent);
                break;
            case R.id.user_setting_industry:
                strType = "行业编辑";
                intent.setClass(context, UserUpdateActivity.class);
                intent.putExtra("strType", strType);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUserDetails();
    }

    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == ImagePicker.REQUEST_CODE_TAKE) {
//            Intent intent = new Intent(getContext(), ImageCropActivity.class);
//            startActivityForResult(intent, ImagePicker.REQUEST_CODE_CROP);  //单选需要裁剪，进入裁剪界面
//        }
//    }
    private void initCommitBottomSheetCoverDialog() {
        changeUserCoverBottomSheetDialog = new BottomSheetDialog(context);
        changeUserCoverBottomSheetDialog.setContentView(R.layout.dialog_change_user_cover);
        changeUserCoverBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(getResources().getColor(R.color.albumTransparent));
        changeUserCoverBottomSheetDialog.findViewById(R.id.tv_take_photo).setOnClickListener(this);
        changeUserCoverBottomSheetDialog.findViewById(R.id.tv_take_pic).setOnClickListener(this);
        changeUserCoverBottomSheetDialog.findViewById(R.id.tv_cancel).setOnClickListener(this);

    }

    private void initCommitBottomSheetUserDialog() {
        changeUserSexBottomSheetDialog = new BottomSheetDialog(context);
        changeUserSexBottomSheetDialog.setContentView(R.layout.dialog_change_user_sex);
        changeUserSexBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(getResources().getColor(R.color.albumTransparent));
        changeUserSexBottomSheetDialog.findViewById(R.id.tv_take_man).setOnClickListener(this);
        changeUserSexBottomSheetDialog.findViewById(R.id.tv_take_woman).setOnClickListener(this);
        changeUserSexBottomSheetDialog.findViewById(R.id.tv_secret).setOnClickListener(this);
    }

    /**
     * 解析数据并组装成要的list
     */
    private void parseData() {
        String jsonStr = new GetJsonUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<List<AreaBean>>() {
        }.getType();
        List<AreaBean> shengList = gson.fromJson(jsonStr, type);
        //把解析后的数据组装成想要的list
        options1Items = shengList;
        //遍历省
        for (int i = 0; i < shengList.size(); i++) {
            //存放城市
            ArrayList<String> cityList = new ArrayList<>();
            //存放区
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();
            //遍历市
            for (int c = 0; c < shengList.get(i).city.size(); c++) {
                //拿到城市名称
                String cityName = shengList.get(i).city.get(c).name;
                cityList.add(cityName);
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表
                if (shengList.get(i).city.get(c).area == null || shengList.get(i).city.get(c).area.size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(shengList.get(i).city.get(c).area);
                }
                province_AreaList.add(city_AreaList);
            }
            /**
             * 添加城市数据
             */
            options2Items.add(cityList);
            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
        showPickerView();
    }

    private void showPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            String tx = options1Items.get(options1).name + " " +
                    options2Items.get(options1).get(options2) + " " +
                    options3Items.get(options1).get(options2).get(options3);
            txtLocation.setText(tx);
            userInfo.getData().setLocation(txtLocation.getText().toString());
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(ConvertUtils.dp2px(20))
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }
}
