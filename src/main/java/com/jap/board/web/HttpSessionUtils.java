package com.jap.board.web;

import javax.servlet.http.HttpSession;

import com.jap.board.domain.User;

public class HttpSessionUtils {

	public static final String USER_SESSION_KEY = "sessionedUser";

	public static boolean isLoginUser(HttpSession session) {

		Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
		System.out.println(sessionedUser);
		if (sessionedUser == null) {
			return false;
		}

		return true;
	}

	public static User getUserFromSession(HttpSession session) {

		if (!isLoginUser(session)) {
			return null;
		}

		return (User) session.getAttribute(USER_SESSION_KEY);
	}
}
