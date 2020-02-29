package com.wsl.tools;

//import com.china.gis.platform.pojo.cityplatform.PlatformUsers;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * key,加密解密工具
 *
 *
 */
public class EncryptionUtil {

	public BASE64Encoder encoder = new BASE64Encoder();
	public BASE64Decoder decoder = new BASE64Decoder();
	/**
	 * 加密
	 */
	public static final String OPERATION_ENCODER = "encoder";
	/**
	 * 解密
	 */
	public static final String OPERATION_DECODER = "decoder";

	/**
	 * 
	 * @param key 需要加密的字符串
	 * @return 加密后的数据,否则为null
	 */
	@SuppressWarnings("restriction")
	public String encode(String key) {
		String base64EncodeStr = null;
		base64EncodeStr = encoder.encode(key.getBytes());
		return base64EncodeStr;
	}

	/**
	 * 
	 * @param key 需要解密的字符创
	 * @return 解密后的数据,否则为null
	 */
	@SuppressWarnings("restriction")
	public String decode(String key) {
		byte[] base64DecodeStr = null;
		try {
			base64DecodeStr = decoder.decodeBuffer(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(base64DecodeStr);
	}

	/**
	 * 加密操作
	 * 
	 * @param key 需要加密的字符串
	 * @return 加密后的字符串
	 */
	public String encodeOption(String key) {
		key = operationEncodeOrDecode(key, OPERATION_ENCODER);
		return key;
	}

	/**
	 * 解密操作
	 * 
	 * @param key 需要解密的字符串
	 * @return 解密后的字符串
	 */
	public String decodeOption(String key) {
		key = operationEncodeOrDecode(key, OPERATION_DECODER);
		return key;
	}

	/**
	 * 解密操作,并返回账号
	 * 
	 * @param key 需要解密的字符串
	 * @return 解密后的账号
	 */
//	public PlatformUsers getUserByKey(String key) {
//		key = decodeOption(key);
//		String[] keys = key.split("@@@");
//		PlatformUsers user = null;
//		if (keys.length == 3) {
//			user = new PlatformUsers();
//			user.setUsercode(keys[0]);
//			user.setPassword(keys[1]);
//			user.setActivationDay(new BigInteger(keys[2]));
//		}
//
//		return user;
//	}

	/**
	 * 加密操作
	 * 
	 * @param key 需要加密的字符串
	 * @return 加密后的字符串
	 */
//	public String getKeyByUser(PlatformUsers user) {
//		String key = null;
//		if (!StringUtils.isEmpty(user)) {
//			key = user.getUsercode() + "@@@" + user.getPassword() + "@@@" + user.getActivationDay();
//		}
//		if (key != null) {
//			key = operationEncodeOrDecode(key, OPERATION_ENCODER);
//		}
//		return key;
//	}

	/**
	 * 
	 * @param key       加密或解密字符串
	 *                  <p>
	 * @param operation 加密解密操作
	 *                  <p>
	 * @return 加密或解密后的字符串,否则为 null
	 */
	private String operationEncodeOrDecode(String key, String operation) {

		String changeStr = StringUtils.isEmpty(key) ? null : key;

		String rightStr = "";
		String leftStr = "";
		switch (operation) {
		case OPERATION_ENCODER:
			// 第一次加密
			String encodeKey = encode(key);

			// 将加密的字符串分为两部分
			rightStr = encodeKey.substring(0, encodeKey.length() / 2);
			leftStr = encodeKey.substring(encodeKey.length() / 2);

			// 将base64加密后的字符串交换位置
			rightStr = changeKey(rightStr);
			leftStr = changeKey(leftStr);

			// 将交换位置的两部分合并为一个字符串
			encodeKey = rightStr + leftStr;
			encodeKey = changeKey(encodeKey);

			// 再次加密
			encodeKey = encode(encodeKey);

			changeStr = encodeKey;
			break;
		case OPERATION_DECODER:
			// 第一次解密
			String decodeKey = decode(key);
			// 将解密的字符串分为两部分
			rightStr = decodeKey.substring(0, decodeKey.length() / 2);
			leftStr = decodeKey.substring(decodeKey.length() / 2);

			// 将base64解密后的字符串交换位置
			rightStr = changeKey(rightStr);
			leftStr = changeKey(leftStr);

			// 将交换位置的两部分合并为一个字符串
			decodeKey = rightStr + leftStr;
			decodeKey = changeKey(decodeKey);

			// 解密
			decodeKey = decode(decodeKey);

			changeStr = decodeKey;
			break;
		}
		return changeStr;
	}

	/**
	 * 
	 * @param key 需要变换的字符串
	 * @return 变换后的字符串
	 */
	private String changeKey(String key) {

		StringBuilder sb = new StringBuilder(key);

		char charRight;
		char charLeft;
		int keyLength = key.length();

		int keyLengthHalf = keyLength / 2;

		int tempLength = 0;

		for (int i = 0; i < keyLengthHalf; i++) {

			tempLength = keyLength - (i + 1);
			charLeft = key.charAt(i);
			charRight = key.charAt(tempLength);

			sb.replace(i, i + 1, charRight + "");
			sb.replace(tempLength, keyLength - i, charLeft + "");

		}
		return sb.toString();
	}

}
