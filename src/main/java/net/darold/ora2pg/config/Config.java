package net.darold.ora2pg.config;

import org.apache.log4j.Logger;

import net.darold.ora2pg.annotation.Ora2PgOption;

/**
 * Ora2Pg Configuration
 * 
 * @author yang.dongdong
 *
 */
public class Config {

    private static final Logger logger = Logger.getLogger(Config.class);

    /** ora2pg version */
    public static final String VERSION = "16.0";
    /** Temporary files prefix */
    public static final String TMP_FILE_PRE = "tmp_ora2pg";

    /** Default data limit value: 10000 */
    public static final Integer DEFAULT_DATA_LIMIT = 10000;
    /** Default cost unit value: 5 */
    public static final Integer DEFAULT_COST_UNIT_VALUE = 5;
    /** Default human day limit value: 10 */
    public static final Integer DEFAULT_HUMAN_DAY_LIMIT = 10;
    /** Default tmp dir: System.getProperty("java.io.tmpdir") */
    public static final String DEFAULT_TMP_DIR = System
	    .getProperty("java.io.tmpdir");
    /** Default boolean: false */
    public static final Boolean DEFAULT_BOOL = false;

    private static Config config;

    private Config() {
	// Set default value;
	this.dataLimit = DEFAULT_DATA_LIMIT;
	this.costUnitValue = DEFAULT_COST_UNIT_VALUE;
	this.humanDayLimit = DEFAULT_HUMAN_DAY_LIMIT;
	this.tmpDir = DEFAULT_TMP_DIR;

	String osName = System.getProperty("os.name").toLowerCase();
	String defaultWindows = "C:/ora2pg/ora2pg.conf";
	String defaultUnix = "/etc/ora2pg/ora2pg.conf";
	this.fileConf = osName.contains("windows") ? defaultWindows
		: defaultUnix;

	this.debug = DEFAULT_BOOL;
	this.quiet = DEFAULT_BOOL;
	this.help = DEFAULT_BOOL;
	this.showVer = DEFAULT_BOOL;
	this.mysql = DEFAULT_BOOL;

	logger.info("Initialize config instance." + "\n  Default value:"
		+ "\n\t-L|--limit=" + this.dataLimit + "\n\t-T|--temp_dir="
		+ this.tmpDir + "\n\t-c|--conf=" + this.fileConf
		+ "\n\t-d|--debug=" + this.debug + "\n\t-q|--quiet="
		+ this.quiet + "\n\t-h|--help=" + this.help
		+ "\n\t-v|--version=" + this.showVer + "\n\t-m|--mysql="
		+ this.mysql + "\n\t--cost_unit_value=" + this.costUnitValue
		+ "\n\t--human_days_limit=" + this.humanDayLimit);

    }

    /**
     * Get the configured value
     * 
     * @return
     */
    public static Config getConfig() {
	if (config == null) {
	    config = new Config();
	}
	return config;
    }

    /**
     * Used to set an alternate configuration file than the default
     * /etc/ora2pg/ora2pg.conf(or C:/ora2pg/ora2pg.conf on windows).
     */
    @Ora2PgOption(opt = "c", longOpt = "conf", hasArg = true, argName = "FILE", type = String.class, desc = "Used to set an alternate configuration file than the default /etc/ora2pg/ora2pg.conf.")
    private String fileConf;

    /** Enable verbose output. Default to {@link Config#DEFAULT_BOOL} */
    @Ora2PgOption(opt = "d", longOpt = "debug", hasArg = false, desc = "Enable verbose output.")
    private Boolean debug;

    /** disable progress bar. Default to {@link Config#DEFAULT_BOOL} */
    @Ora2PgOption(opt = "q", longOpt = "quiet", hasArg = false, desc = "disable progress bar.")
    private Boolean quiet;

    /** Print this short help. Default to {@link Config#DEFAULT_BOOL} */
    @Ora2PgOption(opt = "h", longOpt = "help", hasArg = false, desc = "Print this short help.")
    private Boolean help;

    /** Used to set a log file. Default is stdout. */
    @Ora2PgOption(opt = "l", longOpt = "log", hasArg = true, argName = "FILE", type = String.class, desc = "Used to set a log file. Default is stdout.")
    private String logFile;

