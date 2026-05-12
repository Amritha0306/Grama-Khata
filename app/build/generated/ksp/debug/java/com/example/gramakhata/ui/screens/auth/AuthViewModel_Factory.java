package com.example.gramakhata.ui.screens.auth;

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
public final class AuthViewModel_Factory implements Factory<AuthViewModel> {
  private final Provider<LedgerRepository> ledgerRepositoryProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  public AuthViewModel_Factory(Provider<LedgerRepository> ledgerRepositoryProvider,
      Provider<SessionManager> sessionManagerProvider) {
    this.ledgerRepositoryProvider = ledgerRepositoryProvider;
    this.sessionManagerProvider = sessionManagerProvider;
  }

  @Override
  public AuthViewModel get() {
    return newInstance(ledgerRepositoryProvider.get(), sessionManagerProvider.get());
  }

  public static AuthViewModel_Factory create(Provider<LedgerRepository> ledgerRepositoryProvider,
      Provider<SessionManager> sessionManagerProvider) {
    return new AuthViewModel_Factory(ledgerRepositoryProvider, sessionManagerProvider);
  }

  public static AuthViewModel newInstance(LedgerRepository ledgerRepository,
      SessionManager sessionManager) {
    return new AuthViewModel(ledgerRepository, sessionManager);
  }
}
