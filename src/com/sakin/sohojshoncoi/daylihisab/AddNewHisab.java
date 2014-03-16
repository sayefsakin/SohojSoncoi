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
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class AddNewHisab extends Fragment 
				implements CategoryFragment.OnCategorySelectedListener,
				DatePickerFragment.OnDateSelectedListener	{

	private EditText mulloEditText, descriptionEditText;
	private Button categoryButton, dateButton, saveButton, resetButton;
	private Button aeBaeSwitch;
	private View view = null;
	private ImageView imgPreview;
	
	private String fileUrl;
	private Boolean aeOrBae, isEdit; //true = bae, false = ae
	private String categoryName;
	private Calendar date;
	private double amount;
	private String description;
	private Transaction transaction;
	
	public AddNewHisab(){
		this.aeOrBae = true;
		this.amount = 0.0;
		this.categoryName = "";
		this.date = Calendar.getInstance();
		this.description = "";
		this.fileUrl = "";
		this.isEdit = false;
	}
	
	public AddNewHisab(Boolean aeOrBae, double amount, String cat,
					Calendar date, String description, String url){
		this.aeOrBae = aeOrBae;
		this.amount = amount;
		this.categoryName = cat;
		this.date = date;
		this.description = description;
		this.fileUrl = url;
		this.isEdit = true;
		if(aeOrBae) {
			this.amount *= -1.0;
		}
	}
	
	public AddNewHisab(Transaction transaction) {
		this.isEdit = true;
		this.transaction = transaction;
		this.amount = transaction.getAmount();
		this.date = Utils.dateToCalendar(transaction.getDate());
		this.description = transaction.getDescription();
		String pictureUrl = transaction.getPictureUrl();
		if(pictureUrl.length() > 0) {
			this.fileUrl = pictureUrl;
		} else {
			this.fileUrl = null;
		}
		
		try {
			Category cat = SSDAO.getSSdao().getCategoryFromID(transaction.getCategory().getCategoryID());
			this.categoryName = cat.getName();
			if(cat.getType().equalsName("INCOME")) {
				this.aeOrBae = false;
			} else {
				this.aeOrBae = true;
				this.amount *= -1.0;
			}
		} catch (SQLException e) {
			Utils.print(e.toString());
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {
		if(view == null){
			view = inflater.inflate(R.layout.addnewhisab, container, false);
			Utils.print("addNewHisab created");

			setText(R.id.mulloLabel, "g~j¨t");
			setText(R.id.categoryLabel, "K¨vUvMwit");
			setText(R.id.dateLabel, "ZvwiLt");
			setText(R.id.descriptionLabel, "eY©bvt");
			setText(R.id.pictureLabel, "Qwet");
			
			mulloEditText = (EditText) view.findViewById(R.id.mulloEditText);
			mulloEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if(mulloEditText.getText().toString().length() > 0){
						if(Double.compare(Double.parseDouble(mulloEditText.getText().toString()),0.0) == 0) {
							mulloEditText.setText("");
						}
					}
				}
			});
			descriptionEditText = (EditText) view.findViewById(R.id.descriptionEditText);
			imgPreview = (ImageView) view.findViewById(R.id.imgPreview);
			imgPreview.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(fileUrl != "") {
						showPreviewPictureDialog(v);
					} else { 
						showTakePictureDialog(v);
					}
				}
			});
			
			aeBaeSwitch = (Button) view.findViewById(R.id.aeBaeButton);
			aeBaeSwitch.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					aeOrBae = !aeOrBae;
					if(aeOrBae){
						aeBaeSwitch.setBackgroundResource(R.drawable.baebutton);
					} else {
						aeBaeSwitch.setBackgroundResource(R.drawable.aebutton);
					}
					categoryName = "";
					if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
						categoryButton.setText("পছন্দ করুন");
					} else {
						categoryButton.setText("Choose");
					}
				}
			});
			
			categoryButton = (Button) view.findViewById(R.id.categoryButton);
			categoryButton.setBackgroundResource(R.drawable.optionbtn);
			if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
				categoryButton.setTypeface(Utils.banglaTypeFace);
			}
			categoryButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Fragment categoryFragment = new CategoryFragment(AddNewHisab.this, aeOrBae);
					FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
					ft.remove(AddNewHisab.this);
	                ft.add(R.id.content_frame, categoryFragment);
	                ft.addToBackStack(null);
	                ft.commit();
				}
			});
			
			dateButton = (Button) view.findViewById(R.id.dateButton);
			dateButton.setTypeface(Utils.banglaTypeFaceSutonny);
			dateButton.setBackgroundResource(R.drawable.optionbtn);
			dateButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showDatePickerDialog(v);
				}
			});

			saveButton = (Button) view.findViewById(R.id.saveButton);
			saveButton.setBackgroundResource(R.drawable.save_button);
			saveButton.setTypeface(Utils.banglaTypeFaceSutonny);
			saveButton.setText("†mf");
			saveButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					doSave();
				}
			});
			
			resetButton = (Button) view.findViewById(R.id.resetButton);
			resetButton.setBackgroundResource(R.drawable.reset_button);
			resetButton.setTypeface(Utils.banglaTypeFaceSutonny);
			resetButton.setText("wi‡mU");
			resetButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					aeOrBae = true;
					amount = 0.0;
					description = "";
					categoryName = "";
					date = Calendar.getInstance();
					fileUrl = "";
					doReset();
				}
			});
			doReset();
		} else {
			Utils.print(" view already created ");
			ViewGroup parent = (ViewGroup) view.getParent();
			parent.removeView(view);
		}
		Utils.setActionBarTitle(getActivity(), "bZzb Avq/e¨q");
	    return view;
	}

	public void setText(int id, String item) {
		TextView tv = (TextView) view.findViewById(id);
		tv.setTextColor(Color.WHITE);
		tv.setTypeface(Utils.banglaTypeFaceSutonny);
		tv.setText(item);
	}
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment(AddNewHisab.this, this.date);
	    newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
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
	    } else if (requestCode == Utils.CAPTURE_IMAGE_FROM_GALLARY_REQUEST_CODE) {
	    	if (resultCode == Activity.RESULT_OK) {
	            // Image captured and saved to fileUri specified in the Intent
	            Toast.makeText(getActivity(), "Image captured from gallery", Toast.LENGTH_LONG).show();
	            Uri selectedImage = data.getData(); 
	            String[] filePathColumn = { MediaStore.Images.Media.DATA };
	    	    
	            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
	                    filePathColumn, null, null, null);
	            cursor.moveToFirst();
	    
	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String picturePath = cursor.getString(columnIndex);
	            fileUrl = picturePath;
	            cursor.close();
	            
	            previewCapturedImage();
	        } else if (resultCode == Activity.RESULT_CANCELED) {
	            // User cancelled the image capture
	        } else {
	            // Image capture failed, advise user
	        }
	    } else {
	    	Toast.makeText(getActivity(), "Image not captured" + Integer.toString(resultCode), Toast.LENGTH_LONG).show();
	    }
	}

	private void captureImageFromGallery() {
	    Intent intent = new Intent(Intent.ACTION_PICK,
	    		android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	    // start the image capture Intent
	    startActivityForResult(intent, Utils.CAPTURE_IMAGE_FROM_GALLARY_REQUEST_CODE);
	}
	private boolean isDeviceSupportCamera() {
        return (getActivity().getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA));
    }
	private void captureImageFromCamera() {
	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    Uri fileUri = getOutputMediaFileUri();
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
 
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUrl, options);
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
	private File getOutputMediaFile() {

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
	    fileUrl = mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg";
        mediaFile = new File(fileUrl);
	 
	    return mediaFile;
	}
	
	private void doReset(){
		if(aeOrBae){
			aeBaeSwitch.setBackgroundResource(R.drawable.baebutton);
		} else {
			aeBaeSwitch.setBackgroundResource(R.drawable.aebutton);
		}
		mulloEditText.setText(Double.toString(amount));
		descriptionEditText.setText(description);
		
		if(categoryName.length() == 0) {
			if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
				categoryButton.setText("পছন্দ করুন");
			} else {
				categoryButton.setText("Choose");
			}
		} else {
			categoryButton.setText(categoryName);			
		}
		
		if(this.fileUrl == "") {
			imgPreview.setImageResource(R.drawable.image_placeholder);
		} else {
			previewCapturedImage();
		}
		onDateSelected(date, false);
	}
	
	private void doSave(){
		Utils.print("transaction saving.......");
		description = descriptionEditText.getText().toString();
		amount = Double.parseDouble(mulloEditText.getText().toString());
		if(categoryName.length() == 0 ||
				descriptionEditText.length() == 0 ||
				Double.compare(amount, 0.0) == 0) {
			Utils.showToast(getActivity(), "mKj k~b¨¯’vb c~iY Ki“b");
		} else if (amount < 0.0 ) {
			Utils.showToast(getActivity(), "†b‡MwUf cwigvY MÖnY‡hvM¨ bq");
		} else if( description.length() > 50 ) {
			Utils.showToast(getActivity(), "weeiY msw¶ß Ki“b");
		} else {
			if(aeOrBae) {
				amount *= -1.0;
			}
			try {
				if(isEdit){
					Category cat = SSDAO.getSSdao().getCategoryFromName(categoryName);
					transaction.setCategory(cat);

					long size = 0;
					if(fileUrl != ""){
						File f = new File(fileUrl);
						size = f.length();
						
						transaction.setPictureUrl(fileUrl);
						transaction.setPictureSize(size);
					}
					transaction.setAccount(Utils.userAccount);
					transaction.setDescription(description);
					transaction.setAmount(amount);
					transaction.setDate(date.getTime());
					
					SSDAO.getSSdao().getTransactionDAO().update(transaction);
					Utils.showToast(getActivity(), "wnmve cwieZ©b msiw¶Z");
				} else {
					Category cat = SSDAO.getSSdao().getCategoryFromName(categoryName);
					long size = 0;
					if(fileUrl != ""){
						File f = new File(fileUrl);
						size = f.length();
					}
					Transaction transaction = new Transaction(cat, Utils.userAccount, 
							description, 
							amount,
							fileUrl, size, date.getTime());
					SSDAO.getSSdao().getTransactionDAO().create(transaction);
					Utils.showToast(getActivity(), "wnmve msiw¶Z");
				}
			} catch (SQLException e) {
				Utils.print("SQL error in adding new transaction");
				Utils.showToast(getActivity(), "msiw¶Z nqwb, Avevi †Póv Ki“b!");
			}
			FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
			ft.remove(AddNewHisab.this);
	        ft.commit();
	        getActivity().getSupportFragmentManager().popBackStack();
		}
	}
	
	@Override
	public void onCategorySelected(String cat) {
		Utils.print("category selected" + cat);
		categoryButton.setText(cat);
		categoryName = cat;
	}

	@Override
	public void onDateSelected(Calendar date, boolean se) {
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
	
	public void showTakePictureDialog(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		String builderMessage = "Qwe Zzjyb";
		Typeface tf = Utils.banglaTypeFaceSutonny;
		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
			builderMessage = "ছবি তুলুন";
			tf = Utils.banglaTypeFace;
		}
	    builder.setMessage(builderMessage)
	           .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   if(!isDeviceSupportCamera()){
							Toast.makeText(getActivity(), "No camera found", Toast.LENGTH_SHORT).show();
						} else {
							Utils.print("camera found");
							captureImageFromCamera();
						}
	               }
	           })
	           .setNeutralButton("Gallery",  new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   captureImageFromGallery();
	               }
	           })
	           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	               }
	           });
	    builder.create();
	    builder.setCancelable(false);
	    AlertDialog alert = builder.show();
	    TextView msgView = (TextView) alert.findViewById(android.R.id.message);
	    msgView.setTypeface(tf);
	}
	
	public void showPreviewPictureDialog(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		String builderMessage = "Qwe †`Lyb";
		Typeface tf = Utils.banglaTypeFaceSutonny;
		if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
			builderMessage = "ছবি দেখুন";
			tf = Utils.banglaTypeFace;
		}
	    builder.setMessage(builderMessage)
	           .setPositiveButton("Preview", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   Fragment previewImage = new PreviewImage(fileUrl);
	            	   FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
	            	   ft.remove(AddNewHisab.this);
	            	   ft.add(R.id.content_frame, previewImage);
	            	   ft.addToBackStack(null);
	            	   ft.commit();
	               }
	           })
	           .setNeutralButton("Delete",  new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   fileUrl = "";
	            	   imgPreview.setImageResource(R.drawable.image_placeholder);
	               }
	           })
	           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	               }
	           });
	    builder.create();
	    builder.setCancelable(false);
	    AlertDialog alert = builder.show();
	    TextView msgView = (TextView) alert.findViewById(android.R.id.message);
	    msgView.setTypeface(tf);
	}
}
