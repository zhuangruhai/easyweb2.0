
package com.hichlink.easyweb.portal.common.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.validator.GenericValidator;

import com.hichlink.easyweb.portal.common.exception.PasswordException;

import sun.misc.BASE64Encoder;

/**
 * 成员密码工具.
 * 
 * <pre>
 *          使用PBEWithMD5AndDES算法，根据成员ID生成公钥.
 *          加入一固定八位字符串作为加密算法的盐，增加破解难度.
 *          使用MD5对经过加密的密码进行处理.
 *          使用Base64编码对消息摘要进行编码.
 * </pre>
 */
public final class PasswordUtil {

	/**
	 * 密码强度枚举类.
	 * <ul>
	 * <li>NONE: 不做任何限制</li>
	 * <li>SIMPLE: 数字和大小写字母组成，并且必须同时包含数字和字母</li>
	 * <li>COMPLEX: 数字、字母、特殊符号组成，并且必须同时包含数字和字母</li>
	 * <li>VERY_COMPLEX: 数字、字母、特殊符号组成，并且必须同时包含数字、字母、特殊符号</li>
	 * </ul>
	 */
	public enum PasswordComplex {
	    
		/**
		 * 密码强度类型.
		 */
		NONE, SIMPLE, COMPLEX, VERY_COMPLEX;

		/**
		 * 获取枚举值的描述，主要用于iBatis的SQLMap.
		 * @return 枚举值的描述
		 */
		public String getValue() {
			return this.toString();
		}
	}
	
    private static final String CHAR_NUMBER = "0123456789";
	private static final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	private static final String CHAR_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String CHAR_LETTERS = CHAR_LOWERCASE + CHAR_UPPERCASE;
	private static final String CAHR_SPECIAL = "?!@#+=%^*";

    private static final byte normalChars[]={
        65,66,67,68,69,70,71,72,73,74,75,76,77,78,
        79,80,81,82,83,84,85,86,87,88,89,90,97,98,
        99,100,101,102,103,104,105,106,107,108,
        109,110,111,112,113,114,115,116,117,118,
        119,120,121,122
    };

    private static final byte digitChars[]={
     48,49,50,51,52,53,54,55,56,57
    };

    private static final char specialChars[]={
        '+','-','*','/',',','.','@','_','=','>',
        '<','?','#','$','%','&','(',')','!','~'
    };

    /**
     * 加密算法.
     */
    private static final String ALGORITHM_PBEWithMD5AndDES = "PBEWithMD5AndDES";

    /**
     * 八位字符数组（盐）.
     */
    private static final byte[] SALT = { 0x41, 0x52, 0x67, 0x36, 0x30, 0x53, 0x63, 0x47 };
    
    /**
     * 伪随机序列的种子
     */
    private static long seed = System.currentTimeMillis();

    
    private static int passwordMinLength = 6;
    private static int passwordMaxLength = 20;
    private static String passwordRule = "NONE";
    /**
     * 私有构造方法.
     */
    private PasswordUtil() {
    }

    /**
     * 构造密码，通过调用verifyPasswordLength、verifyPasswordComplex和cryptPassword实现.
     * @param staffId 成员ID，使用成员ID作为Key
     * @param password 密码（明文）
     * @return 加密后的密码
     * @throws PasswordNotStrongException 密码强度不够异常
     */
    public static String buildPassword(final String staffId, final String password) throws PasswordException {
        verifyPasswordLength(password);
        verifyPasswordComplex(password);
        return cryptPassword(staffId, password);
    }
    
    /**
     * 构造密码，通过调用cryptPassword实现.
     * @param staffId 成员ID，使用成员ID作为Key
     * @param password 密码（明文）
     * @return 加密后的密码
     */
    public static String buildPasswordWithoutVerify(final String staffId, final String password) {
    	return cryptPassword(staffId, password);
    }

