package config;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Config sConfig;
    private Properties mConfig;

    public static Config getInstance() {
        if (sConfig == null) {
            sConfig = new Config();
        }
        return sConfig;
    }

    private Config() {
        mConfig = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
        if (inputStream == null) {
            return;
        }
        try {
            mConfig.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getLeftMotorMinus() throws Exception {
        return getMotorProperty("leftMotorMinus");
    }

    public int getLeftMotorPlus() throws Exception {
        return getMotorProperty("leftMotorPlus");
    }

    public int getRightMotorMinus() throws Exception {
        return getMotorProperty("rightMotorMinus");
    }

    public int getRightMotorPlus() throws Exception {
        return getMotorProperty("rightMotorPlus");
    }


    private int getMotorProperty(String propertyName) {
        return Integer.valueOf(mConfig.getProperty(propertyName));
    }
}
