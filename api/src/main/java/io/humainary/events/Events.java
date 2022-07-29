/*
 * Copyright © 2022 JINSPIRED B.V.
 */

package io.humainary.events;

import io.humainary.events.spi.EventsProvider;
import io.humainary.spi.Providers;
import io.humainary.substrates.Substrates;
import io.humainary.substrates.Substrates.Environment;
import io.humainary.substrates.Substrates.Instrument;
import io.humainary.substrates.Substrates.Name;
import io.humainary.substrates.Substrates.Type;

import java.util.function.Supplier;

import static io.humainary.substrates.Substrates.Environment.EMPTY;
import static io.humainary.substrates.Substrates.type;

/**
 * An open and extensible interface that aims to fundamentally rethink and reimagine how observability events are captured,
 * collected, and communicated within high- and low-latency computing environments. The primary objective of this project
 * is to offer a simplified but highly versatile instrumentation interface that affords efficient and effective
 * contextualization of emitted phenomenon states within application and runtime system spaces.
 */

public final class Events {

  private static final EventsProvider PROVIDER =
    Providers.create (
      "io.humainary.events.spi.factory",
      "io.phaneros.events.spi.alpha.ProviderFactory",
      EventsProvider.class
    );


  private Events () {
  }


  /**
   * Returns the default {@link Context}.
   *
   * @return The default {@link Context}
   */

  public static Context context () {

    return
      PROVIDER.context ();

  }


  /**
   * Returns a {@link Context} mapped based on one or more properties within {@link Environment} provided.
   * <p>
   * Implementation Notes:
   * — The context returned should equal a context returned with the same environment properties.
   *
   * @param environment the configuration used in the mapping and construction of a {@link Context}
   * @return A {@link Context} constructed from and mapped to the {@link Environment}
   */

  public static Context context (
    final Environment environment
  ) {

    return
      PROVIDER.context (
        environment
      );

  }


  /**
   * A context represents some configured boundary within a process space.
   * <p>
   * Note: An SPI implementation of this interface is free to override
   * the default methods implementation included here.
   *
   * @see Events#context(Environment)
   */

  public interface Context
    extends Substrates.Context< Emitter, Environment > {

    /**
     * Returns an emitter that can be used for firing notifications of occurrences.
     *
     * @param name the name used by all events emitted by the returned emitter
     * @return An emitter, specific to this context, that can be used for emitting named event occurrences
     */

    Emitter emitter (
      final Name name
    );

  }


  /**
   * An interface that represents an instrument used for emitting events
   */

  public interface Emitter
    extends Instrument {

    /**
     * The {@link Type} used to identify the interface type of this referent
     */

    Type TYPE = type ( Emitter.class );

    /**
     * Emits an event.
     */

    default void emit () {

      emit (
        EMPTY
      );

    }

    /**
     * Emits an event with a specified {@code Environment} state payload
     *
     * @param environment the environment to be used for event specific state inspection
     */

    void emit (
      Environment environment
    );

    /**
     * Emits an event with a deferred creation of {@code Environment} payload via a provided {@code Supplier}
     *
     * @param supplier the supplier used to produce an {@code Environment} on-demand for event specific state inspection
     */

    default void emit (
      final Supplier< ? extends Environment > supplier
    ) {

      // default is to resolve environment immediately
      // spi implementations are free to override

      emit (
        supplier.get ()
      );

    }

  }

}
