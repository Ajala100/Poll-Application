package africa.semicolon.pollapplication.service;

import africa.semicolon.pollapplication.data.models.AppUser;
import africa.semicolon.pollapplication.data.models.Poll;
import africa.semicolon.pollapplication.exceptions.PollDoesNotExistException;
import africa.semicolon.pollapplication.exceptions.PollLikeDoesNotExistException;
import africa.semicolon.pollapplication.exceptions.UserDoesNotExistException;
import africa.semicolon.pollapplication.payLoad.response.PollLikeAndUnlikeResponse;

import java.util.List;

public interface PollLikeService {
    PollLikeAndUnlikeResponse createPollLike(Poll poll, AppUser user);
    PollLikeAndUnlikeResponse findPollLikeByLikerAndPoll(Long likerId, Long pollId) throws UserDoesNotExistException, PollDoesNotExistException, PollLikeDoesNotExistException;
    List<PollLikeAndUnlikeResponse> findPollLikeByLiker(Long likerId) throws UserDoesNotExistException, PollLikeDoesNotExistException;

}
