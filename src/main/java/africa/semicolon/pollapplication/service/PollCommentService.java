package africa.semicolon.pollapplication.service;

import africa.semicolon.pollapplication.exceptions.PollCommentDoesNotExist;
import africa.semicolon.pollapplication.exceptions.PollDoesNotExistException;
import africa.semicolon.pollapplication.exceptions.UserDoesNotExistException;
import africa.semicolon.pollapplication.payLoad.request.CreatePollCommentRequest;
import africa.semicolon.pollapplication.payLoad.response.CreatePollCommentResponse;
import africa.semicolon.pollapplication.payLoad.response.PollCommentResponse;

import java.util.List;

public interface PollCommentService {
    CreatePollCommentResponse createPollComment(CreatePollCommentRequest createPollCommentRequest) throws UserDoesNotExistException, PollDoesNotExistException;
    void deletePollComment(Long pollId, Long commentId) throws PollDoesNotExistException, PollCommentDoesNotExist;
    List<PollCommentResponse> viewAllCommentOnAPoll(Long pollId) throws PollCommentDoesNotExist, PollDoesNotExistException;
    PollCommentResponse viewPostComment(Long pollId, Long commentId) throws PollDoesNotExistException, PollCommentDoesNotExist;

}
