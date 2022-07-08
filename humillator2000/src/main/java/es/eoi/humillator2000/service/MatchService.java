package es.eoi.humillator2000.service;

import es.eoi.humillator2000.data.entity.Match;
import es.eoi.humillator2000.data.entity.Notification;
import es.eoi.humillator2000.data.repository.MatchRepository;
import es.eoi.humillator2000.data.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService extends AbstractService<Integer, Match, MatchRepository>{

    @Autowired
    public MatchService(MatchRepository repository) {
        super(repository);
    }



}
