package com.example.gramakhata.ui.screens.splash;

import com.example.gramakhata.data.local.SessionManager;
import com.example.gramakhata.data.repository.LedgerRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class SplashViewModel_Factory implements Factory<SplashViewModel> {
  private final Provider<LedgerRepository> ledgerRepositoryProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  public SplashViewModel_Factory(Provider<LedgerRepository> ledgerRepositoryProvider,
      Provider<SessionManager> sessionManagerProvider) {
    this.ledgerRepositoryProvider = ledgerRepositoryProvider;
    this.sessionManagerProvider = sessionManagerProvider;
  }

  @Override
  public SplashViewModel get() {
    return newInstance(ledgerRepositoryProvider.get(), sessionManagerProvider.get());
  }

  public static SplashViewModel_Factory create(Provider<LedgerRepository> ledgerRepositoryProvider,
      Provider<SessionManager> sessionManagerProvider) {
    return new SplashViewModel_Factory(ledgerRepositoryProvider, sessionManagerProvider);
  }

  public static SplashViewModel newInstance(LedgerRepository ledgerRepository,
      SessionManager sessionManager) {
    return new SplashViewModel(ledgerRepository, sessionManager);
  }
}
