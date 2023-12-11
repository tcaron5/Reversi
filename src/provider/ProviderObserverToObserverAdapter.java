package provider;

import java.util.Objects;

import provider.model.GameEvents;
import controller.ObserverInterface;

/**
 * Turns providers observer into our observer.
 */
public class ProviderObserverToObserverAdapter implements ObserverInterface {

  private final GameEvents providerObserver;

  public ProviderObserverToObserverAdapter(GameEvents providerObserver) {
    Objects.requireNonNull(providerObserver);
    this.providerObserver = providerObserver;
  }

  @Override
  public void getNotifiedItsYourPlayersMove() {
    providerObserver.yourTurn();
  }
}
