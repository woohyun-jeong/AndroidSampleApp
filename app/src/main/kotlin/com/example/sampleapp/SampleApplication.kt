package com.example.sampleapp

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.LruCache
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.DataSource
import coil.disk.DiskCache
import coil.intercept.Interceptor
import coil.map.Mapper
import coil.memory.MemoryCache
import coil.request.ImageResult
import coil.request.Options
import coil.request.SuccessResult
import com.example.sampleapp.core.model.Shopping
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SampleApplication : Application(), ImageLoaderFactory{
    /**
     * 임시로 설정
     * https://coil-kt.github.io/coil/image_loaders/
     */
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            /*.memoryCache {
                MemoryCache.Builder(applicationContext)
                    .maxSizePercent(0.25)
                    .build()
            }*/
            /*.diskCache {
                DiskCache.Builder()
                    .directory(applicationContext.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }*/
            /*.components {
                add(CustomCacheInterceptor()
                add(ItemMapper())
            }*/
            .crossfade(true)
            .build()
    }

    /**
     * We could write a custom mapper to map it to its URL, which will be handled later in the pipeline:
     *
     * After registering it when building our ImageLoader (see above), we can safely load an Item:
     *
     * ex)
     * ------------------------------------------------------------
     * val request = ImageRequest.Builder(context)
     *     .data(item)
     *     .target(imageView)
     *     .build()
     *
     * imageLoader.enqueue(request)
     * ------------------------------------------------------------
     *
     * See Mapper for more information.
     */
    class ItemMapper : Mapper<Shopping, String> {
        override fun map(data: Shopping, options: Options) = data.imageUrl
    }

    /**
     * Interceptors allow you to observe, transform, short circuit, or retry requests to an ImageLoader's image engine.
     * For example, you can add a custom cache layer like so:
     *
     * Interceptors are an advanced feature that let you wrap an ImageLoader's image pipeline with custom logic.
     * Their design is heavily based on OkHttp's Interceptor interface.
     *
     * See Interceptor for more information.
     */
    class CustomCacheInterceptor(
        private val context: Context,
        private val cache: LruCache<String, Drawable>
    ) : Interceptor {
        override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
            val value = cache.get(chain.request.data.toString())
            if (value != null) {
                return SuccessResult(
                    drawable = value,
                    request = chain.request,
                    dataSource = DataSource.MEMORY_CACHE
                )
            }
            return chain.proceed(chain.request)
        }
    }
}
