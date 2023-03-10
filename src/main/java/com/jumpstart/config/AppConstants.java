package com.jumpstart.config;

public class AppConstants {

	// web site available roles constants variables
	public static final Integer ADMIN_USER = 501;
	public static final Integer NORMAL_USER = 502;

	// pagination constants variables for user post
	public static final String POST_PAGE_NUMBER = "0";
	public static final String POST_PAGE_SIZE = "10";
	public static final String POST_SORT_BY = "pid";
	public static final String POST_SORT_DIR = "ASC";

	// pagination constants variables for stores
	public static final String STORE_PAGE_NUMBER = "0";
	public static final String STORE_PAGE_SIZE = "10";
	public static final String STORE_SORT_BY = "sid";
	public static final String STORE_SORT_DIR = "ASC";

	// slack website constants variables for user queries
	public static final String USER_HOOKS_URL = "https://hooks.slack.com/services/";
	public static final String USER_APP_URL = "T04S0S33RD0/B04S0PK5S11/SENFI2I6uTS9TFWFPTtmlbec";

	// slack website constants variables for admin respondent user queries
	public static final String ADMIN_HOOKS_URL = "https://hooks.slack.com/services/";
	public static final String ADMIN_APP_URL = "T04S0S33RD0/B04T5BESPAA/TIasB5qYHNpdzc4CaorPPcgZ";

}