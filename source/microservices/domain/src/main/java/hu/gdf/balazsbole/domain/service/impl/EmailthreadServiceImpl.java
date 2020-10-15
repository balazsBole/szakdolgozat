package hu.gdf.balazsbole.domain.service.impl;


import hu.gdf.balazsbole.domain.dto.Email;
import hu.gdf.balazsbole.domain.dto.Emailthread;
import hu.gdf.balazsbole.domain.enumeration.Direction;
import hu.gdf.balazsbole.domain.enumeration.Status;
import hu.gdf.balazsbole.domain.mapper.EmailthreadMapper;
import hu.gdf.balazsbole.domain.repository.EmailthreadRepository;
import hu.gdf.balazsbole.domain.service.EmailthreadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailthreadServiceImpl implements EmailthreadService {

    private final EmailthreadRepository repository;
    private final EmailthreadMapper mapper;

    public EmailthreadServiceImpl(EmailthreadRepository repository, EmailthreadMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void createEmailThreadFor(Email email) {
        Emailthread emailthread = new Emailthread();
        email.setDirection(Direction.IN);
        emailthread.getEmails().add(email);
        emailthread.setStatus(Status.OPEN);
        repository.saveAndFlush(mapper.map(emailthread));

    }
}
