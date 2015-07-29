<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="xml"/>
	<xsl:template match="/">
		<parameters>
		    <elementParameter field="TEXT" name="SCREEN_OFFSET_X" value="0"/>
		    <elementParameter field="TEXT" name="SCREEN_OFFSET_Y" value="0"/>
		    <elementParameter field="TEXT" name="REPOSITORY_CONNECTION_ID" value=""/>
		    <elementParameter field="CHECK" name="IMPLICITCONTEXT_USE_PROJECT_SETTINGS" value="true"/>
		    <elementParameter field="CHECK" name="STATANDLOG_USE_PROJECT_SETTINGS" value="true"/>
		    <elementParameter field="CHECK" name="MULTI_THREAD_EXECATION" value="false"/>
		    <elementParameter field="CHECK" name="IMPLICIT_TCONTEXTLOAD" value="false"/>
		    <elementParameter field="RADIO" name="FROM_FILE_FLAG_IMPLICIT_CONTEXT" value="false"/>
		    <elementParameter field="RADIO" name="FROM_DATABASE_FLAG_IMPLICIT_CONTEXT" value="false"/>
		    <elementParameter field="FILE" name="IMPLICIT_TCONTEXTLOAD_FILE" value="&quot;&quot;"/>
		    <elementParameter field="TEXT" name="FIELDSEPARATOR" value="&quot;&quot;"/>
		    <elementParameter field="TECHNICAL" name="PROPERTY_TYPE_IMPLICIT_CONTEXT:PROPERTY_TYPE" value=""/>
		    <elementParameter field="TECHNICAL" name="PROPERTY_TYPE_IMPLICIT_CONTEXT:REPOSITORY_PROPERTY_TYPE" value=""/>
		    <elementParameter field="CLOSED_LIST" name="DB_TYPE_IMPLICIT_CONTEXT" value=""/>
		    <elementParameter field="CLOSED_LIST" name="DB_VERSION_IMPLICIT_CONTEXT" value=""/>
		    <elementParameter field="TEXT" name="URL_IMPLICIT_CONTEXT" value="&quot;&quot;"/>
		    <elementParameter field="TABLE" name="DRIVER_JAR_IMPLICIT_CONTEXT"/>
		    <elementParameter field="TEXT" name="DRIVER_CLASS_IMPLICIT_CONTEXT" value="&quot;&quot;"/>
		    <elementParameter field="TEXT" name="HOST_IMPLICIT_CONTEXT" value="&quot;&quot;"/>
		    <elementParameter field="TEXT" name="PORT_IMPLICIT_CONTEXT" value="&quot;&quot;"/>
		    <elementParameter field="TEXT" name="DBNAME_IMPLICIT_CONTEXT" value="&quot;&quot;"/>
		    <elementParameter field="TEXT" name="LOCAL_SERVICE_NAME_IMPLICIT_CONTEXT" value="&quot;&quot;"/>
		    <elementParameter field="TEXT" name="PROPERTIES_IMPLICIT_CONTEXT" value="&quot;&quot;"/>
		    <elementParameter field="TEXT" name="SCHEMA_DB_IMPLICIT_CONTEXT" value="&quot;&quot;"/>
		    <elementParameter field="TEXT" name="USER_IMPLICIT_CONTEXT" value="&quot;&quot;"/>
		    <elementParameter field="PASSWORD" name="PASS_IMPLICIT_CONTEXT" value="0RMsyjmybrE="/>
		    <elementParameter field="FILE" name="DBFILE_IMPLICIT_CONTEXT" value="&quot;&quot;"/>
		    <elementParameter field="DBTABLE" name="DBTABLE_IMPLICIT_CONTEXT" value="&quot;&quot;"/>
		    <elementParameter field="TEXT" name="QUERY_CONDITION_IMPLICIT_CONTEXT" value="&quot;&quot;"/>
		    <elementParameter field="CLOSED_LIST" name="LOAD_NEW_VARIABLE" value="Warning"/>
		    <elementParameter field="CLOSED_LIST" name="NOT_LOAD_OLD_VARIABLE" value="Warning"/>
		    <elementParameter field="CHECK" name="PRINT_OPERATIONS" value="false"/>
		    <elementParameter field="CHECK" name="DISABLE_ERROR" value="false"/>
		    <elementParameter field="CHECK" name="DISABLE_WARNINGS" value="false"/>
		    <elementParameter field="CHECK" name="DISABLE_INFO" value="false"/>
		    <elementParameter field="CHECK" name="ON_STATCATCHER_FLAG" value="false"/>
		    <elementParameter field="CHECK" name="ON_LOGCATCHER_FLAG" value="false"/>
		    <elementParameter field="CHECK" name="ON_METERCATCHER_FLAG" value="false"/>
		    <elementParameter field="CHECK" name="ON_CONSOLE_FLAG" value="false"/>
		    <elementParameter field="CHECK" name="ON_FILES_FLAG" value="false"/>
		    <elementParameter field="DIRECTORY" name="FILE_PATH" value="&quot;C:/Users/becher/Desktop/TOS/TOS_DI-Win32-20141207_1530-V5.6.1/workspace/.metadata&quot;"/>
		    <elementParameter field="TEXT" name="FILENAME_STATS" value="&quot;stats_file.txt&quot;"/>
		    <elementParameter field="TEXT" name="FILENAME_LOGS" value="&quot;logs_file.txt&quot;"/>
		    <elementParameter field="TEXT" name="FILENAME_METTER" value="&quot;meter_file.txt&quot;"/>
		    <elementParameter field="ENCODING_TYPE" name="ENCODING" value="ISO-8859-15"/>
		    <elementParameter field="TECHNICAL" name="ENCODING:ENCODING_TYPE" value="ISO-8859-15"/>
		    <elementParameter field="CHECK" name="ON_DATABASE_FLAG" value="false"/>
		    <elementParameter field="CLOSED_LIST" name="DB_TYPE" value="tJDBCOutput"/>
		    <elementParameter field="CLOSED_LIST" name="DB_VERSION" value="ORACLE_11"/>
		    <elementParameter field="TEXT" name="URL" value="&quot;&quot;"/>
		    <elementParameter field="TABLE" name="DRIVER_JAR"/>
		    <elementParameter field="TEXT" name="DRIVER_CLASS" value="&quot;&quot;"/>
		    <elementParameter field="TEXT" name="HOST" value="&quot;&quot;"/>
		    <elementParameter field="TEXT" name="PORT" value="&quot;&quot;"/>
		    <elementParameter field="TEXT" name="DATASOURCE" value="&quot;&quot;"/>
		    <elementParameter field="TEXT" name="DBNAME" value="&quot;&quot;"/>
		    <elementParameter field="TEXT" name="LOCAL_SERVICE_NAME" value="&quot;&quot;"/>
		    <elementParameter field="TEXT" name="PROPERTIES" value="&quot;&quot;"/>
		    <elementParameter field="TEXT" name="SCHEMA_DB" value="&quot;&quot;"/>
		    <elementParameter field="TEXT" name="USER" value="&quot;&quot;"/>
		    <elementParameter field="PASSWORD" name="PASS" value="0RMsyjmybrE="/>
		    <elementParameter field="FILE" name="DBFILE" value="&quot;&quot;"/>
		    <elementParameter field="DBTABLE" name="TABLE_STATS" value="&quot;&quot;"/>
		    <elementParameter field="DBTABLE" name="TABLE_LOGS" value="&quot;&quot;"/>
		    <elementParameter field="DBTABLE" name="TABLE_METER" value="&quot;&quot;"/>
		    <elementParameter field="CHECK" name="CATCH_RUNTIME_ERRORS" value="true"/>
		    <elementParameter field="CHECK" name="CATCH_USER_ERRORS" value="true"/>
		    <elementParameter field="CHECK" name="CATCH_USER_WARNING" value="true"/>
		    <elementParameter field="CHECK" name="CATCH_REALTIME_STATS" value="false"/>
		    <elementParameter field="TEXT" name="HEADERFOOTER_HEADERID" value=""/>
		    <elementParameter field="CHECK" name="HEADER_ENABLED" value="false"/>
		    <elementParameter field="TEXT" name="HEADER_LIBRARY" value=""/>
		    <elementParameter field="TEXT" name="HEADER_CODE" value=""/>
		    <elementParameter field="TEXT" name="HEADER_IMPORT" value=""/>
		    <elementParameter field="TEXT" name="HEADERFOOTER_FOOTERID" value=""/>
		    <elementParameter field="CHECK" name="FOOTER_ENABLED" value="false"/>
		    <elementParameter field="TEXT" name="FOOTER_LIBRARY" value=""/>
		    <elementParameter field="TEXT" name="FOOTER_CODE" value=""/>
		    <elementParameter field="TEXT" name="FOOTER_IMPORT" value=""/>
		    <routinesParameter id="_9gTPQNeqEeSlQZubuySunA" name="DataOperation"/>
		    <routinesParameter id="_9gTPRNeqEeSlQZubuySunA" name="Mathematical"/>
		    <routinesParameter id="_9gcZM9eqEeSlQZubuySunA" name="Numeric"/>
		    <routinesParameter id="_9gcZN9eqEeSlQZubuySunA" name="Relational"/>
		    <routinesParameter id="_9gcZO9eqEeSlQZubuySunA" name="StringHandling"/>
		    <routinesParameter id="_9gcZP9eqEeSlQZubuySunA" name="TalendDataGenerator"/>
		    <routinesParameter id="_9gmKMNeqEeSlQZubuySunA" name="TalendDate"/>
		    <routinesParameter id="_9gmKNNeqEeSlQZubuySunA" name="TalendString"/>
  		</parameters>
		<xsl:apply-templates select="/ProcessType/JobStarter"/>
		<xsl:apply-templates select="/ProcessType/JobFinisher"/>
	</xsl:template>
	<xsl:template match="JobStarter">
		<node>
			<xsl:attribute name="componentName">
				<xsl:value-of select="name"/>
			</xsl:attribute>
			<elementParameter>
				<xsl:attribute name="field">TEXT</xsl:attribute>
				<xsl:attribute name="name">UNIQUE_NAME</xsl:attribute>
				<xsl:attribute name="value">
					<xsl:value-of select="uniqueName"/>"
				</xsl:attribute>
			</elementParameter>
			 <elementParameter field="DIRECTORY" name="JAVA_LIBRARY_PATH" value="C:\Users\becher\Desktop\TOS\TOS_DI-Win32-20141207_1530-V5.6.1\configuration\lib\java"/>
   			 <elementParameter field="TEXT" name="CONNECTION_FORMAT" value="row"/>
		</node>
	</xsl:template>
</xsl:stylesheet>