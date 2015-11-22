package net.darold.ora2pg.action.implement;

import net.darold.ora2pg.action.BaseAction;
import net.darold.ora2pg.config.Config;
import net.darold.ora2pg.config.ConfigManager;

/**
 * Option: --help<br>
 * 
 * Print this short help.
 * 
 * @author yang.dongdong
 *
 */
public class HelpAction extends BaseAction {

    @Override
    public boolean process(Config config) {
	ConfigManager.help();
	return true;
    }

}
