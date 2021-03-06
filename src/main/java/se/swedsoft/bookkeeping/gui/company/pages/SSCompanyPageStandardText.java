package se.swedsoft.bookkeeping.gui.company.pages;

import se.swedsoft.bookkeeping.gui.util.SSBundle;
import se.swedsoft.bookkeeping.gui.company.panel.SSStandardTextPanel;
import se.swedsoft.bookkeeping.data.SSNewCompany;

import javax.swing.*;

/**
 * User: Andreas Lago
 * Date: 2006-aug-25
 * Time: 10:14:40
 */
public class SSCompanyPageStandardText extends SSCompanyPage{

    private SSNewCompany iCompany;

    private JPanel iPanel;

    private SSStandardTextPanel iStandardTextPanel;

    /**
     * @param iDialog
     */
    public SSCompanyPageStandardText(JDialog iDialog) {
        super(iDialog);
    }

    /**
     *
     * @return the name and title
     */
    public String getName() {
        return SSBundle.getBundle().getString("companyframe.pages.standardtexts");
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

        iStandardTextPanel.setData  ( iCompany.getStandardTexts() );
    }

    /**
     * Get the edited company
     *
     * @return the company
     */
    public SSNewCompany getCompany() {
        iStandardTextPanel.getData( iCompany.getStandardTexts() );

        return iCompany;
    }

}
