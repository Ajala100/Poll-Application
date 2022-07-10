package africa.semicolon.pollapplication.service;

import africa.semicolon.pollapplication.data.models.AppUser;
import africa.semicolon.pollapplication.data.models.Poll;
import africa.semicolon.pollapplication.data.models.PollComment;
import africa.semicolon.pollapplication.data.repository.AppUserRepository;
import africa.semicolon.pollapplication.data.repository.PollCommentRepository;
import africa.semicolon.pollapplication.data.repository.PollRepository;
import africa.semicolon.pollapplication.exceptions.PollCommentDoesNotExist;
import africa.semicolon.pollapplication.exceptions.PollDoesNotExistException;
import africa.semicolon.pollapplication.exceptions.UserDoesNotExistException;
import africa.semicolon.pollapplication.payLoad.request.CreatePollCommentRequest;
import africa.semicolon.pollapplication.payLoad.response.CreatePollCommentResponse;
import africa.semicolon.pollapplication.payLoad.response.PollCommentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


@Service
@RequiredArgsConstructor
@Slf4j
public class PollCommentServiceImpl implements PollCommentService{

    private final PollCommentRepository pollCommentRepository;
    private final PollRepository pollRepository;
    private final AppUserRepository userRepository;

    @Override
    public CreatePollCommentResponse createPollComment(CreatePollCommentRequest createPollCommentRequest) throws UserDoesNotExistException, PollDoesNotExistException {

        AppUser commenter = userRepository.findById(createPollCommentRequest.getCommenterId()).
                orElseThrow(()-> new UserDoesNotExistException("User does not exist"));

        Poll pollToBeCommentedOn = pollRepository.findById(createPollCommentRequest.getPollId()).
                orElseThrow(()-> new PollDoesNotExistException("Poll does not exist"));

        PollComment pollComment = new PollComment(createPollCommentRequest.getComment(), commenter, pollToBeCommentedOn);

        PollComment savedPollComment = pollCommentRepository.save(pollComment);

        pollToBeCommentedOn.getComments().add(savedPollComment);

        pollToBeCommentedOn.setCommentCount(pollToBeCommentedOn.getCommentCount() + 1);

        pollRepository.save(pollToBeCommentedOn);

        return new CreatePollCommentResponse(savedPollComment.getId(), pollToBeCommentedOn.getId(), savedPollComment.getComment(), commenter.getId(),
                savedPollComment.getCreatedAt());

    }

    @Override
    public void deletePollComment(String pollId, Long commentId) throws PollDoesNotExistException, PollCommentDoesNotExist {

        Poll retrievedPoll = pollRepository.findById(pollId).orElseThrow(()-> new PollDoesNotExistException("Poll does not exist"));

        PollComment commentToBeDeleted = pollCommentRepository.findById(commentId).orElseThrow(()->
                new PollCommentDoesNotExist("Comment does not exist"));

        retrievedPoll.getComments().remove(commentToBeDeleted);

        retrievedPoll.setCommentCount(retrievedPoll.getCommentCount() - 1);

        pollRepository.save(retrievedPoll);

        pollCommentRepository.delete(commentToBeDeleted);
    }

    @Override
    public List<PollCommentResponse> viewAllCommentOnAPoll(String pollId) throws PollCommentDoesNotExist, PollDoesNotExistException {

        Poll retrievedPoll = pollRepository.findById(pollId).orElseThrow(()->
                new PollDoesNotExistException("Poll does not exist"));

        if(retrievedPoll.getComments() == null) throw new PollCommentDoesNotExist("Poll has no comments");

        List<PollCommentResponse> pollCommentResponses = new ArrayList<>();

        for(PollComment comment: retrievedPoll.getComments()){

            PollCommentResponse response = PollCommentResponse.builder()
                    .createdAt(comment.getCreatedAt())
                    .comment(comment.getComment())
                    .pollId(comment.getPoll().getId())
                    .commenterId(comment.getCommenter().getId())
                    .build();

            pollCommentResponses.add(response);
        }

        return pollCommentResponses;
    }

    @Override
    public PollCommentResponse viewPollComment(String pollId, Long commentId) throws PollDoesNotExistException, PollCommentDoesNotExist {

        Poll retrievedPoll = pollRepository.findById(pollId).orElseThrow(()-> new PollDoesNotExistException("Poll does not exist"));

        Predicate<PollComment> commentPredicate = i -> i.getId().equals(commentId);

        Optional<PollComment> comment = retrievedPoll.getComments().stream().filter(commentPredicate).findFirst();

        PollComment commentRetrieved;

        if (comment.isPresent()) {
            commentRetrieved = comment.get();
        }
        else throw new PollCommentDoesNotExist("Poll comment does not exist");

        return PollCommentResponse.builder()
                .comment(commentRetrieved.getComment())
                .commenterId(commentRetrieved.getCommenter().getId())
                .commentId(commentRetrieved.getId())
                .pollId(commentRetrieved.getPoll().getId())
                .createdAt(commentRetrieved.getCreatedAt()).build();
    }
}
