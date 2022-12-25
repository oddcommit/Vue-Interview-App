package com.tristanruecker.interviewexampleproject.utils;


/**
 * TODO: When multi-language is needed, replace this with spring i18n
 */
public class TextConstants {

    /**
     * {@link com.tristanruecker.interviewexampleproject.services.LoginService#userLogin}
     */
    public final static String CANT_OBTAIN_JWT_TOKEN = "Can not login. Please contact us.";
    public final static String WRONG_EMAIL_OR_PASSWORD = "Sorry, wrong email or wrong password.";
    public final static String RENEW_TOKEN_USER_NOT_FOUND = "Can't find user.";

    /**
     * {@link com.tristanruecker.interviewexampleproject.services.LoginService#registerUser}
     */
    public final static String EMAIL_IS_MANDATORY = "Email is mandatory";
    public final static String EMAIL_IS_INVALID = "This email is not appropriate";
    public final static String AGE_NOT_APPROPRIATE = "Your age must be between 0 and 120";
    public final static String EMAIL_ALREADY_IN_USE = "Sorry, you can't use this email anymore.";
    public final static String AGE_IS_MANDATORY = "Age is mandatory";
    public final static String NAME_IS_MANDATORY = "Name is mandatory";
    public final static String PASSWORD_IS_MANDATORY = "Password is mandatory";
    public final static String GENDER_IS_MANDATORY = "Gender is mandatory";
    public final static String GENDER_CAN_NOT_BE_FOUND = "This gender can not be found";
    public final static String CAPTCHA_NOT_SEND_CORRECTLY = "The captcha was not correctly send to us. Please reload the page and try it again!";
    public final static String CAPTCHA_NOT_VALID_ANYMORE = "Sorry but the captcha is not valid anymore.";
    public final static String CAPTCHA_TEXT_INOUT_DOES_NOT_MATCH_CAPTCHA = "Input of text does not match captcha.";
    public final static String CAPTCHA_CAN_NOT_DESERIALIZE_UUID = "Can not deserialize UUID. Please reload the page and try again!";

    /**
     * {@link com.tristanruecker.interviewexampleproject.services.LoginService#regenerateTokenOnExpire}
     */
    public final static String CANT_REGENERATE_TOKEN = "Sorry, can't regenerate token.";
}
