package com.application.refinary.helper;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.application.refinary.R;
import com.application.refinary.pojo.housekeeping.Data;
import com.application.refinary.pojo.housekeeping.WithCategory;
import com.application.refinary.pojo.laundry.Item;
import com.application.refinary.pojo.navigation.SideMenu;
import com.application.refinary.unlock.Refinary;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

public class GlobalClass {

    public static final String android_id = Settings.Secure.getString(Refinary.getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);


    //check in check out dates
    public static Date mUserCheckInDate;
    public static Date mUserCheckOutDate;
   // public static LegicMobileSdkManager mManager;

    //legic
    public static String mDeviceID = "";
    public static String mLegicToken = "";


    public static final Integer mDelay = 50;
    //doorunlock
    public static String mPreviousRouteName = "";

   // public static MultipleRoomNumber booking;

    public static List<SideMenu> headerList = new ArrayList<com.application.refinary.pojo.navigation.SideMenu>();

    public static final Integer PICKER_REQUEST_CODE = 30;
   // public static List<Room> MY_ROOMS;

    public static final String BOTTOM_VIEW = "BOTTOM_VIEW";

    public static Boolean mISVisible = false;


    public static boolean flow = false;
    //complte setup
    public static String loacation = "";
    public static Boolean isMpinSetupComplete = false;
    public static Boolean hasActiveBooking = false;
    public static Boolean isAdapterSet = false;
    public static final String shredPrefName = "experienceOne";
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor edit;
    public static String token = "";
    public static Integer Booking_id;
    public static String Booking_Number;
    public static Integer Guest_id;
    public static String Guest_UUID;
    public static String Location_ID;

    public static String Location_UUID;
    public static Integer Room_no;

    public static final SharedPreferences sharedPreferences1 = Refinary.getAppContext().getSharedPreferences("REFINARY", Context.MODE_PRIVATE);
    public static final SharedPreferences.Editor editor = sharedPreferences1.edit();

    public static final String mHouseKeeing = "HouseKeeping";
    public static final String mLaundry = "Laundry";

    public static DateFormat inputdateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    // public static DateFormat outputdateformat = new SimpleDateFormat("MMM d, yy h:mm a");
    public static DateFormat outputdateformat = new SimpleDateFormat("MMM d, yyyy h:mm a");

    public static DateFormat weatherinputdateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static DateFormat weatheroutputdateformat = new SimpleDateFormat("EEEE h:mm a");

    public static DateFormat tomorrowDayformat = new SimpleDateFormat("EEEE");
    public static DateFormat weainputdateformat = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat weaoutputdateformat = new SimpleDateFormat("EEEE");


    public static DateFormat mRoutineDateFormat = new SimpleDateFormat("MMM d, yyyy");


    public static Gson gson = new Gson();

    public static DateFormat inputTimeFormat = new SimpleDateFormat("HH:mm:ss");


    public static DateFormat outputTimeFormat = new SimpleDateFormat("hh:mm a");


    public static DateFormat outputhourFormat = new SimpleDateFormat("HH");

    public static DateFormat outputMinFormat = new SimpleDateFormat("mm");

    public static DecimalFormat decimalFormat = new DecimalFormat("0.00");


    public static String getFormattedDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //2nd of march 2015
        int day = cal.get(Calendar.DATE);

