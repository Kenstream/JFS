package se.swedsoft.bookkeeping.gui.util.table.editors;

import se.swedsoft.bookkeeping.gui.util.components.SSTableComboBoxOld;
import se.swedsoft.bookkeeping.gui.util.components.SSTableComboBox;
import se.swedsoft.bookkeeping.gui.util.model.SSTaxCodeTableModel;
import se.swedsoft.bookkeeping.data.common.SSTaxCode;
import se.swedsoft.bookkeeping.data.SSNewCompany;
import se.swedsoft.bookkeeping.data.system.SSDB;

import javax.swing.table.DefaultTableCellRenderer;
import java.math.BigDecimal;
import java.util.Map;
import java.util.HashMap;

/**
 * User: Andreas Lago
 * Date: 2006-mar-27
 * Time: 11:37:41
 */
public class SSTaxCodeCellEditor extends SSTableComboBox.CellEditor<SSTaxCode> {

    private Map<SSTaxCode, BigDecimal> iValues;

    /**
     *
     */
    public SSTaxCodeCellEditor() {
        super();

        setModel( SSTaxCodeTableModel.getDropDownModel() );

        iValues = new HashMap<SSTaxCode, BigDecimal>();
    }


    /**
     *
     * @param iTaxCode
     * @param iValue
     */
    public void setValue(SSTaxCode iTaxCode, BigDecimal iValue){
        iValues.put(iTaxCode, iValue );

        setModel( SSTaxCodeTableModel.getDropDownModel(iValues) );
    }



}
