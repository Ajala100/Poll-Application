package africa.semicolon.pollapplication.service;

import africa.semicolon.pollapplication.data.models.AppUser;
import africa.semicolon.pollapplication.data.models.Poll;
import africa.semicolon.pollapplication.data.models.PollInteraction;
import africa.semicolon.pollapplication.data.repository.AppUserRepository;
import africa.semicolon.pollapplication.data.repository.PollInteractionRepository;
import africa.semicolon.pollapplication.data.repository.PollRepository;
import africa.semicolon.pollapplication.exceptions.PollDoesNotExistException;
import africa.semicolon.pollapplication.exceptions.PollInteractionDoesNotExist;
import africa.semicolon.pollapplication.exceptions.UserDoesNotExistException;
import africa.semicolon.pollapplication.payLoad.response.FindPollInteractionByPollResponse;
import africa.semicolon.pollapplication.payLoad.response.FindPollInteractionByUserResponse;
import africa.semicolon.pollapplication.payLoad.response.PollInteractionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PollInteractionServiceImpl implements PollInteractionService{

    private final AppUserRepository userRepository;
    private final PollRepository pollRepository;
    private final PollInteractionRepository pollInteractionRepository;

    @Override
    public PollInteractionResponse findPollInteractionByUserAndPoll(String userId, String pollId) throws UserDoesNotExistException, PollDoesNotExistException, PollInteractionDoesNotExist {
        AppUser user = userRepository.findById(userId).orElseThrow(()-> new UserDoesNotExistException("User does not exist!!"));

        Poll poll = pollRepository.findById(pollId).orElseThrow(()-> new PollDoesNotExistException("Poll does not exist"));

        PollInteraction pollInteraction = pollInteractionRepository.
                findPollInteractionByUserAndPoll(user, poll).
                orElseThrow(()-> new PollInteractionDoesNotExist("Poll interaction does not exist"));

        return new PollInteractionResponse(pollInteraction.getId(), pollInteraction.getOption().getId(), pollInteraction.getOption().getValue());

    }

    @Override
    public List<FindPollInteractionByUserResponse> findPollInteractionByUser(String userId) throws UserDoesNotExistException, PollInteractionDoesNotExist {
        AppUser user = userRepository.findById(userId).orElseThrow(()-> new UserDoesNotExistException("User does not exist!!"));

        List<PollInteraction> pollInteractions = pollInteractionRepository.findPollInteractionByUser(user)
                .orElseThrow(()-> new PollInteractionDoesNotExist("User has not interacted with any poll"));

        List<FindPollInteractionByUserResponse> pollInteractionResponses = new ArrayList<>();

        for(PollInteraction pollInteraction: pollInteractions){
            pollInteractionResponses.add(new FindPollInteractionByUserResponse(pollInteraction.getId(),
                    pollInteraction.getPoll().getId(), pollInteraction.getOption().getId()));
        }
        return pollInteractionResponses;
    }

    @Override
    public List<FindPollInteractionByPollResponse> findPollInteractionByPoll(String pollId) throws PollDoesNotExistException, PollInteractionDoesNotExist {
        Poll poll = pollRepository.findById(pollId).orElseThrow(()-> new PollDoesNotExistException("Poll does not exist"));

        List<PollInteraction> pollInteractionResponses = pollInteractionRepository.findPollInteractionByPoll(poll)
                .orElseThrow(()-> new PollInteractionDoesNotExist("Poll has no interactions yet"));

        List<FindPollInteractionByPollResponse> pollInteractions = new ArrayList<>();

        for(PollInteraction pollInteraction : pollInteractionResponses){
            pollInteractions.add(new FindPollInteractionByPollResponse(pollInteraction.getId(), pollInteraction.getUser().getId(),
                    pollInteraction.getOption().getId()));
        }
        return pollInteractions;
    }
}
