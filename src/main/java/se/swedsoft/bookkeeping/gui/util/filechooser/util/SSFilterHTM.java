package se.swedsoft.bookkeeping.gui.util.filechooser.util;

import se.swedsoft.bookkeeping.gui.util.SSBundle;

import javax.swing.filechooser.FileFilter;
import java.util.ResourceBundle;
import java.io.File;

/**
 * Date: 2006-feb-13
 * Time: 14:48:14
 */
public class SSFilterHTM extends SSFileFilter {

    public static ResourceBundle bundle = SSBundle.getBundle();

    public SSFilterHTM(){
        super();
        addExtention("htm");
        addExtention("html");
    }
    /**
     * @return The description of this filter
     */
    public String getDescription() {
        return bundle.getString("filechooser.html.filter");
    }

}
