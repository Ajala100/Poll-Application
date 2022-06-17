package africa.semicolon.pollapplication.service;

import africa.semicolon.pollapplication.data.models.Option;
import africa.semicolon.pollapplication.data.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService{
    private final OptionRepository optionRepository;

    @Override
    public Option createOption(String value) {
        Option option = Option.builder()
                .count(0)
                .value(value)
                .build();

        Option savedOption = saveOption(option);
        return savedOption;
    }

    private Option saveOption(Option option){
        return optionRepository.save(option);
    }

}
