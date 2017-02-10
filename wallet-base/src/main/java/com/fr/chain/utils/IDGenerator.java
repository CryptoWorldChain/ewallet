package com.fr.chain.utils;

import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

public class IDGenerator {
	/**
	 * generateId，生成Id
	 * 
	 * @return String 返回GUID字符串
	 */
	public synchronized String generate() {
		return new StringBuffer(36).append(IPString).append(JVMString)
				.append(format(getHiTime())).append(format(getLoTime()))
				.append(format(getCount())).toString();
	}

	/**
	 * 单例对象的获取函数
	 * 
	 * 
	 * @return GUIDHexGenerator
	 */
	public static IDGenerator getInstance() {
		if (instance == null) {
			synchronized (IDGenerator.class) {
				if (instance == null) {
					instance = new IDGenerator();
				}
			}
		}
		return instance;
	}
	public static String nextID()
	{
		return getInstance().generate();
	}

	private static volatile IDGenerator instance;

	private final int IP;
	{
		int ipadd;
		try {
			ipadd = toInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}

	private final String IPString = format(IP);

	private static short counter = (short) 0;

	private final int JVM = (int) (System.currentTimeMillis() >>> 8);

	private final String JVMString = format(JVM);

	/**
	 * 通过JVM使id好对于不同的JVM不重复，除非它们同时装载这个类 Unique across JVMs on this machine
	 * (unless they load this class in the same quater second - very unlikely)
	 */
	protected int getJVM() {
		return JVM;
	}

	/**
	 * Unique in a millisecond for this JVM instance (unless there are
	 * Short.MAX_VALUE instances created in a millisecond)
	 */

	protected short getCount() {
		counter++;
		if (counter < 0) {
			counter = 0;
		}
		return counter;
	}

	/**
	 * IP地址使局域网内唯一，如果你用网卡物理号则全球唯一了：）
	 */
	protected int getIP() {
		return IP;
	}

	/**
	 * Unique down to millisecond
	 */

	protected short getHiTime() {
		short hiTime = (short) ((System.currentTimeMillis() >>> 32) & 0xFFFF);
		return hiTime;
	}

	protected int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	// private static final String SEPERATOR = "";

	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	private int toInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	public static byte[] toBytes(short value) {
		byte[] result = new byte[2];
		result[1] = (byte) ((0xFFl & value) + Byte.MIN_VALUE);
		result[0] = (byte) ((0xFFl & (value >>> 8)) + Byte.MIN_VALUE);
		return result;
	}

	public static void main(String[] str) {
		Set<String> set = new HashSet<String>();
		for(int i=0;i<4000000;i++)
			set.add(IDGenerator.getInstance().generate());
		
		System.out.println(set.size());
	}
}
