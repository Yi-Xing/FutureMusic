package service.user.administrators;

import mapper.PlayMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "PlayInformationService")
public class PlayInformationService {

    @Resource(name ="PlayMapper")
    PlayMapper playMapper;
}
