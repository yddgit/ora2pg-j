package net.darold.ora2pg.action.implement;

import org.apache.log4j.Logger;

import net.darold.ora2pg.action.BaseAction;
import net.darold.ora2pg.config.Config;

/**
 * Option: --version<br>
 * 
 * Show Ora2Pg Version and exit.
 * 
 * @author yang.dongdong
 *
 */
public class ShowVerAction extends BaseAction {

	private static final Logger logger = Logger.getLogger(ShowVerAction.class);

	@Override
	public boolean process(Config config) {
		logger.info("Show version: Ora2Pg v" + Config.VERSION);
		System.out.println("Ora2Pg v" + Config.VERSION);
		return true;
	}

}
