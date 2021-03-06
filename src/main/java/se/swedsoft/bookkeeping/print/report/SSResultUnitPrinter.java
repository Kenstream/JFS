package se.swedsoft.bookkeeping.print.report;

import se.swedsoft.bookkeeping.data.system.SSDB;
import se.swedsoft.bookkeeping.data.SSNewResultUnit;
import se.swedsoft.bookkeeping.gui.util.SSBundle;
import se.swedsoft.bookkeeping.gui.util.model.SSDefaultTableModel;
import se.swedsoft.bookkeeping.print.SSPrinter;

import java.util.List;

/**
 * Date: 2006-mar-01
 * Time: 10:50:45
 */
public class SSResultUnitPrinter extends SSPrinter {


    List<SSNewResultUnit> iResultUnits;

    /**
     *
     */
    public SSResultUnitPrinter(  ){
        this( SSDB.getInstance().getResultUnits()  );
    }

    /**
     *
     * @param pResultUnits The accountplan
     */
    public SSResultUnitPrinter(List<SSNewResultUnit> pResultUnits ){
        super();
        iResultUnits = pResultUnits;

        setPageHeader  ("header.jrxml");
        setColumnHeader("resultunits.jrxml");
        setDetail      ("resultunits.jrxml");
    }



    /**
     * Gets the title file for this repport
     *
     * @return
     */
    public String getTitle() {
        return SSBundle.getBundle().getString("resultunitreport.title");
    }


    /**
     * @return SSDefaultTableModel
     */
    protected SSDefaultTableModel getModel() {

        SSDefaultTableModel<SSNewResultUnit> iModel = new SSDefaultTableModel<SSNewResultUnit>() {

            public Class getType() {
                return SSNewResultUnit.class;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                SSNewResultUnit iResultUnit = getObject(rowIndex);
                Object value = null;

                switch (columnIndex) {
                    case 0:
                        value = iResultUnit.getNumber();
                        break;
                    case 1:
                        value = iResultUnit.getName();
                        break;
                    case 2:
                        value = iResultUnit.getDescription();
                        break;
                }

                return value;
            }
        };
        iModel.addColumn("resultunit.number");
        iModel.addColumn("resultunit.name");
        iModel.addColumn("resultunit.description");
        iModel.setObjects( iResultUnits );

        return iModel;
    }


}