    /**
     * Used to set the export type. It will override the one given in the
     * configuration file (TYPE).
     */
    @Ora2PgOption(opt = "t", longOpt = "type", hasArg = true, argName = "EXPORT", type = String.class, desc = "Used to set the export type. It will override the one given in the configuration file (TYPE).")
    private String exportType;

    /**
     * Used to set the path to the output file where SQL will be written.
     * Default: output.sql in running directory.
     */
    @Ora2PgOption(opt = "o", longOpt = "out", hasArg = true, argName = "FILE", type = String.class, desc = "Used to set the path to the output file where SQL will be written. Default: output.sql in running directory.")
    private String outFile;

    /**
     * Used to set the default output directory, where files resulting from
     * exports will be stored.
     */
    @Ora2PgOption(opt = "b", longOpt = "basedir", hasArg = true, argName = "DIR", type = String.class, desc = "Used to set the default output directory, where files resulting from exports will be stored.")
    private String outDir;

    /** Show Ora2Pg Version and exit. Default to {@link Config#DEFAULT_BOOL} */
    @Ora2PgOption(opt = "v", longOpt = "version", hasArg = false, desc = "Show Ora2Pg Version and exit.")
    private Boolean showVer;

    /** Enable PLSQL to PLPSQL code conversion. */
    @Ora2PgOption(opt = "p", longOpt = "plsql", hasArg = false, desc = "Enable PLSQL to PLPSQL code conversion.")
    private String plsql;

    /** Allow to set the Oracle DBI data source. */
    @Ora2PgOption(opt = "s", longOpt = "source", hasArg = true, argName = "DSN", type = String.class, desc = "Allow to set the Oracle DBI datasource.")
    private String dsn;

    /**
     * Used to set the Oracle database connection user. ORA2PG_USER environment
     * variable can be used instead.
     */
    @Ora2PgOption(opt = "u", longOpt = "user", hasArg = true, argName = "NAME", type = String.class, desc = "Used to set the Oracle database connection user. ORA2PG_USER environment variable can be used instead.")
    private String dbUser;

    /**
     * Used to set the password of the Oracle database user. ORA2PG_PASSWD
     * environment variable can be used instead.
     */
    @Ora2PgOption(opt = "w", longOpt = "password", hasArg = true, argName = "PWD", type = String.class, desc = "Used to set the password of the Oracle database user. ORA2PG_PASSWD environment variable can be used instead.")
    private String dbPwd;

    /** Used to set the Oracle schema to extract from. */
    @Ora2PgOption(opt = "n", longOpt = "namespace", hasArg = true, argName = "SCHEMA", type = String.class, desc = "Used to set the Oracle schema to extract from.")
    private String schema;

    /**
     * if set to 1 force ora2pg to set tables and sequences owner like in Oracle
     * database. If the value is set to a username this one will be used as the
     * objects owner. By default it's the user used to connect to the Pg
     * database that will be the owner.
     */
    @Ora2PgOption(longOpt = "forceowner", hasArg = true, argName = "NAME", type = String.class, desc = "if set to 1 force ora2pg to set tables and sequences owner like in Oracle database. If the value is set to a username this one will be used as the objects owner. By default it's the user used to connect to the Pg database that will be the owner.")
    private String forceOwner;

    /** use this to set the Oracle NLS_LANG client encoding. */
    @Ora2PgOption(longOpt = "nls_lang", hasArg = true, argName = "CODE", type = String.class, desc = "use this to set the Oracle NLS_LANG client encoding.")
    private String oraEncoding;

    /** Use this to set the PostgreSQL client encoding. */
    @Ora2PgOption(longOpt = "client_encoding", hasArg = true, argName = "CODE", type = String.class, desc = "Use this to set the PostgreSQL client encoding.")
    private String pgEncoding;

    /**
     * File containing Oracle PL/SQL code to convert with no Oracle database
     * connection initiated.
     */
    @Ora2PgOption(opt = "i", longOpt = "input", hasArg = true, argName = "FILE", type = String.class, desc = "File containing Oracle PL/SQL code to convert with no Oracle database connection initiated.")
    private String inputFile;

