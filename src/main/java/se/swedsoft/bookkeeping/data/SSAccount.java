/*
 * @(#)SSAccount.java                v 1.0 2005-jul-06
 *
 * Time-stamp: <2005-jul-06 09:16:14 Hasse>
 *
 * Copyright (c) Trade Extensions TradeExt AB, Sweden.
 * www.tradeextensions.com, info@tradeextensions.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Trade Extensions ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Trade Extensions.
 */
package se.swedsoft.bookkeeping.data;

import se.swedsoft.bookkeeping.gui.util.table.SSTableSearchable;

import java.io.Serializable;


/**
 * Accounts
 */
public class SSAccount implements Serializable, Cloneable, SSTableSearchable {

    /// Constant for serialization versioning.
    static final long serialVersionUID = 1L;

    private int iNumber;

    private String iDescription;

    private String iSRUCode;

    private String iVATCode;

    private String iReportCode;

    private boolean iActive;

    private boolean iProjectRequired;

    private boolean iResultUnitRequired;


    /**
     * Default constructor.
     */
    public SSAccount() {
        iNumber = -1;
        iActive = true;
        iProjectRequired = false;
        iResultUnitRequired = false;
    }


    /**
     * @param iNumber
     */
    public SSAccount(int iNumber) {
        this.iNumber = iNumber;
        this.iActive = true;
        iProjectRequired = false;
        iResultUnitRequired = false;
    }

    /**
     * Copy contructor.
     *
     * @param pAccount The account to copy.
     */
    public SSAccount(SSAccount pAccount) {
        copyFrom(pAccount);
    }

    ////////////////////////////////////////////////////////////////////////


    /**
     *
     * @param pAccount
     */
    public void copyFrom(SSAccount pAccount) {
        iNumber             = pAccount.iNumber;
        iDescription        = pAccount.iDescription;
        iSRUCode            = pAccount.iSRUCode;
        iVATCode            = pAccount.iVATCode;
        iReportCode         = pAccount.iReportCode;
        iActive             = pAccount.iActive;
        iProjectRequired    = pAccount.iProjectRequired;
        iResultUnitRequired = pAccount.iResultUnitRequired;
    }


    ////////////////////////////////////////////////////////////////////////

    /**
     *
     * @return
     */
    public Integer getNumber() {
        return iNumber < 1 ? null : iNumber;
    }

    /**
     *
     * @param iNumber
     */
    public void setNumber(Integer iNumber) {
        this.iNumber =  iNumber == null ? -1 : iNumber;
    }

    ////////////////////////////////////////////////////////////////////////

    /**
     *
     * @return
     */
    public String getDescription() {
        return iDescription;
    }

    /**
     *
     * @param pDescription
     */
    public void setDescription(String pDescription) {
        iDescription = pDescription;
    }

    ////////////////////////////////////////////////////////////////////////

    /**
     *
     * @return
     */
    public String getSRUCode() {
        return iSRUCode;
    }

    /**
     *
     * @param pSRUCode
     */
    public void setSRUCode(String pSRUCode) {
        iSRUCode = pSRUCode;
    }

    ////////////////////////////////////////////////////////////////////////


    /**
     *
     * @return
     */
    public String getVATCode() {
        return iVATCode;
    }

    /**
     *
     * @param pVATCode
     */
    public void setVATCode(String pVATCode) {
        iVATCode = pVATCode;
    }

    ////////////////////////////////////////////////////////////////////////


    /**
     *
     * @return
     */
    public String getReportCode() {
        return iReportCode;
    }

    /**
     *
     * @param pReportCode
     */
    public void setReportCode(String pReportCode) {
        iReportCode = pReportCode;
    }

    ////////////////////////////////////////////////////////////////////////

    /**
     *
     * @return
     */
    public Boolean isActive() {
        return iActive;
    }

    /**
     *
     * @param pActive
     */
    public void setActive(Boolean pActive) {
        iActive = pActive != null && pActive;
    }

    ////////////////////////////////////////////////////////////////////////

    public boolean isProjectRequired() {
        return iProjectRequired;
    }

    public void setProjectRequired(boolean iProjectRequired) {
        this.iProjectRequired = iProjectRequired;
    }

    public boolean isResultUnitRequired() {
        return iResultUnitRequired;
    }

    public void setResultUnitRequired(boolean iResultUnitRequired) {
        this.iResultUnitRequired = iResultUnitRequired;
    }


    /**
     * Returns a string representation of the object. In general, the
     * <code>toString</code> method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     *
     * @return a string representation of the object.
     */
    public String toString() {
        return iNumber + " - " + iDescription;
    }

    /**
     * Returns the render string to be shown in the tables
     *
     * @return The searchable string
     */
    public String toRenderString() {
        return "" + iNumber;
    }



    /**
     * Indicates whether some other object is "equal to" this one.

     * @param obj the reference object with which to compare.
     * @return <code>true</code> if this object is the same as the obj
     *         argument; <code>false</code> otherwise.
     * @see #hashCode()
     * @see java.util.Hashtable
     */
    public boolean equals(Object obj) {
        if(obj instanceof SSAccount ){
            SSAccount iAccount = (SSAccount) obj;

            return iNumber == iAccount.iNumber;

        }
        return false;
    }

    /**
     * We need to override this as account is saved as instances for the various classes like SSVoucherRow
     *
     * This enshures that the same account from different years will be treated as the same account over
     * different years. The account number shall qualify as a good enought hash key anyway.
     *
     * @return the hashcode
     */
    public int hashCode() {
        //if(iNumber != null){
            return iNumber;
       // } else {
        //    return super.hashCode();
       // }

    }



}
