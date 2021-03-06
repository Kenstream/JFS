package se.swedsoft.bookkeeping.gui.util.filechooser.util;

import se.swedsoft.bookkeeping.gui.util.SSBundle;

import javax.swing.filechooser.FileFilter;
import java.util.ResourceBundle;
import java.io.File;

/**
 * Date: 2006-feb-13
 * Time: 14:48:14
 */
public class SSFilterXML extends SSFileFilter {

    public static ResourceBundle bundle = SSBundle.getBundle();

    public SSFilterXML(){
        super();
        addExtention("xml");
    }
    /**
     * @return The description of this filter
     */
    public String getDescription() {
        return bundle.getString("filechooser.xml.filter");
    }

}
