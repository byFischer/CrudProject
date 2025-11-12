package crudproject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbHelper {

	private static final String PROPERTIES_FILE_NAME = "db.properties";
	private static final String PROP_URL = "db.url";
	private static final String PROP_USER = "db.user";
	private static final String PROP_PASSWORD = "db.password";

	private static final Properties CONFIG = loadConfiguration();

	private static final String URL = getRequiredValue(PROP_URL, "DB_URL");
	private static final String USER = getRequiredValue(PROP_USER, "DB_USER");
	private static final String PASS = getOptionalValue(PROP_PASSWORD, "DB_PASSWORD");
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(URL, USER, PASS);
	}

	private static Properties loadConfiguration() {
		Properties properties = new Properties();
		Path path = Path.of(PROPERTIES_FILE_NAME);
		if (Files.exists(path)) {
			try (InputStream in = Files.newInputStream(path)) {
				properties.load(in);
			} catch (IOException e) {
				throw new IllegalStateException("Veritabanı yapılandırma dosyası okunamadı: " + path.toAbsolutePath(), e);
			}
		}
		return properties;
	}

	private static String getRequiredValue(String propertyKey, String envKey) {
		String value = getOptionalValue(propertyKey, envKey);
		if (value == null || value.isBlank()) {
			throw new IllegalStateException(
					"Veritabanı bağlantısı için gerekli ayar bulunamadı. Lütfen " + propertyKey + " veya " + envKey
							+ " değerini sağlayın.");
		}
		return value;
	}

	private static String getOptionalValue(String propertyKey, String envKey) {
		String envValue = System.getenv(envKey);
		if (envValue != null && !envValue.isBlank()) {
			return envValue;
		}
		String propertyValue = CONFIG.getProperty(propertyKey);
		if (propertyValue != null && !propertyValue.isBlank()) {
			return propertyValue;
		}
		return null;
	}
}
