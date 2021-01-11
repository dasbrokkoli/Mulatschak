package itp.project.Exceptions;

public class WinException extends Throwable {
    public WinException(int playerNumber) {
        super(String.valueOf(playerNumber));
    }
}
