package top.wingcloud.common.encryption;

/**
 * 字节工具类
 **/
public class ByteUtils {
    public static String byte2HexString(byte[] b){
        int len = 0;
        if(null == b || (len = b.length) == 0){
            throw new NullPointerException("byte[] is empty.");
        }
        StringBuilder builder = new StringBuilder(len);
        String hexString = null;
        for (int i = 0; i < len; i++) {
            hexString = Integer.toHexString(b[i] & 0xFF);
            if(hexString.length() < 2){
                builder.append('0').append(hexString);
            }else {
                builder.append(hexString);
            }
        }
        return builder.toString();
    }


    public static byte[] hexString2Byte(String hexString){
        int len = 0;
        if(null == hexString || (len = hexString.length()) == 0){
            throw new NullPointerException("String is empty.");
        }
        hexString = hexString.toUpperCase();
        char[] c = hexString.toCharArray();
        len = len / 2;
        byte[] b = new byte[len];
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            b[i] = (byte)(char2Byte(c[pos]) << 4 | char2Byte(c[pos+1]));
        }
        return b;
    }


    public static byte[] intArray2ByteArray(int[] data, boolean includeLength) {
        int n;
        if (includeLength) {
            n = data[data.length - 1];
        } else {
            n = data.length << 2;
        }
        byte[] result = new byte[n];
        for (int i = 0; i < n; i++) {
            result[i] = (byte) (data[i >>> 2] >>> ((i & 3) << 3));
        }
        return result;
    }

    public static int[] byteArray2IntArray(byte[] data, boolean includeLength) {
        int n = (((data.length & 3) == 0) ? (data.length >>> 2)
                : ((data.length >>> 2) + 1));
        int[] result;
        if (includeLength) {
            result = new int[n + 1];
            result[n] = data.length;
        } else {
            result = new int[n];
        }
        n = data.length;
        for (int i = 0; i < n; i++) {
            result[i >>> 2] |= (0x000000ff & data[i]) << ((i & 3) << 3);
        }
        return result;
    }


    private static byte char2Byte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    private ByteUtils(){
        throw new AssertionError();
    }

}