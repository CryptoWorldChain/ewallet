package com.fr.chain.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtil {
	public static String compress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		System.out.println("String length : " + str.length());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes());
		gzip.close();
		String outStr = out.toString("UTF-8");
		System.out.println("Output String lenght : " + outStr.length());
		return outStr;
	}

	public static String decompress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		System.out.println("Input String length : " + str.length());
		GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(
				str.getBytes("UTF-8")));
		BufferedReader bf = new BufferedReader(new InputStreamReader(gis,
				"UTF-8"));
		String outStr = "";
		String line;
		while ((line = bf.readLine()) != null) {
			outStr += line;
		}
		System.out.println("Output String lenght : " + outStr.length());
		return outStr;
	}

	public static void main(String[] args) throws IOException {
		String filePath = ".\response.txt";

		String string = "7777response.txt";
		System.out.println("after compress:");
		String compressed = compress(string);
		System.out.println(compressed);
		System.out.println("after decompress:");
		String decomp = decompress(compressed);
		System.out.println(decomp);

	}

	public static String getFileData(String filepath)
			throws FileNotFoundException, IOException {
		BufferedReader bf = new BufferedReader(new FileReader(filepath));
		String data = "";
		String line;
		while ((line = bf.readLine()) != null) {
			data += line;
		}
		return data;
	}
}
