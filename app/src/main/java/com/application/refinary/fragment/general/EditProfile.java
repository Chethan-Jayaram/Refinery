package com.application.refinary.fragment.general;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CAMERA_SERVICE;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.refinary.R;
import com.application.refinary.helper.APIResponse;
import com.application.refinary.helper.CustomMessageHelper;
import com.application.refinary.helper.GlobalClass;
import com.application.refinary.pojo.profile.ProfilePojo;
import com.application.refinary.retrofit.ClientServiceGenerator;
import com.application.refinary.services.APIMethods;
import com.application.refinary.services.ApiListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.myhexaville.smartimagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;


public class EditProfile extends Fragment implements ApiListener {

    private Context mContext;
    private ImageView img_profile;
    private EditText et_fst_name, et_lst_name, et_mob_no, et_email,et_dob,et_spouse_dob,et_anniversary;
    private TextView status_spinner, gender_spinner;
    private Bitmap bitmap,scaled;
    private Boolean isProfilePicChanged = false;
    private String firstName, lastName, contactNumber, email, martialStatus, gender, img, dob, anniversary, spouse_dob;
    private ImagePicker imagePicker;
    private Uri uri;
    private int SELECT_PICTURE = -1;
    private String stringUri,path;
    private LinearLayout lyt_spouse_dob,lyt_anniversary,lyt_dob;
    String img_base64_st = "";
    private Calendar myCalendar;
    private RequestBody fbody;
    private File file;
    MultipartBody.Part body;
    private ProgressBar loading;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        try {

            mContext = view.getContext();

            TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            getActivity().findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.nav_menu).setVisibility(View.VISIBLE);


            loading = view.findViewById(R.id.loading);
            loading.setVisibility(View.GONE);
            loading.bringToFront();
            et_anniversary = view.findViewById(R.id.et_anniversary);
            et_spouse_dob = view.findViewById(R.id.et_spouse_dob);
            et_dob = view.findViewById(R.id.et_dob);
            myCalendar = Calendar.getInstance();
            lyt_anniversary = view.findViewById(R.id.lyt_anniversary);
            lyt_spouse_dob = view.findViewById(R.id.lyt_spouse_dob);
            lyt_dob = view.findViewById(R.id.lyt_dob);
            toolbar_title.setText("My Profile");
            img_profile = view.findViewById(R.id.img_profile);
            et_fst_name = view.findViewById(R.id.et_fst_name);
            et_lst_name = view.findViewById(R.id.et_lst_name);
            et_mob_no = view.findViewById(R.id.et_mob_no);
            et_email = view.findViewById(R.id.et_email);

            status_spinner = view.findViewById(R.id.status_spinner);
            gender_spinner = view.findViewById(R.id.gender_spinner);
            Button btn_edit_profile = view.findViewById(R.id.btn_edit_profile);
            /*imagePicker = new ImagePicker(getActivity(), this, v -> {
                try {
                    isProfilePicChanged = true;
                    uri = v;

                    //img_profile.setImageBitmap(bitmap);
                    //img_profile.setImageURI(v);

                    Glide.with(mContext).load(v).into(img_profile);

                    bitmap = ((BitmapDrawable) img_profile.getDrawable()).getBitmap();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });*/

            et_dob.setOnClickListener(v -> {
                openDatePickerDialog(v,"my_bday");
            });

            et_anniversary.setOnClickListener(v -> {
                openDatePickerDialog(v,"my_anniversary");
            });

            et_spouse_dob.setOnClickListener(v -> {
                openDatePickerDialog(v,"my_spouse_dob");
            });

            img_profile.setOnClickListener(v -> {
                imageChooser();
            });


            getGuestProfileDetails();


            et_fst_name.setText(firstName);
            et_lst_name.setText(lastName);
            et_email.setText(email);
            et_mob_no.setText(contactNumber);
            if (!martialStatus.isEmpty()) {
                status_spinner.setText(martialStatus);
            } else {
                status_spinner.setText("-Please Select--");
            }

            if (!gender.isEmpty()) {
                gender_spinner.setText(gender);
            } else {
                gender_spinner.setText("-Please Select--");
            }

            et_dob.setText(dob);
            et_anniversary.setText(anniversary);
            et_spouse_dob.setText(spouse_dob);



            status_spinner.setOnClickListener(v -> {
                showCustomDialog("Single", "Married", 1);

            });
            gender_spinner.setOnClickListener(v -> {
                showCustomDialog("Male", "Female", 0);

            });

            if (martialStatus.equalsIgnoreCase("Single")){
                lyt_spouse_dob.setVisibility(View.GONE);
                lyt_anniversary.setVisibility(View.GONE);
            }else{
                lyt_spouse_dob.setVisibility(View.VISIBLE);
                lyt_anniversary.setVisibility(View.VISIBLE);
            }


