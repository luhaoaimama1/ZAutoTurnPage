package zone.com.retrofit.callwrapper;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.view.View;

import java.io.IOException;

import ezy.ui.layout.LoadingLayout;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zone.com.retrofit.utils.HandlerUiUtil;
import zone.com.retrofit.views.BasePopWindow;

public class DialogCall<T> implements Call<T> {

    private final Call<T> call;
    private boolean mNeedCloneCall;
    private LoadingLayout mLoadingLayout;
    private BasePopWindow mPopWindow;
    private Dialog mDialog;
    private Callback<T> mCallback;
    private long delayMillis;

    private OnLoadingListener mOnLoadingListener = new OnLoadingListener() {
        @Override
        public void onLoading(State state) {

        }
    };


    public DialogCall(Call<T> call) {
        this.call = call;
    }

    // =======================================
    // ============ Call原生方法 ==============
    // =======================================
    @Override
    public Response execute() throws IOException {
        return call.execute();
    }

    @Override
    public boolean isExecuted() {
        return call.isExecuted();
    }

    @Override
    public void cancel() {
        call.cancel();
    }

    @Override
    public boolean isCanceled() {
        return call.isCanceled();
    }

    @Override
    public Call clone() {
        return call.clone();
    }

    @Override
    public Request request() {
        return call.request();
    }


    @Override
    public void enqueue(@NonNull Callback callback) {
        mCallback = callback;
        if (!(mCallback instanceof CallBackInner)) {
            mCallback = new CallBackInner(callback, new OnLoadingListenerInner(mOnLoadingListener));
        }
        ((CallBackInner) mCallback).onLoadingListenerInner.onLoading(State.Loading);

        if (mNeedCloneCall)
            call.clone().enqueue(mCallback);
        else
            call.enqueue(mCallback);
        mNeedCloneCall = false;
    }


    // =======================================
    // ============ extra方法 ==============
    // =======================================


    public DialogCall<T> delay(long delayMillis) {
        this.delayMillis = delayMillis;
        return this;
    }

    public DialogCall<T> firstLoading(@NonNull final LoadingLayout loadingLayout) {
        this.mLoadingLayout = loadingLayout;
        loadingLayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNeedCloneCall = true;
                enqueue(mCallback);
            }
        });
        return this;
    }

    public DialogCall<T> dialog(@NonNull final Dialog dialog) {
        this.mDialog = dialog;
        return this;
    }

    public DialogCall<T> popWindow(@NonNull final BasePopWindow popWindow) {
        this.mPopWindow = popWindow;
        return this;
    }

    public DialogCall<T> OnLoadingListener(@NonNull OnLoadingListener loadingListener) {
        mOnLoadingListener = loadingListener;
        return this;
    }

    public interface OnLoadingListener {
        void onLoading(State state);
    }

    public enum State {
        Error, Loading, Success;
    }


    /**
     * 真正的实现
     */
    private class CallBackInner<T> implements Callback<T> {

        final OnLoadingListenerInner onLoadingListenerInner;
        private Callback<T> callBack;

        public CallBackInner(Callback<T> callBack, @NonNull OnLoadingListenerInner onLoadingListenerInner) {
            this.callBack = callBack;
            this.onLoadingListenerInner = onLoadingListenerInner;
            onLoadingListenerInner.onLoading(State.Loading);
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if (callBack != null)
                callBack.onResponse(call, response);
            boolean successful = false;
            if (response.isSuccessful()) {
                successful = true;
            }
            doLast(successful, call, response, null);
        }

        private void doLast(boolean successful, Call<T> call, Response<T> response, Throwable t) {
            if (successful) {
                onLoadingListenerInner.onLoading(State.Success);
            } else
                onLoadingListenerInner.onLoading(State.Error);
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            if (callBack != null)
                callBack.onFailure(call, t);
            doLast(false, call, null, t);
        }
    }


    /**
     * 被委任的实现 这里处理 pop dialog等功能
     */
    private class OnLoadingListenerInner implements OnLoadingListener {
        private final OnLoadingListener onLoadingListener;

        public OnLoadingListenerInner(@NonNull OnLoadingListener onLoadingListener) {
            this.onLoadingListener = onLoadingListener;
        }

        @Override
        public void onLoading(final State state) {
            if (state != State.Loading)
                HandlerUiUtil.postDelay(new Runnable() {
                    @Override
                    public void run() {
                        onLoadingReal(state);
                    }
                }, delayMillis);
            else
                onLoadingReal(state);
        }

        private void onLoadingReal(State state) {
            onLoadingListener.onLoading(state);
            switch (state) {
                case Loading:
                    if (mLoadingLayout != null)
                        mLoadingLayout.showLoading();
                    else if (mPopWindow != null)
                        mPopWindow.show();
                    else if (mDialog != null)
                        mDialog.show();
                    break;
                case Success:
                    if (mLoadingLayout != null)
                        mLoadingLayout.showContent();
                    else if (mPopWindow != null)
                        mPopWindow.dismiss();
                    else if (mDialog != null)
                        mDialog.dismiss();
                    break;
                case Error:
                    if (mLoadingLayout != null)
                        mLoadingLayout.showError();
                    else if (mPopWindow != null)
                        mPopWindow.dismiss();
                    else if (mDialog != null)
                        mDialog.dismiss();
                    break;
            }
        }
    }

}