package com.fubang.video.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.fubang.video.AppConstant;
import com.fubang.video.R;
import com.fubang.video.base.BaseActivity;
import com.fubang.video.callback.JsonCallBack;
import com.fubang.video.entity.BaseInfoEntity;
import com.fubang.video.entity.PayInfoEntity;
import com.fubang.video.parse.AuthResult;
import com.fubang.video.parse.PayResult;
import com.fubang.video.util.ImagUtil;
import com.fubang.video.util.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.socks.library.KLog;
import com.vmloft.develop.library.tools.utils.VMSPUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jacky on 2017/7/18.
 */
public class RechargeActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_action)
    ImageView ivAction;
    @BindView(R.id.tv_recharge_nk_num)
    TextView tvRechargeNkNum;
    @BindView(R.id.btn_recharge_1)
    Button btnRecharge1;
    @BindView(R.id.btn_recharge_2)
    Button btnRecharge2;
    @BindView(R.id.btn_recharge_3)
    Button btnRecharge3;
    @BindView(R.id.btn_recharge_4)
    Button btnRecharge4;
    @BindView(R.id.btn_recharge_5)
    Button btnRecharge5;
    @BindView(R.id.btn_recharge_6)
    Button btnRecharge6;
    public final static String RETURNURL = "http://61.153.104.118:9418/index.php/Alipay/callbak";
    private int moneyB = -1;
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDBIB+LPRJA7ptfnCfVTGDg3up27ehQ3EB/uAbgx7EExhfYvAxz5O/nzb/prAMc5ekEk6iPpNjlhIilfTN+IiFYgNe5nke+8xFsV9BRnHcrSHAyx9gGeF+ZM7ywpk8EpcY3bMm3ywJpeCSrfnKpIAGXo9aTfeg9/W0AmRwJHW8AK0L+9veCGuyKEDh0ZiiXWlM+D/xotSzdzMbzJX6wapaHGtSQeb5KrEdm86r4zs8gFtHkzD4+VFmHE4dKHjXHrzPXuyrRmGdlKo1kxfzStLwwd2rTwF/GoSc4IP22Nt0HyRtaEmPWOqf6AafvsG2B9HxGHLFQsv7iilL9bjU0AzShAgMBAAECggEALH82RWKg3g0cB393PhIHEEjMRPKz4E2fHs5L52abRrpNrEZHqDxLemFRoolbfkEK218aK16UCu3qfhbG0n0Sqc2l5sKtTTW8AfaJrQQcAQEDuO+KupLpk+bzxs/KzQtVDFgocgx4Vcy0xj6wNH+HXmUFf5428HF/t++PIhqCBMuMgURxTTL8fSOw1DbnWta17EuVIb3WVoCvTxHXAcvjDyhT49s2WZdwBRlAGzCwjd7mo2TbWrUNh8P436ZN3Ecpcp803bZvq9ZY72tm0p6f5SUPzS4hIhmN5Nxacbmb37Zlz6/nsgxiReWojIDGjZBfvGsc0x3fUX08UROYQdzVsQKBgQDfkC31EyDTx6pLB7N8wO4SaSznRzFWxNwbKUITtKMvthOLQeJkzfuBk0DH5tptOS59A39ag+zTDQnwwS6W0zg6W47obh2us18lDrU/dBXOebtM1zgD0VI5rRh9NVeB9WpA43Ai9YfTxIhQQBkbZX+S22KzwqNZjmsAbrgPWgf5kwKBgQDdJWTNyBSVZoGhVphMdfSdjQu/QVZmMpcxYzKXEbOnMFF93DQIGJDfYkqRWJqZq4LfUYrB9sChLojMHWVjpCeriS6kOrwSPS/lSdph2n5VvsneES7wvVDTqjCXl8r7+Sw85RMbepGGyzWwnzmvUQf6w5VIHPfv73nkYy0SbotpewKBgEKbrHWmO1Od24j7RGjY+pWVrIFD3tlhUcrt4fgJC829S9ljydYK4PJcMyLcDxwXu1gX4KvRPpArBFks1Xqud8Q7Xzq1BDb2zlQ2K546Mf0Qm9NrFA9fIEZ64h7785SO0BeBK/neeZeIqKxwTIqzFO1mW+P/C7g1DIBWfJRmWJXtAoGBAMi8CKRB2W1DfqdUUyoUZ9YbEW6pgCOECHlr8Z/vEYPQ+CemmhQ/OEGMluMX6j1S0ZikC6/Eq1WjR8j/ZejsOJYcD5pDNjPYFoGqZ/AqxEJQzCwmrNUZAyUedr0jq7zMkLs1tAJdX3tbOYD5BwN8KiIVJowuv1+roJkO7PquHR3BAoGBANTf7wJ6NsJ6kRP/RT3MHgq3db6ZQaxgtRWQyYcZ5knkyXoNPCfiWJ93v7Cj1vWoCuDiyNrI4XyB62G4/PYycAnIZv5ghUXMxPiK9nII9kPrPazhTl9gGg37mglDRitByav88ij09uO5e+bjiYPQJpBiAEYlO6tANHnMu3QeW9nI".replace(" ", "");
    public static final String RSA_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDBIB+LPRJA7ptfnCfVTGDg3up27ehQ3EB/uAbgx7EExhfYvAxz5O/nzb/prAMc5ekEk6iPpNjlhIilfTN+IiFYgNe5nke+8xFsV9BRnHcrSHAyx9gGeF+ZM7ywpk8EpcY3bMm3ywJpeCSrfnKpIAGXo9aTfeg9/W0AmRwJHW8AK0L+9veCGuyKEDh0ZiiXWlM+D/xotSzdzMbzJX6wapaHGtSQeb5KrEdm86r4zs8gFtHkzD4+VFmHE4dKHjXHrzPXuyrRmGdlKo1kxfzStLwwd2rTwF/GoSc4IP22Nt0HyRtaEmPWOqf6AafvsG2B9HxGHLFQsv7iilL9bjU0AzShAgMBAAECggEALH82RWKg3g0cB393PhIHEEjMRPKz4E2fHs5L52abRrpNrEZHqDxLemFRoolbfkEK218aK16UCu3qfhbG0n0Sqc2l5sKtTTW8AfaJrQQcAQEDuO+KupLpk+bzxs/KzQtVDFgocgx4Vcy0xj6wNH+HXmUFf5428HF/t++PIhqCBMuMgURxTTL8fSOw1DbnWta17EuVIb3WVoCvTxHXAcvjDyhT49s2WZdwBRlAGzCwjd7mo2TbWrUNh8P436ZN3Ecpcp803bZvq9ZY72tm0p6f5SUPzS4hIhmN5Nxacbmb37Zlz6/nsgxiReWojIDGjZBfvGsc0x3fUX08UROYQdzVsQKBgQDfkC31EyDTx6pLB7N8wO4SaSznRzFWxNwbKUITtKMvthOLQeJkzfuBk0DH5tptOS59A39ag+zTDQnwwS6W0zg6W47obh2us18lDrU/dBXOebtM1zgD0VI5rRh9NVeB9WpA43Ai9YfTxIhQQBkbZX+S22KzwqNZjmsAbrgPWgf5kwKBgQDdJWTNyBSVZoGhVphMdfSdjQu/QVZmMpcxYzKXEbOnMFF93DQIGJDfYkqRWJqZq4LfUYrB9sChLojMHWVjpCeriS6kOrwSPS/lSdph2n5VvsneES7wvVDTqjCXl8r7+Sw85RMbepGGyzWwnzmvUQf6w5VIHPfv73nkYy0SbotpewKBgEKbrHWmO1Od24j7RGjY+pWVrIFD3tlhUcrt4fgJC829S9ljydYK4PJcMyLcDxwXu1gX4KvRPpArBFks1Xqud8Q7Xzq1BDb2zlQ2K546Mf0Qm9NrFA9fIEZ64h7785SO0BeBK/neeZeIqKxwTIqzFO1mW+P/C7g1DIBWfJRmWJXtAoGBAMi8CKRB2W1DfqdUUyoUZ9YbEW6pgCOECHlr8Z/vEYPQ+CemmhQ/OEGMluMX6j1S0ZikC6/Eq1WjR8j/ZejsOJYcD5pDNjPYFoGqZ/AqxEJQzCwmrNUZAyUedr0jq7zMkLs1tAJdX3tbOYD5BwN8KiIVJowuv1+roJkO7PquHR3BAoGBANTf7wJ6NsJ6kRP/RT3MHgq3db6ZQaxgtRWQyYcZ5knkyXoNPCfiWJ93v7Cj1vWoCuDiyNrI4XyB62G4/PYycAnIZv5ghUXMxPiK9nII9kPrPazhTl9gGg37mglDRitByav88ij09uO5e+bjiYPQJpBiAEYlO6tANHnMu3QeW9nI".replace(" ", "");

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
        initview();
        initdate();
    }


    private void initview() {
        back(ivBack);
        setText(tvTitle, R.string.nk_recharge);
    }

    private void initdate() {
        OkGo.<BaseInfoEntity>post(AppConstant.BASE_URL + AppConstant.URL_BASE_INFO)
                .tag(this)
                .params("nuserid", String.valueOf(VMSPUtil.get(context, AppConstant.USERID, "")))
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .execute(new JsonCallBack<BaseInfoEntity>(BaseInfoEntity.class) {
                    @Override
                    public void onSuccess(Response<BaseInfoEntity> response) {
                        if (response.body().getStatus().equals("success")) {
                            tvRechargeNkNum.setText(response.body().getInfo().getNmoney() + " ");
                        } else {//token失效
                            startActivity(new Intent(context, LoginActivity.class));
                        }
                    }

                    @Override
                    public void onError(Response<BaseInfoEntity> response) {
                        super.onError(response);
                    }
                });
    }

    private void getpayinfo(int amount) {
        OkGo.<PayInfoEntity>post(AppConstant.BASE_URL + AppConstant.URL_PAY_INFO)
                .tag(this)
                .params("nuserid", String.valueOf(VMSPUtil.get(context, AppConstant.USERID, "")))
                .params("ctoken", String.valueOf(VMSPUtil.get(context, AppConstant.TOKEN, "")))
                .params("amount", amount)
                .execute(new JsonCallBack<PayInfoEntity>(PayInfoEntity.class) {
                    @Override
                    public void onSuccess(final Response<PayInfoEntity> response) {
                        if (response.body().getStatus().equals("success")) {
                            KLog.e(response.body().getInfo());
                            Runnable payRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(RechargeActivity.this);
                                    Map<String, String> result = alipay.payV2(response.body().getInfo(), true);
                                    KLog.e(result.toString());
                                    Message msg = new Message();
                                    msg.what = SDK_PAY_FLAG;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                }
                            };
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        } else {
                            ToastUtil.show(context, R.string.get_order_info_fail);
                        }
                    }

                    @Override
                    public void onError(Response<PayInfoEntity> response) {
                        super.onError(response);
                    }
                });
    }

    @OnClick({R.id.btn_recharge_1, R.id.btn_recharge_2, R.id.btn_recharge_3, R.id.btn_recharge_4, R.id.btn_recharge_5, R.id.btn_recharge_6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_recharge_1:
                moneyB = 10;
                getpayinfo(moneyB);
                break;
            case R.id.btn_recharge_2:
                moneyB = 30;
                getpayinfo(moneyB);
                break;
            case R.id.btn_recharge_3:
                moneyB = 50;
                getpayinfo(moneyB);
                break;
            case R.id.btn_recharge_4:
                moneyB = 98;
                getpayinfo(moneyB);
                break;
            case R.id.btn_recharge_5:
                moneyB = 500;
                getpayinfo(moneyB);
                break;
            case R.id.btn_recharge_6:
                moneyB = 998;
                getpayinfo(moneyB);
                break;
        }
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(context, R.string.pay_success, Toast.LENGTH_SHORT).show();
                        initdate();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(context, R.string.pay_fail, Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(context,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(context,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
}
