package com.github.epay.config;

import com.github.epay.domain.enums.PaymentEvent;
import com.github.epay.domain.enums.PaymentState;
import com.github.epay.service.statemachine.action.impl.CancelAction;
import com.github.epay.service.statemachine.action.impl.CommitAction;
import com.github.epay.service.statemachine.action.impl.PlacedAction;
import com.github.epay.service.statemachine.action.impl.ProcessAction;
import com.github.epay.service.statemachine.action.impl.TransferAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.nonNull;


/**
 * @author Raman Haurylau
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableStateMachineFactory
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<PaymentState, PaymentEvent> {

    private final CancelAction cancelAction;

    private final CommitAction commitAction;

    private final ProcessAction processAction;

    private final TransferAction transferAction;

    private final PlacedAction placedAction;

    @Override
    public void configure(StateMachineStateConfigurer<PaymentState, PaymentEvent> states) throws Exception {
        states.withStates()
                .initial(PaymentState.NEW)
                .end(PaymentState.COMMITED)
                .end(PaymentState.CANCELED)
                .state(PaymentState.COMMITED, commitAction)
                .state(PaymentState.PLACED, placedAction)
                .state(PaymentState.CANCELED, cancelAction)
                .state(PaymentState.PROCESSED, processAction)
                .state(PaymentState.TRANSFERRED, transferAction)
                .states(EnumSet.allOf(PaymentState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<PaymentState, PaymentEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(PaymentState.NEW)
                .target(PaymentState.PLACED)
                .event(PaymentEvent.PLACE)

                .and()
                .withExternal()
                .source(PaymentState.PLACED)
                .target(PaymentState.PROCESSED)
                .event(PaymentEvent.START_PROCESSING)
                .action(processedActionWait())

                .and()
                .withExternal()
                .source(PaymentState.PROCESSED)
                .target(PaymentState.TRANSFERRED)
                .event(PaymentEvent.TRANSFER)
                .action(transferActionWait())

                .and()
                .withExternal()
                .source(PaymentState.TRANSFERRED)
                .target(PaymentState.COMMITED)
                .event(PaymentEvent.COMMIT);

        for (PaymentState state : EnumSet.of(PaymentState.NEW, PaymentState.PLACED, PaymentState.PROCESSED, PaymentState.TRANSFERRED)) {
            transitions.withExternal().source(state).target(PaymentState.CANCELED).event(PaymentEvent.CANCEL);
        }
    }

    @Bean
    public Action<PaymentState, PaymentEvent> processedActionWait() {
        return context -> {
            log.info("Started processing (30 sec)...");
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                log.error("thread interrupted {0}", e);
            }
            log.info("Processing completed!");
        };
    }

    @Bean
    public Action<PaymentState, PaymentEvent> transferActionWait() {
        return context -> {
            log.info("Started transfer (30 sec)...");
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                log.error("thread interrupted {0}", e);
            }
            log.info("Transfer completed!");
        };
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<PaymentState, PaymentEvent> config) throws Exception {
        StateMachineListenerAdapter<PaymentState, PaymentEvent> listenerAdapter = new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<PaymentState, PaymentEvent> from, State<PaymentState, PaymentEvent> to) {
                if (nonNull(from)) {
                    log.info("State changed from [{}] to [{}]", from.getId(), to.getId());
                }
            }
        };
        config.withConfiguration().listener(listenerAdapter);
    }
}
