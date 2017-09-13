
package sdong.defectAI.exception;

public class GitException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7034806583085959016L;

	private String reason;

	public GitException(Throwable cause){
		 super(cause);
		 this.reason = "Get exception : " + cause.getMessage();
	}
	
	public GitException(String message) {
			super(message);
			this.reason = "Get exception : " + message;
		}

	public String getMessage() {
		return reason;
	}

}