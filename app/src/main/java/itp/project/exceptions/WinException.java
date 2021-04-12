package itp.project.exceptions;

public class WinException extends Throwable {
    public WinException(int playerNumber) {
        super(String.valueOf(playerNumber));
    }
}
