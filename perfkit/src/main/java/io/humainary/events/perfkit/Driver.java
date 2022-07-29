/*
 * Copyright © 2022 JINSPIRED B.V.
 */

package io.humainary.events.perfkit;

import io.humainary.devkit.perfkit.PerfKit;
import io.humainary.events.Events.Context;
import io.humainary.events.Events.Emitter;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;

import static io.humainary.events.Events.context;
import static io.humainary.substrates.Substrates.*;
import static io.humainary.substrates.Substrates.Environment.EMPTY;

@State( Scope.Benchmark )
public class Driver
  implements PerfKit.Driver {

  private static final Outlet< Environment >     OUTLET     = Outlet.empty ();
  private static final Subscriber< Environment > SUBSCRIBER = subscriber ( OUTLET );


  private Name    name;
  private Context context;
  private Emitter emitter;

  @Setup( Level.Trial )
  public final void setup ()
  throws NoSuchMethodException, IOException {

    final var configuration =
      configuration ();

    context =
      context (
        environment (
          lookup (
            path ->
              configuration.apply (
                path.toString ()
              )
          )
        )
      );

    emitter =
      context.emitter (
        name (
          "emitter"
        )
      );

    final var method = getClass ().getMethod (
      "setup"
    );

    name =
      name (
        method
      );

  }

  /**
   * Lookup of an emitter.
   */

  @Benchmark
  public void context_get () {

    context.get (
      name
    );

  }


  /**
   * Lookup of an emitter.
   */

  @Benchmark
  public void context_emitter () {

    context.emitter (
      name
    );

  }


  @Benchmark
  public void context_iterator () {

    context.iterator ();

  }


  @Benchmark
  public void context_foreach () {

    for ( final Emitter e : context ) {
      assert e != null;
    }

  }


  @Benchmark
  public void context_subscribe_cancel () {

    context.subscribe (
      SUBSCRIBER
    ).close ();

  }


  @Benchmark
  public void context_consume_cancel () {

    context.consume (
      OUTLET
    ).close ();

  }


  @Benchmark
  public void events_context () {

    context ();

  }


  @Benchmark
  public void events_context_environment () {

    context (
      EMPTY
    );

  }


  @Benchmark
  public void emitter_emit () {

    emitter.emit ();

  }


  @Benchmark
  public void emitter_emit_empty () {

    emitter.emit (
      EMPTY
    );

  }


  @Benchmark
  public void emitter_emit_single () {

    emitter.emit (
      environment (
        name,
        name
      )
    );

  }


  @Benchmark
  public void emitter_emit_supplier_empty () {

    emitter.emit (
      () ->
        EMPTY
    );

  }

}