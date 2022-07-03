package africa.semicolon.pollapplication.service;

import africa.semicolon.pollapplication.data.models.AppUser;
import africa.semicolon.pollapplication.data.models.Poll;
import africa.semicolon.pollapplication.data.models.PollLike;
import africa.semicolon.pollapplication.data.repository.AppUserRepository;
import africa.semicolon.pollapplication.data.repository.PollLikeRepository;
import africa.semicolon.pollapplication.data.repository.PollRepository;
import africa.semicolon.pollapplication.exceptions.PollDoesNotExistException;
import africa.semicolon.pollapplication.exceptions.PollLikeDoesNotExistException;
import africa.semicolon.pollapplication.exceptions.UserDoesNotExistException;
import africa.semicolon.pollapplication.payLoad.response.PollLikeAndUnlikeResponse;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class PollLikeServiceImpl implements PollLikeService{

    private final AppUserRepository userRepository;
    private final PollRepository pollRepository;
    private final PollLikeRepository pollLikeRepository;

    @Override
    public PollLikeAndUnlikeResponse createPollLike(Poll poll, AppUser user) {

        PollLike pollLike = PollLike.builder()
                .liker(user)
                .poll(poll)
                .build();

        PollLike savedLike = pollLikeRepository.save(pollLike);

        return new PollLikeAndUnlikeResponse(savedLike.getId(), user.getId(), poll.getId(), savedLike.getLikedAt() );

    }

    @Override
    public PollLikeAndUnlikeResponse findPollLikeByLikerAndPoll(String likerId, String pollId) throws UserDoesNotExistException, PollDoesNotExistException, PollLikeDoesNotExistException {
        AppUser user = userRepository.findById(likerId).orElseThrow(()-> new UserDoesNotExistException("User does not exist"));

        Poll poll = pollRepository.findById(pollId).orElseThrow(()-> new PollDoesNotExistException("Poll does not exist"));

        PollLike pollLike = pollLikeRepository.findPollLikeByLikerAndPoll(user, poll).orElseThrow(()-> new PollLikeDoesNotExistException("Poll like does not exist"));

        return new PollLikeAndUnlikeResponse(pollLike.getId(), pollLike.getLiker().getId(), pollLike.getPoll().getId(), pollLike.getLikedAt());


    }

    @Override
    public List<PollLikeAndUnlikeResponse> findPollLikeByLiker(String likerId) throws UserDoesNotExistException, PollLikeDoesNotExistException {
        AppUser user = userRepository.findById(likerId).orElseThrow(()-> new UserDoesNotExistException("User does not exist"));

        List<PollLike> pollLikes = pollLikeRepository.findPollLikeByLiker(user).orElseThrow(()-> new PollLikeDoesNotExistException("User has not liked any poll"));

        List<PollLikeAndUnlikeResponse> pollDislikeResponses = new ArrayList<>();

        for(PollLike pollLike: pollLikes){

            pollDislikeResponses.add(new PollLikeAndUnlikeResponse(pollLike.getId(), pollLike.getLiker().getId(),
                    pollLike.getPoll().getId(), pollLike.getLikedAt()));
        }

        return pollDislikeResponses;
    }
}