            /*img_profile.setOnClickListener(v -> {
                try {
                    imagePicker.choosePicture(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });*/

            btn_edit_profile.setOnClickListener(v -> {
                try {
                    if (!et_fst_name.getText().toString().isEmpty()
                            && !et_email.getText().toString().isEmpty()
                            && !et_mob_no.getText().toString().isEmpty()
                            && !et_lst_name.getText().toString().isEmpty()) {
                        String img_base64_st = "";
                        if (bitmap != null) {
                            // bitmap = new Compressor(mContext).compressToFile(bitmap);
                           // img_base64_st = GlobalClass.encodeTobase64(bitmap);
                            img_base64_st =   GlobalClass.encodeTobase64(GlobalClass.getResizedBitmap(bitmap,512));
                        }
                        prepareProfileData(et_fst_name.getText().toString(),
                                et_lst_name.getText().toString(),
                                et_email.getText().toString(),
                                et_mob_no.getText().toString(),
                                fbody);
                    } else {
                        CustomMessageHelper showDialog = new CustomMessageHelper(mContext);
                        showDialog.showCustomMessage((Activity) mContext, "Message", "Fields can't be left blank", false, false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            if (!img.isEmpty()) {
                Glide.with(mContext)
                        .load(img)
                        .apply(RequestOptions.placeholderOf(R.drawable.profile_image).error(R.drawable.profile_image))
                        .into(img_profile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void openDatePickerDialog(View v, String my_bday) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    String selectedDate = (monthOfYear + 1)  + "/" + dayOfMonth + "/" + year;
                    switch (my_bday){
                        case "my_bday":
                            et_dob.setText(selectedDate);
                            break;
                        case "my_anniversary":
                            et_anniversary.setText(selectedDate);
                            break;
                        case "my_spouse_dob":
                            et_spouse_dob.setText(selectedDate);
                            break;
                    }

                }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));


        datePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void imageChooser() {

        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);

        // create an instance of the
        // intent of the type image
       // Intent i = new Intent();
       // i.setType("image/*");
       // i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
       // startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if (resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            if (selectedImage != null) {
                isProfilePicChanged = true;
                Cursor cursor = mContext.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    Log.d("image_Path",""+picturePath);
                    img_profile.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    file = new File(picturePath);
                    fbody = RequestBody.create(MediaType.parse("profileImage"),
                            file);
                    cursor.close();
                }
            }

        }*/

        if (resultCode == RESULT_OK && data != null) {
            ///Bitmap photo = (Bitmap) data.getExtras().get("data");
           // img_profile.setImageBitmap(photo);


            Uri selectedImage = data.getData();


            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
           // Uri tempUri = getImageUri(getContext().getApplicationContext(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            file = new File(getRealPathFromURI(selectedImage));
            fbody = RequestBody.create(MediaType.parse("profileImage"),
                    file);

        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContext().getContentResolver() != null) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                isProfilePicChanged = true;
                img_profile.setImageBitmap(BitmapFactory.decodeFile(path));
                cursor.close();
            }
        }
        return path;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
    }

    private void prepareProfileData(String firstName, String lastName, String email, String contactNumber, RequestBody fbody) {

        RequestBody fname,lname,mail,gender, maritalStatus,dateOfBirth,anniversaryDate,spouseDateOfBirth,profileImage;

        fname  =  RequestBody.create(MediaType.parse("firstName"),firstName);
        lname = RequestBody.create(MediaType.parse("lastName"),lastName);
        gender = RequestBody.create(MediaType.parse("gender"),gender_spinner.getText().toString());
        maritalStatus = RequestBody.create(MediaType.parse("maritalStatus"),status_spinner.getText().toString());
        mail = RequestBody.create(MediaType.parse("email"),email);
        dateOfBirth = RequestBody.create(MediaType.parse("dateOfBirth"),et_dob.getText().toString());
        anniversaryDate = RequestBody.create(MediaType.parse("anniversaryDate"),et_anniversary.getText().toString());
        spouseDateOfBirth = RequestBody.create(MediaType.parse("spouseDateOfBirth"),et_spouse_dob.getText().toString());
        if (isProfilePicChanged){
            body = MultipartBody.Part.createFormData("profileImage",file.getName(),fbody);
        }else{
            body = null;
        }



        postProfileDetails(fname,lname,gender,maritalStatus,mail,dateOfBirth,anniversaryDate,spouseDateOfBirth,body);

    }

    private void postProfileDetails(RequestBody fname, RequestBody lname, RequestBody gender, RequestBody maritalStatus, RequestBody mail, RequestBody dateOfBirth, RequestBody anniversaryDate, RequestBody spouseDateOfBirth, MultipartBody.Part body) {
        loading.setVisibility(View.VISIBLE);
        APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
        Map<String, String> headerMap = new HashMap();
        headerMap.put("Authorization", "bearer " + GlobalClass.token);
        Call<ProfilePojo> profileDetails = api.updateProfile(headerMap,fname,lname,mail,body,gender,maritalStatus,dateOfBirth,spouseDateOfBirth,anniversaryDate);
        APIResponse.getCallRetrofit(profileDetails, "updateProfileDetails", mContext, this);
    }


    private void getGuestProfileDetails() {
        GlobalClass.edit = GlobalClass.sharedPreferences.edit();
        firstName = GlobalClass.sharedPreferences.getString("fName", "");
        lastName = GlobalClass.sharedPreferences.getString("lName", "");
        contactNumber = GlobalClass.sharedPreferences.getString("cNumber", "");
        email = GlobalClass.sharedPreferences.getString("eMail", "");
        martialStatus = GlobalClass.sharedPreferences.getString("mStatus", "");
        gender = GlobalClass.sharedPreferences.getString("gender", "");
        img = GlobalClass.sharedPreferences.getString("img", "");
        dob = GlobalClass.sharedPreferences.getString("dob","");
        anniversary = GlobalClass.sharedPreferences.getString("anniversary","");
        spouse_dob = GlobalClass.sharedPreferences.getString("spouse_dob","");

    }


    private void showCustomDialog(String s1, String s2, int view) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.spinner_content, viewGroup, false);

        RadioGroup rd_group = dialogView.findViewById(R.id.rd_group);
        RadioButton rd1 = dialogView.findViewById(R.id.rd_1);
        RadioButton rd2 = dialogView.findViewById(R.id.rd_2);

        rd1.setText(s1);
        rd2.setText(s2);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();


        rd_group.setOnCheckedChangeListener((group, checkedId) -> {
            // checkedId is the RadioButton selected
            RadioButton rb = dialogView.findViewById(checkedId);
            if (view == 0) {
                gender_spinner.setText(rb.getText());
            } else if (view == 1) {
                status_spinner.setText(rb.getText());
            }

            if (status_spinner.getText().toString().equalsIgnoreCase("Single")){
                lyt_spouse_dob.setVisibility(View.GONE);
                lyt_anniversary.setVisibility(View.GONE);
            }else{
                lyt_spouse_dob.setVisibility(View.VISIBLE);
                lyt_anniversary.setVisibility(View.VISIBLE);
            }
            alertDialog.dismiss();
        });
        //finally creating the alert dialog and displaying it
        alertDialog.show();
    }

    @Override
    public <ResponseType> void success(Response<ResponseType> response, String apiCallName) {

        if (apiCallName.equalsIgnoreCase("updateProfileDetails")) {

            loading.setVisibility(View.GONE);
            ProfilePojo profilePojo = (ProfilePojo) response.body();
            if (profilePojo.getStatusCode().equals(201)){

                    String dob,spousedob,anniversary,gender,marStatus,img;

                    if (profilePojo.getData().getDateOfBirth() != null){
                        dob  = profilePojo.getData().getDateOfBirth().toString();
                    }else{
                        dob = "";
                    }

                    if (profilePojo.getData().getSpouseDateOfBirth() != null){
                        spousedob  = profilePojo.getData().getSpouseDateOfBirth().toString();
                    }else{
                        spousedob = "";
                    }

                    if (profilePojo.getData().getAnniversaryDate() != null){
                        anniversary  =  profilePojo.getData().getAnniversaryDate().toString();
                    }else{
                        anniversary = "";
                    }

                if (profilePojo.getData().getProfileImage() != null){
                    img  = profilePojo.getData().getProfileImage();
                }else{
                    img = "";
                }

                    GlobalClass.putsharedpreference(mContext, profilePojo.getData().getFirstName(),
                            profilePojo.getData().getLastName(),
                            GlobalClass.sharedPreferences.getString("cNumber", ""),
                            profilePojo.getData().getEmail(),
                            GlobalClass.token,
                            profilePojo.getData().getMaritalStatus().toString(),
                            profilePojo.getData().getGender().toString(),
                            img,
                            dob,
                            anniversary,
                            spousedob);
                GlobalClass.showErrorMsg(mContext, "Profile updated successfully");

            }else {
                GlobalClass.showErrorMsg(mContext, profilePojo.getMessage());
            }
        }

    }

    @Override
    public void onErrorListner() {
        loading.setVisibility(View.GONE);
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.iv_sos).setVisibility(View.GONE);
        getActivity().findViewById(R.id.img_et_btn).setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().findViewById(R.id.img_et_btn).setVisibility(View.VISIBLE);
        if (GlobalClass.hasActiveBooking) {
            getActivity().findViewById(R.id.iv_sos).setVisibility(View.VISIBLE);
        } else {
            getActivity().findViewById(R.id.iv_sos).setVisibility(View.GONE);
        }
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}