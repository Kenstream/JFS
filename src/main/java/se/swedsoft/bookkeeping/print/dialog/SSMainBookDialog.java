package se.swedsoft.bookkeeping.print.dialog;

import javax.swing.*;

import se.swedsoft.bookkeeping.gui.util.datechooser.SSDateChooser;
import se.swedsoft.bookkeeping.gui.util.components.SSTableComboBox;
import se.swedsoft.bookkeeping.gui.util.SSButtonPanel;
import se.swedsoft.bookkeeping.gui.util.SSBundle;
import se.swedsoft.bookkeeping.gui.util.dialogs.SSDialog;
import se.swedsoft.bookkeeping.gui.util.model.SSAccountTableModel;
import se.swedsoft.bookkeeping.gui.SSMainFrame;
import se.swedsoft.bookkeeping.gui.resultunit.util.SSResultUnitTableModel;
import se.swedsoft.bookkeeping.gui.project.util.SSProjectTableModel;
import se.swedsoft.bookkeeping.data.system.SSDB;
import se.swedsoft.bookkeeping.data.SSAccount;
import se.swedsoft.bookkeeping.data.SSNewProject;
import se.swedsoft.bookkeeping.data.SSNewResultUnit;
import se.swedsoft.bookkeeping.data.SSVoucher;
import se.swedsoft.bookkeeping.calc.math.SSAccountMath;
import se.swedsoft.bookkeeping.calc.util.SSFilterFactory;
import se.swedsoft.bookkeeping.calc.util.SSFilter;

import java.util.Date;
import java.util.List;
import java.util.LinkedList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Date: 2006-feb-07
 * Time: 16:14:10
 */
public class SSMainBookDialog extends SSDialog implements ActionListener {

    private JPanel iPanel;

    private SSDateChooser iFromDate;

    private SSDateChooser iToDate;

    private SSTableComboBox<SSAccount> iToAccount;

    private SSTableComboBox<SSAccount> iFromAccount;

    private SSButtonPanel iButtonPanel;

    private JCheckBox iCheckProject;
    private JCheckBox iCheckResultunit;

    private SSTableComboBox<SSNewProject> iProject;
    private SSTableComboBox<SSNewResultUnit> iResultunit;

    /**
     *
     */
    public SSMainBookDialog(SSMainFrame iMainFrame){
        super(iMainFrame, SSBundle.getBundle().getString("mainbookreport.dialog.title") );

        setPanel(iPanel);

        iProject   .setModel( SSProjectTableModel   .getDropDownModel() );
        iResultunit.setModel( SSResultUnitTableModel.getDropDownModel() );

        iFromAccount.setModel( SSAccountTableModel.getDropDownModel());
        iFromAccount.setSearchColumns(0);

        iToAccount.setModel( SSAccountTableModel.getDropDownModel());
        iToAccount.setSearchColumns(0);

        iFromAccount.setSelected( SSAccountMath.getFirstAccount( SSDB.getInstance().getAccounts() ));
        iToAccount  .setSelected( SSAccountMath.getLastAccount ( SSDB.getInstance().getAccounts() ));



        iCheckProject   .addActionListener(this);
        iCheckResultunit.addActionListener(this);

        actionPerformed(null);
    }



    /**
     *
     * @param pDateFrom
     */
    public void setDateFrom(Date pDateFrom){
        iFromDate.setDate(pDateFrom);
    }

    /**
     *
     * @param pDateTo
     */
    public void setDateTo(Date pDateTo){
        iToDate.setDate(pDateTo);
    }



    /**
     *
     * @return
     */
    public SSAccount getAccountFrom(){
        return iFromAccount.getSelected();
    }

    /**
     *
     * @return
     */
    public SSAccount getAccountTo(){

        return iToAccount.getSelected();
    }

    /**
     *
     * @return
     */
    public Date getDateFrom(){
        return iFromDate.getDate();
    }

    /**
     *
     * @return
     */
    public Date getDateTo(){
        return iToDate.getDate();
    }

    /**
     *
     * @return
     */
    public SSNewProject getProject() {
        if( iCheckProject.isSelected()){
            return iProject.getSelected();
        } else {
            return null;
        }
    }

    /**
     *
     * @return
     */
    public SSNewResultUnit getResultUnit() {
        if( iCheckResultunit.isSelected()){
            return iResultunit.getSelected();
        } else {
            return null;
        }
    }


    /**
     *
     * @return
     */
    public boolean isProjectSelected() {
        return iCheckProject.isSelected();
    }

    /**
     *
     * @return
     */
    public boolean isResultUnitSelected() {
        return iCheckResultunit.isSelected();
    }


    /**
     *
     * @param l
     */
    public void addOkActionListener(ActionListener l) {
        iButtonPanel.addOkActionListener(l);
    }

    /**
     *
     * @param l
     */
    public void addCancelActionListener(ActionListener l) {
        iButtonPanel.addCancelActionListener(l);
    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
        iProject   .setEnabled( iCheckProject   .isSelected() );
        iResultunit.setEnabled( iCheckResultunit.isSelected() );
    }
}


