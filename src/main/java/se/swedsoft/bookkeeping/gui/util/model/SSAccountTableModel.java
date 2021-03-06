package se.swedsoft.bookkeeping.gui.util.model;

import se.swedsoft.bookkeeping.data.SSAccount;
import se.swedsoft.bookkeeping.data.SSNewAccountingYear;
import se.swedsoft.bookkeeping.data.system.SSDB;
import se.swedsoft.bookkeeping.gui.util.SSBundle;
import se.swedsoft.bookkeeping.gui.util.table.model.SSTableModel;
import se.swedsoft.bookkeeping.gui.util.table.model.SSTableColumn;
import se.swedsoft.bookkeeping.SSBookkeeping;

import java.util.List;

/**
 * User: Andreas Lago
 * Date: 2006-mar-21
 * Time: 11:59:22
 */
public class SSAccountTableModel extends SSTableModel<SSAccount> {


    /**
     * Default constructor.
     */
    public SSAccountTableModel() {
        super(SSDB.getInstance().getAccounts());
    }

    /**
     * Default constructor.
     */
    public SSAccountTableModel(List<SSAccount> iAccounts) {
        super(iAccounts);
    }

    /**
     * Returns the type of data in this model.
     *
     * @return The current data type.
     */
    public Class getType() {
        return SSAccount.class;
    }


    /**
     *
     * @return
     */
    public static SSAccountTableModel getDropDownModel(){
        SSAccountTableModel iModel;
        SSNewAccountingYear iAccountingYear = SSDB.getInstance().getCurrentYear();
        if(iAccountingYear == null) {
            iModel = new SSAccountTableModel();
        }else{
            List<SSAccount> iAccounts = SSDB.getInstance().getCurrentYear().getActiveAccounts();
            if (iAccounts == null) {
                iModel = new SSAccountTableModel();
            } else {
                iModel = new SSAccountTableModel(iAccounts);
            }
        }

        iModel.addColumn( SSAccountTableModel.COLUMN_NUMBER );
        iModel.addColumn( SSAccountTableModel.COLUMN_DESCRIPTION   );

        return iModel;
    }

    public static SSAccountTableModel getDropDownModel(List<SSAccount> iAccounts){
        SSAccountTableModel iModel = new SSAccountTableModel(iAccounts);
            
        iModel.addColumn( SSAccountTableModel.COLUMN_NUMBER );
        iModel.addColumn( SSAccountTableModel.COLUMN_DESCRIPTION   );

        return iModel;
    }





    /**
     *  Nummer
     */
    public static SSTableColumn<SSAccount> COLUMN_NUMBER = new SSTableColumn<SSAccount>(SSBundle.getBundle().getString("accounttable.column.1")) {
        public Object getValue(SSAccount iAccount) {
            return iAccount.getNumber();
        }

        public void setValue(SSAccount iAccount, Object iValue) {
            iAccount.setNumber((Integer)iValue);
        }

        public Class getColumnClass() {
            return Integer.class;
        }

        public int getDefaultWidth() {
            return 60;
        }
    };


    /**
     *  beskrivning
     */
    public static SSTableColumn<SSAccount> COLUMN_DESCRIPTION = new SSTableColumn<SSAccount>(SSBundle.getBundle().getString("accounttable.column.2")) {
        public Object getValue(SSAccount iAccount) {
            return iAccount.getDescription();
        }

        public void setValue(SSAccount iAccount, Object iValue) {
            iAccount.setDescription((String)iValue);
        }

        public Class getColumnClass() {
            return String.class;
        }

        public int getDefaultWidth() {
            return 300;
        }
    };

    /**
     *  Aktivt
     */
    public static SSTableColumn<SSAccount> COLUMN_ACTIVE = new SSTableColumn<SSAccount>(SSBundle.getBundle().getString("accounttable.column.3")) {
        public Object getValue(SSAccount iAccount) {
            return iAccount.isActive();
        }

        public void setValue(SSAccount iAccount, Object iValue) {
            iAccount.setActive((Boolean)iValue);
        }

        public Class getColumnClass() {
            return Boolean.class;
        }

        public int getDefaultWidth() {
            return 16;
        }
    };

       /**
     *  Momskod
     */
    public static SSTableColumn<SSAccount> COLUMN_VATCODE = new SSTableColumn<SSAccount>(SSBundle.getBundle().getString("accounttable.column.4")) {
        public Object getValue(SSAccount iAccount) {
            return iAccount.getVATCode();
        }

        public void setValue(SSAccount iAccount, Object iValue) {
            iAccount.setVATCode((String)iValue);
        }

        public Class getColumnClass() {
            return String.class;
        }

        public int getDefaultWidth() {
            return 80;
        }
    };

       /**
     *  SRU
     */
    public static SSTableColumn<SSAccount> COLUMN_SRUCODE = new SSTableColumn<SSAccount>(SSBundle.getBundle().getString("accounttable.column.5")) {
        public Object getValue(SSAccount iAccount) {
            return iAccount.getSRUCode();
        }

        public void setValue(SSAccount iAccount, Object iValue) {
            iAccount.setSRUCode((String)iValue);
        }

        public Class getColumnClass() {
            return Boolean.class;
        }

        public int getDefaultWidth() {
            return 80;
        }
    };

       /**
     *  Rapportkod
     */
    public static SSTableColumn<SSAccount> COLUMN_REPORTCODE = new SSTableColumn<SSAccount>(SSBundle.getBundle().getString("accounttable.column.6")) {
        public Object getValue(SSAccount iAccount) {
            return iAccount.getReportCode();
        }

        public void setValue(SSAccount iAccount, Object iValue) {
            iAccount.setReportCode((String)iValue);
        }

        public Class getColumnClass() {
            return String.class;
        }

        public int getDefaultWidth() {
            return 80;
        }
    };






}

