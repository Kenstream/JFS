package se.swedsoft.bookkeeping.gui.supplierinvoice.panel;

import se.swedsoft.bookkeeping.gui.util.components.SSTableComboBox;
import se.swedsoft.bookkeeping.gui.supplierinvoice.SSSupplierInvoiceFrame;
import se.swedsoft.bookkeeping.gui.supplierinvoice.util.SSSupplierInvoiceTableModel;
import se.swedsoft.bookkeeping.data.SSSupplierInvoice;
import se.swedsoft.bookkeeping.data.SSSupplierInvoice;
import se.swedsoft.bookkeeping.data.system.SSDB;
import se.swedsoft.bookkeeping.SSBookkeeping;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * User: Andreas Lago
 * Date: 2006-nov-15
 * Time: 14:57:36
 */
public class SSSupplierInvoiceSearchPanel extends JPanel {
    private JPanel iPanel;

    private JTextField iTextField;

    private SSSupplierInvoiceTableModel iModel;


    public SSSupplierInvoiceSearchPanel(SSSupplierInvoiceTableModel iModel) {
        this.iModel = iModel;

        setLayout(new BorderLayout());
        setVisible(true);
        add(iPanel, BorderLayout.CENTER);


        iTextField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                ApplyFilter(SSDB.getInstance().getSupplierInvoices());
            }
        });
    }


    public void ApplyFilter(List<SSSupplierInvoice> iList) {
        List<SSSupplierInvoice> iFiltered = new LinkedList<SSSupplierInvoice>();

        String iText = iTextField.getText();

        if(iText == null) iText = "";

        iText = iText.toLowerCase();

        for (SSSupplierInvoice iSupplierInvoice : iList) {
            String iNumber      = iSupplierInvoice.getNumber().toString();
            String iDescription = iSupplierInvoice.getSupplierName();
            String iSupplierNumber = iSupplierInvoice.getSupplierNr();

            if( (iText.length() == 0) || (iNumber != null && iNumber.toLowerCase().startsWith(iText)) || (iDescription != null && iDescription.toLowerCase().startsWith(iText) ) || (iSupplierNumber != null && iSupplierNumber.toLowerCase().startsWith(iText) ) ){
                iFiltered.add(iSupplierInvoice);
            }
        }
        SSSupplierInvoiceFrame.getInstance().setFilterIndex(SSSupplierInvoiceFrame.getInstance().getTabbedPane().getSelectedIndex(),iFiltered);
        //iModel.setObjects(iFiltered);

    }
}
