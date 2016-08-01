package com.xiu.common.web.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.xiu.mobile.core.utils.MD5SignUtils;

/**
 * RSA 工具类。提供加密，解密，生成密钥对等方法。
 * 需要到http://www.bouncycastle.org下载bcprov-jdk14-123.jar。
 * 
 */
public class RSAUtil {
	
//	private static String RSAKeyStore = "E:/xiu/project/mop/trunk/xiu-mop/src/main/resources/RSAKey.txt";
	private static String RSAKeyStore = RSAUtil.class.getResource("/").getPath() + "RSAKey.txt";
	
	
	/**
	 * * 生成密钥对 *
	 * 
	 * @return KeyPair *
	 * @throws EncryptException
	 */
	public static KeyPair generateKeyPair() throws Exception {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA", new BouncyCastleProvider());
			final int KEY_SIZE = 1024;// 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
			keyPairGen.initialize(KEY_SIZE, new SecureRandom());
			KeyPair keyPair = keyPairGen.generateKeyPair();
			
			saveKeyPair(keyPair);
			
			return keyPair;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public static KeyPair getKeyPair() throws Exception {
		FileInputStream fis = new FileInputStream(RSAKeyStore);
		ObjectInputStream oos = new ObjectInputStream(fis);
		KeyPair kp = (KeyPair) oos.readObject();
		oos.close();
		fis.close();
		return kp;
	}

	public static void saveKeyPair(KeyPair kp) throws Exception {

		FileOutputStream fos = new FileOutputStream(RSAKeyStore);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		// 生成密钥
		oos.writeObject(kp);
		oos.close();
		fos.close();
	}

	/**
	 * * 生成公钥 *
	 * 
	 * @param modulus *
	 * @param publicExponent *
	 * @return RSAPublicKey *
	 * @throws Exception
	 */
	public static RSAPublicKey generateRSAPublicKey(byte[] modulus,
			byte[] publicExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA", new BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}

		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(
				modulus), new BigInteger(publicExponent));
		try {
			return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * * 生成私钥 *
	 * 
	 * @param modulus *
	 * @param privateExponent *
	 * @return RSAPrivateKey *
	 * @throws Exception
	 */
	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus,
			byte[] privateExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA", new BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}

		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(
				modulus), new BigInteger(privateExponent));
		try {
			return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * * 加密 *
	 * 
	 * @param key
	 *            加密的密钥 *
	 * @param data
	 *            待加密的明文数据 *
	 * @return 加密后的数据 *
	 * @throws Exception
	 */
	public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, pk);

			// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
			// 加密块大小为127
			// byte,加密后为128个byte;因此共有2个加密块，第一个127
			// byte第二个为1个byte
			int blockSize = cipher.getBlockSize();
			int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * blockSize > 0) {
				if (data.length - i * blockSize > blockSize)
					cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
				else
					// 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
					// ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
					// OutputSize所以只好用dofinal方法。
					cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);

				i++;
			}
			return raw;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * * 解密 *
	 * 
	 * @param key
	 *            解密的密钥 *
	 * @param raw
	 *            已经加密的数据 *
	 * @return 解密后的明文 *
	 * @throws Exception
	 */
	public static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
			int j = 0;

			while (raw.length - j * blockSize > 0) {
				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				j++;
			}
			return bout.toByteArray();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * * *
	 * 
	 * @param args *
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String test = "hello world 中"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doU}{ ()（）pdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把"
				+ "byte[]放到";
		byte[] en_test = encrypt(getKeyPair().getPublic(), test.getBytes());
		byte[] de_test = decrypt(getKeyPair().getPrivate(), en_test);
		System.out.println(new String(de_test));
		

		/********文件加密****/
		File filestr = new File("E://main.js");
		BufferedReader in = new BufferedReader(new FileReader(filestr));
		StringBuffer str=new StringBuffer();
		String st;
		while ((st = in.readLine()) != null) {
			str.append(st);
		}
		
		//MD5
		String result=MD5SignUtils.MD5Encode(str.toString());
		
		byte[] en_test1 = encrypt(getKeyPair().getPublic(), result.getBytes());
		in.close();
		System.out.println("main.js加密结果："+en_test1.toString());
		byte[] de_test1 = decrypt(getKeyPair().getPrivate(), en_test1);
		System.out.println(new String(de_test1));
		
			
	}
}
