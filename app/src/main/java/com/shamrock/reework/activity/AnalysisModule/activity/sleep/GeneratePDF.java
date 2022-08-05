package com.shamrock.reework.activity.AnalysisModule.activity.sleep;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import androidx.core.content.FileProvider;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.shamrock.BuildConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GeneratePDF extends AsyncTask<String, String, String> {
    private ArrayList<SleepAnaysisData> transactionsDetails;
    private Context context;
    private ProgressDialog progressDialog;
    private String fromdate;
    private String todate;

    private String month="";
    public GeneratePDF(ArrayList<SleepAnaysisData> ary_clsDebitCardTransDetails, Context mContext, String month) {
        this.transactionsDetails = ary_clsDebitCardTransDetails;
        this.context = mContext;
//        this.month=month;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {

        return generatePDF();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        try {
            if (result != null) {
                File file = new File(result);
                if (file.exists()) {
                    final Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Reework");
                    intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider",file));
                    intent.setType("application/pdf");
                    context.startActivity(Intent.createChooser(intent, "Share file via"));
//                    Intent target = new Intent(Intent.ACTION_VIEW);
//                    target.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                    Uri apkURI = FileProvider.getUriForFile(
//                            context,
//                            context.getApplicationContext()
//                                    .getPackageName() + ".provider", file);
//                    target.setDataAndType(apkURI, "application/pdf");
//                    target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                    try {
//                        context.startActivity(target);
//                    } catch (ActivityNotFoundException e) {
//                        Toast.makeText(context, "You may not have a proper app for viewing this content..", Toast.LENGTH_LONG).show();
//                    }
//
//                    Toast.makeText(context, "PDF downloaded at : " + result, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error while generating PDF", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(context, "Error while generating PDF", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generatePDF() {
        Document document = null;
        String FILE = null;
        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("mm-dd-yyyy");
            String pdfName = "/Trend_Sleep_Quota"  + ".pdf";
//            final String pdfPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            final String pdfPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download/";
            FILE = pdfPath + pdfName;
// Add Permission into Manifest.xml
//

// Create New Blank Document
            document = new Document(PageSize.A4);

// Create Directory in External Storage
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root, "Download");
            myDir.mkdirs();

// Create Pdf Writer for Writting into New Created Document
            PdfWriter.getInstance(document, new FileOutputStream(FILE));

// Open Document for Writting into document
            document.open();

// User Define Method
            //addMetaData(document);
            addTitlePage(document);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
// Close Document after writting all content
        document.close();
        return FILE;
    }

    public void addTitlePage(Document document) throws DocumentException {
// Font Style for Document
        try {
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD
            );
            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bfBold14 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, new BaseColor(0, 0, 0));
            Font bfBold16 = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf_normal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, new BaseColor(0, 0, 0));
// Start New Paragraph
            Paragraph prHead = new Paragraph();
// Set Font in this Paragraph
            prHead.setFont(titleFont);

            String header="Trend_Sleep_Quota "+month;
// Add item into Paragraph
            prHead.add(header+"\n\n\n");
            prHead.setAlignment(Element.ALIGN_CENTER);

            Date date1 = null;
            Date date2 = null;

            Paragraph fromdateTodate = new Paragraph();
            fromdateTodate.setFont(bfBold16);
            fromdateTodate.add("");
            fromdateTodate.setAlignment(Element.ALIGN_LEFT);


// Create Table into Document with 1 Row
            float[] columnWidths = {2.4f, 1.4f,2.6f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setWidthPercentage(100f);

// Create New Cell into Table
            insertCell(table, "Date ", Element.ALIGN_CENTER, 1, bfBold14);
            insertCell(table, "Scheduled(mins) ", Element.ALIGN_CENTER, 1, bfBold14);
            insertCell(table, "Actual(mins)", Element.ALIGN_CENTER, 1, bfBold14);
            table.setHeaderRows(1);
// Add Cell into Table

            for (int i = 0; i < transactionsDetails.size(); i++) {


                insertCell(table,transactionsDetails.get(i).getCreatedOn(), Element.ALIGN_CENTER, 1, bf_normal);
                insertCell(table,String.valueOf(formatHoursAndMinutesMin((int) Double.parseDouble(transactionsDetails.get(i).getScheduledSleep()))), Element.ALIGN_CENTER, 1, bf_normal);
                insertCell(table, String.valueOf(formatHoursAndMinutesMin((int) Double.parseDouble(transactionsDetails.get(i).getActualSleep()))), Element.ALIGN_CENTER, 1, bf_normal);
            }
// Add all above details into Document
            document.add(prHead);
            document.add(fromdateTodate);
            document.add(table);

// Create new Page in PDF
            document.newPage();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    public  String formatHoursAndMinutesMin(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        String strhours="";
        int hoursstr=(totalMinutes / 60);
        if (hoursstr==1){
            strhours=" h ";
        }else {
            strhours=" h ";

        }


        String submittime="";
        submittime= (totalMinutes / 60) + strhours + minutes+" m";




//        if (hoursstr>0){
//
//        }else {
//
//            submittime=  minutes+" Mins";
//
//        }


        String newSubmit="";
        if (totalMinutes<60){
            newSubmit=submittime.replace("0 h ","");
        }else {
            newSubmit=submittime;
        }
        return newSubmit;


    }


    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        //in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        } else {
            cell.setMinimumHeight(15f);
        }
        cell.setPaddingTop(5.0f);
        cell.setPaddingBottom(10.0f);
        cell.setPaddingRight(5.0f);
        cell.setPaddingLeft(7.0f);
        //add the call to the table
        table.addCell(cell);

    }

}
