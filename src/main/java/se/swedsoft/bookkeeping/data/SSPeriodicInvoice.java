package se.swedsoft.bookkeeping.data;

import se.swedsoft.bookkeeping.data.system.SSDB;
import se.swedsoft.bookkeeping.data.base.SSSaleRow;
import se.swedsoft.bookkeeping.data.common.SSInvoiceType;
import se.swedsoft.bookkeeping.calc.math.SSDateMath;
import se.swedsoft.bookkeeping.calc.math.SSInvoiceMath;
import se.swedsoft.bookkeeping.gui.util.SSBundle;

import java.io.Serializable;
import java.util.*;
import java.text.DateFormat;

/**
 * User: Andreas Lago
 * Date: 2006-aug-11
 * Time: 09:10:23
 */
public class SSPeriodicInvoice implements Serializable {

    private static final long serialVersionUID = 4800991425088361649L;

    private Integer iNumber;
    // Fakturamallen
    private SSInvoice iTemplate;

    // Start Datum
    private Date iDate;
    // Antal fakturor
    private Integer iCount;
    // Perioden i m�nader
    private Integer iPeriod;
    // Beskrivning
    private String iDescription;

    private Date iPeriodStart;

    private Date iPeriodEnd;

    private boolean iAppendPeriod;

    private boolean iAppendInformation;

    private String iInformation;

    // Fakturor
    private List<SSInvoice> iInvoices;
    // Tillagta fakturor
    private Map<Integer, Boolean> iAdded;

    //////////////////////////////////////////////////////////////////////////////////

    /**
     *
     */
    public SSPeriodicInvoice() {
        iDate            = new Date();
        iCount           = 1;
        iPeriod          = 1;
        iAppendPeriod    = false;
        iAppendInformation = false;
        iInformation = "Detta �r faktura [FAK] av [TOT].";
        iPeriodStart     = SSDateMath.getFirstDayInMonth(iDate);
        iPeriodEnd       = SSDateMath.getLastDayMonth   (iDate);
        iInvoices        = new LinkedList<SSInvoice>();
        iAdded           = new HashMap<Integer, Boolean>();
        doAutoIncrecement();
    }

    /**
     *
     * @param iPeriodicInvoice
     */
    public SSPeriodicInvoice(SSPeriodicInvoice iPeriodicInvoice) {
        copyFrom(iPeriodicInvoice);
    }


    /**
     *
     * @param iPeriodicInvoice
     */
    public void copyFrom(SSPeriodicInvoice iPeriodicInvoice) {
        this.iNumber           = iPeriodicInvoice.iNumber;
        this.iPeriod           = iPeriodicInvoice.iPeriod;
        this.iDate             = iPeriodicInvoice.iDate;
        this.iCount            = iPeriodicInvoice.iCount;
        this.iDescription      = iPeriodicInvoice.iDescription;
        this.iPeriodStart      = iPeriodicInvoice.iPeriodStart;
        this.iPeriodEnd        = iPeriodicInvoice.iPeriodEnd;
        this.iAppendPeriod     = iPeriodicInvoice.iAppendPeriod;
        this.iAppendInformation = iPeriodicInvoice.iAppendInformation;
        this.iInformation      = iPeriodicInvoice.iInformation;
        this.iTemplate         = new SSInvoice(iPeriodicInvoice.iTemplate);
        this.iInvoices         = new LinkedList<SSInvoice>();
        this.iAdded            = new HashMap<Integer, Boolean>();
        this.iTemplate.setCurrency(iPeriodicInvoice.getTemplate().getCurrency());
        this.iTemplate.setCurrencyRate(iPeriodicInvoice.getTemplate().getCurrencyRate());

        for (SSInvoice iInvoice : iPeriodicInvoice.iInvoices) {
            boolean isAdded = iPeriodicInvoice.isAdded(iInvoice);

            this.iInvoices.add( new SSInvoice(iInvoice) );
            this.iAdded   .put( iInvoice.getNumber(), isAdded);
        }
    }



    ////////////////////////////////////////////////////


    /**
     * Auto increment the sales number
     */
    public void doAutoIncrecement(){
        List<SSPeriodicInvoice> iPeriodicInvoices = SSDB.getInstance().getPeriodicInvoices();

        int iMax = 0;
        for(SSPeriodicInvoice iPeriodicInvoice: iPeriodicInvoices){

            if(iPeriodicInvoice.getNumber() != null && iPeriodicInvoice.getNumber() > iMax){
                iMax = iPeriodicInvoice.getNumber();
            }
        }
        iNumber = iMax + 1;
    }

