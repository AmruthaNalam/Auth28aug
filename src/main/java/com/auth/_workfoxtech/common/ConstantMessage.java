package com.auth._workfoxtech.common;

public class ConstantMessage {
    public static final String UNAUTHORIZED = "UNAUTHORIZED PERSON";
    public static final String BAD_CREDENTIALS = "Invalid username or password";
    public static final String TOKEN_EXPIRED = "Token has expired. Please login again.";
    public static final String TOKEN_INVALID = "Invalid token format or signature";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized access";
    public static final String ACCESS_DENIED = "Access denied: insufficient permissions";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String ACCOUNT_LOCKED = "Account is locked. Contact support.";
    public static final String ACCOUNT_DISABLED = "Account is disabled";
    public static final String LOGIN_SUCCESS = "Login successful";
    public static final String LOGOUT_SUCCESS = "Logout successful";
    public static final String ACCOUNT_CREATED="Account Created SuccessFully";

    public static final Integer AE_UNAUTHORIZED = 401;
    public static final Integer AE_BAD_CREDENTIALS = 401;
    public static final Integer AE_TOKEN_EXPIRED = 401;
    public static final Integer AE_TOKEN_INVALID = 401;
    public static final Integer AE_UNAUTHORIZED_ACCESS = 403;
    public static final Integer AE_ACCESS_DENIED = 403;
    public static final Integer AE_USER_NOT_FOUND = 404;
    public static final Integer AE_ACCOUNT_LOCKED = 423;
    public static final Integer AE_ACCOUNT_DISABLED = 403;
    public static final Integer AE_LOGIN_SUCCESS = 200;
    public static final Integer AE_LOGOUT_SUCCESS = 200;
    public static final Integer AE_ACCOUNT_CREATED=201;
}
