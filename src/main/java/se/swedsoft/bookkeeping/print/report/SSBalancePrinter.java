package se.swedsoft.bookkeeping.print.report;

import se.swedsoft.bookkeeping.print.SSPrinter;
import se.swedsoft.bookkeeping.data.*;
import se.swedsoft.bookkeeping.data.system.SSDB;
import se.swedsoft.bookkeeping.calc.data.SSAccountSchema;
import se.swedsoft.bookkeeping.gui.util.SSBundle;
import se.swedsoft.bookkeeping.gui.util.model.SSDefaultTableModel;
import se.swedsoft.bookkeeping.calc.SSBalanceCalculator;
import se.swedsoft.bookkeeping.SSBookkeeping;

import java.util.Date;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.math.BigDecimal;


/**
 * Date: 2006-feb-16
 * Time: 16:47:37
 */
public class SSBalancePrinter extends SSPrinter {


    SSNewAccountingYear iYearData;

    SSAccountSchema iAccountSchema;

    Date iDateFrom;

    Date iDateTo;

    /**
     *
     */
    public SSBalancePrinter(Date pFrom, Date pTo ){
        this( SSDB.getInstance().getCurrentYear(), pFrom, pTo  );
    }

    /**
     *
     * @param pYearData The year data
     */
    public SSBalancePrinter(SSNewAccountingYear pYearData, Date pFrom, Date pTo ){
        super();
        iYearData    = pYearData;
        iDateFrom    = pFrom;
        iDateTo      = pTo;

        iAccountSchema = SSAccountSchema.getAccountSchema(pYearData);

        setPageHeader  ("header_period.jrxml");
        setColumnHeader("balance.jrxml");
        setDetail      ("balance.jrxml");
        setSummary     ("balance.jrxml");
    }


    /**
     * Gets the title file for this repport
     *
     * @return
     */
    public String getTitle() {
        return SSBundle.getBundle().getString("balancereport.title");
    }




