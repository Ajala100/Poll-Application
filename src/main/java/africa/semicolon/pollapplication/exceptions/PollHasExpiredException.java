package africa.semicolon.pollapplication.exceptions;

import africa.semicolon.pollapplication.service.PollService;

public class PollHasExpiredException extends Throwable{
    public PollHasExpiredException(String message){
        super(message);
    }
}
