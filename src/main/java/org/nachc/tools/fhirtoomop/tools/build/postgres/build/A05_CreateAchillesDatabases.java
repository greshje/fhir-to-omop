package org.nachc.tools.fhirtoomop.tools.build.postgres.build;

import java.sql.Connection;

import org.nachc.tools.fhirtoomop.util.db.connection.postgres.PostgresDatabaseConnectionFactory;
import org.nachc.tools.fhirtoomop.util.params.AppParams;
import org.yaorma.database.Database;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class A05_CreateAchillesDatabases {

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
		String achillesResDbName = AppParams.getFullySpecifiedAchillesResultsSchemaName();
		String achillesTempDbName = AppParams.getFullySpecifiedAchillesTempSchemaName();
		createDatabase(achillesResDbName, conn);
		createDatabase(achillesTempDbName, conn);
		Database.commit(conn);
		log.info("Done creating Achilles databases.");
	}

	private static void createDatabase(String databaseName, Connection conn) {
		log.info("Creating database: " + databaseName);
		Database.update("drop schema if exists " + databaseName + " cascade", conn);
		Database.update("create schema " + databaseName, conn);
	}

}
