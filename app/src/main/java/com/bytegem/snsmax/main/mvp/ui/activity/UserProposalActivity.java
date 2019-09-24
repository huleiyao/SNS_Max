package com.bytegem.snsmax.main.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bytegem.snsmax.R;
import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.FileSignBean;
import com.bytegem.snsmax.main.app.config.UpdataImageService;
import com.bytegem.snsmax.main.app.config.UserService;
import com.bytegem.snsmax.main.app.utils.HttpMvcHelper;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.mvp.model.HelperModel;
import com.bytegem.snsmax.main.mvp.ui.adapter.HelperAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.ImagePickerAdapter;
import com.bytegem.snsmax.main.mvp.ui.base.BaseActivity;
import com.bytegem.snsmax.main.mvp.ui.dialog.loadingDialog.ShapeLoadingDialog;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lzy.imagepicker.GlideImageLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.io.EOFException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserProposalActivity extends BaseActivity implements View.OnClickListener, ImagePickerAdapter.OnRecyclerViewItemClickListener {

    @BindView(R.id.title_back)
    RelativeLayout btnBack;
    @BindView(R.id.title_title)
    TextView txtTitle;
    @BindView(R.id.edt_proposal_title)
    EditText edtProposalTitle;
    @BindView(R.id.edt_proposal_details)
    EditText edtProposalDetails;
    @BindView(R.id.txt_proposal_type)
    TextView txtProposalType;
    @BindView(R.id.rev_proposal_load_image)
    RecyclerView revLoadImage;
    @BindView(R.id.btn_proposal_submit)
    TextView submit;
    BottomSheetDialog changeUserCoverBottomSheetDialog;
    Context context;
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList;
    private int maxImgCount = 9;

    private ShapeLoadingDialog loadingDialog;
    private int finishCount = 0; //图片上传已完成数量

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_proposal;
    }

    @Override
    public void initView() {
        initImagePicker();
        initWidget();
    }

    @Override
    public void setListener() {
        btnBack.setOnClickListener(this);
        submit.setOnClickListener(this);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.tv_take_photo:
                changeUserCoverBottomSheetDialog.dismiss();
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent = new Intent(context, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            case R.id.tv_take_pic:
                changeUserCoverBottomSheetDialog.dismiss();
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent1 = new Intent(context, ImageGridActivity.class);
                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                break;
            case R.id.tv_cancel:
                changeUserCoverBottomSheetDialog.dismiss();
                break;
            case R.id.btn_proposal_submit:
                uploadImages(selImageList);
                break;
            default:
                break;
        }
    }

    private void initWidget() {
        txtTitle.setText("我要反馈");
        context = this;
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        revLoadImage.setLayoutManager(new GridLayoutManager(this, 4));
        revLoadImage.setHasFixedSize(true);
        revLoadImage.setAdapter(adapter);
        initCommitBottomSheetCoverDialog();
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);                      //显示拍照按钮
        imagePicker.setCrop(true);                            //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形 区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setMultiMode(true);                      //多选
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(1000);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(1000);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素

    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                changeUserCoverBottomSheetDialog.show();
                break;
            default:
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }

    }

    private void initCommitBottomSheetCoverDialog() {
        changeUserCoverBottomSheetDialog = new BottomSheetDialog(context);
        changeUserCoverBottomSheetDialog.setContentView(R.layout.dialog_change_user_cover);
        changeUserCoverBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(getResources().getColor(R.color.albumTransparent));
        changeUserCoverBottomSheetDialog.findViewById(R.id.tv_take_photo).setOnClickListener(this);
        changeUserCoverBottomSheetDialog.findViewById(R.id.tv_take_pic).setOnClickListener(this);
        changeUserCoverBottomSheetDialog.findViewById(R.id.tv_cancel).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
    }

    //提交数据
    @SuppressLint("CheckResult")
    private synchronized void submitData() {
        for (ImageItem imageItem : selImageList) {
            if (imageItem.sericeSaveUrl == null || "".equals(imageItem.sericeSaveUrl)) {
                if (finishCount >= selImageList.size() - 1) {
                    ToastUtils.showShort("图片上传失败,请重试!");
                    loadingDialog.dismiss();
                }
                return;
            }
        }
        if (finishCount < selImageList.size()) {
            return;
        }
        String[] imgs = new String[selImageList.size()];
        for (int i = 0; i < selImageList.size(); i++) {
            imgs[i] = selImageList.get(i).sericeSaveUrl;
        }
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                M.getMapString(
                        "title", edtProposalTitle.getText().toString(),
                        "body", edtProposalDetails.getText().toString(),
                        "images", imgs
                )
        );
        HttpMvcHelper
                .obtainRetrofitService(UserService.class)
                .feedback(HttpMvcHelper.getTokenOrType(), requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(helperModel -> {
                    loadingDialog.dismiss();
                    ToastUtils.showShort("反馈已收到,感谢您的反馈");
                }, err -> {
                    if(err.toString().contains("Null is not a valid element")){
                        ToastUtils.showShort("反馈已收到,感谢您的反馈");
                        finish();
                    }else{
                        ToastUtils.showShort("反馈提交失败");
                    }
                    loadingDialog.dismiss();
                });

    }

    //上传文件
    @SuppressLint("CheckResult")
    private void uploadImages(ArrayList<ImageItem> imageItems) {
        if (edtProposalTitle.getText().toString().isEmpty() || edtProposalTitle.getText().toString().length() < 10) {
            ToastUtils.showShort("标题不能为空并且不小于10个字符");
            return;
        }
        if (edtProposalDetails.getText().toString().isEmpty()) {
            ToastUtils.showShort("请输入反馈内容");
            return;
        }
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        finishCount = 0;
        loadingDialog = new ShapeLoadingDialog(this);
        loadingDialog.show();
        if (imageItems.size() > 0) {
            for (int i = 0; i < imageItems.size(); i++) {
                if(imageItems.get(i).sericeSaveUrl == null || "".equals(imageItems.get(i).sericeSaveUrl)){
                    getSign(imageItems.get(i));
                }else{
                    finishCount++;
                    submitData();
                }
            }
        } else {
            submitData();
        }
    }
    //上传临时资源换取服务器地址
    @SuppressLint("CheckResult")
    private void getSign(ImageItem imageItem) {
        String minType = getMediaMessageMimeType(imageItem.path);
        File tempCover = M.getTempFile(this, minType);
        if (!tempCover.exists()) {
            throw new IllegalArgumentException("文件[" + tempCover.getAbsolutePath() + "]不存在,请检查");
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), tempCover);
        MultipartBody.Part part = MultipartBody.Part.createFormData("factor", tempCover.getName(), requestBody);
        //表示成功
        final String[] path = new String[1];
        HttpMvcHelper
                .obtainRetrofitService(UpdataImageService.class)
                .getImageSign(
                        MApplication.getTokenOrType()
                        , MultipartBody.Part.createFormData("type", "image")
                        , part
                        , MultipartBody.Part.createFormData("length", "" + imageItem.size)
                        , MultipartBody.Part.createFormData("md5", M.getFileMD5(new File(imageItem.path)))
                )
                .flatMap(fileSignBean -> {
                    path[0] = fileSignBean.getPath();
                    if (path[0].indexOf("/") == 0)
                        path[0] = path[0].substring(1);
                    return updataCover(fileSignBean, imageItem);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mBaseBean -> {
                    finishCount++;
                    submitData();
                }, err -> {
                    finishCount++;
                    if (err instanceof EOFException) {
                        imageItem.sericeSaveUrl = path[0];
                    }
                    submitData();
                });
    }

    //上传实际的文件
    private Observable<MBaseBean> updataCover(FileSignBean fileSignBean, ImageItem imageItem) {
        String path = fileSignBean.getPath();
        if (path.indexOf("/") == 0)
            path = path.substring(1);
        imageItem.sericeSaveUrl = path;
        return HttpMvcHelper
                .obtainRetrofitService(UpdataImageService.class)
                .updataImage(
                        fileSignBean.getHeaders().getAuthorization()
                        , fileSignBean.getHeaders().getHost()
                        , fileSignBean.getHeaders().getMd5()
                        , fileSignBean.getHeaders().getCos()
                        , imageItem.mimeType
                        , RequestBody.create(MediaType.parse("application/otcet-stream"), new File(imageItem.path))
                        , path
                );
    }

    /*
     * 获取媒体消息的文件类型
     * @return
     */
    private String getMediaMessageMimeType(String fileName) {
        String type = "";
        String ext = FileUtils.getFileExtension(fileName);
        switch (ext) {
            case "png":
                type = "image/png";
                break;
            case "jpeg":
                type = "image/jpeg";
                break;
            case "jpg":
                type = "image/jpg";
                break;
        }
        return type;
    }

}