    /**
     * coma separated list of objects to exclude from export.Can be used with
     * SHOW_COLUMN too.
     */
    @Ora2PgOption(opt = "e", longOpt = "exclude", hasArg = true, argName = "STR", type = String.class, desc = "coma separated list of objects to exclude from export.Can be used with SHOW_COLUMN too.")
    private String exclude;

    /**
     * coma separated list of objects to allow from export.Can be used with
     * SHOW_COLUMN too.
     */
    @Ora2PgOption(opt = "a", longOpt = "allow", hasArg = true, argName = "STR", type = String.class, desc = "coma separated list of objects to allow from export.Can be used with SHOW_COLUMN too.")
    private String allow;

    /** coma separated list of view to export as table. */
    @Ora2PgOption(longOpt = "view_as_table", hasArg = true, argName = "STR", type = String.class, desc = "coma separated list of view to export as table.")
    private String viewAsTable;

    /** activate the migration cost evalution with SHOW_REPORT */
    @Ora2PgOption(longOpt = "estimate_cost", hasArg = false, desc = "activate the migration cost evalution with SHOW_REPORT")
    private String estimateCost;

    /**
     * number of minutes for a cost evalution unit. default:
     * {@link Config#DEFAULT_COST_UNIT_VALUE} minutes, correspond to a migration
     * conducted by a PostgreSQL expert. Set it to 10 if this is your first
     * migration.
     */
    @Ora2PgOption(longOpt = "cost_unit_value", hasArg = true, argName = "MINUTES", type = Integer.class, desc = "number of minutes for a cost evalution unit. default: 5 minutes, correspond to a migration conducted by a PostgreSQL expert. Set it to 10 if this is your first migration.")
    private Integer costUnitValue;

    /**
     * force ora2pg to dump report in HTML, used only with SHOW_REPORT. Default
     * is to dump report as simple text.
     */
    @Ora2PgOption(longOpt = "dump_as_html", hasArg = false, desc = "force ora2pg to dump report in HTML, used only with SHOW_REPORT. Default is to dump report as simple text.")
    private String dumpAsHTML;

    /** as above but force ora2pg to dump report in CSV. */
    @Ora2PgOption(longOpt = "dump_as_csv", hasArg = false, desc = "as above but force ora2pg to dump report in CSV.")
    private String dumpAsCSV;

    /** report migration assessment one CSV line per database. */
    @Ora2PgOption(longOpt = "dump_as_sheet", hasArg = false, desc = "report migration assessment one CSV line per database.")
    private String dumpAsSheet;

    /** number of parallel process to send data to PostgreSQL. */
    @Ora2PgOption(opt = "j", longOpt = "jobs", hasArg = true, argName = "NUM", type = Integer.class, desc = "number of parallel process to send data to PostgreSQL.")
    private Integer threadCount;

    /** number of parallel connection to extract data from Oracle. */
    @Ora2PgOption(opt = "J", longOpt = "copies", hasArg = true, argName = "NUM", type = Integer.class, desc = "number of parallel connection to extract data from Oracle.")
    private Integer oracleCopies;

    /** Number of parallel tables to extract at the same time. */
    @Ora2PgOption(opt = "P", longOpt = "parallel", hasArg = true, argName = "NUM", type = Integer.class, desc = "Number of parallel tables to extract at the same time.")
    private Integer parallelTables;

    /**
     * number of tuples extracted from Oracle and stored in memory before
     * writing, default: {@link Config#DEFAULT_DATA_LIMIT}.
     */
    @Ora2PgOption(opt = "L", longOpt = "limit", hasArg = true, argName = "NUM", type = Integer.class, desc = "number of tuples extracted from Oracle and stored in memory before writing, default: 10000.")
    private Integer dataLimit;

    /**
     * initialize a typical ora2pg project tree. Top directory will be created
     * under project base dir.
     */
    @Ora2PgOption(longOpt = "init_project", hasArg = true, argName = "NAME", type = String.class, desc = "initialise a typical ora2pg project tree. Top directory will be created under project base dir.")
    private String createProject;

