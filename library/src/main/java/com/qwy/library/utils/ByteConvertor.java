package com.qwy.library.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 字节数据转换器
 */
public class ByteConvertor {

    /**
     * int转换为小端byte[]（高位放在高地址中）
     *
     * @param value 需要被转换的整型
     * @return byte[]
     */
    public static byte[] int2BytesLE(int value) {
        byte[] rst = new byte[4];
        // 先写int的最后一个字节
        rst[0] = (byte) (value & 0xFF);
        // int 倒数第二个字节
        rst[1] = (byte) ((value & 0xFF00) >> 8);
        // int 倒数第三个字节
        rst[2] = (byte) ((value & 0xFF0000) >> 16);
        // int 第一个字节
        rst[3] = (byte) ((value & 0xFF000000) >> 24);
        return rst;
    }

    /**
     * int转换为小端byte[]（高位放在低地址中）
     *
     * @param value 需要被转换的整型
     * @return byte[]
     */
    public static byte[] int2BytesBE(int value) {
        byte[] byteSrc = new byte[4];
        byteSrc[0] = (byte) ((value & 0xFF000000) >> 24);
        byteSrc[1] = (byte) ((value & 0x00FF0000) >> 16);
        byteSrc[2] = (byte) ((value & 0x0000FF00) >> 8);
        byteSrc[3] = (byte) ((value & 0x000000FF));
        return byteSrc;
    }

    /**
     * 转换String为小段byte[]
     *
     * @param str 传入需要被转换的字符串
     * @return 小端的byte[]
     */
    public static byte[] string2BytesLE(String str) {
        if (str == null) {
            return null;
        }
        char[] chars = str.toCharArray();
        byte[] rst = chars2BytesLE(chars);
        return rst;
    }

    /**
     * 转换字符数组为定长byte[]
     *
     * @param chars 字符数组
     * @return 若指定的定长不足返回null, 否则返回byte数组
     */
    public static byte[] chars2BytesLE(char[] chars) {
        if (chars == null) {
            return null;
        }
        int iCharCount = chars.length;
        byte[] rst = new byte[iCharCount * 2];
        for (int i = 0; i < iCharCount; i++) {
            rst[i * 2] = (byte) (chars[i] & 0xFF);
            rst[i * 2 + 1] = (byte) ((chars[i] & 0xFF00) >> 8);
        }
        return rst;
    }

    /**
     * 转换byte数组为int（小端）
     *
     * @return int
     * @apiNote 数组长度至少为4, 按小端方式转换, 即传入的bytes是小端的, 按这个规律组织成int
     */
    public static int bytes2IntLE(byte[] bytes) {
        if (bytes.length < 4) {
            return -1;
        }
        int iRst = (bytes[0] & 0xFF);
        iRst |= (bytes[1] & 0xFF) << 8;
        iRst |= (bytes[2] & 0xFF) << 16;
        iRst |= (bytes[3] & 0xFF) << 24;
        return iRst;
    }

    /**
     * 转换byte数组为int（大端）
     *
     * @return int
     * @apiNote 数组长度至少为4, 按小端方式转换, 即传入的bytes是大端的, 按这个规律组织成int
     */
    public static int bytes2IntBE(byte[] bytes) {
        if (bytes.length < 4) {
            return -1;
        }
        int iRst = (bytes[0] << 24) & 0xFF;
        iRst |= (bytes[1] << 16) & 0xFF;
        iRst |= (bytes[2] << 8) & 0xFF;
        iRst |= bytes[3] & 0xFF;
        return iRst;
    }

    /**
     * 转换byte数组为int
     *
     * @param bytes 传入byte[]
     * @return int
     */
    public static int bytes2Int(byte[] bytes) {
        return bytes[3] & 0xFF | (bytes[2] & 0xFF) << 8 | (bytes[1] & 0xFF) << 16 | (bytes[0] & 0xFF) << 24;
    }

    /**
     * 转换byte数组为Char（小端）
     *
     * @return char
     * @apiNote 数组长度至少为2, 按小端方式转换
     */
    public static char bytes2CharLE(byte[] bytes) {
        if (bytes.length < 2) {
            return (char) -1;
        }
        int iRst = (bytes[0] & 0xFF);
        iRst |= (bytes[1] & 0xFF) << 8;
        return (char) iRst;
    }

    /**
     * 转换byte数组为char（大端）
     *
     * @return char
     * @apiNote 数组长度至少为2, 按小端方式转换
     */
    public static char bytes2CharBE(byte[] bytes) {
        if (bytes.length < 2) {
            return (char) -1;
        }
        int iRst = (bytes[0] << 8) & 0xFF;
        iRst |= bytes[1] & 0xFF;
        return (char) iRst;
    }

    /**
     * 合并两个字节数组
     *
     * @param bytes1 字节数组1
     * @param bytes2 字节数组2
     * @return byte[] 合并后的字节数组
     */
    public static byte[] byteMerger(byte[] bytes1, byte[] bytes2) {
        if (bytes1.length == 0) {
            return bytes2;
        } else if (bytes2.length == 0) {
            return bytes1;
        }
        byte[] mergedBytes = new byte[bytes1.length + bytes2.length];
        System.arraycopy(bytes1, 0, mergedBytes, 0, bytes1.length);
        System.arraycopy(bytes2, 0, mergedBytes, bytes1.length, bytes2.length);
        return mergedBytes;
    }

