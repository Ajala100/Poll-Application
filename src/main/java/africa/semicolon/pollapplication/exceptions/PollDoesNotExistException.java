package africa.semicolon.pollapplication.exceptions;

public class PollDoesNotExistException extends Throwable{
    public PollDoesNotExistException(String message){
        super(message);
    }
}
