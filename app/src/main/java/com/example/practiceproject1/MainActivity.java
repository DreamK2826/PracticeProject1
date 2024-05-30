package com.example.practiceproject1;

import static android.content.ContentValues.TAG;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_result;
    private TextView tv_upper;
    private String firstNum = "";  //第一个数字
    private String firstNumOld = "";  //之前的第一个数字
    private String operator = "";  //运算符
    private String secondNum = "";//第二个数字
    private String result = "";//当前的计算结果
    private String showText = "";//下面显示的文本
    private String showText_u = "";//上面显示的文本



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_result = findViewById(R.id.tv_result);
        tv_upper = findViewById(R.id.tv_upper);
        findViewById(R.id.btn_clearAll).setOnClickListener(this);  //清除按钮
        findViewById(R.id.btn_multiply).setOnClickListener(this);  //乘法按钮
        findViewById(R.id.btn_divide).setOnClickListener(this);    //除法按钮
        findViewById(R.id.btn_backspace).setOnClickListener(this);  //退格按钮
        findViewById(R.id.btn_plus).setOnClickListener(this);       //加法按钮
        findViewById(R.id.btn_minus).setOnClickListener(this);       //减法按钮
        findViewById(R.id.btn_openSquared).setOnClickListener(this);  //开平方按钮
        findViewById(R.id.btn_square).setOnClickListener(this);  //平方按钮
        findViewById(R.id.btn_equal).setOnClickListener(this);  //计算按钮
        findViewById(R.id.btn_dot).setOnClickListener(this);  //点 按钮
        findViewById(R.id.btn_num0).setOnClickListener(this);  //0 按钮
        findViewById(R.id.btn_num1).setOnClickListener(this);  //1 按钮
        findViewById(R.id.btn_num2).setOnClickListener(this);  //2 按钮
        findViewById(R.id.btn_num3).setOnClickListener(this);  //3 按钮
        findViewById(R.id.btn_num4).setOnClickListener(this);  //4 按钮
        findViewById(R.id.btn_num5).setOnClickListener(this);  //5 按钮
        findViewById(R.id.btn_num6).setOnClickListener(this);  //6 按钮
        findViewById(R.id.btn_num7).setOnClickListener(this);  //7 按钮
        findViewById(R.id.btn_num8).setOnClickListener(this);  //8 按钮
        findViewById(R.id.btn_num9).setOnClickListener(this);  //9 按钮

    }

    @Override
    public void onClick(View v) {
        String inputText;
        inputText = ((TextView)v).getText().toString();
        Log.i(TAG, "onClick: " + inputText );

        switch(v.getId()){
            //清空按钮
            case R.id.btn_clearAll:
                clear();
                break;
            //退格按钮
            case R.id.btn_backspace:

                break;
                //   +-*/
            case R.id.btn_plus:
            case R.id.btn_minus:
            case R.id.btn_multiply:
            case R.id.btn_divide:

                if(!firstNum.equals("")){
                    operator = inputText;
                    refreshText(operator, showText);
                }

                break;
                //平方按钮
            case R.id.btn_square:

                try {
                    double sq = Double.parseDouble(firstNum) * Double.parseDouble(firstNum);
                    refreshOperate(String.valueOf(sq));

                    refreshText(result, showText + "^2" + "=");
                } catch (NumberFormatException e) {
                    Toast.makeText(this,"Input Error",Toast.LENGTH_SHORT).show();

                    Log.e(TAG, "onClick: 计算错误！");
                }
                break;
                //开方按钮
            case R.id.btn_openSquared:
                try {
                    double opSq = Math.sqrt(Double.parseDouble(firstNum));
                    refreshOperate(String.valueOf(opSq));
                    refreshText(result,"√" + showText + "=");
                } catch (NumberFormatException e) {
                    Toast.makeText(this,"Input Error",Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onClick: 计算错误！");
                }
                break;
                //等于按钮
            case R.id.btn_equal:
                try {
                    Log.i(TAG, "onClick: "+"num1:" + firstNum + " |>operator:" + operator +
                            " |>num2:" + secondNum);

                    double calculate_result = cal4();

                    refreshOperate(String.valueOf(calculate_result));
                    refreshText(result, firstNumOld + showText + "=");
                } catch (Exception e){
                    Log.e(TAG, "onClick: 计算错误！");
                    Toast.makeText(this, "计算错误", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                if(result.length() > 0 && operator.equals("")){//在显示上次结果的时候按下按键时进行清除
                    clear();
                }


                //如果还没输入操作符，更新第一个数字，否则更新第二个数字
                if(operator.equals("")){
                    firstNum += inputText;
                }else{
                    secondNum += inputText;
                }

                if(showText.equals("0") && !inputText.equals(".")){
                    //数字处理：小数--0 8 9 -> 89 整数前面没有0，不拼接；小数 输入 . 进else 拼接。 0 . 1 2 5
                    refreshText(inputText," ");
                }else {
                    refreshText(showText + inputText, firstNum);
                }
                break;
        }
    }


//加减乘除的计算
    private double cal4() {
        switch(operator){
            case "+":
                return Double.parseDouble(firstNum) + Double.parseDouble(secondNum);
            case "-":
                return Double.parseDouble(firstNum) - Double.parseDouble(secondNum);
            case "*":
                return Double.parseDouble(firstNum) * Double.parseDouble(secondNum);
            default:
                return Double.parseDouble(firstNum) / Double.parseDouble(secondNum);
        }
    }

    //清空显示
    private void clear() {
        refreshText("0","0");
        operator = "";
        firstNum = "";
        firstNumOld = "";
        secondNum = "";
        result = "";

    }
    //操作刷新
    private void refreshOperate(String resultNew){

        if((resultNew.charAt(resultNew.length()-1)) == '0' && (resultNew.charAt(resultNew.length()-2)) == '.'){
            //如果result的最后两位是“.0”，就给它去掉
            result = resultNew.substring(0,resultNew.length()-2);//取（0~length-2）作为结果
        }else{
            result = resultNew;
        }
        firstNumOld = firstNum;
        firstNum = result;
        operator = "";
        secondNum = "";
    }

    //显示文本刷新
    private void refreshText(String text_dw,String text_up){
        showText = text_dw;
        showText_u = text_up;
        tv_result.setText(showText);
        tv_upper.setText(showText_u);
    }

}