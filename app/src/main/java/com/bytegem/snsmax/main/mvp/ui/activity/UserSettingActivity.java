package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.bytegem.snsmax.R;
import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
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
import com.bytegem.snsmax.main.mvp.ui.dialog.UpdateDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.ui.ImageCropActivity;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.bytegem.snsmax.main.app.MApplication.getContext;
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
    @BindView(R.id.user_location)
    TextView txtLocation;
    @BindView(R.id.user_nickName)
    TextView txtNickName;
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

    private Context context;
    private Intent intent = new Intent();
    //  省
    private List<AreaBean> options1Items = new ArrayList<>();
    //  市
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    //  区
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private DATAUser userInfo;

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
        setUserDetails();
        initCommitBottomSheetDialog();
        txtTitle.setText("编辑个人资料");
        btnMore.setText("保存");
        btnMore.setTextColor(getResources().getColor(R.color.color_5e6ce7));
        setListener();
    }

    private void setListener() {
        btnMore.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnUserCover.setOnClickListener(this);
        txtNickName.setOnClickListener(this);
        txtSex.setOnClickListener(this);
        txtLocation.setOnClickListener(this);
        txtSchool.setOnClickListener(this);
        txtDesc.setOnClickListener(this);
        txtIndustry.setOnClickListener(this);
    }

    private void saveUserDetails() {
        userInfo.getData().setName(txtNickName.getText().toString());
        if (txtSex.getText().toString().equals("男")) {
            userInfo.getData().setSex("man");
        } else if (txtSex.getText().toString().equals("女")) {
            userInfo.getData().setSex("woman");
        } else {
            userInfo.getData().setSex("unknown");
        }
        userInfo.getData().setLocation(txtLocation.getText().toString());
        userInfo.getData().setSchool(txtSchool.getText().toString());
        userInfo.getData().setBio(txtDesc.getText().toString());
        userInfo.getData().setTrade(txtIndustry.getText().toString());
    }

    private void setUserDetails() {
        userInfo = UserInfoUtils.getUserInfo(mPresenter.getGson());
        if (userInfo != null && userInfo.getData() != null) {
            txtNickName.setText(mPresenter.isNull(userInfo.getData().getName()) ? "--" : userInfo.getData().getName());
            String sex = mPresenter.isNull(userInfo.getData().getSex()) ? "--" : userInfo.getData().getSex();
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
            case R.id.user_setting_more:
                Toast.makeText(getApplication(), "保存", Toast.LENGTH_SHORT).show();
                //把修改的值丢到Model 里面转成JSON 丢服务器保存
                saveUserDetails();
                String userJson = UserInfoUtils.getJsonUserData(userInfo.getData(), new Gson());
                HttpMvcHelper
                        .obtainRetrofitService(UserService.class)
                        .updateUserData(MApplication.getTokenOrType(), RequestBody.create(
                                MediaType.parse("application/json; charset=utf-8"),
                                userJson))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ErrorHandleSubscriber<NetDefaultBean>(mErrorHandler) {
                            @Override
                            public void onNext(NetDefaultBean netDefaultBean) {
                            }
                        });
                UserInfoUtils.saveUserInfo(userInfo, new Gson());
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
            case R.id.user_location:
                parseData();
                break;
            case R.id.user_nickName:
                UpdateDialog dialogNickName = new UpdateDialog(context, "昵称修改", new UpdateDialog.OnUpdateDialogListener() {
                    @Override
                    public void back(String name) {
                        txtNickName.setText(name);
                    }
                });
                dialogNickName.show();
                break;
            case R.id.user_sex:
                //123
                break;
            case R.id.user_school:
                UpdateDialog dialogSchool = new UpdateDialog(context, "学院修改", new UpdateDialog.OnUpdateDialogListener() {
                    @Override
                    public void back(String name) {
                        txtSchool.setText(name);
                    }
                });
                dialogSchool.show();
                break;
            case R.id.user_desc:
                UpdateDialog dialogDesc = new UpdateDialog(context, "简介修改", new UpdateDialog.OnUpdateDialogListener() {
                    @Override
                    public void back(String name) {
                        txtDesc.setText(name);
                    }
                });
                dialogDesc.show();
                break;
            case R.id.user_industry:
                UpdateDialog dialogIndustry = new UpdateDialog(context, "行业修改", new UpdateDialog.OnUpdateDialogListener() {
                    @Override
                    public void back(String name) {
                        txtIndustry.setText(name);
                    }
                });
                dialogIndustry.show();
                break;
            default:
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.REQUEST_CODE_TAKE) {
            Intent intent = new Intent(getContext(), ImageCropActivity.class);
            startActivityForResult(intent, ImagePicker.REQUEST_CODE_CROP);  //单选需要裁剪，进入裁剪界面

        }
    }


    private void initCommitBottomSheetDialog() {
        changeUserCoverBottomSheetDialog = new BottomSheetDialog(context);
        changeUserCoverBottomSheetDialog.setContentView(R.layout.dialog_change_user_cover);
        changeUserCoverBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(getResources().getColor(R.color.albumTransparent));
        changeUserCoverBottomSheetDialog.findViewById(R.id.tv_take_photo).setOnClickListener(this);
        changeUserCoverBottomSheetDialog.findViewById(R.id.tv_take_pic).setOnClickListener(this);
        changeUserCoverBottomSheetDialog.findViewById(R.id.tv_cancel).setOnClickListener(this);
    }


    private void parseData2() {
        List<HashMap<String, Object>> aList = new ArrayList<>();
        List<HashMap<String, Object>> cList = new ArrayList<>();
        List<HashMap<String, Object>> pList = new ArrayList<>();
        String jsonStr = new GetJsonUtil().getJson(this, "ChinaArea.json");//获取assets目录下的json文件数据
        Map<String, Object> map = new GetJsonUtil().getMap(jsonStr);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if (entry.getKey().substring(2).equals("0000")) {
                //省 名称
                HashMap<String, Object> pMap = new LinkedHashMap<>();
                pMap.put(entry.getKey(), entry.getValue());
                pList.add(pMap);
                continue;
            }
            if (entry.getKey().substring(4).equals("00")) {
                HashMap<String, Object> cMap = new LinkedHashMap<>();
                cMap.put(entry.getKey(), entry.getValue());
                cList.add(cMap);
                continue;
            }
            HashMap<String, Object> aMap = new LinkedHashMap<>();
            aMap.put(entry.getKey(), entry.getValue());
            aList.add(aMap);
        }
        String a = "循环结束";
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
