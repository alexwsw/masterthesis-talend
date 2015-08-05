<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
	<talendfile:ProcessType xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://www.talend.org/mapper" xmlns:talendfile="platform:/resource/org.talend.model/model/TalendFile.xsd" defaultContext="Default">
  	<context confirmationNeeded="false" name="Default">
  	</context>
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
		<xsl:apply-templates select="/ProcessType/DBase"/>
		<xsl:apply-templates select="/ProcessType/DBCommit"/>
		<xsl:apply-templates select="/ProcessType/connection"/>
		</talendfile:ProcessType>
	</xsl:template>
	
	<xsl:template match="JobStarter|JobFinisher">
		<node componentVersion="0.102" offsetLabelX="0" offsetLabelY="0" posX="192" posY="96">
			<xsl:call-template name="doBasicStuff"/>
		</node>	
	</xsl:template>
	
	<xsl:template match="DBase">
		<node componentVersion="0.102" offsetLabelX="0" offsetLabelY="0" posX="256" posY="64">
			<xsl:call-template name="doBasicStuff"/>
   			 <elementParameter field="TEXT" name="TYPE" value="MSSQL" show="false"/>
   			 <elementParameter field="ENCODING_TYPE" name="ENCODING" value="&quot;ISO-8859-15&quot;" show="false"/>
			<elementParameter field="TECHNICAL" name="ENCODING:ENCODING_TYPE" value="ISO-8859-15"/>
		    <elementParameter field="TEXT" name="PROPERTIES" value="&quot;&quot;"/>
		    <elementParameter field="CHECK" name="USE_SHARED_CONNECTION" value="false"/>
		    <elementParameter field="TEXT" name="SHARED_CONNECTION_NAME" value="" show="false"/>
		    <elementParameter field="LABEL" name="NOTE" value="This option only applies when deploying and running in the Talend Runtime"/>
		    <elementParameter field="CHECK" name="SPECIFY_DATASOURCE_ALIAS" value="false"/>
		    <elementParameter field="TEXT" name="DATASOURCE_ALIAS" value="&quot;&quot;" show="false"/>
		    <elementParameter field="CHECK" name="AUTO_COMMIT" value="false"/>
		    <elementParameter field="CHECK" name="SHARE_IDENTITY_SETTING" value="false"/>
			<elementParameter field="TEXT" name="HOST">
				<xsl:attribute name="value">
					<xsl:value-of select="host"/>
				</xsl:attribute>
			</elementParameter>
			<elementParameter field="TEXT" name="PORT">
				<xsl:attribute name="value">
					<xsl:value-of select="port"/>
				</xsl:attribute>
			</elementParameter>
			<elementParameter field="TEXT" name="SCHEMA_DB">
				<xsl:attribute name="value">
					<xsl:value-of select="schema_db"/>
				</xsl:attribute>
			</elementParameter>
			<elementParameter field="TEXT" name="USER">
				<xsl:attribute name="value">
					<xsl:value-of select="user"/>
				</xsl:attribute>
			</elementParameter>
			<elementParameter field="PASSWORD" name="PASS">
				<xsl:attribute name="value">
					<xsl:value-of select="pass"/>
				</xsl:attribute>
			</elementParameter>
		</node>
	</xsl:template>
		
	<xsl:template match="DBCommit">
		<node componentVersion="0.102" offsetLabelX="0" offsetLabelY="0" posX="192" posY="96">
			<xsl:call-template name="doBasicStuff"/>
			<elementParameter field="CHECK" name="CLOSE" value="true"/>
			<elementParameter field="COMPONENT_LIST" name="CONNECTION">
				<xsl:attribute name="value">
					<xsl:value-of select="dataBase"/>
				</xsl:attribute>
			</elementParameter>
			<xsl:call-template name="addMetadata"/>
		</node>
	</xsl:template>
	
	<!-- Template(Method) for adding metadata to the given node -->
	<xsl:template name="addMetadata">
		<xsl:for-each select="./metadata">
			<metadata>
				<xsl:attribute name="connector">
					<xsl:value-of select="./type"/>
				</xsl:attribute>
				<xsl:attribute name="name">
					<xsl:value-of select="./connectorName"/>
				</xsl:attribute>
				<xsl:call-template name="addColumnsToMetadata"/>
			</metadata>
			</xsl:for-each>
	</xsl:template>
	
	<!-- Iteration successful, precise specification of all possible attributes required -->
	<xsl:template name="addColumnsToMetadata">
		<!-- for each column - element -->
		<xsl:for-each select="./column">
			<column>
			<!-- for each child of the column element -->
			<xsl:for-each select="./*">
				<xsl:attribute name="{name()}">
					<xsl:value-of select="text()"/>
				</xsl:attribute>
			</xsl:for-each>
			</column>
		</xsl:for-each>
	</xsl:template>
	
	<!-- Add Attributes valid for each Node in the Document -->
	<xsl:template name="doBasicStuff">
			<xsl:attribute name="componentName">
				<xsl:value-of select="./name"/>
			</xsl:attribute>
			<elementParameter field="TEXT" name="UNIQUE_NAME">
				<xsl:attribute name="value">
					<xsl:value-of select="./uniqueName"/>
				</xsl:attribute>
			</elementParameter>
			<!--  -->
			 <elementParameter field="DIRECTORY" name="JAVA_LIBRARY_PATH" value="C:\Users\becher\Desktop\TOS\TOS_DI-Win32-20141207_1530-V5.6.1\configuration\lib\java"/>
   			 <elementParameter field="TEXT" name="CONNECTION_FORMAT" value="row"/>
   			 <elementParameter field="TEXT" name="LABEL">
				<xsl:attribute name="value">
					<xsl:value-of select="./label"/>
				</xsl:attribute>
			</elementParameter>
	</xsl:template>
	
	<xsl:template match="connection">
		<connection offsetLabelX="0" offsetLabelY="0">
		<xsl:attribute name="source">
			<xsl:value-of select="./source"/>
		</xsl:attribute>
		<xsl:attribute name="target">
			<xsl:value-of select="./target"/>
		</xsl:attribute>
		<xsl:attribute name="connectorName">
			<xsl:value-of select="./connectorName"/>
		</xsl:attribute>
		<xsl:attribute name="label">
			<xsl:value-of select="./label"/>
		</xsl:attribute>
		<xsl:attribute name="lineStyle">
			<xsl:value-of select="./type"/>
		</xsl:attribute>
		<xsl:attribute name="metaname">
			<xsl:value-of select="./metaname"/>
		</xsl:attribute>
		<elementParameter field="TEXT" name="UNIQUE_NAME">
				<xsl:attribute name="value">
					<xsl:value-of select="./uniqueName"/>
				</xsl:attribute>
			</elementParameter>
		<!-- add the monitoring option if the connection is a main type -->
		<xsl:if test="./type/text() = 0">
			<elementParameter field="CHECK" name="MONITOR_CONNECTION" value="false"/>
		</xsl:if>
		<!-- add transferable columns if the element possess any -->
		<xsl:if test="./data">
			<elementParameter field="TABLE" name="TRACES_CONNECTION_FILTER">
				<xsl:for-each select="./data/*">
					<elementValue elementRef="TRACE_COLUMN">
						<xsl:attribute name="value">
							<xsl:value-of select="text()"/>
						</xsl:attribute>
      				</elementValue>
						<elementValue elementRef="TRACE_COLUMN_CHECKED" value="true"/>
      					<elementValue elementRef="TRACE_COLUMN_CONDITION" value=""/>
				</xsl:for-each>
			</elementParameter>
		</xsl:if>
		</connection>
	</xsl:template>
	
</xsl:stylesheet>