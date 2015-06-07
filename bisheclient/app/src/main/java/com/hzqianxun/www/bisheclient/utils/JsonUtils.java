package com.hzqianxun.www.bisheclient.utils;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLHandshakeException;


/***
 * json工具解析类
 */
public class JsonUtils {

	public static final ObjectMapper MAPPER = new ObjectMapper().configure(
			DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	public static <T> T toBean(Class<T> type, InputStream is) {
		T obj = null;
		try {
			obj = parse(is, type, null);
		} catch (IOException e) {
			e.printStackTrace();
		}


		return obj;
	}

	/**
     * 对获取到的网络数据进行处理
     * @param inputStream
     * @param type
     * @param instance
     * @return
     * @throws java.io.IOException
     */
    private static <T> T parse(InputStream inputStream, Class<T> type, T instance) throws IOException {
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(inputStream);
            String data = IOUtils.toString(reader);

            if (type != null) {
                return MAPPER.readValue(data, type);
            } else if (instance != null) {
                return MAPPER.readerForUpdating(instance).readValue(data);
            } else {
                return null;
            }
        } catch (SSLHandshakeException e) {
            throw new SSLHandshakeException("You can disable certificate checking by setting ignoreCertificateErrors on GitlabHTTPRequestor");
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    public static <T> T toBean(Class<T> type, byte[] bytes) {
        if (bytes == null) return null;
        return toBean(type, new ByteArrayInputStream(bytes));
    }

    public static <T> List<T> getList(Class<T[]> type, byte[] bytes) {
        if (bytes == null) return null;
        List<T> results = new ArrayList<T>();
        T[] _next = toBean(type, bytes);
        results.addAll(Arrays.asList(_next));
        return results;
    }
}
