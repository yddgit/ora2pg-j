package net.darold.ora2pg.action.implement;

import org.apache.log4j.Logger;

import net.darold.ora2pg.action.BaseAction;
import net.darold.ora2pg.config.Config;
import net.darold.ora2pg.config.Config.SchemaType;

/**
 * Option: --mysql<br>
 * 
 * Export a MySQL database instead of an Oracle schema.
 * 
 * @author yang.dongdong
 *
 */
public class MySQLAction extends BaseAction {

	private static final Logger logger = Logger.getLogger(MySQLAction.class);

	@Override
	public boolean process(Config config) {
		logger.info("Source database is mysql.");
		config.setSchemaArray(config.getMysqlSchemaArray());
		config.setSourcesArray(config.getMysqlSourcesArray());
		config.setExternalArray(new SchemaType[] {});
		return true;
	}

}