    /**
     * 密码加密.
     * @param staffId 成员ID，使用成员ID作为Key
     * @param password 密码（明文）
     * @return 加密后的密码
     */
    public static String cryptPassword(final String key, final String password) {
        PBEKeySpec publicKey = null;
        SecretKey secretKey = null;
        PBEParameterSpec parameter = null;
        // 明码编码
        String encoding = "GBK";
        try {
            // 生成公钥，使用成员ID
            publicKey = new PBEKeySpec(key.toCharArray());
            // 根据算法获取私钥工厂
            SecretKeyFactory factory = SecretKeyFactory.getInstance(PasswordUtil.ALGORITHM_PBEWithMD5AndDES);
            // 产生私钥
            secretKey = factory.generateSecret(publicKey);
            // 根据算法获取加密器
            Cipher cipher = Cipher.getInstance(PasswordUtil.ALGORITHM_PBEWithMD5AndDES);
            // 获取加密参数
            parameter = new PBEParameterSpec(PasswordUtil.SALT, 1000);
            // 初始化加密器
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameter);
            // 根据编码获取明码的字符数组
            byte[] passwordByte = password.getBytes(encoding);
            // 加密器进行加密，得到加密后的字符数组
            byte[] encrypedPasswordByte = cipher.doFinal(passwordByte);
            // 创建消息摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 生成密码的消息摘要
            byte[] digistPasswordByte = md.digest(encrypedPasswordByte);
            // 创建Base64编码器
            BASE64Encoder encoder = new BASE64Encoder();
            // 对消息摘要进行Base64编码
            String encyrpedPassword = encoder.encode(digistPasswordByte);
            // 替换编码后的特殊字符，去掉\r和\n
            encyrpedPassword =  encyrpedPassword.replace('\r', ' ').replace('\n', ' ').replaceAll(" ", "");
            return encyrpedPassword;
        } catch (NoSuchAlgorithmException e) {
            throw new PasswordException("加密算法：" + PasswordUtil.ALGORITHM_PBEWithMD5AndDES + "不存在！", e);
        } catch (InvalidKeySpecException e) {
            throw new PasswordException("公钥" + publicKey.toString() + "不正确！", e);
        } catch (NoSuchPaddingException e) {
            throw new PasswordException("不支持" + PasswordUtil.ALGORITHM_PBEWithMD5AndDES + "加密算法的Padding！", e);
        } catch (InvalidKeyException e) {
            throw new PasswordException("加密算法：" + PasswordUtil.ALGORITHM_PBEWithMD5AndDES + "的私钥" + secretKey
                    + "不正确！", e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new PasswordException("加密算法：" + PasswordUtil.ALGORITHM_PBEWithMD5AndDES + "的参数" + parameter
                    + "不正确！", e);
        } catch (UnsupportedEncodingException e) {
            throw new PasswordException("不支持" + encoding + "编码！", e);
        } catch (IllegalBlockSizeException e) {
            throw new PasswordException("加密块大小不正确！", e);
        } catch (BadPaddingException e) {
            throw new PasswordException("加密填充异常！", e);
        }
    }

    /**
     * 验证密码长度是否符合规则.
     * @param password 密码（未加密）
     * @return 密码是否符合长度范围
     * @throws PasswordException 密码强度不够异常
     */
    public static boolean verifyPasswordLength(final String password) throws PasswordException {
        if (password == null) {
            return false;
        }
//        int minLength = SecurityConfig.getConfig().getInt("password.minimum-length");
//        int maxLength = SecurityConfig.getConfig().getInt("password.maximum-length");
//        int minLength = SecurityConfig.getSecurityConfig().getPasswordMinLength();
//        int maxLength = SecurityConfig.getSecurityConfig().getPasswordMaxLength();
        
        if (password.length() < passwordMinLength) {
            throw new PasswordException("密码长度不能小于" + passwordMinLength + "位");
        }
        if (password.length() > passwordMaxLength) {
            throw new PasswordException("密码长度不能大于" + passwordMaxLength + "位");
        }
        return true;
    }

