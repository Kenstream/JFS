package se.swedsoft.bookkeeping.gui.ownreport.util;

import se.swedsoft.bookkeeping.data.SSAccount;
import se.swedsoft.bookkeeping.data.SSMonth;
import se.swedsoft.bookkeeping.data.SSNewAccountingYear;
import se.swedsoft.bookkeeping.data.system.SSDB;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.io.Serializable;

/**
 * User: Johan Gunnarsson
 * Date: 2007-nov-26
 * Time: 16:36:45
 */


public class SSOwnReportAccountRow implements Serializable {

    static final long serialVersionUID = 1L;
    
    private SSAccount iAccount;

    private Map<SSMonth, BigDecimal> iBudget;

    public SSOwnReportAccountRow(){
        iAccount = new SSAccount();
        iBudget = new HashMap<SSMonth, BigDecimal>();

        SSNewAccountingYear iYear = SSDB.getInstance().getCurrentYear();
        List<SSMonth> iMonths = SSMonth.splitYearIntoMonths(iYear);
        for(SSMonth iMonth : iMonths){
            iBudget.put(iMonth, new BigDecimal(0));
        }
    }

    public SSAccount getAccount() {
        return iAccount;
    }

    public void setAccount(SSAccount iAccount) {
        this.iAccount = iAccount;
    }

    public void setAccount(String iAccountNr){
        Integer iNumber = Integer.parseInt(iAccountNr);
        for(SSAccount pAccount : SSDB.getInstance().getCurrentYear().getAccounts()){
            if(pAccount.getNumber().equals(iNumber)){
                setAccount(pAccount);
            }
        }
    }

    public Map<SSMonth, BigDecimal> getBudget() {
        return iBudget;
    }

    public void setBudget(Map<SSMonth, BigDecimal> iBudget) {
        this.iBudget = iBudget;
    }

    public void setYearBudget(BigDecimal iValue){
        SSNewAccountingYear iYear = SSDB.getInstance().getCurrentYear();
        if(iYear == null) return;

        BigDecimal iMonthly = iValue.divide(new BigDecimal(iBudget.keySet().size()),new MathContext(100));

        for(SSMonth iMonth : iBudget.keySet()){
            if(iBudget.containsKey(iMonth)){
                iBudget.put(iMonth, iMonthly);
            }
        }
    }

    public BigDecimal getSum(){
        BigDecimal iSum = new BigDecimal(0);
        for(SSMonth iMonth : iBudget.keySet()){
            iSum = iSum.add(iBudget.get(iMonth));
        }
        return iSum;
    }

    public BigDecimal getSumForMonths(Date iFrom, Date iTo){
        BigDecimal iSum = new BigDecimal(0);
        List<SSMonth> iMonths = SSMonth.splitYearIntoMonths(iFrom, iTo);
        for(SSMonth iMonth : iMonths){
            if(iBudget.containsKey(iMonth)){
                iSum = iSum.add(iBudget.get(iMonth));
            }
        }
        return iSum;
    }


}
