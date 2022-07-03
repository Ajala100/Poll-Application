package africa.semicolon.pollapplication.service;

import africa.semicolon.pollapplication.data.models.AppUser;
import africa.semicolon.pollapplication.data.models.Poll;
import africa.semicolon.pollapplication.data.models.PollDislike;
import africa.semicolon.pollapplication.data.repository.AppUserRepository;
import africa.semicolon.pollapplication.data.repository.PollDislikeRepository;
import africa.semicolon.pollapplication.data.repository.PollRepository;
import africa.semicolon.pollapplication.exceptions.PollDislikeDoesNotExist;
import africa.semicolon.pollapplication.exceptions.PollDislikeDoesNotExistException;
import africa.semicolon.pollapplication.exceptions.PollDoesNotExistException;
import africa.semicolon.pollapplication.exceptions.UserDoesNotExistException;
import africa.semicolon.pollapplication.payLoad.response.PollLikeAndUnlikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PollDislikeServiceImpl implements PollDislikeService{
    private final PollDislikeRepository pollDislikeRepository;
    private final PollRepository pollRepository;
    private final AppUserRepository userRepository;


    @Override
    public PollLikeAndUnlikeResponse createPollDisLike(Poll poll, AppUser user) {
        PollDislike pollDislike = new PollDislike(user, poll);

        PollDislike savedDislike = pollDislikeRepository.save(pollDislike);

        return new PollLikeAndUnlikeResponse(savedDislike.getId(), user.getId(), poll.getId(), savedDislike.getDislikedAt());

    }

    @Override
    public PollLikeAndUnlikeResponse findPollDislikeByDislikerAndPoll(String pollId, String dislikerId) throws UserDoesNotExistException, PollDoesNotExistException, PollDislikeDoesNotExistException, PollDislikeDoesNotExist {

        AppUser user = userRepository.findById(dislikerId).orElseThrow(()-> new UserDoesNotExistException("User does not exist"));

        Poll poll = pollRepository.findById(pollId).orElseThrow(()-> new PollDoesNotExistException("Poll does not exist"));

        PollDislike pollDislike = pollDislikeRepository.findPollDislikeByDislikerAndPoll(user, poll).orElseThrow(()-> new PollDislikeDoesNotExist("Poll dislike does not exist"));

        return new PollLikeAndUnlikeResponse(pollDislike.getId(), pollDislike.getDisliker().getId(), pollDislike.getPoll().getId(), pollDislike.getDislikedAt());

    }

    @Override
    public List<PollLikeAndUnlikeResponse> findPollDislikeByDisliker(String dislikerId) throws UserDoesNotExistException, PollDislikeDoesNotExistException, PollDislikeDoesNotExist {


        AppUser user = userRepository.findById(dislikerId).orElseThrow(()-> new UserDoesNotExistException("User does not exist"));

        List<PollDislike> pollDislikes = pollDislikeRepository.findPollDislikeByDisliker(user).orElseThrow(()-> new PollDislikeDoesNotExist("User has not disliked any poll"));

        List<PollLikeAndUnlikeResponse> pollDislikeResponses = new ArrayList<>();

        for(PollDislike pollDislike: pollDislikes){

            pollDislikeResponses.add(new PollLikeAndUnlikeResponse(pollDislike.getId(), pollDislike.getDisliker().getId(),
                    pollDislike.getPoll().getId(), pollDislike.getDislikedAt()));
        }
        return pollDislikeResponses;
    }
}
