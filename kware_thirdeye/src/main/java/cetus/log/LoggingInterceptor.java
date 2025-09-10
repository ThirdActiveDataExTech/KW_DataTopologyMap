package cetus.log;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StopWatch;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import cetus.config.CetusConfig.Logging;
import cetus.util.StringUtil;
import jodd.util.ArraysUtil;
import lombok.Setter;

public class LoggingInterceptor implements HandlerInterceptor {
	
	@Setter
	private AntPathMatcher pathMatcher;

	@Setter
	private Logger logger;

	@Setter
	private Logging logging;

	public static final String LOG_KEY_SQL = "_l__sql_";
	public static final String LOG_KEY_SQL_TIME = "_l__sql_t_";
	public static final String LOG_KEY_VIEW_NAME = "_l__view_n_";
	public static final String LOG_STOP_WATCH = "_l__stop_w_";

	private final UrlPathHelper urlPathHelper = new UrlPathHelper();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		request.setAttribute(LOG_STOP_WATCH, stopWatch);

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if (modelAndView != null) {
			request.setAttribute(LOG_KEY_VIEW_NAME, modelAndView.getViewName());
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

		final boolean handleException = (ex != null);
		final String url = urlPathHelper.getRequestUri(request);

		// 로깅 제외 URL 판단 (예외 발생 시에는 항상 로깅)
		if ( !handleException && isExcludeUrl(url) ) {
			return;
		}

		// StringBuilder는 실제 로깅이 필요할 때 생성
		StringBuilder logs = new StringBuilder();
		boolean printLog = logRequestInfo(logs, request, handleException);

		if (logStatus(logs, response, handleException)) printLog = true;
		if (logUri(logs, url, handleException)) printLog = true;
		if (logController(logs, handler, handleException)) printLog = true;
		if (logParams(logs, request, handleException)) printLog = true;
		if (logView(logs, request, handleException)) printLog = true;
		if (logSql(logs, request, handleException)) printLog = true;
		if (logElapsed(logs, request, handleException)) printLog = true;

		/*boolean printLog = false;
		boolean handleException = false;
		boolean isMultipart = false;
		StringBuilder logs = new StringBuilder();

		String url = new UrlPathHelper().getRequestUri(request);

		if(ex != null) {
			handleException = true;
		}

		// 로깅 제외 URL 판단
		 if ( !handleException && isExcludeUrl(url) ) {
			return;
		}

		if (request instanceof MultipartHttpServletRequest) {
			isMultipart = true;
			request = (MultipartHttpServletRequest)request;
		}

		if (handleException || logging.isRequest()) {
			printLog = true;
			logs.append("\nREQUEST    : ");
			logs.append(request.getRemoteAddr());
			logs.append(", ");
			logs.append(isMultipart ? "MULTIPART" : request.getMethod().toUpperCase());
		}

		if (handleException || logging.isUri()) {
			printLog = true;
			logs.append("\nURI        : " + url);
		}

		if (handleException || logging.isController()) {
			printLog = true;
			if (handler == null) {
				logs.append("\nCONTROLLER : N/A");
			}
			else {
				if (handler instanceof HandlerMethod) {
					HandlerMethod method = (HandlerMethod)handler;
					logs.append("\nCONTROLLER : " + method.getBeanType().getSimpleName());
					logs.append("'s " + method.getMethod().getName() + "()");
				}
			}
		}

		if (handleException || logging.isParams()) {
			int parameterLength = 0;
			StringBuilder paramText = new StringBuilder();

			Enumeration<?> _enum = request.getParameterNames();
			while (_enum.hasMoreElements()) {
				String key = _enum.nextElement().toString();
				if ( isExcludeParam(key) ) {
					continue;
				}

				String[] value = request.getParameterValues(key);
				if (value.length == 1) {
					paramText.append("\t{" + key + " = " + StringUtil.fixLength(value[0], 50) + "}\n");
				}
				else {
					paramText.append("\t{" + key + "[] = [");
					paramText.append(ArraysUtil.toString(value));
					paramText.append("]}\n");
				}
				parameterLength++;
			}

			if ( parameterLength > 0 ) {
				printLog = true;
				logs.append("\nPARAMS     : " + parameterLength + " 건\n");
				logs.append(paramText.substring(0, paramText.length()-1));
			}
		}

		if (handleException || logging.isView()) {
			String viewName = (String)request.getAttribute(LOG_KEY_VIEW_NAME);
			if (StringUtil.isNotEmpty(viewName)) {
				printLog = true;
				logs.append("\nVIEW       : " + viewName);
			}
		}

		if (handleException || logging.isSql()) {
			StringBuilder sql = (StringBuilder)request.getAttribute(LOG_KEY_SQL);
			if(StringUtil.isNotEmpty(sql)) {
				printLog = true;
				logs.append("\nSQL        : " + sql);
			}
		}

		StopWatch stopWatch = (StopWatch)request.getAttribute(LOG_STOP_WATCH);
		if (stopWatch != null) {
			stopWatch.stop();
		}
		if (handleException || logging.isElapsed()) {

			if (stopWatch != null) {
				printLog = true;
				double totalTime = stopWatch.getTotalTimeSeconds();

				logs.append(String.format("\nELAPSED    : %.2f sec", totalTime));

				Double sqlTime = (Double)request.getAttribute(LOG_KEY_SQL_TIME);
				if (sqlTime != null) {
					logs.append(String.format(" (SVC : %.2f sec, DB : %.2f sec)", totalTime - sqlTime, sqlTime));
				}
			}
		}*/

		if (!printLog) {
			return;
		}

		logs.append("\n------------------------------------------");

		if(handleException) {
			logger.error(logs.toString());
		} else {
			logger.info(logs.toString());
		}

		request.removeAttribute(LOG_KEY_SQL);
		request.removeAttribute(LOG_KEY_SQL);
		request.removeAttribute(LOG_KEY_VIEW_NAME);
		request.removeAttribute(LOG_STOP_WATCH);
	}

