package org.nachc.tools.fhirtoomop.tools.build.postgres.build;

import java.sql.Connection;

import org.nachc.tools.fhirtoomop.util.db.connection.postgres.PostgresDatabaseConnectionFactory;
import org.nachc.tools.fhirtoomop.util.params.AppParams;
import org.yaorma.database.Database;

import com.nach.core.util.file.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class A06_CreateAchillesTables {

	private static final String FILE_PATH = "/postgres/build/A06_CreateAchillesTables.sql";

	public static void main(String[] args) {
		exec();
	}

	public static void exec() {
		Connection conn = PostgresDatabaseConnectionFactory.getCdmConnection();
		try {
			exec(conn);
		} finally {
			Database.close(conn);
		}

	}

	public static void exec(Connection conn) {
		log.info("Creating Achilles tables.");
		log.info("getting sql script...");
		log.info("executing script...");
		String sqlString = getSqlString();
		Database.executeSqlScript(sqlString, conn);
		log.info("Done creating Achilles tables.");
	}

	private static String getSqlString() {
		String sqlString = FileUtil.getAsString(FILE_PATH);
		sqlString = sqlString.replace("@achillesResultsDbName", AppParams.getFullySpecifiedAchillesResultsSchemaName());
		return sqlString;
	}

}
