package org.sky.sys.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ServiceException extends RuntimeException {
	private Throwable rootCause;

	public ServiceException(String s) {
		super(s);
	}

	public ServiceException(String s, Throwable ex) {
		super(s);
		rootCause = ex;
	}

	public Throwable getRootCause() {
		return rootCause;
	}

	public String getMessage() {
		if (rootCause == null) {
			return super.getMessage();
		} else {
			return super.getMessage() + "; nested exception is: nt"
					+ rootCause.toString();
		}
	}

	public void printStackTrace(PrintStream ps) {
		if (rootCause == null) {
			super.printStackTrace(ps);
		} else {
			ps.println(this);
			rootCause.printStackTrace(ps);
		}
	}

	public void printStackTrace(PrintWriter pw) {
		if (rootCause == null) {
			super.printStackTrace(pw);
		} else {
			pw.println(this);
			rootCause.printStackTrace(pw);
		}
	}

	public void printStackTrace() {
		printStackTrace(System.err);
	}

}
