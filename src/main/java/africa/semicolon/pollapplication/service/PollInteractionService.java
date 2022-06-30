package africa.semicolon.pollapplication.service;

import africa.semicolon.pollapplication.exceptions.PollDoesNotExistException;
import africa.semicolon.pollapplication.exceptions.PollInteractionDoesNotExist;
import africa.semicolon.pollapplication.exceptions.UserDoesNotExistException;
import africa.semicolon.pollapplication.payLoad.response.FindPollInteractionByPollResponse;
import africa.semicolon.pollapplication.payLoad.response.FindPollInteractionByUserResponse;
import africa.semicolon.pollapplication.payLoad.response.PollInteractionResponse;

import java.util.List;

public interface PollInteractionService {
    PollInteractionResponse findPollInteractionByUserAndPoll(String userId, String pollId) throws UserDoesNotExistException, PollDoesNotExistException, PollInteractionDoesNotExist;
    List<FindPollInteractionByUserResponse> findPollInteractionByUser(String userId) throws UserDoesNotExistException, PollInteractionDoesNotExist;
    List<FindPollInteractionByPollResponse> findPollInteractionByPoll(String pollId) throws PollDoesNotExistException, PollInteractionDoesNotExist;

}
