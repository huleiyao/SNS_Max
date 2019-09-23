package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.mvp.ui.adapter.ImagePickerAdapter;
import com.bytegem.snsmax.main.mvp.ui.base.BaseActivity;
import com.lzy.imagepicker.GlideImageLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;

import butterknife.BindView;

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
                ToastUtils.showShort("反馈意见提交");
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

    private void uploadImages(ArrayList<ImageItem> imageItems) {

    }

    private void uploadDetails(){

    }

}

