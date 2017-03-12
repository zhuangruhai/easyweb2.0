package com.hichlink.easyweb.portal.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpSession;

/**
 * 校验数工具类.
 */
public final class CheckCodeUtil {

	private static int width = 90;// 定义图片的width
	private static int height = 30;// 定义图片的height
	private static int codeCount = 4;// 定义图片上显示验证码的个数
	private static int xx = 15;
	private static int fontHeight = 25;
	private static int codeY = 25;
	static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**
	 * Logger.
	 */
	// private static final Logger logger =
	// Logger.getLogger(CheckCodeUtil.class);

	/**
	 * 随机数名称.
	 */
	private static final String CHECK_CODE = "RANDOM_CHECK_CODE";
	private static final String CHECK_CODE_TIMESTAMP = "RANDOM_CHECK_CODE_TIMESTAMP";

	private static int DEFAULT_EXPIRE_TIME = 1000 * 60 * 5;

	/**
	 * 私有构造.
	 */
	private CheckCodeUtil() {
	}

	/**
	 * 创建位图.
	 * 
	 * @param session
	 *            session
	 * @param width
	 *            图象长度
	 * @param height
	 *            图象宽度
	 * @param randomLength
	 *            随机数长度
	 * @return 位图输入流
	 */
	public static BufferedImage createImage(HttpSession session) {
		String randomString = generateRamonWord();
		session.setAttribute(CHECK_CODE, randomString);
		session.setAttribute(CHECK_CODE_TIMESTAMP, new Long(System.currentTimeMillis()));

		return createImage(randomString);
	}

	/**
	 * 得到校验码.
	 * 
	 * @param session
	 *            session
	 * @return 校验码
	 */
	public static String getCheckCode(HttpSession session) throws Exception {
		// 校验码只能取一次,防止用户用旧的校验码访问
		String randomString = (String) session.getAttribute(CHECK_CODE);
		Long randomTimestamp = (Long) session.getAttribute(CHECK_CODE_TIMESTAMP);
		session.removeAttribute(CHECK_CODE);
		session.removeAttribute(CHECK_CODE_TIMESTAMP);
		long now = System.currentTimeMillis();
		// long expireTime = 1000 *
		// PortalConfig.getInstance().getCheckCodeExpireTime();
		// if((randomTimestamp + expireTime) < now ){
		// throw new CheckCodeExpiredException();
		// //throw new CheckCodeExpiredException("校验码必须在" + expireTime +
		// "秒内输入");
		// }
		return randomString;
	}

	public static boolean check(HttpSession session, String checkCode) {

		if (checkCode == null || "".equals(checkCode.trim())) {
			return false;
		}

		// 校验码只能取一次,防止用户用旧的校验码访问
		String randomString = (String) session.getAttribute(CHECK_CODE);
		Long randomTimestamp = (Long) session.getAttribute(CHECK_CODE_TIMESTAMP);
		if (randomString != null) {
			session.removeAttribute(CHECK_CODE);
			session.removeAttribute(CHECK_CODE_TIMESTAMP);
		} else {
			return false;
		}

		long now = System.currentTimeMillis();
		// long expireTime = 1000 *
		// PortalConfig.getInstance().getCheckCodeExpireTime();
		// System.out.println("randomTimestamp="+ randomTimestamp +",
		// expireTime=" + expireTime);
		if ((randomTimestamp + DEFAULT_EXPIRE_TIME) < now) {
			return false;
		}

		return checkCode.trim().equalsIgnoreCase(randomString);
	}

	private static BufferedImage createImage(String code) {
		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics gd = buffImg.getGraphics();
		// 创建一个随机数生成器类
		Random random = new Random();
		// 将图像填充为白色
		gd.setColor(Color.WHITE);
		gd.fillRect(0, 0, width, height);

		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
		// 设置字体。
		gd.setFont(font);

		// 画边框。
		gd.setColor(Color.BLACK);
		gd.drawRect(0, 0, width - 1, height - 1);

		// 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
		gd.setColor(Color.BLACK);
		for (int i = 0; i < 40; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			gd.drawLine(x, y, x + xl, y + yl);
		}

		int red = 0, green = 0, blue = 0;

		char[] codeCh = code.toCharArray();
		// 随机产生codeCount数字的验证码。
		for (int i = 0; i < codeCh.length; i++) {
			// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);

			// 用随机产生的颜色将验证码绘制到图像中。
			gd.setColor(new Color(red, green, blue));
			gd.drawString(String.valueOf(codeCh[i]), (i + 1) * xx, codeY);

		}

		return buffImg;
	}

	/**
	 * 生成随机字符
	 * 
	 * @param length
	 *            随机字符数长度
	 * @return
	 */
	private static String generateRamonWord() {

		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		StringBuffer randomCode = new StringBuffer();

		// 创建一个随机数生成器类
		Random random = new Random();

		// 随机产生codeCount数字的验证码。
		for (int i = 0; i < codeCount; i++) {
			// 得到随机产生的验证码数字。
			String code = String.valueOf(codeSequence[random.nextInt(36)]);
			// 将产生的四个随机数组合在一起。
			randomCode.append(code);
		}

		return randomCode.toString();
	}

}
