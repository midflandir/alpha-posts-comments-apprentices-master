package com.posada.santiago.alphapostsandcomments.application.handlers.gateways;

import co.com.sofka.domain.generic.DomainEvent;

public interface EventBus {
    void publish(DomainEvent event);

    void publishError(Throwable errorEvent);
}
