package com.akolchin.stmg.client;

import java.util.Date;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.i18n.client.NumberFormat;

public class GwtUtils {
	private static final NumberFormat NUMBER_FORMATER = NumberFormat.getFormat("#,###");
	private static final NumberFormat FLOAT_FORMATER = NumberFormat.getFormat("#,##0.00");
	private static final NumberFormat PERCENT_FORMATER = NumberFormat.getFormat("0.00");
	private static final String MILLION_ABR = "m.";
	private static final String THOUSAND_ABR = "K";
	private static final long MILLION = 1000000;
	private static final long THOUSAND = 1000;

	// TODO we could add admin e-mail here
	private static final String NOTIFY_ADMIN_MSG = "Please notify system administrator.";
	// TODO move these msgs in local property i18n file
	private static final String SERVER_FAILURE_MSG = "Some error has occured on the server. " + NOTIFY_ADMIN_MSG;
	private static final String LOAD_DATA_FAILURE_MSG = "Some error has occured on the server. "
			+ "You could try to reload page to continue the work. " + NOTIFY_ADMIN_MSG;
	private static final String GET_PROVIDED_OBJECT_FAILURE_MSG = "Some client error has occured. " + NOTIFY_ADMIN_MSG;

	public static void removeAllChildren(Element element) {
		while (element.hasChildNodes())
			element.removeChild(element.getLastChild());
	}

	public static void removeDownSiblings(Element element) {
		Element parent = element.getParentElement();
		while (parent.hasChildNodes() && !parent.getLastChild().equals(element)) {
			Node child = parent.getLastChild();
			parent.removeChild(child);
		}
	}

	public static void replaceOrAddClass(Element element, String fromClassName, String toClassName) {
		String currClassName = element.getClassName();

		if (currClassName.contains(toClassName))
			return;
		else if (currClassName.contains(fromClassName))
			element.replaceClassName(fromClassName, toClassName);
		else
			element.addClassName(toClassName);

	}

	public static String formatAsLong(Long l) {
		if (l < THOUSAND)
			return NUMBER_FORMATER.format(l);
		else if (l < MILLION)
			return FLOAT_FORMATER.format(Float.valueOf(l) / THOUSAND) + THOUSAND_ABR;
		else
			return FLOAT_FORMATER.format(Float.valueOf(l) / MILLION) + MILLION_ABR;
	}

	public static String formatAsFloat(Float f) {
		if (f < THOUSAND)
			return FLOAT_FORMATER.format(f);
		else if (f < MILLION)
			return FLOAT_FORMATER.format(f / THOUSAND) + THOUSAND_ABR;
		else
			return FLOAT_FORMATER.format(f / MILLION) + MILLION_ABR;
	}

	public static String formatAsPercent(Float f) {
		return PERCENT_FORMATER.format(f) + "%";
	}

	public static Long getToday() {
		return new Date().getTime();
	}

	/**
	 * Use this message when loading data was failed. 
	 */
	public static String onLoadDataFailureMsg() {
		return LOAD_DATA_FAILURE_MSG;
	}
	
	/**
	 * Use this message when loading data was failed. 
	 */
	public static String onServerFailureMsg() {
		return SERVER_FAILURE_MSG;
	}

	/**
	 * Use this message when Asynchronously get the provided object was failed.
	 */
	public static String onGetProvidedObjectFailureMsg() {
		return GET_PROVIDED_OBJECT_FAILURE_MSG;
	}

	/**
	 * Use this message when loading data was failed. And user is in modal window.
	 */
	public static String onLoadAdditionalDataFailureMsg(String whatLoaded) {
		return "Cannot load " + whatLoaded + ". You could try to close the edit dialog and try to open it again. "
				+ NOTIFY_ADMIN_MSG;
	}

}
