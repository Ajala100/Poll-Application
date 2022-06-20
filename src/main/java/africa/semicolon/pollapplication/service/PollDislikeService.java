package africa.semicolon.pollapplication.service;

import africa.semicolon.pollapplication.data.models.AppUser;
import africa.semicolon.pollapplication.data.models.Poll;
import africa.semicolon.pollapplication.exceptions.PollDislikeDoesNotExistException;
import africa.semicolon.pollapplication.exceptions.PollDoesNotExistException;
import africa.semicolon.pollapplication.exceptions.UserDoesNotExistException;
import africa.semicolon.pollapplication.payLoad.response.PollLikeAndUnlikeResponse;

import java.util.List;

public interface PollDislikeService {
    PollLikeAndUnlikeResponse createPollDisLike(Poll poll, AppUser user );
    PollLikeAndUnlikeResponse findPollDislikeByDislikerAndPoll(Long pollId, Long dislikerId) throws UserDoesNotExistException, PollDoesNotExistException, PollDislikeDoesNotExistException;
    List<PollLikeAndUnlikeResponse> findPollDislikeByDisliker(Long dislikerId) throws UserDoesNotExistException, PollDislikeDoesNotExistException;

}
