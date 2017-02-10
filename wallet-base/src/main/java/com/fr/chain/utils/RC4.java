package com.fr.chain.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.crypto.params.KeyParameter;

public class RC4{
    public int[] box = new int[256];
 
    public RC4(String key){
        byte[] k = key.getBytes();
        int i = 0, x = 0, t = 0, l = k.length;
 
        for(i=0; i<256; i++){
            box[i] = i;
        }
 
        for(i=0; i<256; i++){
            x = (x+box[i]+k[i%l]) % 256;
 
            t = box[x];
            box[x] = box[i];
            box[i] = t;
        }
    }
 
    public byte[] make(byte[] data){
        int t, o, i=0, j = 0, l = data.length;
        byte[] out = new byte[l];
        int[] ibox = new int[256];
        System.arraycopy(box, 0, ibox, 0, 256);
 
        for(int c=0; c<l; c++){
            i = (i+1) % 256;
            j = (j+ibox[i]) % 256;
 
            t = ibox[j];
            ibox[j] = ibox[i];
            ibox[i] = t;
 
            o = ibox[(ibox[i] + ibox[j]) % 256];
            out[c] = (byte)(data[c] ^ o);
        }
        return out;
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
		String key = "CHONGTIXITONG";
		String logpwd = "more1";
		String rc4Encrypt = rc4(key, logpwd);
		
		System.out.println(rc4Encrypt);
		
		String base64Encrypt = new String(Base64.encodeBase64(rc4Encrypt.getBytes("gbk")),"gbk");
		System.out.println(base64Encrypt);
		
	}
    
    static String rc4(String key, String logpwd) throws UnsupportedEncodingException{
    	RC4Engine engine = new RC4Engine();
		CipherParameters parameters = new KeyParameter(key.getBytes("gbk"));
		engine.init(false, parameters);
		byte[]  bytes = new byte[logpwd.getBytes("gbk").length];
		for(int i=0;i< logpwd.getBytes("gbk").length;i++) {
			bytes[i] = engine.returnByte(logpwd.getBytes("gbk")[i]);
		}
		
		return new String(bytes,"gbk");
    }
}