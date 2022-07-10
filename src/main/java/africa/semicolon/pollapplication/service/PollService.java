package africa.semicolon.pollapplication.service;

import africa.semicolon.pollapplication.data.models.Option;
import africa.semicolon.pollapplication.exceptions.*;
import africa.semicolon.pollapplication.payLoad.request.CreatePollRequest;
import africa.semicolon.pollapplication.payLoad.response.CreatePollCommentResponse;
import africa.semicolon.pollapplication.payLoad.response.CreatePollResponse;
import africa.semicolon.pollapplication.payLoad.response.FindPollByIdResponse;
import africa.semicolon.pollapplication.payLoad.response.PollLikeAndUnlikeResponse;

import java.util.List;
import java.util.Map;

public interface PollService {
    CreatePollResponse createPoll(String pollCreatorId, CreatePollRequest createPollRequest  ) throws UserDoesNotExistException, InvalidOptionAmountException;
    void deletePollById(String pollId);
    List<CreatePollResponse> findPollByCreatorId(String pollCreatorId) throws PollDoesNotExistException;
    FindPollByIdResponse findPollById(String pollId) throws PollDoesNotExistException, PollHasExpiredException;
    List<CreatePollResponse> findAllAvailablePolls();
    void checkPollStatus();
    Map<Option, String> selectOptions(String userId, String pollId, Long optionId) throws PollDoesNotExistException, OptionDoesNotExistException, PollHasExpiredException, UserDoesNotExistException, OptionHasAlreadyBeenSelectedException;
    PollLikeAndUnlikeResponse likeAndUnlikePoll(String pollId, String userId) throws UserDoesNotExistException, PollDoesNotExistException;
    CreatePollCommentResponse commentOnaPoll(String commenterId, String pollId, String comment) throws UserDoesNotExistException, PollDoesNotExistException;

}
