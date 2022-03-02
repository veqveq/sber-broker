package ru.veqveq.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import ru.veqveq.core.service.MessageService;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageSourceAccessor sourceAccessor;

    @Override
    public String get(String code, Object... args) {
        return sourceAccessor.getMessage(code, args);
    }
}