    ////////////////////////////////////////////////////

    /**
     *
     * @return
     */
    public Integer getNumber() {
        return iNumber;
    }

    /**
     *
     * @param iNumber
     */
    public void setNumber(Integer iNumber) {
        this.iNumber = iNumber;
    }

    ////////////////////////////////////////////////////

    /**
     *
     * @return
     */
    public SSInvoice getTemplate() {
        if(iTemplate == null) iTemplate = new SSInvoice(SSInvoiceType.NORMAL);

        return iTemplate;
    }

    /**
     *
     * @param iTemplate
     */
    public void setTemplate(SSInvoice iTemplate) {
        this.iTemplate = iTemplate;

        //createInvoices();
    }

    ////////////////////////////////////////////////////

    /**
     *
     * @return
     */
    public Date getDate() {
        return iDate;
    }

    /**
     *
     * @param iValue
     */
    public void setDate(Date iValue) {
        this.iDate = iValue;

        //createInvoices();
    }

    ////////////////////////////////////////////////////

    /**
     *
     * @return
     */
    public Integer getCount() {
        return iCount;
    }

    /**
     *
     * @param iValue
     */
    public void setCount(Integer iValue) {
        this.iCount = iValue;

        //createInvoices();
    }

    ////////////////////////////////////////////////////

    /**
     *
     * @return
     */
    public Integer getPeriod() {
        return iPeriod;
    }

    /**
     *
     * @param iValue
     */
    public void setPeriod(Integer iValue) {
        this.iPeriod = iValue;
        //createInvoices();
    }

    ////////////////////////////////////////////////////

    /**
     *
     * @return
     */
    public String getDescription() {
        return iDescription;
    }

    /**
     *
     * @param iDescription
     */
    public void setDescription(String iDescription) {
        this.iDescription = iDescription;
    }

    ////////////////////////////////////////////////////

    /**
     *
     * @return
     */
    public Date getPeriodStart() {
        return iPeriodStart;
    }

    /**
     *
     * @param iPeriodStart
     */
    public void setPeriodStart(Date iPeriodStart) {
        this.iPeriodStart = iPeriodStart;

        //createInvoices();
    }

    ////////////////////////////////////////////////////

    /**
     *
     * @return
     */
    public Date getPeriodEnd() {
        return iPeriodEnd;
    }

    /**
     *
     * @param iPeriodEnd
     */
    public void setPeriodEnd(Date iPeriodEnd) {
        this.iPeriodEnd = iPeriodEnd;

        //createInvoices();
    }

    ////////////////////////////////////////////////////

    /**
     *
     * @return
     */
    public boolean getAppendPeriod() {
        return iAppendPeriod;
    }

    /**
     *
     * @param iAppendPeriod
     */

    public void setAppendPeriod(boolean iAppendPeriod) {
        this.iAppendPeriod = iAppendPeriod;

        //createInvoices();
    }


    public boolean isAppendInformation() {
        return iAppendInformation;
    }

    public void setAppendInformation(boolean iAppendInformation) {
        this.iAppendInformation = iAppendInformation;
    }

    public String getInformation() {
        return iInformation;
    }

    public void setInformation(String iInformation) {
        this.iInformation = iInformation;
    }

    ////////////////////////////////////////////////////

    /**
     *
     * @param iInvoice
     * @return
     */
    public boolean isAdded(SSInvoice iInvoice){
        if(iAdded == null) iAdded = new HashMap<Integer, Boolean>();

        Integer iNumber = iInvoice.getNumber();

        if(iNumber != null){
            return iAdded.get(iNumber);
        } else {
            return true;
        }
    }

    /**
     *
     * @param iInvoice
     */
    public void setAdded(SSInvoice iInvoice){
        if(iAdded == null) iAdded = new HashMap<Integer, Boolean>();

        Integer iNumber = iInvoice.getNumber();

        if(iNumber != null){
            iAdded.put(iNumber, true);
        }
    }


    /**
     *
     * @param iInvoice
     */
    public void setNotAdded(SSInvoice iInvoice){
        if(iAdded == null) return;

        Integer iNumber = iInvoice.getNumber();

        if(iNumber != null){
            iAdded.put(iNumber, false);
        }
    }

