package se.swedsoft.bookkeeping.gui.company.pages;

import se.swedsoft.bookkeeping.gui.util.SSBundle;
import se.swedsoft.bookkeeping.gui.company.panel.SSDefaultAccountPanel;
import se.swedsoft.bookkeeping.data.SSNewCompany;

import javax.swing.*;

/**
 * User: Andreas Lago
 * Date: 2006-aug-25
 * Time: 10:14:40
 */
public class SSCompanyPageDefaultAccount extends SSCompanyPage{

    private SSNewCompany iCompany;

    private JPanel iPanel;

    private SSDefaultAccountPanel iDefaultAccountPanel;

    /**
     * @param iDialog
     */
    public SSCompanyPageDefaultAccount(JDialog iDialog) {
        super(iDialog);
    }

    /**
     *
     * @return the name and title
     */
    public String getName() {
        return SSBundle.getBundle().getString("companyframe.pages.defaultaccounts");
    }

    /**
     *
     * @return the panel
     */
    public JPanel getPanel() {
        return iPanel;
    }

    /**
     * Set the company to edit
     *
     * @param iCompany
     */
    public void setCompany(SSNewCompany iCompany) {
        this.iCompany = iCompany;

        iDefaultAccountPanel.setDefaultAccounts( iCompany.getDefaultAccounts() );
    }

    /**
     * Get the edited company
     *
     * @return the company
     */
    public SSNewCompany getCompany() {
        iCompany.setDefaultAccounts( iDefaultAccountPanel.getDefaultAccounts() );

        return iCompany;
    }

}