	private boolean logRequestInfo(StringBuilder logs, HttpServletRequest request, boolean handleException) {
		if (handleException || logging.isRequest()) {
			boolean isMultipart = (request instanceof MultipartHttpServletRequest);
			logs.append("\nREQUEST    : ");
			logs.append(request.getRemoteAddr());
			logs.append(", ");
			logs.append(isMultipart ? "MULTIPART" : request.getMethod().toUpperCase());
			return true;
		}
		return false;
	}

	private boolean logStatus(StringBuilder logs, HttpServletResponse response, boolean handleException) {
		if (handleException || logging.isRequest()) {
			logs.append("\nSTATUS     : ").append(response.getStatus());
			return true;
		}
		return false;
	}

	private boolean logUri(StringBuilder logs, String url, boolean handleException) {
		if (handleException || logging.isUri()) {
			logs.append("\nURI        : ").append(url);
			return true;
		}
		return false;
	}

	private boolean logController(StringBuilder logs, Object handler, boolean handleException) {
		if (handleException || logging.isController()) {
			logs.append("\nCONTROLLER : ");
			if (handler instanceof HandlerMethod) {
				HandlerMethod method = (HandlerMethod) handler;
				logs.append(method.getBeanType().getSimpleName());
				logs.append("'s ").append(method.getMethod().getName()).append("()");
			} else {
				logs.append("N/A");
			}
			return true;
		}
		return false;
	}

	private boolean logParams(StringBuilder logs, HttpServletRequest request, boolean handleException) {
		if (handleException || logging.isParams()) {
			StringBuilder paramText = new StringBuilder();
			int parameterLength = 0;

			Enumeration<?> _enum = request.getParameterNames();
			while (_enum.hasMoreElements()) {
				String key = _enum.nextElement().toString();
				if (isExcludeParam(key)) {
					continue;
				}

				String[] value = request.getParameterValues(key);
				if (value.length == 1) {
					paramText.append("\t{").append(key).append(" = ").append(StringUtil.fixLength(value[0], 50)).append("}\n");
				} else {
					paramText.append("\t{").append(key).append("[] = [");
					paramText.append(ArraysUtil.toString(value));
					paramText.append("]}\n");
				}
				parameterLength++;
			}

			if (parameterLength > 0) {
				logs.append("\nPARAMS     : ").append(parameterLength).append(" 건\n");
				logs.append(paramText, 0, paramText.length() - 1);
				return true;
			}
		}
		return false;
	}

	private boolean logView(StringBuilder logs, HttpServletRequest request, boolean handleException) {
		if (handleException || logging.isView()) {
			String viewName = (String) request.getAttribute(LOG_KEY_VIEW_NAME);
			if (StringUtil.isNotEmpty(viewName)) {
				logs.append("\nVIEW       : ").append(viewName);
				return true;
			}
		}
		return false;
	}

	private boolean logSql(StringBuilder logs, HttpServletRequest request, boolean handleException) {
		if (handleException || logging.isSql()) {
			StringBuilder sql = (StringBuilder) request.getAttribute(LOG_KEY_SQL);
			if (StringUtil.isNotEmpty(sql)) {
				logs.append("\nSQL        : ").append(sql);
				return true;
			}
		}
		return false;
	}

	private boolean logElapsed(StringBuilder logs, HttpServletRequest request, boolean handleException) {
		StopWatch stopWatch = (StopWatch) request.getAttribute(LOG_STOP_WATCH);
		if (stopWatch == null) {
			return false;
		}

		if (stopWatch.isRunning()) {
			stopWatch.stop();
		}

		if (handleException || logging.isElapsed()) {
			double totalTime = stopWatch.getTotalTimeSeconds();
			logs.append(String.format("\nELAPSED    : %.2f sec", totalTime));

			Double sqlTime = (Double) request.getAttribute(LOG_KEY_SQL_TIME);
			if (sqlTime != null) {
				logs.append(String.format(" (SVC : %.2f sec, DB : %.2f sec)", totalTime - sqlTime, sqlTime));
			}
			return true;
		}
		return false;
	}

	private boolean isExcludeUrl(String url) {

		if (StringUtil.isNotEmpty(logging.getExcludeUrls())) {
			for (String exclude : logging.getExcludeUrls()) {
				if ( pathMatcher.match(exclude, url) )
					return true;
			}
		}
		return false;
	}

	private boolean isExcludeParam(String key) {

		if (StringUtil.isNotEmpty(logging.getExcludeParams())) {
			for (String exclude : logging.getExcludeParams()) {
				if ( StringUtil.equals(key, exclude) )
					return true;
			}
		}
		return false;
	}

	// private String exceptionTitle(Exception e) {
	//     return String.format("%s : %s", e.getClass().getSimpleName(), e.getCause()); 
	// }
}