    /**
     * 验证密码复杂度是否符合规则.
     * @param password 密码明文
     * @return 是否 符合密码复杂度规则
     * @throws PasswordException 密码强度不够异常
     */
    public static boolean verifyPasswordComplex(final String password) throws PasswordException {
        if (password == null) {
            return false;
        }
        boolean result = false;
//        String rule = SecurityConfig.getConfig().getString("password.complex-rule");
//        String rule = SecurityConfig.getSecurityConfig().getPasswordComplexRule();
        switch (PasswordComplex.valueOf(passwordRule)) {
        case SIMPLE:
            // 数字+字母
            if (GenericValidator.matchRegexp(password, "^[a-zA-Z0-9]+$")
                    && GenericValidator.matchRegexp(password, "[a-zA-Z]+")
                    && GenericValidator.matchRegexp(password, "[0-9]+")) {
                result = true;
                break;
            } else {
                throw new PasswordException("您的密码不符合复杂度规则，密码必须由数字和字母组成，请修改密码");
            }
        case COMPLEX:
            // 数字+字母+[特殊符号]
            if (GenericValidator.matchRegexp(password, "[a-zA-Z]+") && GenericValidator.matchRegexp(password, "[0-9]+")) {
                result = true;
                break;
            } else {
                throw new PasswordException("您的密码不符合复杂度规则，密码必须同时包含数字和字母，请修改密码");
            }
        case VERY_COMPLEX:
            // 数字+字母+特殊符号
            if (GenericValidator.matchRegexp(password, "[0-9]+") && GenericValidator.matchRegexp(password, "[a-zA-Z]+")
                    && GenericValidator.matchRegexp(password, "[^a-zA-Z0-9]+")) {
                result = true;
                break;
            } else {
                throw new PasswordException("您的密码不符合复杂度规则，密码必须同时包含数字、字母和特殊字符，请修改密码");
            }
        default:
            // 不验证
            result = true;
            break;
        }
        return result;
    }

    public static String generatePassword(){
//        int len = SecurityConfig.getConfig().getInt("password.minimum-length");
//    	int len = SecurityConfig.getSecurityConfig().getPasswordMinLength();
        //int len = getPasswordMinLen();
		char[] chars = new char[passwordMinLength];
//		String rule = SecurityConfig.getConfig().getString("password.complex-rule");
//		String rule = SecurityConfig.getSecurityConfig().getPasswordComplexRule();
		int r = 0;
        switch (PasswordComplex.valueOf(passwordRule)) {
			case NONE:
				for(int i = 0; i < passwordMinLength; i++){
					r = getRandom(0, (CHAR_NUMBER + CHAR_LOWERCASE).length()-1);
                    //System.out.println("i:::"  + i);
                    //System.out.println("r="  + r);
                    chars[i] = (CHAR_NUMBER + CHAR_LOWERCASE).charAt(r);
                    //System.out.println("char[" + i + "]=" + chars[i]);
                }
				break;
			case SIMPLE:
			    if(passwordMinLength < 2){
			        throw new RuntimeException("简单强度的密码最小长度应该大于等于2。当前设置"+passwordMinLength);
			    }
				r = getRandom(0, CHAR_NUMBER.length()-1);
				chars[0] = CHAR_NUMBER.charAt(r);
				r = getRandom(0, CHAR_LETTERS.length()-1);
				chars[1] = CHAR_LETTERS.charAt(r);
				for(int i = 2; i < passwordMinLength; i++){
					r = getRandom(0, (CHAR_NUMBER + CHAR_LETTERS).length()-1);
					chars[i] = (CHAR_NUMBER + CHAR_LETTERS).charAt(r);
				}
				break;
			case COMPLEX:
                if(passwordMinLength<2){
                    throw new RuntimeException("复杂强度的密码最小长度应该大于等于2。当前设置"+passwordMinLength);
                }
				r = getRandom(0, CHAR_NUMBER.length()-1);
				chars[0] = CHAR_NUMBER.charAt(r);
				r = getRandom(0, CHAR_LETTERS.length()-1);
				chars[1] = CHAR_LETTERS.charAt(r);
				for(int i = 2; i < passwordMinLength; i++){
					r = getRandom(0, (CHAR_NUMBER + CHAR_LETTERS + CAHR_SPECIAL).length()-1);
					chars[i] = (CHAR_NUMBER + CHAR_LETTERS + CAHR_SPECIAL).charAt(r);
				}
				break;
			case VERY_COMPLEX:
                if(passwordMinLength<3){
                    throw new RuntimeException("非常复杂强度的密码最小长度应该大于等于3。当前设置"+passwordMinLength);
                }
				r = getRandom(0, CHAR_NUMBER.length()-1);
				chars[0] = CHAR_NUMBER.charAt(r);
				r = getRandom(0, CHAR_LETTERS.length()-1);
				chars[1] = CHAR_LETTERS.charAt(r);
				r = getRandom(0, CAHR_SPECIAL.length()-1);
				chars[2] = CAHR_SPECIAL.charAt(r);
				for(int i = 3; i < passwordMinLength; i++){
					r = getRandom(0, (CHAR_NUMBER + CHAR_LETTERS + CAHR_SPECIAL).length()-1);
					chars[i] = (CHAR_NUMBER + CHAR_LETTERS + CAHR_SPECIAL).charAt(r);
				}
				break;
		}
		return mix(chars);
    }


