package africa.semicolon.pollapplication.service;

import africa.semicolon.pollapplication.exceptions.PollCommentDoesNotExist;
import africa.semicolon.pollapplication.exceptions.PollDoesNotExistException;
import africa.semicolon.pollapplication.exceptions.UserDoesNotExistException;
import africa.semicolon.pollapplication.payLoad.response.CreatePollCommentResponse;
import africa.semicolon.pollapplication.payLoad.response.PollCommentResponse;

import java.util.List;

public interface PollCommentService {
    CreatePollCommentResponse createPollComment(String commenterId, String pollId, String comment) throws UserDoesNotExistException, PollDoesNotExistException;
    void deletePollComment(String pollId, Long commentId) throws PollDoesNotExistException, PollCommentDoesNotExist;
    List<PollCommentResponse> viewAllCommentOnAPoll(String pollId) throws PollCommentDoesNotExist, PollDoesNotExistException;
    PollCommentResponse viewPollComment(String pollId, Long commentId) throws PollDoesNotExistException, PollCommentDoesNotExist;

}
