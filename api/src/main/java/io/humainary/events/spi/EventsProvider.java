/*
 * Copyright © 2022 JINSPIRED B.V.
 */

package io.humainary.events.spi;

import io.humainary.events.Events.Context;
import io.humainary.spi.Providers;
import io.humainary.substrates.Substrates.Environment;

/**
 * The service provider interface for the humainary events runtime.
 * <p>
 * Note: An SPI implementation of this interface is free to override
 * the default methods implementation included here.
 *
 * @author wlouth
 * @since 1.0
 */

public interface EventsProvider
  extends Providers.Provider {

  Context context ();

  Context context (
    Environment environment
  );

}
