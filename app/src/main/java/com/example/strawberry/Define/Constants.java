package com.example.strawberry.Define;

import android.content.Context;
import android.widget.Toast;

public class Constants {
    public static final String ENTER_USER_NAME = "Email trống";
    public static final String ENTER_PASSWORD = "Mật khẩu trống";
    public static final String ERROR_LOGIN = "Tài khoản không tồn tại";
    public static final String ERROR_SIGN_UP = "Email này tồn tại";
    public static final String ERROR_CODE = "Mã xác nhận không đúng!";
    public static final String SEND_CODE = "Gửi lại mã thành công";
    public static final String ERROR = "Lỗi";
    public static final String SUCCESS_FORGET_PASSWORD = "Mật khẩu đã được gửi về email của bạn";
    public static final String SUCCESS_CREAT_A_ACCOUNT = "Đăng ký thành công!";
    public static final String ERROR_INTERNET = "Lỗi mạng";
    public static final String ENTER_FIRST_NAME = "Họ trống";
    public static final String ENTER_LAST_NAME = "Tên trống";
    public static final String ENTER_CONFIRM_PASSWORD = "Xác nhận mật khẩu trống";
    public static final String PASSWORD_NOT_SAME = "Mật khẩu không trùng nhau";
    public static final Integer UP_POST = 0;
    public static final Integer POST = 1;
    public static final Integer HEAD_PROFILE_USER = 2;
    public static final Integer INFOR_USER = 3;
    public static final void showToast(String string, Context context) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }
}
