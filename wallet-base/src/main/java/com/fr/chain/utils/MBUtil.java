package com.fr.chain.utils;

import java.math.BigDecimal;

public class MBUtil {

	public static BigDecimal toBigDec(String v) {
		if (v != null) {
			try {
				Double db = Double.parseDouble(v);
				return BigDecimal.valueOf(db);
			} catch (NumberFormatException e) {
			}
		}
		return null;
	}

	public static Double toDbl(String v) {
		if (v != null) {
			try {
				return Double.parseDouble(v);
			} catch (NumberFormatException e) {
			}
		}
		return null;
	}
	public static Integer toInt(String v){
		if(v!=null){
			try {
				return Integer.parseInt(v);
			} catch (NumberFormatException e) {
			}
		}
		return null;
	}
}
