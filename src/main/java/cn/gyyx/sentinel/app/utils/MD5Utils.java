package cn.gyyx.sentinel.app.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;

public class MD5Utils {
    public MD5Utils() {
    }

    public static String MD5(String str) {
        if (!StringUtils.isEmpty(str) && !StringUtils.isBlank(str)) {
            char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

            try {
                byte[] btInput = str.getBytes();
                MessageDigest mdInst = MessageDigest.getInstance("MD5");
                mdInst.update(btInput);
                byte[] md = mdInst.digest();
                int j = md.length;
                char[] strArr = new char[j * 2];
                int k = 0;

                for(int i = 0; i < j; ++i) {
                    byte byte0 = md[i];
                    strArr[k++] = hexDigits[byte0 >>> 4 & 15];
                    strArr[k++] = hexDigits[byte0 & 15];
                }

                return new String(strArr);
            } catch (Exception var10) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String getHashCode(Object object) throws IOException {
        if (object == null) {
            return "";
        } else {
            String ss = null;
            ObjectOutputStream s = null;
            ByteArrayOutputStream bo = new ByteArrayOutputStream();

            try {
                s = new ObjectOutputStream(bo);
                s.writeObject(object);
                s.flush();
            } catch (FileNotFoundException var9) {
                var9.printStackTrace();
            } catch (IOException var10) {
                var10.printStackTrace();
            } finally {
                if (s != null) {
                    s.close();
                    s = null;
                }

            }

            ss = MD5(bo.toString());
            return ss;
        }
    }

    public static void main(String[] args) {
        String str = "serviceLocator = getDefaultClassLoader().loadClass(SERVICE_LOCATOR_CLASS)serviceLocator = getDefaultClassLoader().loadClass(SERVICE_LOCATOR_CLASS)serviceLocator = getDefaultClassLoader().loadClass(SERVICE_LOCATOR_CLASS)serviceLocator = getDefaultClassLoader().loadClass(SERVICE_LOCATOR_CLASS)serviceLocator = getDefaultClassLoader().loadClass(SERVICE_LOCATOR_CLASS)serviceLocator = getDefaultClassLoader().loadClass(SERVICE_LOCATOR_CLASS)serviceLocator = getDefaultClassLoader().loadClass(SERVICE_LOCATOR_CLASS)";
        System.out.println(MD5(str));
    }
}
