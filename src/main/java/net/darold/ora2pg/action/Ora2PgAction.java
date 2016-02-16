package net.darold.ora2pg.action;

import net.darold.ora2pg.config.Config;

/**
 * Common action in ora2pg
 * 
 * @author yang.dongdong
 *
 */
public interface Ora2PgAction {
	/**
	 * Process the action
	 * 
	 * @param config
	 *            User options
	 * 
	 * @return if success return true, otherwise return false
	 */
	public boolean process(Config config);

	/**
	 * Print the usage information
	 */
	public void usage();
}
