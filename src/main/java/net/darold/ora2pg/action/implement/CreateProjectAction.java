package net.darold.ora2pg.action.implement;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import net.darold.ora2pg.action.BaseAction;
import net.darold.ora2pg.config.Config;
import net.darold.ora2pg.config.Config.SchemaType;

/**
 * Option: --init_project<br>
 * 
 * Initialize a typical ora2pg project tree. Top directory will be created under
 * project base directory.
 * 
 * @author yang.dongdong
 *
 */
public class CreateProjectAction extends BaseAction {

	private static final Logger logger = Logger
			.getLogger(CreateProjectAction.class);

	@Override
	public boolean process(Config config) {
		String projectBase = config.getCreateProject();

		if (StringUtils.isEmpty(projectBase)) {
			logger.error("Create ora2pg project option is null!");
			return false;
		}

		File baseDir = new File(projectBase);

		if (baseDir.exists()) {
			logger.error("Project base directory exists.");
			return false;
		}

		try {
			FileUtils.forceMkdir(baseDir);
		} catch (IOException e) {
			logger.error("Create project base directory failed.", e);
			return false;
		}

		logger.info("Creating project " + projectBase + ".");

		try {
			// create schema directory
			for (SchemaType s : config.getSchemaArray()) {
				FileUtils.forceMkdir(new File(projectBase + File.separator
						+ "schema" + File.separator + s.name().toLowerCase()));
			}
			// create source directory
			for (SchemaType s : config.getSourcesArray()) {
				FileUtils.forceMkdir(new File(projectBase + File.separator
						+ "source" + File.separator + s.name().toLowerCase()));
			}
			// create data directory
			FileUtils
					.forceMkdir(new File(projectBase + File.separator + "data"));
			// create config directory
			FileUtils.forceMkdir(new File(projectBase + File.separator
					+ "config"));
			// create report directory
			FileUtils.forceMkdir(new File(projectBase + File.separator
					+ "report"));
		} catch (Exception e) {
			logger.error("Create project " + projectBase + " error.", e);
			return false;
		}

		logger.info("Generate config file. [!!!NOT IMPLEMENT!!!]");

		// TODO generate a config file

		return true;
	}
}
