
package sdong.defectAI.exception;

public class DefectAIException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6392636107238496535L;
	private String reason;

	public DefectAIException(Throwable cause){
		 super(cause);
		 this.reason = "Get exception : " + cause.getMessage();
	}
	
	public DefectAIException(String message) {
			super(message);
			this.reason = "Get exception : " + message;
		}

	public String getMessage() {
		return reason;
	}

}