    public static String autoGeneratePassword(){
//        String rule = SecurityConfig.getConfig().getString("password.complex-rule");
//        int passwordminLen = SecurityConfig.getConfig().getInt("password.minimum-length");
//        int passwordmaxLen = SecurityConfig.getConfig().getInt("password.maximum-length");
//    	String rule = SecurityConfig.getSecurityConfig().getPasswordComplexRule();
//        int passwordminLen = SecurityConfig.getSecurityConfig().getPasswordMinLength();
//        int passwordmaxLen = SecurityConfig.getSecurityConfig().getPasswordMaxLength();
        Random random=new Random();

        //长度在最大长度和最小长度之间随机获取
        int passwordLen=passwordMinLength+random.nextInt(passwordMaxLength-passwordMinLength);

        //数字长度为总字符数的1/3
        int digitsize=passwordLen/3;
        //特殊字符长度为总字符数1/3
        int specialsize=passwordLen/3;

        String strPassword="";
        if(PasswordComplex.valueOf(passwordRule)== PasswordComplex.NONE){
        	passwordRule = "SIMPLE";
        }

        switch(PasswordComplex.valueOf(passwordRule)){
            case SIMPLE:
                //数字+字母
                //获取字母
                for(int i=0;i<passwordLen-digitsize;i++)
                    strPassword+=(char)normalChars[random.nextInt(normalChars.length)];
                //获取数字
                for(int i=0;i<digitsize;i++)
                    strPassword+=(char)digitChars[random.nextInt(digitChars.length)];
                break;
            case COMPLEX:
                //数字+字母+特殊字符
                for(int i=0;i<passwordLen-digitsize-specialsize;i++)
                    strPassword+=(char)normalChars[random.nextInt(normalChars.length)];
                //获取数字
                for(int i=0;i<digitsize;i++)
                    strPassword+=(char)digitChars[random.nextInt(digitChars.length)];
                //获取特殊字符
                for(int i=0;i<specialsize;i++)
                    strPassword+=(char)specialChars[random.nextInt(specialChars.length)];
                break;
           case VERY_COMPLEX:
                //数字+字母+特殊字符
                for(int i=0;i<passwordLen-digitsize-specialsize;i++)
                    strPassword+=(char)normalChars[random.nextInt(normalChars.length)];
                //获取数字
                for(int i=0;i<digitsize;i++)
                    strPassword+=(char)digitChars[random.nextInt(digitChars.length)];
                //获取特殊字符
                for(int i=0;i<specialsize;i++)
                    strPassword+=(char)specialChars[random.nextInt(specialChars.length)];
                break;
        }
        return mix(strPassword.toCharArray());
    }

    private static String mix(char[] chars){
		char[] newchars = new char[chars.length];
		for(int i = 0; i < newchars.length; i++){
			newchars[i] = ' ';
		}
		for(int i = 0; i < chars.length; i++){
			int r = getRandom(0, newchars.length - 1);
			while(newchars[r] != ' '){
				r = getRandom(0, newchars.length - 1);
			}
			newchars[r] = chars[i];
		}
		return new String(newchars);
	}

    private static int getRandom(int min, int max) {
        //long seed = System.currentTimeMillis();
        //int seed = (int) (Math.random() * 40) + 1;

        Random random = new Random(seed);
        seed += random.nextLong();

        return min + random.nextInt(max - min + 1);
	}


    public static void main(String[] args){

        if (args.length != 2) {
            printHelp();
            System.exit(-1);
        }

        try {
            String staffId = args[0];
            String pwd = args[1];
            String crypPwd= PasswordUtil.cryptPassword(staffId, pwd);
            System.out.println("加密后的密码：" + crypPwd);
        } catch (Exception e) {
            System.out.println("生产加密后的密码出错");
            System.out.println(e);
            System.exit(-1);
        }


    }


    /**
     * 输出打印.
     */
    private static void printHelp() {
        StringBuffer buf = new StringBuffer();
        buf.append("用法:\n passwordGenerator.bat <用户ID> <未加密密码>\n");
        buf.append("例如:\n");
        buf.append("PasswordUtil.bat user01 user001\n");
        System.out.println(buf.toString());
    }

}
