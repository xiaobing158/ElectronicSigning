package fragment;

import android.app.DownloadManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bigbing.electronicsigning.R;

import utils.CheckBankCard;
import utils.CheckIdentityCard;
import utils.CheckoutIphone;


public class Fragment1 extends Fragment {

    // 认证签署
    private Button button;
    // 姓名
    private EditText editText;
    // 身份证号
    private EditText editText2;
    // 银行卡号
    private EditText editText3;
    // 手机号码
    private EditText editText5;
    // 验证码
    private EditText editText6;
    // 待收本金
    private EditText editText7;
    // 验证码
    private Button btnCaptcha;
    // 验证码倒计时
    private CountTimer countTimer;
    private DownloadManager downManager ;
    private Button button3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 实例化Fragment
        return inflater.inflate(R.layout.fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        button = getActivity().findViewById(R.id.button);
        button3 = getActivity().findViewById(R.id.button3);
        editText = getActivity().findViewById(R.id.editText);
        editText2 = getActivity().findViewById(R.id.editText2);
        editText3 = getActivity().findViewById(R.id.editText3);
        editText5 = getActivity().findViewById(R.id.editText5);
        editText6 = getActivity().findViewById(R.id.editText6);
        editText7 = getActivity().findViewById(R.id.editText7);
        btnCaptcha = getActivity().findViewById(R.id.button2);
        // 监听认证签署按钮
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 校验空值及合法性
                Boolean flag1 = verify();
                if(flag1){
                    verify2();
                }
            }
        });

        // 监听验证码倒计时
        btnCaptcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean flag = verify();
                if(flag){
                    countTimer = new CountTimer(60000, 1000);
                    countTimer.start();
                    // 调用验证码接口

                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    /**
     * 校验规则一
     * @return
     */
    private Boolean verify(){
        Boolean flag = false;
        if(TextUtils.isEmpty(editText.getText())){
            tost("请输入您的姓名！");
        } else if(TextUtils.isEmpty(editText2.getText())){
            tost("请输入您的身份证号！");
        } else if(TextUtils.isEmpty(editText3.getText())){
            tost("请输入您的银行卡号！");
        } else if(TextUtils.isEmpty(editText5.getText())){
            tost("请输入您的手机号码！");
        } else {
            // 校验内容准确性
            String idNo = editText2.getText().toString();
            String bankCard = editText3.getText().toString();
            String iphone = editText5.getText().toString();
            String bankCardErrMsg = CheckBankCard.luhmCheck(bankCard);
            if(!CheckIdentityCard.checkIDCard(idNo)){
                tost("请输入正确的身份证号码！");
            } else if (!"true".equals(bankCardErrMsg)){
                tost(bankCardErrMsg);
            } else if(!CheckoutIphone.isMobile(iphone)){
                tost("请输入正确的手机号码！");
            }else {
                // 调用接口签署接口
                flag = true;
            }


        }

        return flag;
    }

    /**
     * 校验规则二
     * @return
     */
    private void verify2(){
        if(TextUtils.isEmpty(editText6.getText())){
            tost("请输入验证码！");
        } else if(TextUtils.isEmpty(editText7.getText())){
            tost("请输入待收本金！");
        }
    }


    // 弹框提示
    private void tost(String msg) {
       Toast.makeText(getActivity().getApplication(), msg, Toast.LENGTH_LONG).show();
    }


    /**
     * 倒计时类
     */
     class CountTimer extends CountDownTimer {

        public CountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        /**
         * 倒计时过程中调用
         *
         * @param millisUntilFinished
         */
        @Override
        public void onTick(long millisUntilFinished) {
            //处理后的倒计时数值
            int time = (int) (Math.round((double) millisUntilFinished / 1000) - 1);
            btnCaptcha.setText(String.valueOf(time)+"S");
            //设置倒计时中的按钮外观
            btnCaptcha.setClickable(false);//倒计时过程中将按钮设置为不可点击
            btnCaptcha.setBackgroundColor(ContextCompat.getColor(getActivity().getApplication(), android.R.color.holo_blue_light));
            btnCaptcha.setTextColor(ContextCompat.getColor(getActivity().getApplication(), android.R.color.background_light));
        }
        /**
         * 倒计时完成后调用
         */
        @Override
        public void onFinish() {
            //设置倒计时结束之后的按钮样式
            btnCaptcha.setBackgroundColor(ContextCompat.getColor(getActivity().getApplication(), android.R.color.holo_blue_light));
            btnCaptcha.setTextColor(ContextCompat.getColor(getActivity().getApplication(), android.R.color.background_light));
            btnCaptcha.setText("重新发送");
            btnCaptcha.setClickable(true);
        }
    }

}
