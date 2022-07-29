/*
 * Copyright © 2022 JINSPIRED B.V.
 */

package io.humainary.events.perfkit;

import io.humainary.devkit.perfkit.PerfKit.Target;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.humainary.devkit.perfkit.PerfKit.Driver.ALL;
import static io.humainary.devkit.perfkit.PerfKit.execute;
import static io.humainary.devkit.perfkit.PerfKit.target;

@TestMethodOrder(
  OrderAnnotation.class
)
final class DriverTest {

  private static final Target TARGET =
    target (
      Driver.class,
      "events",
      "io.phaneros.events.spi.alpha.ProviderFactory"
    );

  private static final String PROFILE    = "spi";
  private static final String CONCURRENT = "emitter_";

  @Test
  @Order( 1 )
  void one () {

    execute (
      TARGET,
      PROFILE,
      ALL,
      1,
      250.0,
      Assertions::fail
    );

  }

  @Test
  @Order( 2 )
  void two () {

    execute (
      TARGET,
      PROFILE,
      CONCURRENT,
      2,
      500.0,
      Assertions::fail
    );

  }

  @Test
  @Order( 4 )
  void four () {

    execute (
      TARGET,
      PROFILE,
      CONCURRENT,
      4,
      1000.0,
      Assertions::fail
    );

  }

}
