package us.guihouse.autobank.http;

/**
 * Created by aluno on 14/10/16.
 */

public class Constants {
    public static final String BASE_URL = "http://192.168.218.253:8080/AutoBank/api/";
    public static final String LOGIN_URL = BASE_URL + "login";
    public static final String BILLS_URL = BASE_URL + "bill";
    public static final String FINANTIAL_STATEMENTS_URL  = BASE_URL + "finantial-statements";
    public static final String CARD_URL = BASE_URL + "card";

    public static final String SHARED_PREFS_FILE = "us.guihouse.autobank.token";
    public static final String SHARED_PREFS_TOKEN = "token";

    public static final String CATEGORY_IMG_PREFIX = "categories/";
    public static final String CATEGORY_IMG_POSFIX = ".png";
}
