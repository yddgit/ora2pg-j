package net.darold.ora2pg.action;

import org.apache.log4j.Logger;

import net.darold.ora2pg.config.Config;
import net.darold.ora2pg.config.ConfigManager;

/**
 * Base action in ora2pg<br>
 * 
 * Implement some base operation in actions
 * 
 * @author yang.dongdong
 *
 */
public abstract class BaseAction implements Ora2PgAction {

	private static final Logger logger = Logger.getLogger(BaseAction.class);

	public abstract boolean process(Config config);

	public void usage() {
		ConfigManager.help();
	}

	protected void someBaseOperation() {
		logger.debug("Some base operation.");
	}

}