    /**
     * define the base dir for ora2pg project trees. Default is current
     * directory.
     */
    @Ora2PgOption(longOpt = "project_base", hasArg = true, argName = "DIR", type = String.class, desc = "define the base dir for ora2pg project trees. Default is current directory.")
    private String projectBase;

    /**
     * to be used with --dump_as_sheet to print the CSV header especially for
     * the first run of ora2pg.
     */
    @Ora2PgOption(longOpt = "print_header", hasArg = false, desc = "to be used with --dump_as_sheet to print the CSV header especially for the first run of ora2pg.")
    private String printHeader;

    /**
     * set the number human-days limit where the migration assessment level
     * switch from B to C. Default is set to
     * {@link Config#DEFAULT_HUMAN_DAY_LIMIT} human-days.
     */
    @Ora2PgOption(longOpt = "human_days_limit", hasArg = true, argName = "NUM", type = Integer.class, desc = "set the number human-days limit where the migration assessment level switch from B to C. Default is set to 10 human-days.")
    private Integer humanDayLimit;

    /**
     * Export a MySQL database instead of an Oracle schema. Default to
     * {@link Config#DEFAULT_BOOL}
     */
    @Ora2PgOption(opt = "m", longOpt = "mysql", hasArg = false, desc = "Export a MySQL database instead of an Oracle schema.")
    private Boolean mysql;

    /**
     * comma separated list of username to filter queries in the DBA_AUDIT_TRAIL
     * table. Used only with SHOW_REPORT and QUERY export type.
     */
    @Ora2PgOption(longOpt = "audit_user", hasArg = true, argName = "LIST", type = String.class, desc = "comma separated list of username to filter queries in the DBA_AUDIT_TRAIL table. Used only with SHOW_REPORT and QUERY export type.")
    private String auditUser;

    /**
     * use it to set a distinct temporary directory when two or more ora2pg are
     * run in parallel. Default to {@link Config#DEFAULT_TMP_DIR }
     */
    @Ora2PgOption(opt = "T", longOpt = "temp_dir", hasArg = true, argName = "DIR", type = String.class, desc = "use it to set a distinct temporary directory when two or more ora2pg are run in parallel.")
    private String tmpDir;

    private SchemaType[] schemaArray = { SchemaType.TABLE, SchemaType.PACKAGE,
	    SchemaType.VIEW, SchemaType.GRANT, SchemaType.SEQUENCE,
	    SchemaType.TRIGGER, SchemaType.FUNCTION, SchemaType.PROCEDURE,
	    SchemaType.TABLESPACE, SchemaType.PARTITION, SchemaType.TYPE,
	    SchemaType.MVIEW, SchemaType.DBLINK, SchemaType.SYNONYM,
	    SchemaType.DIRECTORY };

    private SchemaType[] externalArray = { SchemaType.KETTLE, SchemaType.FDW };

    private ReportType[] reportArray = { ReportType.SHOW_VERSION,
	    ReportType.SHOW_REPORT, ReportType.SHOW_SCHEMA,
	    ReportType.SHOW_TABLE, ReportType.SHOW_COLUMN,
	    ReportType.SHOW_ENCODING };

    private SchemaType[] sourcesArray = { SchemaType.PACKAGE, SchemaType.VIEW,
	    SchemaType.TRIGGER, SchemaType.FUNCTION, SchemaType.PROCEDURE,
	    SchemaType.PARTITION, SchemaType.TYPE, SchemaType.MVIEW };

    private DataType[] dataArray = { DataType.INSERT, DataType.COPY };

    private Capability[] capabilities = { Capability.QUERY };

    private SchemaType[] mysqlSchemaArray = { SchemaType.TABLE,
	    SchemaType.VIEW, SchemaType.GRANT, SchemaType.TRIGGER,
	    SchemaType.FUNCTION, SchemaType.PROCEDURE, SchemaType.PARTITION,
	    SchemaType.DBLINK };

    private SchemaType[] mysqlSourcesArray = { SchemaType.VIEW,
	    SchemaType.TRIGGER, SchemaType.FUNCTION, SchemaType.PROCEDURE,
	    SchemaType.PARTITION };

