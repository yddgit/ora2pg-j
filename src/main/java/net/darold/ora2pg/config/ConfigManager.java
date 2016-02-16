package net.darold.ora2pg.config;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Option.Builder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import net.darold.ora2pg.annotation.Ora2PgOption;

/**
 * Configure Manager
 * 
 * @author yang.dongdong
 *
 */
public class ConfigManager {

	private static final Logger logger = Logger.getLogger(ConfigManager.class);

	/** Contain all options */
	private static final Options OPTIONS = new Options();

	/** Initialize all options */
	static {

		logger.info("Initialize all options.");

		Field[] fields = Config.class.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Ora2PgOption.class)) {
				Ora2PgOption option = field.getAnnotation(Ora2PgOption.class);

				Builder builder = null;
				if (StringUtils.isEmpty(option.opt())) {
					builder = Option.builder();
				} else {
					builder = Option.builder(option.opt());
				}
				builder.longOpt(option.longOpt());

				if (option.hasArg()) {
					builder.hasArg(true).argName(option.argName())
							.optionalArg(option.optionalArg())
							.type(option.type());
				} else {
					builder.hasArg(false);
				}

				OPTIONS.addOption(builder.desc(option.desc()).build());
			}
		}
	}

	/**
	 * Parse the command line arguments
	 * 
	 * @param args
	 *            command line arguments
	 * @return
	 * @throws ParseException
	 */
	public static Config parse(String... args) throws Exception {

		if (args == null || args.length <= 0) {
			logger.error("args is null!");
			return null;
		}

		Config config = Config.getConfig();

		// Create the command line parser.
		CommandLineParser parser = new DefaultParser();

		// Parse the command line arguments.
		logger.info("Get input args: " + Arrays.asList(args));
		CommandLine line = parser.parse(OPTIONS, args);

		// Copy args to config
		copyOptionToConfig(line, config);

		return config;
	}

	/**
	 * Print help information
	 */
	public static void help() {

		logger.debug("Print help information.");

		HelpFormatter formatter = new HelpFormatter();
		String usage = "Usage: ora2pg [-dhpqv --estimate_cost --dump_as_html] [--option value]";
		String header = "";
		String footer = "\nSee full documentation at\n\n"
				+ "   http://ora2pg.darold.net/\n\n"
				+ "for more help or see manpage with 'man ora2pg'.\n\n"
				+ "ora2pg will return 0 on success, 1 on error. "
				+ "It will return 2 when a child process have been "
				+ "interrupted and you've got the warning message: \n\n"
				+ "  \"WARNING: an error occurs during data export. "
				+ "Please check what's happen.\"\n\n"
				+ "Most of the time this is an OOM issue, you might "
				+ "first reduce DATA_LIMIT value.";
		formatter.printHelp(usage, header, OPTIONS, footer);
	}

	/**
	 * Copy the command line options' value to config instance
	 * 
	 * @param options
	 *            Command line options' value
	 * @param config
	 *            Config instance
	 * @throws Exception
	 */
	private static void copyOptionToConfig(CommandLine options, Config config)
			throws Exception {

		logger.info("Copy options to config instance.");

		Field[] fields = Config.class.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Ora2PgOption.class)) {

				Ora2PgOption option = field.getAnnotation(Ora2PgOption.class);
				String longOpt = option.longOpt();

				// ===If option is not set===
				if (!options.hasOption(longOpt)) {
					continue;
				}

				// ===If option is set===
				PropertyDescriptor pd = null;
				try {
					pd = new PropertyDescriptor(field.getName(), Config.class);
				} catch (IntrospectionException e) {
					logger.error("Get PropertyDescriptor error.", e);
					throw e;
				} catch (Exception e) {
					logger.error("Get PropertyDescriptor error.", e);
					throw e;
				}

				// Get original option value
				Method get = pd.getReadMethod();
				Object originalValue = null;
				try {
					originalValue = get.invoke(config);
				} catch (Exception e) {
					logger.error("Invoke the getter method error.", e);
					throw e;
				}

				// Set command new option value
				Method set = pd.getWriteMethod();
				String value = null;
				try {
					if (option.hasArg()) {
						value = options.getOptionValue(longOpt);

						// Optional option
						if (option.optionalArg() && StringUtils.isEmpty(value)) {
							value = originalValue.toString();
						}

						if (String.class.equals(option.type())) {
							set.invoke(config, value);
						} else if (Integer.class.equals(option.type())) {
							set.invoke(config, Integer.parseInt(value));
						}
					} else {
						set.invoke(config, true);
					}
				} catch (NumberFormatException e) {
					logger.error("Convert option's value error: --" + longOpt
							+ " must be a number. '" + value
							+ "' is not a valid number.", e);
					throw e;
				} catch (IllegalAccessException e) {
					logger.error("Invoke the setter method error. (args: "
							+ value + ")", e);
					throw e;
				} catch (IllegalArgumentException e) {
					logger.error("Invoke the setter method error. (args: "
							+ value + ")", e);
					throw e;
				} catch (InvocationTargetException e) {
					logger.error("Invoke the setter method error. (args: "
							+ value + ")", e);
					throw e;
				} catch (Exception e) {
					logger.error("Copy options to config instance error");
					throw e;
				}
			}
		}
	}
}
