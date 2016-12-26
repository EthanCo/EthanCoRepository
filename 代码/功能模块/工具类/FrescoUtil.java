package cn.nbhope.smarthome.utils;

import android.content.Context;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import cn.nbhope.smarthome.Constants;

/**
 * Created by qiukeling on 2016/6/15.
 */
public class FrescoUtil {
    private static final int MAX_MEMORY_CACHE_SIZE = (int) Runtime.getRuntime().maxMemory() / 4;

    public static void initFresco(Context context) {
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder()
                .setBaseDirectoryPath(Constants.getOwnCacheDirectory(context, Constants.DEFAULT_IMG_CACHE_PATH))
                .setBaseDirectoryName("v1")
                .setMaxCacheSize(100 * ByteConstants.MB)
                .setMaxCacheSizeOnLowDiskSpace(20 * ByteConstants.MB)
                .setMaxCacheSizeOnVeryLowDiskSpace(10 * ByteConstants.MB)
                .setVersion(1)
                .build();

        MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(MAX_MEMORY_CACHE_SIZE,
                Integer.MAX_VALUE, MAX_MEMORY_CACHE_SIZE, Integer.MAX_VALUE, Integer.MAX_VALUE);

        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return bitmapCacheParams;
            }
        };

        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(context)
                .setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();

        Fresco.initialize(context, imagePipelineConfig);
    }
}
