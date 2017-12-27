package com.theatro.curbside;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;
import java.net.InetAddress;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sethu vignesh on 1/6/17.
 */

public class Utility {
    public static final String FIRST_TIME = "first_time";
    private static String PREF_FILE_NAME = "log_on_pref";
    private static String LOG_ON_URL = "log_on_url";
    private static String NAME = "name";
    private static String ROLE = "role";
    private static String MAIL = "mail";
    private static String LOG_SIZE = "log_size";
    private static String APP_UNIQUE_ID = "app_unique_id";
    static AlertDialog adb;
    static AlertDialog adbWifi;
    public static String WEBSOCKET_MESSAGE = "websocket_message";

    public static void showAlertDialog(final String title, final String message, final Activity activity) {
        activity.runOnUiThread(new Runnable() {
            public void run() {

                try {
                    if (adb != null && adb.isShowing()) {
                        adb.cancel();
                    }
                    adb = new AlertDialog.Builder(activity).setTitle(title).setMessage(message)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            })
                            .show();
                    adb.setCancelable(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public synchronized static void logInfo(@NonNull Context cxt, String error) {
//        WriteLogOnSD.generateLogOnSD(error, cxt);
    }


    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(@NonNull String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

//        String phrase = "";
        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
//                phrase += Character.toUpperCase(c);
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
//            phrase += c;
            phrase.append(c);
        }

        return phrase.toString();
    }

    public static void showAlertDialogForWifi(final String title, final String message, final Activity activity) {
        activity.runOnUiThread(new Runnable() {
            public void run() {


                try {
                    if (adbWifi != null && adbWifi.isShowing()) {
                        adbWifi.cancel();
                    }
                    adbWifi = new AlertDialog.Builder(activity).setTitle(title).setMessage(message)
//                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//
//                                }
//                            })
                            .show();
                    adbWifi.setCancelable(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static void closeWifiAlertDialog() {
        if (adbWifi != null && adbWifi.isShowing()) {
            adbWifi.cancel();
        }
    }

    public synchronized static void saveLogOn(String appId, Context context) {
//        appId="13.13.13.25:2618";
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefrencesEditor = preferences.edit();
        prefrencesEditor.putString(LOG_ON_URL, appId);
        prefrencesEditor.commit();
    }

    public static synchronized String getName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(NAME, null);
    }

    public synchronized static void saveName(String appId, Context context) {
//        appId="13.13.13.25:2618";
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefrencesEditor = preferences.edit();
        prefrencesEditor.putString(NAME, appId);
        prefrencesEditor.commit();
    }

    public static synchronized String getRole(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(ROLE, null);
    }

    public synchronized static void saveRole(String role, Context context) {
//        appId="13.13.13.25:2618";
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefrencesEditor = preferences.edit();
        prefrencesEditor.putString(ROLE, role);
        prefrencesEditor.commit();
    }

    public static synchronized String getMail(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(MAIL, null);
    }

    public synchronized static void saveMail(String role, Context context) {
//        appId="13.13.13.25:2618";
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefrencesEditor = preferences.edit();
        prefrencesEditor.putString(MAIL, role);
        prefrencesEditor.commit();
    }

    public static synchronized String getLogOn(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(LOG_ON_URL, null);
    }

    public synchronized static void saveFirstTimeLogOn(boolean firstTime, Context context) {
//        appId="13.13.13.25:2618";
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefrencesEditor = preferences.edit();
        prefrencesEditor.putBoolean(FIRST_TIME, firstTime);
        prefrencesEditor.commit();
    }

    public static synchronized boolean isFirstTime(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(FIRST_TIME, true);
    }

    public synchronized static void saveMaxLogSize(int size, Context context) {
//        appId="13.13.13.25:2618";
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefrencesEditor = preferences.edit();
        prefrencesEditor.putInt(LOG_SIZE, size);
        prefrencesEditor.commit();
    }

    public static synchronized int getMaxLogSize(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(LOG_SIZE, 5);
    }

    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public static boolean validate(final String ip) {
        return PATTERN.matcher(ip).matches();
    }

    public static boolean validatePort(final String port) {
        try {
            if (Integer.parseInt(port) > 65535) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void freezeSystem() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized boolean deleteFile(String fileName) {
        File file = new File(fileName);
        return file.delete();
    }

    public static String getOsBuildNumber() {
        return Build.FINGERPRINT;
    }

    public static long getLogFileSize(Context context) {

        long file_size = 0;
//        File filedir = new File(Environment.getExternalStorageDirectory(),
//                WriteLogOnSD.DIRECTORY_NAME);
//
//        String fileName = filedir + "/" + WriteLogOnSD.LOG_FILE_NAME;
//
//        try {
//            File file = new File(fileName);
//            file_size = Long.parseLong(String.valueOf(file.length() / (1024 * 1024)));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return file_size;
    }

    public synchronized static void saveAppUniqueId(String appId, @NonNull Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefrencesEditor = preferences.edit();
        prefrencesEditor.putString(APP_UNIQUE_ID, appId);
        prefrencesEditor.commit();
    }

    public static synchronized String getUniqueId(@NonNull Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        String uniqueId = preferences.getString(APP_UNIQUE_ID, null);
        if (uniqueId == null) {
            uniqueId = getUnique12DigitId();
            saveAppUniqueId(uniqueId, context);
        }
        return uniqueId;
    }

    public static void allowNetworkOnMainthread() {
        //This is to override permission issue for running network operations on main thread.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public static boolean validateUrl(String url) {
        if (url.trim().isEmpty() == false) {
            allowNetworkOnMainthread();
            return isInternetAvailable(url.trim());
        }
        return false;
    }

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(EMAIL_REGEX);

    public static boolean emailValidator(String email) {
        if (email == null)
            return false;

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public static boolean isInternetAvailable(String serverUrl) {

        try {
            serverUrl = serverUrl.replace("https://", "");
            serverUrl = serverUrl.replace("http://", "");
            serverUrl = serverUrl.replace("/", "");
            InetAddress ipAddr = InetAddress.getByName(serverUrl); // You can
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }

    }

    public static String getUnique12DigitId() {
        String uniqueID = UUID.randomUUID().toString();
        if (uniqueID.length() < 30) return null;
        uniqueID = uniqueID.replace("-", "");
//        uniqueID = uniqueID.substring(0, 12);
        return uniqueID;
    }


}
