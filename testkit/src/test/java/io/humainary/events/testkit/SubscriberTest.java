/*
 * Copyright © 2022 JINSPIRED B.V.
 */

package io.humainary.events.testkit;

import io.humainary.events.Events.Context;
import io.humainary.events.Events.Emitter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.humainary.devkit.testkit.TestKit.capture;
import static io.humainary.devkit.testkit.TestKit.recorder;
import static io.humainary.events.Events.context;
import static io.humainary.substrates.Substrates.Environment.EMPTY;
import static io.humainary.substrates.Substrates.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The test class for the {@link Subscriber} and {@link Subscription} interfaces.
 *
 * @author wlouth
 * @since 1.0
 */

final class SubscriberTest {

  private static final String VALUE_NA = null;
  private static final String VALUE_1  = "1";
  private static final String VALUE_2  = "2";

  private static final Name NAME    = name ( "variable" );
  private static final Name E1_NAME = name ( "event" ).name ( "1" );
  private static final Name E2_NAME = name ( "event" ).name ( "2" );

  private static final Variable< String > VAR = variable ( NAME, VALUE_NA );

  private Context context;
  private Emitter e1;
  private Emitter e2;

  @BeforeEach
  void setup () {

    context =
      context (
        EMPTY
      );

    e1 =
      context.emitter (
        E1_NAME
      );


    e2 =
      context.emitter (
        E2_NAME
      );

  }

  @Test
  void subscribe () {

    final var recorder =
      recorder (
        context,
        VAR::of
      );

    recorder.start ();

    final var environment =
      environment (
        NAME,
        VALUE_1
      );

    e1.emit ();

    e2.emit (
      environment
    );

    e2.emit (
      environment.override (
        NAME,
        VALUE_2
      )
    );

    final var capture =
      recorder
        .stop ()
        .orElseThrow (
          AssertionError::new
        );

    e1.emit ();
    e2.emit ();

    assertEquals (
      capture (
        e1,
        VALUE_NA
      ).to (
        e2,
        VALUE_1
      ).to (
        e2,
        VALUE_2
      ),
      capture
    );

  }

}
