package com.xiu.mobile.portal.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.springframework.util.DigestUtils;

import com.xiu.mobile.portal.common.constants.XiuConstant;

public class AESCipher {

	private final String password;

	public AESCipher(String password) {
		this.password = password;
	}

	public byte[] encrypt(byte[] plainText) throws Exception {
		return transform(true, plainText);
	}

	//
	public String encrypt(String plainText) throws Exception {
		byte[] bytes = this.encrypt(plainText.getBytes("utf-8"));
		return this.byte2hex(bytes);
	}

	public byte[] decrypt(byte[] cipherText) throws Exception {
		return transform(false, cipherText);
	}

	public String decrypt(String cipherText) throws Exception {
		byte[] bytes = this.decrypt(this.hex2byte(cipherText));
		// return this.byte2hex(bytes);
		StringBuffer sb = new StringBuffer();
		for (int i = 0, len = bytes.length; i < len; i++) {
			int value = bytes[i];
			sb.append((char) value);
		}

		return sb.toString();
	}

	private byte[] transform(boolean encrypt, byte[] inputBytes) throws Exception {
		byte[] key = DigestUtils.md5Digest(password.getBytes("utf-8"));
		BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESFastEngine()));
		cipher.init(encrypt, new KeyParameter(key));
		ByteArrayInputStream input = new ByteArrayInputStream(inputBytes);
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		int inputLen;
		int outputLen;

		byte[] inputBuffer = new byte[1024];
		byte[] outputBuffer = new byte[cipher.getOutputSize(inputBuffer.length)];

		while ((inputLen = input.read(inputBuffer)) > -1) {
			outputLen = cipher.processBytes(inputBuffer, 0, inputLen, outputBuffer, 0);
			if (outputLen > 0) {
				output.write(outputBuffer, 0, outputLen);
			}
		}

		outputLen = cipher.doFinal(outputBuffer, 0);
		if (outputLen > 0) {
			output.write(outputBuffer, 0, outputLen);
		}

		return output.toByteArray();
	}

	private String byte2hex(byte[] b) {
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}

	private byte[] hex2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 != 0) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
		}
		return b;
	}

	public static void main(String[] args) throws Exception {
		String decode = new AESCipher(XiuConstant.SAFE_CODE).decrypt("fb8afe312545f41b077bef9b7dab0e5e");
		System.out.println(decode);
	}

}