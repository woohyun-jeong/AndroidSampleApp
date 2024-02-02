package com.example.sampleapp

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.LruCache
import androidx.lifecycle.Observer
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
import com.example.sampleapp.core.data.event.EventObserver
import com.example.sampleapp.core.data.websocket.WebSocketMangerImp
import com.example.sampleapp.core.model.Shopping
import com.example.sampleapp.feature.sample.broadcast.ConnectivityWatcher
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.migration.CustomInjection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltAndroidApp
class SampleApplication : Application(), ImageLoaderFactory {
    val tag = "SampleApplication"

    @Inject
    lateinit var webSocketManagerImp: WebSocketMangerImp

    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(object: android.app.Application.ActivityLifecycleCallbacks{
            override fun onActivityCreated(activity: android.app.Activity, savedInstanceState: android.os.Bundle?) {
                Log.d(tag, "onActivityCreated: ${activity.javaClass.name}")
            }

            override fun onActivityStarted(activity: android.app.Activity) {
                Log.d(tag, "onActivityStarted: ${activity.javaClass.name}")
            }

            override fun onActivityResumed(activity: android.app.Activity) {
                Log.d(tag, "onActivityResumed: ${activity.javaClass.name}")
            }

            override fun onActivityPaused(activity: android.app.Activity) {
                Log.d(tag, "onActivityPaused: ${activity.javaClass.name}")
            }

            override fun onActivityStopped(activity: android.app.Activity) {
                Log.d(tag, "onActivityStopped: ${activity.javaClass.name}")
            }

            override fun onActivitySaveInstanceState(activity: android.app.Activity, outState: android.os.Bundle) {
                Log.d(tag, "onActivitySaveInstanceState: ${activity.javaClass.name}")
            }

            override fun onActivityDestroyed(activity: android.app.Activity) {
                Log.d(tag, "onActivityDestroyed: ${activity.javaClass.name}")
            }
        })

        val listener = object : WebSocketListener() {
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.d(tag, "onClosed code = $code, reason = $reason")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                Log.d(tag, "onClosing code = $code, reason = $reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.d(
                    tag,
                    "onFailure webSocket = $webSocket, response = ${response?.body?.string()}"
                )
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
//                    Log.d(tag, "onMessage webSocket = $webSocket, text = $text")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
//                    Log.d(
//                        tag,
//                        "onMessage webSocket = $webSocket, bytes = ${bytes.string(StandardCharsets.UTF_8)}"
//                    )
            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d(
                    tag,
                    "onOpen webSocket = $webSocket, response = ${response.body?.string()}"
                )
                val testRequest = "[\n" +
                        "  {\n" +
                        "    \"ticket\": \"test example\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"type\": \"ticker\",\n" +
                        "    \"codes\": [\n" +
                        "      \"KRW-BTC\",\n" +
                        "      \"KRW-ETH\"\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"format\": \"DEFAULT\"\n" +
                        "  }\n" +
                        "]"
                webSocket.send(testRequest)
            }
        }

//        runCatching {
//            webSocketManagerImp.createConnectWebSocket(
//                id = 1234,
//                url = "wss://api.upbit.com/websocket/v1",
//                listener = listener
//            )
//        }.onFailure { error ->
//            error.printStackTrace()
//        }

    }

    override fun onTerminate() {
        super.onTerminate()
        Log.d(tag, "onTerminate")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.d(tag, "onLowMemory")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        when (level) {
            TRIM_MEMORY_COMPLETE -> Log.d(tag, "onTrimMemory TRIM_MEMORY_COMPLETE")
            TRIM_MEMORY_RUNNING_CRITICAL -> Log.d(tag, "onTrimMemory TRIM_MEMORY_RUNNING_CRITICAL")
            TRIM_MEMORY_RUNNING_LOW -> Log.d(tag, "onTrimMemory TRIM_MEMORY_RUNNING_LOW")
            TRIM_MEMORY_BACKGROUND -> Log.d(tag, "onTrimMemory TRIM_MEMORY_BACKGROUND")
            TRIM_MEMORY_MODERATE -> Log.d(tag, "onTrimMemory TRIM_MEMORY_MODERATE")
            TRIM_MEMORY_UI_HIDDEN -> Log.d(tag, "onTrimMemory TRIM_MEMORY_UI_HIDDEN")
            TRIM_MEMORY_RUNNING_MODERATE -> Log.d(tag, "onTrimMemory TRIM_MEMORY_RUNNING_MODERATE")
            else -> Log.d(tag, "onTrimMemoryTRIM_MEMORY else")
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(tag, "onConfigurationChanged")
    }

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
