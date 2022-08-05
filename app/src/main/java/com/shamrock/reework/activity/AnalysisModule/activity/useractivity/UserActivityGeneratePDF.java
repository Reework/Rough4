package com.shamrock.reework.activity.AnalysisModule.activity.useractivity;


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
import com.shamrock.reework.activity.AnalysisModule.activity.activityanalysis.ClsCustomActivityData;
import com.shamrock.reework.activity.AnalysisModule.activity.food.ClsCustomFoodNapData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UserActivityGeneratePDF extends AsyncTask<String, String, String> {
    private ArrayList<ClsCustomActivityData> transactionsDetails;
    private Context context;
    private ProgressDialog progressDialog;
    private String fromdate;
    private String todate;

    private ArrayList<String>  arylst_header_graph;
    private String month="";
    public UserActivityGeneratePDF(ArrayList<ClsCustomActivityData> ary_clsDebitCardTransDetails, Context mContext, String month, ArrayList<String> arylst_header_graph) {
        this.transactionsDetails = ary_clsDebitCardTransDetails;
        this.context = mContext;
        this.arylst_header_graph=arylst_header_graph;
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
            String pdfName = "/Activity_Discipline" + df.format(c.getTime()) + ".pdf";
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

            String header="Activity Discipline"+month+" "+"(hh:mm)";
// Add item into Paragraph
            prHead.add(header+"\n\n\n");
            prHead.setAlignment(Element.ALIGN_CENTER);

            Date date1 = null;
            Date date2 = null;

            Paragraph fromdateTodate = new Paragraph();
            fromdateTodate.setFont(bfBold16);
            fromdateTodate.add("");
            fromdateTodate.setAlignment(Element.ALIGN_LEFT);
            ArrayList<Float> floats=new ArrayList<>();
            boolean b1 = false;
            boolean b2 = false;
            boolean b3=false;
            boolean b4=false;
            boolean b5=false;
            boolean b6=false;
            boolean b7=false;
            boolean b8=false;
            boolean b9=false;
            boolean b10=false;
            boolean b11=false;
            boolean b12=false;
            boolean b13=false;
            boolean b14=false;
            boolean b15=false;
            boolean b16=false;
            boolean b17=false;
            boolean b18=false;
            boolean b19=false;
            boolean b20=false;
            boolean b21=false;
            boolean b22=false;
            boolean b23=false;
            boolean b24=false;
            boolean b25=false;
            boolean b26=false;
            boolean b27=false;
            boolean b28=false;
            boolean b29=false;
            boolean b30=false;





            boolean ab1 = false;

            boolean ab2 = false;
            boolean ab3=false;
            boolean ab4=false;
            boolean ab5=false;
            boolean ab6=false;
            boolean ab7=false;
            boolean ab8=false;
            boolean ab9=false;
            boolean ab10=false;
            boolean ab11=false;
            boolean ab12=false;
            boolean ab13=false;
            boolean ab14=false;
            boolean ab15=false;
            boolean ab16=false;
            boolean ab17=false;
            boolean ab18=false;
            boolean ab19=false;
            boolean ab20=false;
            boolean ab21=false;
            boolean ab22=false;
            boolean ab23=false;
            boolean ab24=false;
            boolean ab25=false;
            boolean ab26=false;
            boolean ab27=false;
            boolean ab28=false;
            boolean ab29=false;
            boolean ab30=false;


            floats.add((float) 1.4);
            for (int i = 0; i <transactionsDetails.size() ; i++) {

                try {
                    if (transactionsDetails.get(i).getBICYCLING() != 0) {
                    if(!b1) {
                        floats.add((float) 1.4);
                        b1=true;
                    }
                    }


                    if (transactionsDetails.get(i).getCONDITIONINGEXERCISE() != 0) {
                        if(!b2) {
                            floats.add((float) 1.4);
                            b2=true;
                        }
                    }

                    if (transactionsDetails.get(i).getDANCING() != 0) {
                        if(!b3) {
                            floats.add((float) 1.4);
                            b3=true;
                        }

                    }


                    if (transactionsDetails.get(i).getFISHINGANDHUNTING() != 0) {

                        if(!b4) {
                            floats.add((float) 1.4);
                            b4=true;
                        }
                    }

                    if (transactionsDetails.get(i).getHOMEACTIVITIES() != 0) {

                        if(!b5) {
                            floats.add((float) 1.4);
                            b5=true;
                        }

                    }

                    if (transactionsDetails.get(i).getHOMEREPAIR() != 0) {
                        if(!b6) {
                            floats.add((float) 1.4);
                            b6=true;
                        }
                    }



                    if (transactionsDetails.get(i).getINACTIVITYQUIETLIGHT() != 0) {
                        if(!b7) {
                            floats.add((float) 1.4);
                            b7=true;
                        }
                    }

                    if (transactionsDetails.get(i).getLAWNANDGARDEN() != 0) {
                        if(!b8) {
                            floats.add((float) 1.4);
                            b8=true;
                        }

                    }

                    if (transactionsDetails.get(i).getMISCELLANEOUS() != 0) {
                        if(!b9) {
                            floats.add((float) 1.4);
                            b9=true;
                        }
                    }

                    if (transactionsDetails.get(i).getMUSICPLAYING() != 0) {
                        if(!b10) {
                            floats.add((float) 1.4);
                            b10=true;
                        }
                    }

                    if (transactionsDetails.get(i).getOCCUPATION() != 0) {
                        if(!b11) {
                            floats.add((float) 1.4);
                            b11=true;
                        }
                    }
                    if (transactionsDetails.get(i).getRELIGIOUSACTIVITIES() != 0) {
                        if(!b12) {
                            floats.add((float) 1.4);
                            b12=true;
                        }
                    }

                    if (transactionsDetails.get(i).getRUNNING() != 0) {
                        if(!b13) {
                            floats.add((float) 1.4);
                            b13=true;
                        }
                    }


                    if (transactionsDetails.get(i).getSELFCARE() != 0) {
                        if(!b14) {
                            floats.add((float) 1.4);
                            b14=true;
                        }
                    }

                    if (transactionsDetails.get(i).getSEXUALACTIVITY() != 0) {
                        if(!b15) {
                            floats.add((float) 1.4);
                            b15=true;
                        }
                    }

                    if (transactionsDetails.get(i).getSPORTS() != 0) {
                        if(!b16) {
                            floats.add((float) 1.4);
                            b16=true;
                        }
                    }

                    if (transactionsDetails.get(i).getTRANSPORTATION() != 0) {
                        if(!b17) {
                            floats.add((float) 1.4);
                            b17=true;
                        }
                    }


                    if (transactionsDetails.get(i).getVOLUNTEERACTIVITIES() != 0) {
                        if(!b18) {
                            floats.add((float) 1.4);
                            b18=true;
                        }
                    }


                    if (transactionsDetails.get(i).getWALKING() != 0) {
                        if(!b19) {
                            floats.add((float) 1.4);
                            b19=true;
                        }
                    }

                    if (transactionsDetails.get(i).getWATERACTIVITIES() != 0) {
                        if(!b20) {
                            floats.add((float) 1.4);
                            b20=true;
                        }
                    }



                    if (transactionsDetails.get(i).getActivity21() != 0) {
                        if(!b21) {
                            floats.add((float) 1.4);
                            b21=true;
                        }
                    }


                    if (transactionsDetails.get(i).getActivity22() != 0) {
                        if(!b22) {
                            floats.add((float) 1.4);
                            b22=true;
                        }
                    }

                    if (transactionsDetails.get(i).getActivity23() != 0) {
                        if(!b23) {
                            floats.add((float) 1.4);
                            b23=true;
                        }
                    }


                    if (transactionsDetails.get(i).getActivity24() != 0) {
                        if(!b24) {
                            floats.add((float) 1.4);
                            b24=true;
                        }
                    }

                    if (transactionsDetails.get(i).getActivity25() != 0) {
                        if(!b25) {
                            floats.add((float) 1.4);
                            b25=true;
                        }
                    }

                    if (transactionsDetails.get(i).getActivity26() != 0) {
                        if(!b26) {
                            floats.add((float) 1.4);
                            b26=true;
                        }
                    }


                    if (transactionsDetails.get(i).getActivity27() != 0) {
                        if(!b27) {
                            floats.add((float) 1.4);
                            b27=true;
                        }
                    }


                    if (transactionsDetails.get(i).getActivity28() != 0) {
                        if(!b28) {
                            floats.add((float) 1.4);
                            b28=true;
                        }
                    }


                    if (transactionsDetails.get(i).getActivity29() != 0) {
                        if(!b29) {
                            floats.add((float) 1.4);
                            b29=true;
                        }
                    }


                    if (transactionsDetails.get(i).getActivity30() != 0) {
                        if(!b30) {
                            floats.add((float) 1.4);
                            b30=true;
                        }
                    }









                }catch (Exception e){
                    e.printStackTrace();
                }


            }



//             b1 = false;
//             b2 = false;
//             b3=false;
//             b4=false;
//             b5=false;
//             b6=false;
//             b7=false;
//             b8=false;
//             b9=false;
//             b10=false;
//             b11=false;
//             b12=false;
//             b13=false;
//             b14=false;
//             b15=false;
//             b16=false;
//             b17=false;
//             b18=false;
//             b19=false;
//             b20=false;



// Create Table into Document with 1 Row


//            float[] columnWidths = {2.4f, 1.4f,2.6f};


          final  float[] arr=new float[floats.size()];
          int index=0;
            for (final  Float value:floats) {
                arr[index++]=value;
            }







            PdfPTable table = new PdfPTable(arr);
            // set table width a percentage of the page width
            table.setWidthPercentage(100f);


// Create New Cell into Table

            insertCell(table, "Date ", Element.ALIGN_CENTER, 1, bfBold14);
            floats.add((float) 1.4);


            for (int i = 0; i <transactionsDetails.size() ; i++) {

                try {
                    if (transactionsDetails.get(i).getBICYCLING() != 0) {
                        if (!ab1){
                            insertCell(table, arylst_header_graph.get(0).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            floats.add((float) 1.4);
                            ab1=true;

                        }

                    }else {

                    }


                    if (transactionsDetails.get(i).getCONDITIONINGEXERCISE() != 0) {
                        if (!ab2){
                            insertCell(table, arylst_header_graph.get(1).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab2=true;

                        }

                    }

                    if (transactionsDetails.get(i).getDANCING() != 0) {
                        if (!ab3){
                            insertCell(table, arylst_header_graph.get(2).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab3=true;

                        }
                        floats.add((float) 1.4);


                    }


                    if (transactionsDetails.get(i).getFISHINGANDHUNTING() != 0) {
                        if (!ab4){
                            insertCell(table, arylst_header_graph.get(3).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab4=true;

                        }

                    }

                    if (transactionsDetails.get(i).getHOMEACTIVITIES() != 0) {
                        if (!ab5){
                            insertCell(table, arylst_header_graph.get(4).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab5=true;

                        }

                    }

                    if (transactionsDetails.get(i).getHOMEREPAIR() != 0) {
                        if (!ab6){
                            insertCell(table, arylst_header_graph.get(5).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab6=true;

                        }

                        floats.add((float) 1.4);

                    }



                    if (transactionsDetails.get(i).getINACTIVITYQUIETLIGHT() != 0) {
                        floats.add((float) 1.4);
                        if (!ab7){
                            insertCell(table, arylst_header_graph.get(6).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab7=true;

                        }

                    }

                    if (transactionsDetails.get(i).getLAWNANDGARDEN() != 0) {
                        floats.add((float) 1.4);
                        if (!ab8){
                            insertCell(table, arylst_header_graph.get(7).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab8=true;

                        }

                    }

                    if (transactionsDetails.get(i).getMISCELLANEOUS() != 0) {
                        floats.add((float) 1.4);
                        if (!ab9){
                            insertCell(table, arylst_header_graph.get(8).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab9=true;

                        }
                    }

                    if (transactionsDetails.get(i).getMUSICPLAYING() != 0) {
                        floats.add((float) 1.4);
                        if (!ab10){
                            insertCell(table, arylst_header_graph.get(9).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab10=true;

                        }

                    }

                    if (transactionsDetails.get(i).getOCCUPATION() != 0) {
                        if (!ab11){
                            insertCell(table, arylst_header_graph.get(10).toString(),Element.ALIGN_CENTER, 1, bfBold14);
                            ab11=true;

                        }
                    }
                    if (transactionsDetails.get(i).getRELIGIOUSACTIVITIES() != 0) {
                        floats.add((float) 1.4);
                        if (!ab12){
                            insertCell(table,  arylst_header_graph.get(11).toString(),Element.ALIGN_CENTER, 1, bfBold14);
                            ab12=true;

                        }

                    }

                    if (transactionsDetails.get(i).getRUNNING() != 0) {
                        if (!ab13){
                            insertCell(table,  arylst_header_graph.get(12).toString(),Element.ALIGN_CENTER, 1, bfBold14);
                            ab13=true;

                        }

                    }


                    if (transactionsDetails.get(i).getSELFCARE() != 0) {

                        floats.add((float) 1.4);
                        if (!ab14){
                            insertCell(table,  arylst_header_graph.get(13).toString(),Element.ALIGN_CENTER, 1, bfBold14);
                            ab14=true;

                        }

                    }

                    if (transactionsDetails.get(i).getSEXUALACTIVITY() != 0) {
                        floats.add((float) 1.4);
                        if (!ab15){
                            insertCell(table,  arylst_header_graph.get(14).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab15=true;

                        }
                    }

                    if (transactionsDetails.get(i).getSPORTS() != 0) {
                        floats.add((float) 1.4);
                        if (!ab16){
                            insertCell(table,  arylst_header_graph.get(15).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab16=true;

                        }

                    }

                    if (transactionsDetails.get(i).getTRANSPORTATION() != 0) {
                        floats.add((float) 1.4);
                        if (!ab17){
                            insertCell(table,  arylst_header_graph.get(16).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab17=true;

                        }

                    }


                    if (transactionsDetails.get(i).getVOLUNTEERACTIVITIES() != 0) {
                        if (!ab18){
                            insertCell(table,  arylst_header_graph.get(17).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab18=true;

                        }


                    }


                    if (transactionsDetails.get(i).getWALKING() != 0) {
                        if (!ab19){
                            insertCell(table,  arylst_header_graph.get(18).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab19=true;

                        }

                    }

                    if (transactionsDetails.get(i).getWATERACTIVITIES() != 0) {
                        floats.add((float) 1.4);
                        if (!ab20){
                            insertCell(table,  arylst_header_graph.get(19).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab20=true;

                        }

                    }

                    if (transactionsDetails.get(i).getActivity21() != 0) {
                        floats.add((float) 1.4);
                        if (!ab21){
                            insertCell(table,  arylst_header_graph.get(20).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab21=true;
                        }
                    }

                    if (transactionsDetails.get(i).getActivity22() != 0) {
                        floats.add((float) 1.4);
                        if (!ab22){
                            insertCell(table,  arylst_header_graph.get(21).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab22=true;
                        }
                    }

                    if (transactionsDetails.get(i).getActivity23() != 0) {
                        floats.add((float) 1.4);
                        if (!ab23){
                            insertCell(table,  arylst_header_graph.get(22).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab23=true;
                        }
                    }

                    if (transactionsDetails.get(i).getActivity24() != 0) {
                        floats.add((float) 1.4);
                        if (!ab24){
                            insertCell(table,  arylst_header_graph.get(23).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab24=true;
                        }
                    }


                    if (transactionsDetails.get(i).getActivity25() != 0) {
                        floats.add((float) 1.4);
                        if (!ab25){
                            insertCell(table,  arylst_header_graph.get(24).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab25=true;
                        }
                    }

                    if (transactionsDetails.get(i).getActivity26() != 0) {
                        floats.add((float) 1.4);
                        if (!ab26){
                            insertCell(table,  arylst_header_graph.get(25).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab26=true;
                        }
                    }


                    if (transactionsDetails.get(i).getActivity27() != 0) {
                        floats.add((float) 1.4);
                        if (!ab27){
                            insertCell(table,  arylst_header_graph.get(26).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab27=true;
                        }
                    }

                    if (transactionsDetails.get(i).getActivity28() != 0) {
                        floats.add((float) 1.4);
                        if (!ab28){
                            insertCell(table,  arylst_header_graph.get(27).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab28=true;
                        }
                    }


                    if (transactionsDetails.get(i).getActivity29() != 0) {
                        floats.add((float) 1.4);
                        if (!ab29){
                            insertCell(table,  arylst_header_graph.get(28).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab29=true;
                        }
                    }


                    if (transactionsDetails.get(i).getActivity30() != 0) {
                        floats.add((float) 1.4);
                        if (!ab30){
                            insertCell(table,  arylst_header_graph.get(29).toString(), Element.ALIGN_CENTER, 1, bfBold14);
                            ab30=true;
                        }
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }


            }



//             ab1 = false;
//
//             ab2 = false;
//             ab3=false;
//             ab4=false;
//             ab5=false;
//             ab6=false;
//             ab7=false;
//             ab8=false;
//             ab9=false;
//             ab10=false;
//             ab11=false;
//             ab12=false;
//             ab13=false;
//             ab14=false;
//             ab15=false;
//             ab16=false;
//             ab17=false;
//             ab18=false;
//             ab19=false;
//             ab20=false;



            table.setHeaderRows(1);
// Add Cell into Table

            for (int i = 0; i < transactionsDetails.size(); i++) {
                insertCell(table,transactionsDetails.get(i).getCreatedOn(), Element.ALIGN_CENTER, 1, bf_normal);


                try {
                    if (transactionsDetails.get(i).getBICYCLING() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getBICYCLING())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);

                    }else {
                        if (ab1){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }



                    if (transactionsDetails.get(i).getCONDITIONINGEXERCISE() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getCONDITIONINGEXERCISE())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab2){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }

                    if (transactionsDetails.get(i).getDANCING() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getDANCING())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);

                    }else {
                        if (ab3){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }


                    if (transactionsDetails.get(i).getFISHINGANDHUNTING() != 0) {
                        insertCell(table,( String.valueOf(transactionsDetails.get(i).getFISHINGANDHUNTING())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab4){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }

                    if (transactionsDetails.get(i).getHOMEACTIVITIES() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getHOMEACTIVITIES())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab5){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }

                    if (transactionsDetails.get(i).getHOMEREPAIR() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getHOMEREPAIR())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab6){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }



                    if (transactionsDetails.get(i).getINACTIVITYQUIETLIGHT() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getINACTIVITYQUIETLIGHT())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab7){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }

                    if (transactionsDetails.get(i).getLAWNANDGARDEN() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getLAWNANDGARDEN())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);

                    }else {
                        if (ab8){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }

                    if (transactionsDetails.get(i).getMISCELLANEOUS() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getMISCELLANEOUS())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab9){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }

                    if (transactionsDetails.get(i).getMUSICPLAYING() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getMUSICPLAYING())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab10){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }

                    if (transactionsDetails.get(i).getOCCUPATION() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getOCCUPATION())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab11){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }
                    if (transactionsDetails.get(i).getRELIGIOUSACTIVITIES() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getRELIGIOUSACTIVITIES())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab12){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }

                    if (transactionsDetails.get(i).getRUNNING() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getRUNNING())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab13){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }


                    if (transactionsDetails.get(i).getSELFCARE() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getSELFCARE())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab14){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }

                    if (transactionsDetails.get(i).getSEXUALACTIVITY() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getSEXUALACTIVITY())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab15){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }

                    if (transactionsDetails.get(i).getSPORTS() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getSPORTS())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab16){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }

                    if (transactionsDetails.get(i).getTRANSPORTATION() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getTRANSPORTATION())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab17){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }


                    if (transactionsDetails.get(i).getVOLUNTEERACTIVITIES() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getVOLUNTEERACTIVITIES())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab18){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }


                    if (transactionsDetails.get(i).getWALKING() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getWALKING())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab19){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }

                    if (transactionsDetails.get(i).getWATERACTIVITIES() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getWATERACTIVITIES())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab20){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }

                    if (transactionsDetails.get(i).getActivity21() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getActivity21())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab21){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }


                    if (transactionsDetails.get(i).getActivity22() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getActivity22())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab22){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }


                    if (transactionsDetails.get(i).getActivity23() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getActivity23())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab23){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }


                    if (transactionsDetails.get(i).getActivity24() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getActivity24())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab24){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }


                    if (transactionsDetails.get(i).getActivity25() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getActivity25())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab25){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }


                    if (transactionsDetails.get(i).getActivity26() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getActivity26())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab26){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }



                    if (transactionsDetails.get(i).getActivity27() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getActivity27())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab27){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }



                    if (transactionsDetails.get(i).getActivity28() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getActivity28())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab28){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }



                    if (transactionsDetails.get(i).getActivity29() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getActivity29())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab29){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }




                    if (transactionsDetails.get(i).getActivity30() != 0) {
                        insertCell(table, (String.valueOf(transactionsDetails.get(i).getActivity30())).replace(".",":"), Element.ALIGN_CENTER, 1, bf_normal);
                    }else {
                        if (ab30){
                            insertCell(table,"-", Element.ALIGN_CENTER, 1, bf_normal);

                        }
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }



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
