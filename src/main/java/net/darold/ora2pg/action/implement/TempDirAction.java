package net.darold.ora2pg.action.implement;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import net.darold.ora2pg.action.BaseAction;
import net.darold.ora2pg.config.Config;

/**
 * Option: --temp_dir<br>
 * 
 * Use it to set a distinct temporary directory when two or more ora2pg are run
 * in parallel.
 * 
 * @author yang.dongdong
 *
 */
public class TempDirAction extends BaseAction {

	private static final Logger logger = Logger.getLogger(TempDirAction.class);

	@Override
	public boolean process(Config config) {

		String tmpDir = config.getTmpDir();

		logger.info("Check the temporary directory: " + tmpDir);

		if (StringUtils.isEmpty(tmpDir)) {
			logger.error("Temporary directory option is null!");
			return false;
		}

		File tmp = new File(tmpDir);

		if (!tmp.exists()) {
			logger.error("Temporary directory " + tmpDir + " does not exist.");
			return false;
		}

		if (!tmp.canWrite()) {
			logger.error("Can't open directory " + tmpDir + ".");
			return false;
		}

		File[] tmpFiles = tmp.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				// Start with 'tmp_ora2pg' is ora2pg temporary file
				if (name.startsWith("tmp_ora2pg")) {
					return true;
				}
				return false;
			}
		});

		// Delete the temporary files
		for (File tmpFile : tmpFiles) {
			try {
				logger.debug("Remove old temporary file: "
						+ tmpFile.getAbsolutePath());
				FileUtils.forceDeleteOnExit(tmpFile);
			} catch (IOException e) {
				logger.error(
						"Can't remvoe old temporary file: "
								+ tmpFile.getAbsolutePath(), e);
				return false;
			}
		}

		return true;
	}
}
