package com.posada.santiago.alphapostsandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.posada.santiago.alphapostsandcomments.application.handlers.gateways.DomainEventRepository;
import com.posada.santiago.alphapostsandcomments.application.handlers.gateways.EventBus;
import com.posada.santiago.alphapostsandcomments.business.generic.UseCaseForCommand;
import com.posada.santiago.alphapostsandcomments.domain.Post;
import com.posada.santiago.alphapostsandcomments.domain.commands.ChangeFontCommand;
import com.posada.santiago.alphapostsandcomments.domain.values.CommentId;
import com.posada.santiago.alphapostsandcomments.domain.values.Font;
import com.posada.santiago.alphapostsandcomments.domain.values.PostId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Slf4j
@Component
public class ChangeFontUseCase extends UseCaseForCommand<ChangeFontCommand> {
    private final DomainEventRepository repository;
    private final EventBus bus;

    public ChangeFontUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<ChangeFontCommand> changeFontCommandMono) {
        return changeFontCommandMono.flatMapMany(command ->
                repository.findById(command.getPostId())
                        .collectList()
                        .flatMapIterable(domainEvents -> {
                                Post post = Post.from(PostId.of(command.getPostId()), domainEvents);
                                post.Changefont(CommentId.of(command.getCommentId()), new Font(command.getFont()));
                            log.info(" Congratulations, Comment is being added, looks like everything is working fine so far ");
                            return  post.getUncommittedChanges();
                        }).flatMap(event -> repository.saveEvent(event))).doOnNext(bus::publish)
                .doOnError(error -> log.error("Error - Font couldn't be change." + error));
    }
}
