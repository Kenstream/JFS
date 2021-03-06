package se.swedsoft.bookkeeping.gui.util.filechooser.util;

import se.swedsoft.bookkeeping.gui.util.SSBundle;

import javax.swing.filechooser.FileFilter;
import java.util.ResourceBundle;
import java.io.File;

/**
 * Date: 2006-feb-13
 * Time: 14:48:14
 */
public class SSFilterALL extends FileFilter {

    public static ResourceBundle bundle = SSBundle.getBundle();

    /**
     * 
     */
    public SSFilterALL(){
        super();

    }

    /**
     * Whether the given file is accepted by this filter.
     */
    public boolean accept(File f) {
        return true;    }

    /**
     * @return The description of this filter
     */
    public String getDescription() {
        return SSFilterALL.bundle.getString("filechooser.all.filter");
    }

}
