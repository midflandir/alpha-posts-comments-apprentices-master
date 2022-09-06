package com.posada.santiago.alphapostsandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.posada.santiago.alphapostsandcomments.application.handlers.gateways.DomainEventRepository;
import com.posada.santiago.alphapostsandcomments.application.handlers.gateways.EventBus;
import com.posada.santiago.alphapostsandcomments.domain.commands.CreatePostCommand;
import com.posada.santiago.alphapostsandcomments.domain.events.PostCreated;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class CreatePostUseCaseTest {

    @Mock
    private EventBus eventBus;
    @Mock
    private DomainEventRepository domainEventRepository;
    @InjectMocks
    private CreatePostUseCase useCase;

    @Test
    void CreatePostUseCaseTest() {

        CreatePostCommand command = new CreatePostCommand(
                "77777",
                "Jean Camille",
                "我的中文不好"
        );

        PostCreated event = new PostCreated(
                "我的中文不好",
                "Jean Camille"
        );


        Mockito
                .when(this.domainEventRepository.saveEvent(ArgumentMatchers.any(DomainEvent.class)))
                .thenReturn(Mono.just(event));

        Mono<List<DomainEvent>> triggeredevents = this.useCase.apply(Mono.just(command))
                .collectList();

        StepVerifier.create(triggeredevents)
                .expectSubscription()
                .expectNextMatches(domainEvents ->
                        domainEvents.size() == 1 &&
                                domainEvents.get(0) instanceof PostCreated &&
                        ((PostCreated) domainEvents.get(0)).getTitle().equals("我的中文不好") &&
                                ((PostCreated) domainEvents.get(0)).getAuthor().equals("Jean Camille")
                ).verifyComplete();

        Mockito.verify(this.eventBus, BDDMockito.times(1))
                .publish(ArgumentMatchers.any(DomainEvent.class));

        Mockito.verify(this.domainEventRepository, BDDMockito.times(1))
                .saveEvent(ArgumentMatchers.any(DomainEvent.class));

    }
}