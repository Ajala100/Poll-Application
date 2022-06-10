package africa.semicolon.pollapplication.exceptions;

public class UserDoesNotExistException extends Throwable{
    public UserDoesNotExistException(String message){
        super(message);
    }
}
