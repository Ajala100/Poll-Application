package africa.semicolon.pollapplication.service;

import africa.semicolon.pollapplication.data.models.Option;
import africa.semicolon.pollapplication.exceptions.*;
import africa.semicolon.pollapplication.payLoad.request.CreatePollRequest;
import africa.semicolon.pollapplication.payLoad.response.CreatePollResponse;
import africa.semicolon.pollapplication.payLoad.response.FindPollByIdResponse;
import africa.semicolon.pollapplication.payLoad.response.PollLikeAndUnlikeResponse;

import java.util.List;
import java.util.Map;

public interface PollService {
    CreatePollResponse createPoll(Long pollCreatorId, CreatePollRequest createPollRequest  ) throws UserDoesNotExistException, InvalidOptionAmountException;
    void deletePollById(Long pollId);
    List<CreatePollResponse> findPollByCreatorId(Long pollCreatorId) throws PollDoesNotExistException;
    FindPollByIdResponse findPollById(Long pollId) throws PollDoesNotExistException, PollHasExpiredException;
    List<CreatePollResponse> findAllAvailablePolls();
    void checkPollStatus();
    Map<Option, String> selectOptions(Long userId, Long pollId, Long optionId) throws PollDoesNotExistException, OptionDoesNotExistException, PollHasExpiredException, UserDoesNotExistException, OptionHasAlreadyBeenSelectedException;
    PollLikeAndUnlikeResponse likeAndUnlikePoll(Long pollId, Long userId) throws UserDoesNotExistException, PollDoesNotExistException;

}
