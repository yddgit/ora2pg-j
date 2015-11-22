package net.darold.ora2pg;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import net.darold.ora2pg.action.Ora2PgAction;
import net.darold.ora2pg.action.implement.CreateProjectAction;
import net.darold.ora2pg.action.implement.FileConfAction;
import net.darold.ora2pg.action.implement.HelpAction;
import net.darold.ora2pg.action.implement.MySQLAction;
import net.darold.ora2pg.action.implement.ShowVerAction;
import net.darold.ora2pg.action.implement.TempDirAction;
import net.darold.ora2pg.config.Config;
import net.darold.ora2pg.config.ConfigManager;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {

	logger.info("Application start.");

	Config config;
	try {
	    config = ConfigManager.parse(args);
	} catch (Exception e) {
	    logger.error("Parse the args error", e);
	    return;
	}
	Ora2PgAction action = null;
	boolean success = false;

	// --version
	if (config.isShowVer()) {
	    action = new ShowVerAction();
	    success = action.process(config);
	    return;
	}

	// --help
	if (config.isHelp()) {
	    action = new HelpAction();
	    success = action.process(config);
	    return;
	}

	// --mysql
	if (config.isMysql()) {
	    action = new MySQLAction();
	    success = action.process(config);
	    if (!success) {
		return;
	    }
	}

	// --init_project
	if (StringUtils.isNotEmpty(config.getCreateProject())) {
	    action = new CreateProjectAction();
	    success = action.process(config);
	    return;
	}

	// --temp_dir
	if (StringUtils.isNotEmpty(config.getTmpDir())) {
	    action = new TempDirAction();
	    success = action.process(config);
	    if (!success) {
		action.usage();
		return;
	    }
	}

	// --conf
	if ((StringUtils.isNotEmpty(config.getFileConf()))) {
	    action = new FileConfAction();
	    success = action.process(config);
	    if (!success) {
		action.usage();
		return;
	    }
	}
    }
}
