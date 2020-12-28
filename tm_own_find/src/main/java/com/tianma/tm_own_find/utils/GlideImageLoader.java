package com.tianma.tm_own_find.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tenma.ventures.bean.TMBaseConfig;
import com.tenma.ventures.config.TMServerConfig;
import com.tianma.tm_own_find.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by wcc on 2018/9/6.
 */


public class GlideImageLoader extends ImageLoader {
    Context context1;

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        context1 = context;
        /**
         注意：
         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
         切记不要胡乱强转！
         */
        //Glide 加载图片简单用法
        //   Glide.with(context).load(path).into(imageView);

        //Picasso 加载图片简单用法
        //  Picasso.with(context).load((String) path).into(imageView);
        RequestOptions requestOptions = new RequestOptions()
//                .placeholder(R.drawable.icon_banner_tianqi)
                .error(R.drawable.icon_banner_tianqi).transform(new GlideRoundTransform(context, 10));
        if (path instanceof Integer) {
            Glide.with(context)
                    .load((int) path)
                    .apply(requestOptions)
                    .into(imageView);
        } else {
            Glide.with(context)
                    .load(getRealUrl((String) path))
                    .apply(requestOptions)
                    .into(imageView);
        }




       /* Glide.with(context)
                .load((String) path)
                .placeholder(R.drawable.list_holder)
                .error(R.drawable.list_holder)
                .transform(new CenterCrop(context, new GlideRoundTransform(context, 2))
                .into(pic);*/

        //用fresco加载图片简单用法，记得要写下面的createImageView方法
        /*Uri uri = Uri.parse((String) path);
        imageView.setImageURI(uri);*/
    }

    private String getRealUrl(String path) {
        if (path.startsWith("http")) {
            return path;
        } else {
            return TMServerConfig.BASE_URL + path;
        }
    }

    //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
   /* @Override
    public ImageView createImageView(Context context) {
        //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
        return simpleDraweeView;
    }*/

    /*Transformation transformation = new RoundedTransformationBuilder()
            .borderColor(Color.TRANSPARENT)
            .borderWidthDp(20)
            .oval(false)
            .cornerRadiusDp(10)
            .build();*/
}