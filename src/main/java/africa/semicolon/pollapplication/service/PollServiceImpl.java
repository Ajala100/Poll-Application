package africa.semicolon.pollapplication.service;

import africa.semicolon.pollapplication.data.models.*;
import africa.semicolon.pollapplication.data.repository.*;
import africa.semicolon.pollapplication.exceptions.*;
import africa.semicolon.pollapplication.payLoad.request.CreatePollRequest;
import africa.semicolon.pollapplication.payLoad.response.CreatePollCommentResponse;
import africa.semicolon.pollapplication.payLoad.response.CreatePollResponse;
import africa.semicolon.pollapplication.payLoad.response.FindPollByIdResponse;
import africa.semicolon.pollapplication.payLoad.response.PollLikeAndUnlikeResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PollServiceImpl implements PollService{

    private final OptionService optionsService;
    private final AppUserRepository userRepository;
    private final PollRepository pollRepository;
    private final OptionRepository optionRepository;
    private final PollLikeService pollLikeService;
    private final PollDislikeService pollDislikeService;
    private final PollLikeRepository likeRepository;
    private final PollDislikeRepository pollDislikeRepository;
    private final PollInteractionRepository pollInteractionRepository;
    private final PollCommentService pollCommentService;

    private static final Logger logger = LoggerFactory.getLogger(PollServiceImpl.class);

    @Override
    public CreatePollResponse createPoll(String pollCreatorId, CreatePollRequest createPollRequest) throws UserDoesNotExistException, InvalidOptionAmountException {

        Poll savedPoll;

        if(createPollRequest.getOptions().size() < 2 || createPollRequest.getOptions().size() > 4) throw new
                InvalidOptionAmountException("Options cannot be less than 2 or more than 4");

        AppUser pollCreator = userRepository.findById(pollCreatorId).orElseThrow(()->
                new UserDoesNotExistException("User does not exist"));

        ArrayList<Option> options = new ArrayList<>();

        for(String value : createPollRequest.getOptions()){
            Option option = optionsService.createOption(value);
            options.add(option);
        }

        Poll poll = new Poll();
        if(createPollRequest.getDurationInMinutes() == 0){
            poll.setQuestion(createPollRequest.getQuestion());
            poll.setOptions(options);
            poll.setPollCreator(pollCreator);
            poll.setOn(true);
            savedPoll = savePoll(poll);

        }else{
            LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(createPollRequest.getDurationInMinutes());

            poll.setQuestion(createPollRequest.getQuestion());
            poll.setOptions(options);
            poll.setPollCreator(pollCreator);
            poll.setExpiresAt(expiresAt);
            poll.setOn(true);
            savedPoll = savePoll(poll);
        }

        List<CreatePollCommentResponse> listOfPollComments = new ArrayList<>();

        if(createPollRequest.getDurationInMinutes() != 0){

            String duration = String.format("%d %s", createPollRequest.getDurationInMinutes(), "minutes");

            return new CreatePollResponse(savedPoll.getId(), pollCreator.getId(), savedPoll.getQuestion(), savedPoll.getOptions(), savedPoll.getCommentCount(),
                    savedPoll.getLikeCount(), savedPoll.getDislikeCount(), listOfPollComments, duration);
        }else

            return new CreatePollResponse(savedPoll.getId(), pollCreator.getId(), savedPoll.getQuestion(), savedPoll.getOptions(), savedPoll.getCommentCount(),
                    savedPoll.getLikeCount(), savedPoll.getDislikeCount(), listOfPollComments, null);
    }

    @Scheduled(fixedRate = 60 * 1000)
    public void checkPollStatus(){

        List<Poll> allPolls = pollRepository.findAll();

        List<Poll> availablePolls = allPolls.stream().filter(Poll::isOn).collect(Collectors.toList());

        for(Poll poll : availablePolls){
            if(!LocalDateTime.now().isBefore(poll.getExpiresAt())) {
                poll.setOn(false);
                pollRepository.save(poll);
            }
        }

    }

    private Poll savePoll(Poll poll){
        return pollRepository.save(poll);
    }

    @Override
    public void deletePollById(String pollId) {
        pollRepository.deleteById(pollId);
    }

    @Override
    public List<CreatePollResponse> findPollByCreatorId(String pollCreatorId) throws PollDoesNotExistException {

        ArrayList<CreatePollResponse> listOfPollResponses = new ArrayList<>();

        List<Poll> listOfPolls;

        listOfPolls = pollRepository.findPollByPollCreatorId(pollCreatorId).orElseThrow(()-> new
                PollDoesNotExistException("Poll  does not exist"));

        for(Poll poll : listOfPolls){

            List<CreatePollCommentResponse> listOfPollCommentResponse = new ArrayList<>();

            for(PollComment pollComment : poll.getComments()){

                CreatePollCommentResponse commentResponse = CreatePollCommentResponse.builder()
                        .comment(pollComment.getComment())
                        .commenterId(pollComment.getCommenter().getId())
                        .createdAt(pollComment.getCreatedAt())
                        .pollCommentId(pollComment.getId())
                        .pollId(pollComment.getPoll().getId()).build();
                listOfPollCommentResponse.add(commentResponse);
            }

            CreatePollResponse pollResponse =  CreatePollResponse.builder().pollComments(listOfPollCommentResponse)
                    .pollCreatorId(poll.getPollCreator().getId())
                    .pollId(poll.getId())
                    .likeCount(poll.getLikeCount())
                    .commentCount(poll.getCommentCount())
                    .dislikeCount(poll.getDislikeCount())
                    .question(poll.getQuestion())
                    .options(poll.getOptions())
                    .build();
            listOfPollResponses.add(pollResponse);
        }

        return listOfPollResponses;

    }

    @Override
    public FindPollByIdResponse findPollById(String pollId) throws PollDoesNotExistException, PollHasExpiredException {

        Poll retrievedPoll = pollRepository.findById(pollId).orElseThrow(()->
                new PollDoesNotExistException("Poll  does not exist"));

        if(!retrievedPoll.isOn()) throw new PollHasExpiredException("Poll has expired");

        ArrayList<CreatePollCommentResponse> pollCommentResponses = new ArrayList<>();

        for(PollComment pollComment : retrievedPoll.getComments()){
            CreatePollCommentResponse commentResponse = CreatePollCommentResponse.builder()
                    .comment(pollComment.getComment())
                    .commenterId(pollComment.getCommenter().getId())
                    .createdAt(pollComment.getCreatedAt())
                    .pollCommentId(pollComment.getId())
                    .pollId(pollComment.getPoll().getId()).build();

            pollCommentResponses.add(commentResponse);

        }

        if(retrievedPoll.getExpiresAt() != null){
            String expiresAt = retrievedPoll.getExpiresAt().toString();
            return FindPollByIdResponse.builder()
                    .likeCount(retrievedPoll.getLikeCount())
                    .pollCreatorId(retrievedPoll.getPollCreator().getId())
                    .dislikeCount(retrievedPoll.getDislikeCount())
                    .commentCount(retrievedPoll.getCommentCount())
                    .pollComments(pollCommentResponses)
                    .question(retrievedPoll.getQuestion())
                    .options(retrievedPoll.getOptions())
                    .expiresAt(expiresAt)
                    .pollId(retrievedPoll.getId()).build();
        }
        else return FindPollByIdResponse.builder()
                .likeCount(retrievedPoll.getLikeCount())
                .pollCreatorId(retrievedPoll.getPollCreator().getId())
                .dislikeCount(retrievedPoll.getDislikeCount())
                .commentCount(retrievedPoll.getCommentCount())
                .pollComments(pollCommentResponses)
                .question(retrievedPoll.getQuestion())
                .options(retrievedPoll.getOptions())
                .expiresAt(null)
                .pollId(retrievedPoll.getId()).build();

    }

    @Override
    public List<CreatePollResponse> findAllAvailablePolls() {

        List<Poll> listOfPolls = pollRepository.findAll();

        List<Poll> availablePolls = listOfPolls.stream().filter(Poll::isOn).collect(Collectors.toList());

        ArrayList<CreatePollResponse> listOfPollResponses = new ArrayList<>();

        for(Poll poll : availablePolls){

            List<CreatePollCommentResponse> listOfPollCommentResponse = new ArrayList<>();

            for(PollComment pollComment : poll.getComments()){

                CreatePollCommentResponse commentResponse = CreatePollCommentResponse.builder()
                        .comment(pollComment.getComment())
                        .commenterId(pollComment.getCommenter().getId())
                        .createdAt(pollComment.getCreatedAt())
                        .pollCommentId(pollComment.getId())
                        .pollId(pollComment.getPoll().getId()).build();
                listOfPollCommentResponse.add(commentResponse);
            }

            CreatePollResponse pollResponse =  CreatePollResponse.builder().pollComments(listOfPollCommentResponse)
                    .pollCreatorId(poll.getPollCreator().getId())
                    .pollId(poll.getId())
                    .likeCount(poll.getLikeCount())
                    .commentCount(poll.getCommentCount())
                    .dislikeCount(poll.getDislikeCount())
                    .question(poll.getQuestion())
                    .options(poll.getOptions())
                    .build();
            listOfPollResponses.add(pollResponse);
        }

        return listOfPollResponses;

    }

    @Override
    public Map<Option, String> selectOptions(String userId, String pollId, Long optionId) throws PollDoesNotExistException, OptionDoesNotExistException, PollHasExpiredException, UserDoesNotExistException, OptionHasAlreadyBeenSelectedException {

        AppUser retrievedUser = userRepository.findById(userId).orElseThrow(()-> new UserDoesNotExistException("User does not exist"));

        Poll retrievedPoll = pollRepository.findById(pollId).orElseThrow(()->
                new PollDoesNotExistException("Poll does not exist"));

        Option option = optionRepository.findById(optionId).orElseThrow(()->
                new OptionDoesNotExistException("Option does not exist!!"));

        if(retrievedPoll.isOn()){

            Optional<PollInteraction> pollInteraction = pollInteractionRepository.findPollInteractionByUserAndPoll(retrievedUser, retrievedPoll);

            if(pollInteraction.isPresent()){
                throw new OptionHasAlreadyBeenSelectedException("User has already interacted with this poll");
            }else{

                PollInteraction pollInteraction1 = new PollInteraction(retrievedUser, retrievedPoll, option);

                pollInteractionRepository.save(pollInteraction1);

                option.setCount(option.getCount() + 1);


                optionRepository.save(option);

                retrievedPoll.setSelectedOptionsCount(retrievedPoll.getSelectedOptionsCount() + 1);

                pollRepository.save(retrievedPoll);

                return createPieChart(retrievedPoll);
            }

        }else throw new PollHasExpiredException("Poll duration has expired");
    }

    @Override
    public PollLikeAndUnlikeResponse likeAndUnlikePoll(String pollId, String userId) throws UserDoesNotExistException, PollDoesNotExistException {

        AppUser retrievedUser = userRepository.findById(userId).orElseThrow(()-> new UserDoesNotExistException("User does not exist."));

        Poll retrievedPoll = pollRepository.findById(pollId).orElseThrow(()-> new PollDoesNotExistException("Poll does not exist."));


        PollLike pollLike = likeRepository.findPollLikeByLikerAndPoll(retrievedUser, retrievedPoll).orElse(null);

        if(pollLike == null){

            Optional<PollDislike> pollDislike = pollDislikeRepository.findPollDislikeByDislikerAndPoll(retrievedUser, retrievedPoll);

            if(pollDislike.isPresent()){

                retrievedPoll.setDislikeCount(retrievedPoll.getDislikeCount() - 1);
                pollDislikeRepository.delete(pollDislike.get());
            }

            retrievedPoll.setLikeCount(retrievedPoll.getLikeCount() + 1);

            pollRepository.save(retrievedPoll);

            return pollLikeService.createPollLike(retrievedPoll, retrievedUser);
        }
        else{

            likeRepository.delete(pollLike);

            retrievedPoll.setLikeCount(retrievedPoll.getLikeCount() - 1);

            retrievedPoll.setDislikeCount(retrievedPoll.getDislikeCount() + 1);

            pollRepository.save(retrievedPoll);

            return pollDislikeService.createPollDisLike(retrievedPoll, retrievedUser);
        }
    }

    @Override
    public CreatePollCommentResponse commentOnaPoll(String commenterId, String pollId, String comment) throws UserDoesNotExistException, PollDoesNotExistException {

        if(commenterId == null) throw new IllegalArgumentException("Commenter Id cannot be null");
        if(pollId == null) throw new IllegalArgumentException("Poll id cannot be null");
        if(comment == null) throw new IllegalArgumentException("comment cannot be null");

        return pollCommentService.createPollComment(commenterId, pollId, comment);
    }

    private Map<Option, String> createPieChart(Poll poll){

        HashMap<Option, String> map = new HashMap<>();

        int total = poll.getSelectedOptionsCount();

        for(Option option : poll.getOptions()){

            int percentage = calculatePercentage(total, option.getCount());

            String percentageString = String.format("%d%s", percentage, "%");

            map.put(option, percentageString);
        }

        return map;
    }

    private int calculatePercentage(int total, int part){

        double fraction = ((double) part) / total;

        return  (int) (fraction * 100);
    }
}