    /**
     * Database objects
     * 
     * @author yang.dongdong
     *
     */
    public enum SchemaType {
	TABLE, PACKAGE, VIEW, GRANT, SEQUENCE, TRIGGER, FUNCTION, PROCEDURE, TABLESPACE, PARTITION, TYPE, MVIEW, DBLINK, SYNONYM, DIRECTORY, KETTLE, FDW;
    }

    /**
     * Report types
     * 
     * @author yang.dongdong
     *
     */
    public enum ReportType {
	SHOW_VERSION, SHOW_REPORT, SHOW_SCHEMA, SHOW_TABLE, SHOW_COLUMN, SHOW_ENCODING;
    }

    /**
     * Export data types
     * 
     * @author yang.dongdong
     *
     */
    public enum DataType {
	INSERT, COPY;
    }

    /**
     * Capability types
     * 
     * @author yang.dongdong
     *
     */
    public enum Capability {
	QUERY;
    }

    public String getFileConf() {
	return fileConf;
    }

    public void setFileConf(String fileConf) {
	this.fileConf = fileConf;
    }

    public Boolean isDebug() {
	return debug;
    }

    public void setDebug(Boolean debug) {
	this.debug = debug;
    }

    public Boolean isQuiet() {
	return quiet;
    }

    public void setQuiet(Boolean quiet) {
	this.quiet = quiet;
    }

    public Boolean isHelp() {
	return help;
    }

    public void setHelp(Boolean help) {
	this.help = help;
    }

    public String getLogFile() {
	return logFile;
    }

    public void setLogFile(String logFile) {
	this.logFile = logFile;
    }

    public String getExportType() {
	return exportType;
    }

    public void setExportType(String exportType) {
	this.exportType = exportType;
    }

    public String getOutFile() {
	return outFile;
    }

    public void setOutFile(String outFile) {
	this.outFile = outFile;
    }

    public String getOutDir() {
	return outDir;
    }

    public void setOutDir(String outDir) {
	this.outDir = outDir;
    }

    public Boolean isShowVer() {
	return showVer;
    }

    public void setShowVer(Boolean showVer) {
	this.showVer = showVer;
    }

    public String getPlsql() {
	return plsql;
    }

    public void setPlsql(String plsql) {
	this.plsql = plsql;
    }

    public String getDsn() {
	return dsn;
    }

    public void setDsn(String dsn) {
	this.dsn = dsn;
    }

    public String getDbUser() {
	return dbUser;
    }

    public void setDbUser(String dbUser) {
	this.dbUser = dbUser;
    }

    public String getDbPwd() {
	return dbPwd;
    }

    public void setDbPwd(String dbPwd) {
	this.dbPwd = dbPwd;
    }

    public String getSchema() {
	return schema;
    }

    public void setSchema(String schema) {
	this.schema = schema;
    }

    public String getForceOwner() {
	return forceOwner;
    }

    public void setForceOwner(String forceOwner) {
	this.forceOwner = forceOwner;
    }

    public String getOraEncoding() {
	return oraEncoding;
    }

    public void setOraEncoding(String oraEncoding) {
	this.oraEncoding = oraEncoding;
    }

    public String getPgEncoding() {
	return pgEncoding;
    }

    public void setPgEncoding(String pgEncoding) {
	this.pgEncoding = pgEncoding;
    }

    public String getInputFile() {
	return inputFile;
    }

    public void setInputFile(String inputFile) {
	this.inputFile = inputFile;
    }

    public String getExclude() {
	return exclude;
    }

    public void setExclude(String exclude) {
	this.exclude = exclude;
    }

    public String getAllow() {
	return allow;
    }

    public void setAllow(String allow) {
	this.allow = allow;
    }

    public String getViewAsTable() {
	return viewAsTable;
    }

    public void setViewAsTable(String viewAsTable) {
	this.viewAsTable = viewAsTable;
    }

    public String getEstimateCost() {
	return estimateCost;
    }

    public void setEstimateCost(String estimateCost) {
	this.estimateCost = estimateCost;
    }