    /**
     * @return SSDefaultTableModel
     */
    protected SSDefaultTableModel getModel() {
        addParameter("dateFrom", iDateFrom );
        addParameter("dateTo"  , iDateTo);


        SSBalanceCalculator iCalculator = new SSBalanceCalculator(iYearData);

        iCalculator.calculate(iDateFrom, iDateTo);

        final Map<SSAccount, BigDecimal> iInBalance   = iCalculator.getInBalance();
        final Map<SSAccount, BigDecimal> iInSaldo     = iCalculator.getInSaldo();
        final Map<SSAccount, BigDecimal> iPeriodChange = iCalculator.getPeriodChange();
        final Map<SSAccount, BigDecimal> iOutSaldo     = iCalculator.getOutSaldo();


        List<se.swedsoft.bookkeeping.calc.data.SSAccountGroup> iBalanceGroups = iAccountSchema.getBalanceGroups();

        List<SSAccount> iAccounts = iYearData.getAccounts();

        List<BalanceRow> iRows = new LinkedList<BalanceRow>();

        for(se.swedsoft.bookkeeping.calc.data.SSAccountGroup iBalanceGroup: iBalanceGroups){
            List<BalanceRow> iCurrentRows = getRows(iBalanceGroup,  iAccounts,  0);
            for(BalanceRow iRow: iCurrentRows){
                SSAccount iAccount = iRow.iAccount;
                // Only add the row if any field from the balance report is not null
                if(iInBalance.containsKey(iAccount) || iInSaldo.containsKey(iAccount) || iPeriodChange.containsKey(iAccount) || iOutSaldo.containsKey(iAccount)){
                    iRows.add( iRow );
                }
            }
        }

        SSDefaultTableModel<BalanceRow> iModel = new SSDefaultTableModel<BalanceRow>() {

            public Class getType() {
                return BalanceRow.class;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                BalanceRow iRow    = getObject(rowIndex);

                SSAccount iAccount = iRow.iAccount;

                Object value = null;

                switch (columnIndex) {
                    case 0:
                        // account.number
                        value = iAccount.getNumber();
                        break;
                    case 1:
                        // account.description
                        value = iAccount.getDescription();
                        break;
                    case 2:
                        // account.inBalance
                        value = iInBalance.get(iAccount);
                        if(value == null) value = new BigDecimal(0.0);
                        break;
                    case 3:
                        // account.inSaldo
                        value = iInSaldo.get(iAccount);
                        if(value == null) value = new BigDecimal(0.0);
                        break;
                    case 4:
                        // account.change
                        value = iPeriodChange.get(iAccount);
                        if(value == null) value = new BigDecimal(0.0);
                        break;
                    case 5:
                        // account.OutBalance
                        value = iOutSaldo.get(iAccount);
                        if(value == null) value = new BigDecimal(0.0);
                        break;
                    case 6:
                        // account.group.1
                        value = iRow.getLevelGroup(0);
                        break;
                    case 7:
                        // account.group.2
                        value = iRow.getLevelGroup(1);
                        break;
                    case 8:
                        // account.group.3
                        value = iRow.getLevelGroup(2);
                        break;
                    case 9:
                        // group.1.title
                        value = iRow.iLevelGroups[0] != null ? iRow.iLevelGroups[0].getTitle() : null;
                        break;
                    case 10:
                        // group.2.title
                        value = iRow.iLevelGroups[1] != null ? iRow.iLevelGroups[1].getTitle() : null;
                        break;
                    case 11:
                        // group.3.title
                        value = iRow.iLevelGroups[2] != null ? iRow.iLevelGroups[2].getTitle() : null;
                        break;
                    case 12:
                        // group.1.sumtitle
                        value = iRow.iLevelGroups[0] != null ? iRow.iLevelGroups[0].getSumTitle() : null;
                        break;
                    case 13:
                        // group.2.sumtitle
                        value = iRow.iLevelGroups[1] != null ? iRow.iLevelGroups[1].getSumTitle() : null;
                        break;
                    case 14:
                        // group.3.sumtitle
                        value = iRow.iLevelGroups[2] != null ? iRow.iLevelGroups[2].getSumTitle() : null;
                        break;
                }

                return value;
            }
        };

        iModel.addColumn("account.number");
        iModel.addColumn("account.description");
        iModel.addColumn("account.inBalance");
        iModel.addColumn("account.inSaldo");
        iModel.addColumn("account.change");
        iModel.addColumn("account.outSaldo");
        iModel.addColumn("account.group.1");
        iModel.addColumn("account.group.2");
        iModel.addColumn("account.group.3");

        iModel.addColumn("group.1.title");
        iModel.addColumn("group.2.title");
        iModel.addColumn("group.3.title");
        iModel.addColumn("group.1.sumtitle");
        iModel.addColumn("group.2.sumtitle");
        iModel.addColumn("group.3.sumtitle");

        iModel.setObjects( iRows );

        return iModel;
    }




    /**
     *
     * @param iGroup
     * @param iAccounts
     * @param iLevel
     * @return
     */
    private List<BalanceRow> getRows(se.swedsoft.bookkeeping.calc.data.SSAccountGroup iGroup, List<SSAccount> iAccounts, int iLevel){
        List<SSAccount> iGroupAccounts = iGroup.getGroupAccounts(iAccounts);

        List<BalanceRow> iRows = new LinkedList<BalanceRow>();

        // This is a leaf node, add this node's groups to the list
        if( iGroup.getGroups() == null ){

            for(SSAccount iAccount: iGroupAccounts){
                BalanceRow iRow = new BalanceRow();

                iRow.iAccount = iAccount;
                iRow.iLevelGroups[iLevel] = iGroup;

                iRows.add(iRow);
            }
        } else {
            for(se.swedsoft.bookkeeping.calc.data.SSAccountGroup iBalanceGroup: iGroup.getGroups() ){
                iRows.addAll( getRows(iBalanceGroup,  iAccounts,  iLevel+1) );
            }
            for(BalanceRow iRow: iRows){
                iRow.iLevelGroups[iLevel] = iGroup;
            }

        }
        return iRows;
    }


    private class BalanceRow{
        SSAccount iAccount;

        se.swedsoft.bookkeeping.calc.data.SSAccountGroup [] iLevelGroups = new se.swedsoft.bookkeeping.calc.data.SSAccountGroup[3];

        public int getLevelGroup(int iLevel){
            if(iLevelGroups[iLevel] == null) return -1;

            return iLevelGroups[iLevel].getId();
        }
    }
}
