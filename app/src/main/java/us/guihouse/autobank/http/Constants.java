package us.guihouse.autobank.http;

/**
 * Created by aluno on 14/10/16.
 */

public class Constants {
    public static final String BASE_URL = "http://10.8.1.50:8080/AutoBank_Server_war_exploded";
    public static final String LOGIN_URL = BASE_URL + "/LoginServlet";

    public static final String SHARED_PREFS_FILE = "us.guihouse.autobank.token";
    public static final String SHARED_PREFS_TOKEN = "token";
}