    /**
     * 合并多个字节数组
     *
     * @param bytes 字节数组
     * @return byte[] 合并后的字节数组
     */
    public static byte[] byteMerger(byte[]... bytes) {
        byte[] mergedBytes = new byte[0];
        for (byte[] b : bytes) {
            if (b != null) {
                mergedBytes = byteMerger(mergedBytes, b);
            }
        }
        return mergedBytes;
    }

    /**
     * 将16进制的字符串转换为字节数组
     *
     * @param message 16进制字符串
     * @return byte[]
     */
    public static byte[] hexStr2Bytes(String message) {
        int len = message.length() / 2;
        char[] chars = message.toCharArray();
        String[] hexStr = new String[len];
        byte[] bytes = new byte[len];
        for (int i = 0, j = 0; j < len; i += 2, j++) {
            hexStr[j] = "" + chars[i] + chars[i + 1];
            bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
        }
        return bytes;
    }

    /**
     * bytes转换成十六进制字符串
     *
     * @param b byte数组
     * @return String 每个Byte值之间空格分隔
     */
    public static String bytes2HexStr(byte[] b) {
        String tmp;
        StringBuilder sb = new StringBuilder();
        if (b == null) return null;
        for (byte aB : b) {
            tmp = Integer.toHexString(aB & 0xFF);
            sb.append((tmp.length() == 1) ? "0" + tmp : tmp).append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }

    /**
     * 这个是把文件变成二进制流
     *
     * @param imagepath
     * @return
     * @throws Exception
     */
    public static byte[] readStream(String imagepath) throws Exception {
        FileInputStream fs = new FileInputStream(imagepath);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while (-1 != (len = fs.read(buffer))) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        fs.close();
        return outStream.toByteArray();
    }

    /**
     * 根据byte数组生成文件
     *
     * @param bytes 生成文件用到的byte数组
     */
    public static void createFileWithByte(String path, byte[] bytes) {
        // TODO Auto-generated method stub
        /**
         * 创建File对象，其中包含文件所在的目录以及文件的命名
         */
        File file = new File(path);
        // 创建FileOutputStream对象
        FileOutputStream outputStream = null;
        // 创建BufferedOutputStream对象
        BufferedOutputStream bufferedOutputStream = null;
        try {
            // 如果文件存在则删除
            if (file.exists()) {
                file.delete();
            }
            // 在文件系统中根据路径创建一个新的空文件
            file.createNewFile();
            // 获取FileOutputStream对象
            outputStream = new FileOutputStream(file);
            // 获取BufferedOutputStream对象
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            // 往文件所在的缓冲输出流中写byte数据
            bufferedOutputStream.write(bytes);
            // 刷出缓冲输出流，该步很关键，要是不执行flush()方法，那么文件的内容是空的。
            bufferedOutputStream.flush();
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
        } finally {
            // 关闭创建的流对象
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    /**
     * 字节数组转换为十六进制字符串
     *
     * @param b byte[] 需要转换的字节数组
     * @return String 十六进制字符串
     */
    public static final String byte2hex(byte b[]) {
        if (b == null) {
            throw new IllegalArgumentException(
                    "Argument b ( byte array ) is null! ");
        }
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xff);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    // 十六进制转二进制
    public static String HToB(String a) {
        String b = Integer.toBinaryString(Integer.valueOf(a, 16));
        System.out.println("二进制" + b);
        if (b.length() < 8) {
            int c = 8 - b.length();
            String ll = "";
            for (int i = 0; i < c; ++i) {
                ll += "0";
            }
            b = ll + b;
        }
        return b;
    }

    /**
     * 二进制转16进制
     *
     * @param b
     * @return
     */
    public static String BToH(String b) {

        int ten = Integer.parseInt(b, 2);
        String sixteen = Integer.toHexString(ten);
        return sixteen;
    }

    /**
     * byte转String（ascii）
     *
     * @param bytes
     * @return
     */
    public static String asciiToString(byte[] bytes) {
        String str;
        StringBuffer sb = new StringBuffer();
        byte[] tBytes = bytes;
        char[] tChars = new char[tBytes.length];
        for (int i = 0; i < tBytes.length; i++)
            tChars[i] = (char) tBytes[i];
        sb.append(tChars);
        str = sb.toString();

        return str;
    }

    /**
     * String转byte（ascii）
     *
     * @param str
     * @return
     */
    public static byte[] StringToascii(String str) {
        try {
            byte[] bytes = str.getBytes("US-ASCII");
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将10进制转16进制
     *
     * @param byte10
     * @return
     */
    public static String toByte16(int byte10) {
        String sixteen = Integer.toHexString(byte10);
        if (sixteen.length() == 1) {
            sixteen = "0" + sixteen;
        }
        return sixteen;
    }

    /**
     * 16进制转int(10进制)
     *
     * @param content
     * @return
     */
    public static int getInt(String content) {
        return Integer.parseInt(content, 16);
    }
}