        if (!((day > 10) && (day < 19)))
            switch (day % 10) {
                case 1:
                    return new SimpleDateFormat("d'st' MMM yy").format(date);
                case 2:
                    return new SimpleDateFormat("d'nd'  MMM yy").format(date);
                case 3:
                    return new SimpleDateFormat("d'rd'  MMM yy").format(date);
                default:
                    return new SimpleDateFormat("d'th'  MMM yy").format(date);
            }
        return new SimpleDateFormat("d'th'  MMMM yy").format(date);
    }



    public interface AdapterClickListner { // create an interface
        void onItemClickListener(Integer position); // create callback function
    }


    public interface OnBiometricAuthSucess { // create an interface
        void onSucessfullBiometricAuth(); // create callback function
    }


    public static void showErrorMsg(Context context, String error) {
        CustomMessageHelper showDialog = new CustomMessageHelper(context);
        showDialog.showCustomMessage((Activity) context, "Alert!!", error, false, false);
    }

  /*  public static String getColor(String str) {
        ColorPojo map = gson.fromJson(str, ColorPojo.class);
        return map.getActionStyles().getBackground();
    }*/

    public static String getClassName(String className) {
        String name = null;
        if (className.equalsIgnoreCase("internet-wifi")) {
            name = "modules.InternetWifi";
        } else if (className.equalsIgnoreCase("hotel-information")) {
            name = "modules.HotelInformationFragment";
        } else if (className.equalsIgnoreCase("house-keeping")) {
            name = "modules.HouseKeeping";
        } else if (className.equalsIgnoreCase("laundry")) {
            name = "modules.laundry.Laundry";
        } else if (className.equalsIgnoreCase("dine-and-drink")) {
            name = "modules.dining.InRoomDining";
        } else if (className.equalsIgnoreCase("foreign-exchange")) {
            name = "modules.ForeignExchange";
        } else if (className.equalsIgnoreCase("emergency")) {
            name = "modules.EmergencyServices";
        } else if (className.equalsIgnoreCase("preference")) {
            name = "modules.preferences.Preferences";
        } else if (className.equalsIgnoreCase("tavel")) {
            name = "modules.travel.Travel";
        } else if (className.equalsIgnoreCase("places-to-visit")) {
            name = "modules.sightseeing.SightSeeing";
        } else if (className.equalsIgnoreCase("main-screen")) {
            name = "general.HomeGridFragment";
        } else if (className.equalsIgnoreCase("hotel-directory")) {
            name = "modules.HotelDirectory";
        } else if (className.equalsIgnoreCase("services")) {
            name = "";
        } else if (className.equalsIgnoreCase("my-stay")) {
            name = "mystay.MyStayFragment";
        } else if (className.equalsIgnoreCase("my-requests")) {
            name = "general.OrderListFragment";
        } else if (className.equalsIgnoreCase("notification")) {
            name = "general.Notification";
        } else if (className.equalsIgnoreCase("about")) {
            name = "general.About";
        } else if (className.equalsIgnoreCase("mpin-login")) {
            name = "general.Logout";
        } else if (className.equalsIgnoreCase("door-unlock")) {
            name = "general.DoorUnlockFragment";
        } else if (className.equalsIgnoreCase("call-engineer")) {
            name = "modules.callanengineer.CallAnEngineerFragment";
        } else if (className.equalsIgnoreCase("check-in")) {
            name = "checkin.MobileCheckInFragment";
        } else if (className.equalsIgnoreCase("news")) {
            name = "modules.news.News";
        } else if (className.equalsIgnoreCase("weather")) {
            name = "modules.weather.Weather";
        } else if (className.equalsIgnoreCase("instagram")) {
            name = "instagram";
        } else if (className.equalsIgnoreCase("refinery-store")) {
            name = "modules.StoreFragment";
        }else if (className.equalsIgnoreCase("home")) {
            name = "general.HomeGridFragment";
        }else if (className.equalsIgnoreCase("logout")) {
            name = "general.logout";
        }else if (className.equalsIgnoreCase("car-rental")) {
            name = "modules.carservice.CarServiceFragment";
        }

        return name;
    }


    public static int numberStepperAdd(int count) {
        if (count < 100) {
            count = count + 1;
        }
        return count;
    }

    public static int numberStepperSub(int count) {
        if (count > 0) {
            count = count - 1;
        }
        return count;
    }


    // Function to remove duplicates from an ArrayList
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {

        // Create a new LinkedHashSet
        Set<T> set = new LinkedHashSet<>();

        // Add the elements to set
        set.addAll(list);

        // Clear the list
        list.clear();

        // add the elements of set
        // with no duplicates to the list
        list.addAll(set);

        // return the list
        return list;
    }


    public static void ShowAlet(Context context, String title, String message) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, id) -> {
                        dialog.dismiss();
                    });
            AlertDialog alert = builder.create();
            alert.show();
            Button positiveButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
            Button negativeButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
            positiveButton.setTextColor(context.getResources().getColor(R.color.black));
            negativeButton.setTextColor(context.getResources().getColor(R.color.black));
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }


    // Used to convert 24hr format to 12hr format with AM/PM values
    public static String updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        return aTime;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public static String encodeTobase64(Bitmap image) {
        String imageEncoded = "";
        try {
            Bitmap immagex = image;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            immagex.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] b = byteArrayOutputStream.toByteArray();
            imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageEncoded;
    }


    public static void putsharedpreference(Context context, String firstName, String lastName, String contactNumber, String email,
                                           String logic_token,String martialStatus,String gender, String img,String dob,
                                           String anniversary,String spose_dob) {
        GlobalClass.sharedPreferences = context.getSharedPreferences(GlobalClass.shredPrefName, 0);
        GlobalClass.edit = GlobalClass.sharedPreferences.edit();
        GlobalClass.edit.putString("fName", firstName);
        GlobalClass.edit.putString("lName", lastName);
        GlobalClass.edit.putString("cNumber", contactNumber);
        GlobalClass.edit.putString("eMail", email);
        GlobalClass.edit.putString("logic_token",logic_token);
        GlobalClass.edit.putString("mStatus", martialStatus);
        GlobalClass.edit.putString("gender", gender);
        GlobalClass.edit.putString("img", img);
        GlobalClass.edit.putString("dob", dob);
        GlobalClass.edit.putString("anniversary", anniversary);
        GlobalClass.edit.putString("spouse_dob", spose_dob);
        GlobalClass.edit.apply();
    }


   /* public static List<CategoryItem> removeDuplicateItems(ArrayList<CategoryItem> menuItems) {
        List<CategoryItem> menu_item = GlobalClass.removeDuplicates(menuItems);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            menu_item = menu_item.stream().filter(p -> p.getQuantity() != 0).collect(Collectors.toList());
        } else {
            for (int i = 0; i < menu_item.size(); i++) {
                if (menu_item.get(i).getQuantity() == 0) {
                    menu_item.remove(menu_item.get(i));
                }
            }
        }
        return menu_item;
    }*/

    public static Boolean ChangeChildFragment(String className, String title, FragmentActivity context) {
        try {
            if (!mPreviousRouteName.equalsIgnoreCase(className)) {
                mPreviousRouteName = className;
                GlobalClass.mISVisible = false;
                className = GlobalClass.getClassName(className);

                String fullPathOfTheClass = "com.application.refinary.fragment." + className;
                if (className.contains("test")) {
                    mPreviousRouteName = "";
                    GlobalClass.ShowAlet(context, "Alert", "Work in Progress..!!");
                } else if (className.contains("instagram")) {
                    Uri uri = Uri.parse("https://www.instagram.com/refineryhotel/?hl=en");
                    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                    likeIng.setPackage("com.instagram.android");
                    try {
                        context.startActivity(likeIng);
                    } catch (ActivityNotFoundException e) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/refineryhotel/?hl=en")));
                    }
                } else {
                    Class<?> cls = Class.forName(fullPathOfTheClass);
                    Fragment fragment = (Fragment) cls.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString("tool_bar_header", title);
                    fragment.setArguments(bundle);
                    if (className.equalsIgnoreCase("general.doorunlock.DoorUnlockFragment")) {
                        return true; //getInvitationCode();
                    } else {
                        context.getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).addToBackStack(null).commit();
                        return false;
                    }
                }
/*
                context.getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container, fragment).addToBackStack(null).commit();
*/
            } else {
                Log.d("failure", mPreviousRouteName);
            }
        } catch (Exception e) {
            Log.d("failure", e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    public static String dateTimeConverter(String date) {
        String dateTime = "";
        try {
            DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date d = utcFormat.parse(date);
            DateFormat pstFormat = new SimpleDateFormat("MMM d, yyyy h:mm a");
            pstFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            dateTime = pstFormat.format(d);
            return dateTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }



    public static String dateErrorMsgTimeConverter(String date) {
        String dateTime = "";
        try {
            DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date d = utcFormat.parse(date);
            DateFormat pstFormat = new SimpleDateFormat("h:mm a");
            pstFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            dateTime = pstFormat.format(d);
            return dateTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    public static String timeConverter(String date) {
        String dateTime = "";
        try {
            DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date d = utcFormat.parse(date);
            DateFormat pstFormat = new SimpleDateFormat("hh:mm a");
            pstFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            dateTime = pstFormat.format(d);
            return dateTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    public static void scaleView(View v, float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                1f, 1f, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(200);
        v.startAnimation(anim);
    }

    public static Bitmap getBitmap(String path) {
        Bitmap bitmap = null;
        try {
            File f = new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    public static File savebitmap(String filename) {


        Bitmap bmp = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;

        File file = new File(filename );
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, filename );
            Log.e("file exist", "" + file + ",Bitmap= " + filename);
        }
        try {
            // make a new bitmap from your file
            Bitmap bitmap = BitmapFactory.decodeFile(file.getName());

            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 60, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("file", "" + file);
        return file;

    }



    //filter data by the string provided and return same object
    /*public static List<Preference> filterDataBySection(List<Preference> mGuestPreferences, String section_type) {
        List<Preference> mPreferences = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mPreferences = mGuestPreferences.stream().filter(p -> p.getSection().equalsIgnoreCase(section_type)).collect(Collectors.toList());
        } else {
            for (int i = 0; i < mGuestPreferences.size(); i++) {
                if (mGuestPreferences.get(i).getSection().equalsIgnoreCase(section_type)) {
                    mPreferences.add(mGuestPreferences.get(i));
                }
            }
        }
        return mPreferences;
    }*/

    public static String greetingBasedOntime() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        String Greeting = "";

        if (timeOfDay >= 5 && timeOfDay < 12) {
            Greeting = "Good Morning ";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            Greeting = "Good Afternoon ";
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            Greeting = "Good Evening ";
        } else
            {
            Greeting = "Hello ";
        }
        return Greeting;
    }


    public interface houseKeepingAdapterListener {
        void onItemClicked(WithCategory data);
    }

    public interface laundryAdapterListener {
        void onItemClicked(Item data);
    }


    public static Boolean TimeComparison(String from_time, String to_time, Date time_now) throws ParseException
    {

        DateFormat pstFormat = new SimpleDateFormat("h:mm a");


        // Get the two dates to be compared
        Date fromTime = outputTimeFormat.parse(from_time);
        Date toTime =outputTimeFormat.parse(to_time);
        Date timeNow = outputTimeFormat.parse(pstFormat.format(time_now));



        // Compare the dates using compareTo()
        if (timeNow.compareTo(fromTime) > 0
                && timeNow.compareTo(toTime) < 0
        ) {
            return true;
        }

        return false;
    }

}
