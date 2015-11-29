package com.trustcase.client.api.responses;

/**
 * Represents error details. Used as additional info in TrustCaseServiceException and related classes.
 * 
 * @author Gunther Klein
 */
public class ErrorResponse {

	public String errorMessage;
	public String errorCode;
	/**
	 * Optional, error code specific further error details. E.g. in case of constraint violations this contains
	 * information about one or more of the constraint failures. Or if we have compound errors, this may contain a list
	 * of error conditions.
	 */
	public Object errorDetails;

	public ErrorResponse(String errorMessage, String errorCode) {
		this(errorMessage, errorCode, null);
	}

	public ErrorResponse(String errorMessage, String errorCode, Object errorDetails) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
		this.errorDetails = errorDetails;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @return the errorDetails
	 */
	public Object getErrorDetails() {
		return errorDetails;
	}

	public static class ConstraintViolationDetails {

		public String msg;
		public String propertyPath;

		public ConstraintViolationDetails(String msg, String propertyPath) {
			this.msg = msg;
			this.propertyPath = propertyPath;
		}

	}

}
