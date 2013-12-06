package com.sakin.sohojshoncoi.daylihisab;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import com.sakin.sohojshoncoi.R;
import com.sakin.sohojshoncoi.Utils;
import com.sakin.sohojshoncoi.custom.DatePickerFragment;
import com.sakin.sohojshoncoi.database.Category;
import com.sakin.sohojshoncoi.database.SSDAO;
import com.sakin.sohojshoncoi.database.Transaction;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class AddNewHisab extends Fragment 
				implements CategoryFragment.OnCategorySelectedListener,
				DatePickerFragment.OnDateSelectedListener	{

	private EditText mulloEditText, descriptionEditText;
	private Button categoryButton, dateButton, pictureButton, saveButton, resetButton;
	private Switch aeBaeSwitch;
	private View view = null;
	private ImageView imgPreview;
	
	private Uri fileUri;
	private Boolean aeOrBae; //true = bae, false = ae
	private String categoryName;
	private Calendar date;
	private double amount;
	private String description;
	
	public AddNewHisab(){
		this.aeOrBae = true;
		this.amount = 0.0;
		this.categoryName = "";
		this.date = Calendar.getInstance();
		this.description = "";
		this.fileUri = null;
	}
	
	public AddNewHisab(Boolean aeOrBae, double amount, String cat,
					Calendar date, String description, Uri file){
		this.aeOrBae = aeOrBae;
		this.amount = amount;
		this.categoryName = cat;
		this.date = date;
		this.description = description;
		this.fileUri = file;
	}
	
	public AddNewHisab(Transaction transaction) {
		this.amount = transaction.getAmount();
		this.date = DateToCalendar(transaction.getDate());
		this.description = transaction.getDescription();
		String pictureUrl = transaction.getPictureUrl();
		if(pictureUrl.length() > 0) {
			this.fileUri = Uri.fromFile(new File(pictureUrl));
		} else {
			this.fileUri = null;
		}
		
		try {
			Category cat = SSDAO.getSSdao().getCategoryFromID(transaction.getCategory().getCategoryID());
			this.categoryName = cat.getName();
			if(cat.getType().toString().equals("INCOME")) {
				this.aeOrBae = false;
			} else {
				this.aeOrBae = true;
			}
		} catch (SQLException e) {
			Utils.print(e.toString());
		}
	}
	
	private Calendar DateToCalendar(Date date){ 
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.addnewhisab, container, false);
			Utils.print("addNewHisab created");

			setText(R.id.mulloLabel, "মূল্যঃ");
			setText(R.id.categoryLabel, "ক্যাটাগরিঃ");
			setText(R.id.dateLabel, "তারিখঃ");
			setText(R.id.descriptionLabel, "বর্ণনাঃ");
			setText(R.id.pictureLabel, "ছবিঃ");
			
			mulloEditText = (EditText) view.findViewById(R.id.mulloEditText);
			descriptionEditText = (EditText) view.findViewById(R.id.descriptionEditText);
			imgPreview = (ImageView) view.findViewById(R.id.imgPreview);
			aeBaeSwitch = (Switch) view.findViewById(R.id.aeBaeButton);
			
			aeBaeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					aeOrBae = isChecked;
				}
			});
			categoryButton = (Button) view.findViewById(R.id.categoryButton);
			categoryButton.setTypeface(Utils.banglaTypeFace);
			categoryButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Fragment categoryFragment = new CategoryFragment(AddNewHisab.this, aeOrBae);
					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.remove(AddNewHisab.this);
	                ft.add(R.id.content_frame, categoryFragment);
	                ft.addToBackStack(null);
	                ft.commit();
				}
			});
			
			dateButton = (Button) view.findViewById(R.id.dateButton);
			dateButton.setTypeface(Utils.banglaTypeFace);
			dateButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showDatePickerDialog(v);
				}
			});
			
			pictureButton = (Button) view.findViewById(R.id.pictureButton);
			pictureButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(!isDeviceSupportCamera()){
						Toast.makeText(getActivity(), "No camera found", Toast.LENGTH_SHORT).show();
					} else {
						Utils.print("camera found");
						captureImage();
					}
				}
			});
			
			saveButton = (Button) view.findViewById(R.id.saveButton);
			saveButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					doSave();
				}
			});
			
			resetButton = (Button) view.findViewById(R.id.resetButton);
			resetButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					doReset();
				}
			});
			doReset();
		} else {
			Utils.print(" view already created ");
			ViewGroup parent = (ViewGroup) view.getParent();
			parent.removeView(view);
		}
	    return view;
	}

	public void setText(int id, String item) {
		TextView tv = (TextView) view.findViewById(id);
		tv.setTextColor(Color.WHITE);
		tv.setTypeface(Utils.banglaTypeFace);
		tv.setText(item);
	}
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment(AddNewHisab.this, this.date);
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == Utils.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == Utils.CAMERA_RESULT_OK) {
	            // Image captured and saved to fileUri specified in the Intent
	            Toast.makeText(getActivity(), "Image captured", Toast.LENGTH_LONG).show();
	            previewCapturedImage();
	        } else if (resultCode == Utils.CAMERA_RESULT_CANCELED) {
	            // User cancelled the image capture
	        } else {
	            // Image capture failed, advise user
	        }
	    } else {
	    	Toast.makeText(getActivity(), "Image not captured" + Integer.toString(resultCode), Toast.LENGTH_LONG).show();
	    }
	}

	private boolean isDeviceSupportCamera() {
        return (getActivity().getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA));
    }
	private void captureImage() {
	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	 
	    fileUri = getOutputMediaFileUri();
	 
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
	 
	    // start the image capture Intent
	    startActivityForResult(intent, Utils.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}
	private void previewCapturedImage() {
        try {
            imgPreview.setVisibility(View.VISIBLE);
 
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();
 
            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;
 
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);
            imgPreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
	/**
	 * Creating file uri to store image/video
	 */
	public Uri getOutputMediaFileUri() {
	    return Uri.fromFile(getOutputMediaFile());
	}
	 
	/*
	 * returning image / video
	 */
	private static File getOutputMediaFile() {
	 
	    // External sdcard location
	    File mediaStorageDir = new File(
	            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
	            Utils.IMAGE_DIRECTORY_NAME);
	 
	    // Create the storage directory if it does not exist
	    if (!mediaStorageDir.exists()) {
	        if (!mediaStorageDir.mkdirs()) {
	            Utils.print("Oops! Failed create " + Utils.IMAGE_DIRECTORY_NAME + " directory");
	            return null;
	        }
	    }
	 
	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
	            Locale.getDefault()).format(new Date());
	    File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");
	 
	    return mediaFile;
	}
	
	private void doReset(){
		aeBaeSwitch.setChecked(this.aeOrBae);
		mulloEditText.setText(Double.toString(amount));
		descriptionEditText.setText(description);
		
		if(categoryName.length() == 0) {
			categoryButton.setText("পছন্দ করুন");
		} else {
			categoryButton.setText(categoryName);			
		}
		
		if(this.fileUri == null) {
			imgPreview.setImageResource(R.drawable.jogajog);
		} else {
			previewCapturedImage();
		}
		onDateSelected(date);
	}
	
	private void doSave(){
		Utils.print("transaction saving.......");
		description = descriptionEditText.getText().toString();
		amount = Double.parseDouble(mulloEditText.getText().toString());
		if(categoryName.length() == 0 ||
				descriptionEditText.length() == 0 ||
				Double.compare(amount, 0.0) == 0) {
			
			Utils.showToast(getActivity(), "সকল ঘড় পুরন করুন");
		} else {
			try {
				Category cat = SSDAO.getSSdao().getCategoryFromName(categoryName);
				String filePath = "";
				long size = 0;
				if(fileUri != null){
					filePath = fileUri.getPath();
					File f = new File(fileUri.getPath());
					size = f.length();
				}
				Transaction transaction = new Transaction(cat, Utils.userAccount, 
						description, 
						amount,
						filePath, size, date.getTime());
				SSDAO.getSSdao().getTransactionDAO().create(transaction);
				Utils.showToast(getActivity(), "হিসাব সংরক্ষিত");
			} catch (SQLException e) {
				Utils.print("SQL error in adding new transaction");
				Utils.showToast(getActivity(), "সংরক্ষিত হয়নি, আবার চেষ্টা করুনf");
			}
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.remove(AddNewHisab.this);
	        ft.commit();
	        getFragmentManager().popBackStack();
		}
	}
	
	@Override
	public void onCategorySelected(String cat) {
		Utils.print("category selected" + cat);
		categoryButton.setText(cat);
		categoryName = cat;
	}

	@Override
	public void onDateSelected(Calendar date) {
		this.date = date;
		String dt = Integer.toString(date.get(Calendar.DAY_OF_MONTH)) + "-" + 
					Integer.toString(date.get(Calendar.MONTH)+1) + "-" +
					Integer.toString(date.get(Calendar.YEAR));
		dateButton.setText(dt);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
}
