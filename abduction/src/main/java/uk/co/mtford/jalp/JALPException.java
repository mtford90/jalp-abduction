package uk.co.mtford.jalp;

/**
 * Created with IntelliJ IDEA.
 * User: mtford
 * Date: 27/05/2012
 * Time: 08:38
 * To change this template use File | Settings | File Templates.
 */
public class JALPException extends Exception {
    public JALPException() {
    }

    public JALPException(String s) {
        super(s);
    }

    public JALPException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public JALPException(Throwable throwable) {
        super(throwable);
    }
}
