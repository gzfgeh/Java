package com.example.mydialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;


public class MyDialogDemo extends Dialog{
	public MyDialogDemo(Context context) {
		super(context);
	}
	
	public MyDialogDemo(Context context,int theme) {
		super(context,theme);
	}
	
	public static class Builder{
		public GetSureInputData mListener;
		private Context context;
		private int myIcon;
		private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;
        
    	public interface GetSureInputData{
    		void getText(String string);
    	}
    	
    	public Builder(Context context,GetSureInputData mListener){
    		this.context = context;
    		this.mListener = mListener;
    	}
    	
		public Builder(Context context) {
			this.context = context;
		}
		
		public Builder setMessage(String message){
			this.message = message;
			return this;
		}
		
		/**
         * Set the Dialog message from resource
         * 
         * @param title
         * @return
         */
		public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
		}
		
		 /**
         * Set the Dialog title from resource
         * 
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
                this.title = (String) context.getText(title);
                return this;
        }

        /**
         * Set the Dialog title from String
         * 
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
                this.title = title;
                return this;
        }
        public Builder setView(){
        	return this;
        }
        /*
         * Set the Dialog icon from int
         */
        public Builder setIcon(int myIcon){
        	this.myIcon = myIcon;
        	return this;
        }
        
        public Builder setContentView(View v) {
                this.contentView = v;
                return this;
        }

        /**
         * Set the positive button resource and it's listener
         * 
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                        DialogInterface.OnClickListener listener) {
                this.positiveButtonText = (String) context
                                .getText(positiveButtonText);
                this.positiveButtonClickListener = listener;
                return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                        DialogInterface.OnClickListener listener) {
                this.positiveButtonText = positiveButtonText;
                this.positiveButtonClickListener = listener;
                return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                        DialogInterface.OnClickListener listener) {
                this.negativeButtonText = (String) context
                                .getText(negativeButtonText);
                this.negativeButtonClickListener = listener;
                return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                        DialogInterface.OnClickListener listener) {
                this.negativeButtonText = negativeButtonText;
                this.negativeButtonClickListener = listener;
                return this;
        }

        @SuppressWarnings("deprecation")
		public MyDialogDemo create(int layoutXml) {
                LayoutInflater inflater = (LayoutInflater) context
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // instantiate the dialog with the custom Theme
                final MyDialogDemo dialog = new MyDialogDemo(context,R.style.MyDialog);
                final View layout = inflater.inflate(layoutXml, null);
                dialog.addContentView(layout, new LayoutParams(
                                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                //set the dialog icon
                ((ImageView) layout.findViewById(R.id.icon)).setBackgroundResource(myIcon);
                // set the dialog title
                ((TextView) layout.findViewById(R.id.title)).setText(title);
                //set the dialog edit text
                if (layoutXml == R.layout.diaplay_dialog){
                // set the confirm button
		            if (positiveButtonText != null) {
		                    ((Button) layout.findViewById(R.id.sure)).setText(positiveButtonText);
		                    if (positiveButtonClickListener != null) {
		                            ((Button) layout.findViewById(R.id.sure))
		                                            .setOnClickListener(new View.OnClickListener() {
		                                                    public void onClick(View v) {
		                                                            positiveButtonClickListener.onClick(dialog,
		                                                                            DialogInterface.BUTTON_POSITIVE);
		                                                    }
		                                            });
		                    }
		            } else {
		                    // if no confirm button just set the visibility to GONE
		                    layout.findViewById(R.id.sure).setVisibility(View.GONE);
		            }
                }else if (layoutXml == R.layout.input_dialog){
                	if (positiveButtonText != null) {
                        ((Button) layout.findViewById(R.id.sure)).setText(positiveButtonText);
                        if (positiveButtonClickListener != null) {
                                ((Button) layout.findViewById(R.id.sure))
                                                .setOnClickListener(new View.OnClickListener() {
                                                        public void onClick(View v) {
                                                                positiveButtonClickListener.onClick(dialog,
                                                                                DialogInterface.BUTTON_POSITIVE);
                                                                mListener.getText(((EditText)layout.findViewById(R.id.input)).getText().toString());
                                                        }
                                                });
                        }
                	}
				}
                else {
					
				}
                // set the cancel button
                if (negativeButtonText != null) {
                        ((Button) layout.findViewById(R.id.cancle)).setText(negativeButtonText);
                        if (negativeButtonClickListener != null) {
                                ((Button) layout.findViewById(R.id.cancle))
                                                .setOnClickListener(new View.OnClickListener() {
                                                        public void onClick(View v) {
                                                                negativeButtonClickListener.onClick(dialog,
                                                                                DialogInterface.BUTTON_NEGATIVE);
                                                        }
                                                });
                        }
                } else {
                        // if no confirm button just set the visibility to GONE
                        layout.findViewById(R.id.cancle).setVisibility(
                                        View.GONE);
                }
                // set the content message
                if (message != null) {
                        ((TextView) layout.findViewById(R.id.dialog_message)).setText(message);
                } else if (contentView != null) {
                        // if no message set
                        // add the contentView to the dialog body
                        ((LinearLayout) layout.findViewById(R.id.dialog_message))
                                        .removeAllViews();
                        ((LinearLayout) layout.findViewById(R.id.dialog_message)).addView(
                                        contentView, new LayoutParams(
                                                        LayoutParams.WRAP_CONTENT,
                                                        LayoutParams.WRAP_CONTENT));
                }
                dialog.setContentView(layout);
                //点击屏幕外，没反应
                dialog.setCanceledOnTouchOutside(false);
                return dialog;
        }
        
    }
}
	












