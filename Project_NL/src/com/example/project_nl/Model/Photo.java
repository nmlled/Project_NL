package com.example.project_nl.Model;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

/**
 * This class represents a photo
 * 
 * @author Challenge.IT
 */
public class Photo {

        private byte[] image;
        private String description;
        private String name;
        private Date created_at;
        
        /**
         * @param image
         * @param description
         * @param name
         * @param creation date
         */
        public Photo(byte[] image, String description, String name, Date created_at) {
                this.image = image;
                this.description = description;
                this.name = name;
                this.created_at = created_at;
        }

        /**
         * @return the image
         */
        public byte[] getImage() {
                return image;
        }

        /**
         * @return the description
         */
        public String getDescription() {
                return description;
        }
        
        /**
         * @return the name
         */
        public String getName() {
                return name;
        }

        /**
         * @return the created_at
         */
        public Date getCreated_at() {
                return created_at;
        }

        /**
         * Convert bitmap to byte array with JPEG compression
         * @param bitmap
         * @return byte array 
         */
        public static byte[] getBitmapAsByteArray(Bitmap image) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            image.compress(CompressFormat.JPEG, 0, outputStream);       
            return outputStream.toByteArray();
        }
        
        /**
         * Convert long to Date
         * @param time in milliseconds
         * @return date
         */
        public static Date longToDate(long millis){
                return new Date(millis);
        }
        
        /**
         * Return a string with the date formatted to the parameter mask
         * @param mask
         * @return string with date formatted
         */
        public String dateToString(String mask){
                SimpleDateFormat dateFormat = new SimpleDateFormat(mask, Locale.getDefault());
                return dateFormat.format(created_at);
        }
        
        /**
         * Return a string with the date formatted to the parameter mask
         * @param date in millis
         * @param mask
         * @return string with date formatted
         */
        public static String dateToString(long date, String mask){
                SimpleDateFormat dateFormat = new SimpleDateFormat(mask, Locale.getDefault());
                return dateFormat.format(date);
        }
}