/**
 * 请勿将俱乐部专享资源复制给其他人，保护知识产权即是保护我们所在的行业，进而保护我们自己的利益
 * 即便是公司的同事，也请尊重 JFinal 作者的努力与付出，不要复制给同事
 * 
 * 如果你尚未加入俱乐部，请立即删除该项目，或者现在加入俱乐部：http://jfinal.com/club
 * 
 * 俱乐部将提供 jfinal-club 项目文档与设计资源、专用 QQ 群，以及作者在俱乐部定期的分享与答疑，
 * 价值远比仅仅拥有 jfinal club 项目源代码要大得多
 * 
 * JFinal 俱乐部是五年以来首次寻求外部资源的尝试，以便于有资源创建更加
 * 高品质的产品与服务，为大家带来更大的价值，所以请大家多多支持，不要将
 * 首次的尝试扼杀在了摇篮之中
 */

package com.jfinal.club.common.kit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * SqlKit
 */
public class SqlKit {

	/**
	 * 将 id 列表 join 起来，用逗号分隔，并且用小括号括起来
	 */
	public static void joinIds(List<Integer> idList, StringBuilder ret) {
		ret.append("(");
		boolean isFirst = true;
		for (Integer id : idList) {
			if (isFirst) {
				isFirst = false;
			} else {
				ret.append(", ");
			}
			ret.append(id.toString());
		}
		ret.append(")");
	}

	/**
	 * 将 id 列表 join 起来，用逗号分隔，并且用小括号括起来
	 */
	public static void joinIds(String[] idList, StringBuilder ret) {
		ret.append("(");
		boolean isFirst = true;
		for (String id : idList) {
			if (isFirst) {
				isFirst = false;
			} else {
				ret.append(", ");
			}
			ret.append(id.toString());
		}
		ret.append(")");
	}

	/**
	 * 将 id 列表 join 起来，用逗号分隔，并且用小括号括起来
	 */
	public static void joinIds(Integer[] idList, StringBuilder ret) {
		ret.append("(");
		boolean isFirst = true;
		for (int id : idList) {
			if (isFirst) {
				isFirst = false;
			} else {
				ret.append(", ");
			}
			ret.append(id);
		}
		ret.append(")");
	}

	public static void joinIds(ArrayList<String> idList, StringBuilder ret) {
		ret.append("(");
		boolean isFirst = true;
		for (String id : idList) {
			if (isFirst) {
				isFirst = false;
			} else {
				ret.append(", ");
			}
			ret.append("'").append(id).append("'");
		}
		ret.append(")");
	}

	public static void joinIds(Set<Integer> idList, StringBuilder ret) {
		ret.append("(");
		boolean isFirst = true;
		Iterator<Integer> iterator = idList.iterator();
		while (iterator.hasNext()) {
			if (isFirst) {
				isFirst = false;
			} else {
				ret.append(", ");
			}
			ret.append("'").append(iterator.next()).append("'");
		}

		ret.append(")");
	}
}
