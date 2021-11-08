package com.js.YellowBasket;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;

import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.net.URISyntaxException;


public class HomeFragment extends Fragment {
//    private final Handler handler = new Handler();
    private FrameLayout mContainer;
    private WebView webView;
    private WebView webViewPop;

//    public Button btn1,btn2,btn3,btn4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_domestic, container, false);

        webView = (WebView) view.findViewById(R.id.webView);
        webViewPop = (WebView) view.findViewById(R.id.webView);

//        btn1 = view.findViewById(R.id.btn1);
//        btn2 = view.findViewById(R.id.btn2);
//        btn3 = view.findViewById(R.id.btn3);
//        btn4 = view.findViewById(R.id.btn4);

        this.mContainer = view.findViewById(R.id.webview_frame);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
//        CustomBridge bridge = new CustomBridge(this, webView);


//        webView.addJavascriptInterface(bridge, "baseApp");
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.getSettings().setSupportMultipleWindows(true);
        webView.canGoBack();
        webView.goBack();
        webView.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.getAction() == MotionEvent.ACTION_UP
                    && webView.canGoBack()) {
                webView.goBack();
                return true;
            }


            return false;
        });

//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                webView.loadUrl("https://m.naver.com");
//            }
//        });
//
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                webView.loadUrl("https://www.daum.net");
//            }
//        });
//
//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                webView.loadUrl("https://www.google.com");
//            }
//        });
//
//        btn4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                webView.loadUrl("https://mpstation.kr/main/login.php");
//            }
//        });

        webView.loadUrl("");

        return view;
    }

    public class MyWebChromeClient extends WebChromeClient {
        private final String TAG = "MyWebChromeClient";

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            Log.i(TAG, "onProgressChanged(view:" + view.toString() + ", newProgress:" + newProgress + ")");
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
//            MyLog.toastMakeTextShow(view.getContext(), "TAG", "window.open 협의가 필요합니다.");
            webViewPop = new WebView(view.getContext());
            webViewPop.getSettings().setJavaScriptEnabled(true);
            webViewPop.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webViewPop.getSettings().setSupportMultipleWindows(true);
            webViewPop.getSettings().setDomStorageEnabled(true);
            webViewPop.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onCloseWindow(WebView window) {
                    super.onCloseWindow(window);
                }
            });

        //    webViewPop.addJavascriptInterface(new );
//            webViewPop.setWebViewClient(new MyWebViewClient());
//            webViewPop.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT));

            WebView newWebView = new WebView(view.getContext());
            WebSettings webSettings = newWebView.getSettings();
            WebSettings settings = newWebView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            settings.setSupportMultipleWindows(true);

//            final Dialog dialog = new Dialog(view.getContext(),R.style.Theme_DialogFullScreen);




            final Dialog dialog = new Dialog(view.getContext());
            dialog.setContentView(newWebView);
            dialog.show();
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        //MyLog.toastMake
                        //
                        //
                        //
                        // TextShow(view.getContext(), "TAG", "KEYCODE_BACK");
                        if (newWebView.canGoBack()) {
                            newWebView.goBack();
                        } else {
//                            MyLog.toastMakeTextShow(view.getContext(), "TAG", "Window.open 종료");
                            dialog.dismiss();
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            newWebView.setWebViewClient(new MyWebViewClient());
            newWebView.setWebChromeClient(new MyWebChromeClient() {
            });

            webViewPop.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT));
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(newWebView);
            resultMsg.sendToTarget();

            return true;
        }

        @Override
        public void onCloseWindow(WebView window) {
            Log.i(getClass().getName(), "onCloseWindow");
            window.setVisibility(View.GONE);
            window.destroy();

            //mWebViewSub=null;
            super.onCloseWindow(window);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            Log.i(getClass().getName(), "onJsAlert() url:" + url + ", message:" + message);
            //return super.onJsAlert(view, url, message, result);
            new AlertDialog.Builder(view.getContext())
                    .setTitle("")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();

                                }
                            })
                    .setCancelable(false)
                    .create()
                    .show();

            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            Log.i(getClass().getName(), "onJsConfirm() url:" + url + ", message" + message);
            //return super.onJsConfirm(view, url, message, result);

            new AlertDialog.Builder(view.getContext())
                    .setTitle("")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();

                                }
                            })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    result.cancel();
                                }
                            })
                    .create()
                    .show();
            return true;
        }
    }


    public class MyWebViewClient extends WebViewClient {
        private String TAG = "MyWebViewClient";
        private Context mApplicationContext = null;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null && url.startsWith("intent://")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    Intent existPackage = getActivity().getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                    if (existPackage != null) {
                        startActivity(intent);


                    } else {
                        Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                        marketIntent.setData(Uri.parse("market://details?id=" + intent.getPackage()));
                        startActivity(marketIntent);
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (url != null && url.startsWith("market://")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    if (intent != null) {
                        startActivity(intent);
                    }
                    return true;
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            view.loadUrl(url);
            return true;

        }

//        @Override
//        public void onLoadResource(WebView view, String url) {
//
//            //이곳에 코딩해야 웹뷰 실행시에 작동함.(혹은 버튼)
//            view.loadUrl("https://mpstation.kr/main/joinauth_pop.php,javascript:submitApp()");
//
//            //웹에있는 androidCall()이라는 함수를 호출함.
//            super.onLoadResource(view, url);
//        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.i(TAG, "onPageStarted(view:" + view + ", url:" + url + ", favicon:" + favicon + ")");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.i(TAG, "onPageFinished(view:" + view + ", url:" + url + ")");
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //Log.i(TAG, "onReceivedError() " + error.getErrorCode() + " ---> " + error.getDescription());
                onReceivedError(error.getErrorCode(), String.valueOf(error.getDescription()));

            }
        }


        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                // Log.i(TAG, "onReceivedError() " + errorCode + " ---> " + description);
                onReceivedError(errorCode, description);
            }

        }

        private void onReceivedError(int errorCode, String description) {
            Log.i(getClass().getName(), "onReceivedError() " + errorCode + " ---> " + description);
            switch (errorCode) {
                case WebViewClient.ERROR_TIMEOUT:   //연결 시간 초과
                case WebViewClient.ERROR_CONNECT:   //서버로 연결 실패
                    //case WebViewClient.ERROR_UNKNOWN:   // 일반 오류
                case WebViewClient.ERROR_FILE_NOT_FOUND: //404
                case WebViewClient.ERROR_HOST_LOOKUP:
                case WebViewClient.ERROR_UNSUPPORTED_AUTH_SCHEME:
                case WebViewClient.ERROR_AUTHENTICATION:
                case WebViewClient.ERROR_PROXY_AUTHENTICATION:
                case WebViewClient.ERROR_IO:
                case WebViewClient.ERROR_REDIRECT_LOOP:
                case WebViewClient.ERROR_UNSUPPORTED_SCHEME:
                case WebViewClient.ERROR_FAILED_SSL_HANDSHAKE:
                case WebViewClient.ERROR_BAD_URL:
                case WebViewClient.ERROR_FILE:
                case WebViewClient.ERROR_TOO_MANY_REQUESTS:
                case WebViewClient.ERROR_UNSAFE_RESOURCE:
//                    Log.toastMakeTextShow(mApplicationContext,getClass().getName(),"WebViewClient,onReceivedError("+errorCode+") 에러 발생 " );;
                    Log.e(TAG, "WebViewClient,onReceivedError(" + errorCode + ") 에러 발생 ");
                    break;
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            // SSL 인증서 무시
        }
    }


}