    public Integer getCostUnitValue() {
	return costUnitValue;
    }

    public void setCostUnitValue(Integer costUnitValue) {
	this.costUnitValue = costUnitValue;
    }

    public String getDumpAsHTML() {
	return dumpAsHTML;
    }

    public void setDumpAsHTML(String dumpAsHTML) {
	this.dumpAsHTML = dumpAsHTML;
    }

    public String getDumpAsCSV() {
	return dumpAsCSV;
    }

    public void setDumpAsCSV(String dumpAsCSV) {
	this.dumpAsCSV = dumpAsCSV;
    }

    public String getDumpAsSheet() {
	return dumpAsSheet;
    }

    public void setDumpAsSheet(String dumpAsSheet) {
	this.dumpAsSheet = dumpAsSheet;
    }

    public Integer getThreadCount() {
	return threadCount;
    }

    public void setThreadCount(Integer threadCount) {
	this.threadCount = threadCount;
    }

    public Integer getOracleCopies() {
	return oracleCopies;
    }

    public void setOracleCopies(Integer oracleCopies) {
	this.oracleCopies = oracleCopies;
    }

    public Integer getParallelTables() {
	return parallelTables;
    }

    public void setParallelTables(Integer parallelTables) {
	this.parallelTables = parallelTables;
    }

    public Integer getDataLimit() {
	return dataLimit;
    }

    public void setDataLimit(Integer dataLimit) {
	this.dataLimit = dataLimit;
    }

    public String getCreateProject() {
	return createProject;
    }

    public void setCreateProject(String createProject) {
	this.createProject = createProject;
    }

    public String getProjectBase() {
	return projectBase;
    }

    public void setProjectBase(String projectBase) {
	this.projectBase = projectBase;
    }

    public String getPrintHeader() {
	return printHeader;
    }

    public void setPrintHeader(String printHeader) {
	this.printHeader = printHeader;
    }

    public Integer getHumanDayLimit() {
	return humanDayLimit;
    }

    public void setHumanDayLimit(Integer humanDayLimit) {
	this.humanDayLimit = humanDayLimit;
    }

    public Boolean isMysql() {
	return mysql;
    }

    public void setMysql(Boolean mysql) {
	this.mysql = mysql;
    }

    public String getAuditUser() {
	return auditUser;
    }

    public void setAuditUser(String auditUser) {
	this.auditUser = auditUser;
    }

    public String getTmpDir() {
	return tmpDir;
    }

    public void setTmpDir(String tmpDir) {
	this.tmpDir = tmpDir;
    }

    public SchemaType[] getSchemaArray() {
	return schemaArray;
    }

    public void setSchemaArray(SchemaType[] schemaArray) {
	this.schemaArray = schemaArray;
    }

    public SchemaType[] getExternalArray() {
	return externalArray;
    }

    public void setExternalArray(SchemaType[] externalArray) {
	this.externalArray = externalArray;
    }

    public ReportType[] getReportArray() {
	return reportArray;
    }

    public void setReportArray(ReportType[] reportArray) {
	this.reportArray = reportArray;
    }

    public SchemaType[] getSourcesArray() {
	return sourcesArray;
    }

    public void setSourcesArray(SchemaType[] sourcesArray) {
	this.sourcesArray = sourcesArray;
    }

    public DataType[] getDataArray() {
	return dataArray;
    }

    public void setDataArray(DataType[] dataArray) {
	this.dataArray = dataArray;
    }

    public Capability[] getCapabilities() {
	return capabilities;
    }

    public void setCapabilities(Capability[] capabilities) {
	this.capabilities = capabilities;
    }

    public SchemaType[] getMysqlSchemaArray() {
	return mysqlSchemaArray;
    }

    public void setMysqlSchemaArray(SchemaType[] mysqlSchemaArray) {
	this.mysqlSchemaArray = mysqlSchemaArray;
    }

    public SchemaType[] getMysqlSourcesArray() {
	return mysqlSourcesArray;
    }

    public void setMysqlSourcesArray(SchemaType[] mysqlSourcesArray) {
	this.mysqlSourcesArray = mysqlSourcesArray;
    }
}
