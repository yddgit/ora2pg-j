package net.darold.ora2pg.action.implement;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import net.darold.ora2pg.action.BaseAction;
import net.darold.ora2pg.config.Config;

public class FileConfAction extends BaseAction {

	private static final Logger logger = Logger.getLogger(FileConfAction.class);

	@Override
	public boolean process(Config config) {
		String fileConf = config.getFileConf();

		logger.info("Check the config file: " + fileConf);

		if (StringUtils.isEmpty(fileConf)) {
			logger.error("Config file option is null!");
			return false;
		}

		File conf = new File(fileConf);

		if (!conf.exists()) {
			logger.error("Can't find configuration file: " + fileConf);
			return false;
		}

		if (!conf.canRead()) {
			logger.error("Can't read configuration file: " + fileConf);
			return false;
		}

		return true;
	}

}