     /**
     *
     * @return
     */
    private Map<Integer, Boolean> getAdded() {
        if(iAdded == null) iAdded = new HashMap<Integer, Boolean>();
        return iAdded;
    }

    ////////////////////////////////////////////////////

    /**
     *
     * @return
     */
    public List<SSInvoice> getInvoices(){
        if(iInvoices == null) iInvoices = new LinkedList<SSInvoice>();

        return iInvoices;
    }



    /**
     * Returns all invoices that isnt added up till the selected date
     *
     * @param iDate
     * @return
     */
    public List<SSInvoice> getInvoices(Date iDate){
        List<SSInvoice> iFiltered = new LinkedList<SSInvoice>();

        for (SSInvoice iInvoice : getInvoices()) {

            // Skip the invoice if its already added
            if(isAdded(iInvoice) ) continue;

            // The invoice date is before the date
            if( SSInvoiceMath.inPeriod(iInvoice, iDate) ){
                iFiltered.add(iInvoice);
            }
        }
        return iFiltered;
    }

    /**
     * Returns the date of the next invoice
     *
     * @return the date
     */
    public Date getNextDate() {

        for (SSInvoice iInvoice : getInvoices()) {
            // Skip the invoice if its already added
            if(isAdded(iInvoice) ) continue;

            return iInvoice.getDate();
        }
        return null;
    }



    /**
     *
     */
    public void createInvoices(){
        iInvoices = new LinkedList<SSInvoice>();

        if( iPeriod == null || iPeriodStart == null || iPeriodEnd == null || iTemplate == null) return;

        Date iDate        = this.iDate;
        Date iPeriodStart = this.iPeriodStart;
        Date iPeriodEnd   = this.iPeriodEnd;

        DateFormat iFormat = DateFormat.getDateInstance(DateFormat.SHORT);

        for(int i = 0; i < iCount; i++){
            SSInvoice iInvoice = new SSInvoice(iTemplate);

            iInvoice.setDate( iDate );
            iInvoice.setDueDate();
            iInvoice.setNumber( i );
            iInvoice.setOrderNumbers(iTemplate.getOrderNumbers());
            
            if(iAppendPeriod){
                String strPeriodStart = iFormat.format(iPeriodStart);
                String strPeriodEnd   = iFormat.format(iPeriodEnd );

                SSSaleRow iRow = new SSSaleRow();
                iRow.setDescription( String.format(SSBundle.getBundle().getString("periodicinvoiceframe.invoiceperiod"), strPeriodStart, strPeriodEnd ) );
                iRow.setQuantity   ( null );
                iRow.setUnitprice  ( null );
                iRow.setTaxCode    ( null );

                iInvoice.getRows().add(iRow);
            }

            if(iAppendInformation){
                String iInformationText = iInformation;
                if(iInformationText.contains("[FAK]"))
                    iInformationText = iInformationText.replace("[FAK]", "" + (i+1));
                if(iInformationText.contains("[TOT]"))
                    iInformationText = iInformationText.replace("[TOT]", "" + iCount);

                SSSaleRow iRow = new SSSaleRow();
                iRow.setDescription( iInformationText );
                iRow.setQuantity   ( null );
                iRow.setUnitprice  ( null );
                iRow.setTaxCode    ( null );
                iInvoice.getRows().add(iRow);
            }

            iDate        = SSDateMath.addMonths(iDate       , iPeriod);
            iPeriodStart = SSDateMath.addMonths(iPeriodStart, iPeriod);
            iPeriodEnd   = SSDateMath.addMonths(iPeriodStart, iPeriod);

            Calendar iCal = Calendar.getInstance();
            iCal.setTime(iPeriodEnd);
            iCal.add(Calendar.DATE, -1);
            iPeriodEnd = iCal.getTime();
            iInvoices.add(iInvoice);
        }

        if(iAdded == null || iAdded.size() != iInvoices.size()){
            iAdded = new HashMap<Integer, Boolean>();
            for (SSInvoice iInvoice : iInvoices) {
                Integer iNumber = iInvoice.getNumber();

                iAdded .put(iNumber,  false);
            }
        }
    }
    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare.
     *
     * @return <code>true</code> if this object is the same as the obj
     *         argument; <code>false</code> otherwise.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof SSPeriodicInvoice)) {
            return false;
        }
        return iNumber.equals(((SSPeriodicInvoice)obj).getNumber());
    }
}
