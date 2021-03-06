package se.swedsoft.bookkeeping.print.report.sales;

import se.swedsoft.bookkeeping.print.report.sales.SSInvoicePrinter;
import se.swedsoft.bookkeeping.data.SSInvoice;
import se.swedsoft.bookkeeping.data.SSNewCompany;
import se.swedsoft.bookkeeping.data.system.SSDB;
import se.swedsoft.bookkeeping.calc.SSOCRNumber;
import se.swedsoft.bookkeeping.calc.math.SSInvoiceMath;
import se.swedsoft.bookkeeping.gui.util.graphics.SSImage;
import se.swedsoft.bookkeeping.SSBookkeeping;

import java.util.Locale;
import java.util.ResourceBundle;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * User: Andreas Lago
 * Date: 2006-aug-17
 * Time: 10:52:35
 */
public class SSOCRInvoicePrinter extends SSInvoicePrinter {

    private SSORCInvoiceCodeRow iCodeRow;

    /**
     *
     */
    public SSOCRInvoicePrinter(SSInvoice iInvoice, Locale iLocale, boolean iShowBackground) {
        super();
        this.iInvoice = iInvoice;
        this.iLocale  = iLocale;
        super.iLocale = iLocale;
        
        ResourceBundle iBundle = ResourceBundle.getBundle("se.swedsoft.bookkeeping.resources.invoicereport", iLocale);


        setBundle( iBundle );
        setLocale( iLocale );

        setPageHeader  ("sales/sale.header.jrxml");
        setPageFooter  ("sales/ocrinvoice.jrxml");
        setDetail      ("sales/ocrinvoice.jrxml");
        setColumnHeader("sales/ocrinvoice.jrxml");

        if(iShowBackground) setBackground  ("sales/ocrinvoice.jrxml");

        setMargins(0,0,0,0);

        /*SSNewCompany iCompany = SSDB.getInstance().getCurrentCompany();

        String iOCRNumber = SSOCRNumber.getOCRNumber(iInvoice);

        iInvoice.setOCRNumber(iOCRNumber);*/

        iCodeRow = new SSORCInvoiceCodeRow();
        //iCodeRow.setReferenceNumber( iOCRNumber );
        //iCodeRow.setBankNumber     ( iCompany.getBankGiroNumber() );
        iCodeRow.setReferenceNumber( iInvoice.getOCRNumber() );
        iCodeRow.setBankNumber     ( SSDB.getInstance().getCurrentCompany().getBankGiroNumber() );
        iCodeRow.setValue          ( SSInvoiceMath.getTotalSum(iInvoice) );

        addParameters();


    }

    /**
     *
     */
    protected void addParameters() {
        super.addParameters();
        addParameter("number"               , Integer.decode(iInvoice.getOCRNumber() ) );

        addParameter("ocrinvoice.coderow"   , iCodeRow.toString() );
        addParameter("ocrinvoice.background", SSImage.getImage("OCRBackground"));
    }




    /**
     *
     */
    public static class SSORCInvoiceCodeRow{
        private char[] iCharacters;

        /**
         *
         */
        public SSORCInvoiceCodeRow() {
            iCharacters = new char[84];

            for(int i = 0; i < 84; i++){
                iCharacters[i] = ' ';
            }
            // F�rtrykta f�lt
           iCharacters[83] = 'H';
 /*            iCharacters[79] = '#';
            iCharacters[51] = '#';
            iCharacters[34] = '>';
            iCharacters[ 8] = '#';
            iCharacters[ 7] = '4';
            iCharacters[ 6] = '1';
            iCharacters[ 5] = '#';*/
        }


        /**
         *
         * @param iNumber
         */
        public void setReferenceNumber(String iNumber){
            String iText = iNumber.trim();

            for(int i = 0; i < iText.length(); i++){
                iCharacters[53 + i] = iText.charAt(iText.length() - i - 1);
            }
        }

        /**
         *
         * @param iValue
         */
        public void setValue(BigDecimal iValue){
            DecimalFormat iFormat = new DecimalFormat("0.00");
            String iText = iFormat.format( iValue.doubleValue() );

            for(int i = 0; i < iText.length(); i++){
                iCharacters[40 + i] = iText.charAt(iText.length() - i - 1);
            }
            iText = iText.replaceAll("\\.", "");
            iText = iText.replaceAll(",", "");

            iCharacters[42] = ' ';
            iCharacters[36] = SSOCRNumber.getCheckSum(iText);
        }

        /**
         *
         * @param iNumber
         */
        public void setBankNumber(String iNumber) {
            String iText = iNumber.replaceAll("-", "");

            int iLength =  iText.length();

            for(int i = 0; i < iLength; i++){
                iCharacters[9 + i] = iText.charAt(iLength - i - 1);
            }
        }

        /**
         *
         * @return
         */
        public String toString(){
            String iText = "";
            for(int i = 83; i > 0; i--){
                iText =  iText + iCharacters[i];
            }
            return iText;
        }

    }

}
